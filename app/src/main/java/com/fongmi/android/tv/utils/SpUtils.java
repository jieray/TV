package com.fongmi.android.tv.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SpUtils
 * @Description TODO
 * @Author jieray
 * @Date 2022/4/23 16:23
 */
public class SpUtils {

    private static SharedPreferences sp = null;

    /**初始化SpUtil工具类
     * @param application 用于获取上下文
     * @param spName 设置SharedPreferences文件名(不用添加后缀)，默认名称：SpUtil.xml*/
    public static void init(@NonNull Application application, String spName) {
        if (sp == null) {
            sp = application.getApplicationContext().getSharedPreferences(
                    TextUtils.isEmpty(spName) ? "SpUtil" : spName, Context.MODE_PRIVATE);
        }
    }

    /**
     * 存入字符串
     * @param key     字符串的键
     * @param value   字符串的值
     */
    @SuppressLint("ApplySharedPref")
    public static void putString(String key, String value) {
        //存入数据
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符串
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getString(String key) {return getString(key, "");}

    /**
     * 获取字符串
     * @param key     字符串的键
     * @param defValue   字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(String key, String defValue)
    {return sp.getString(key, defValue);}

    /**
     * 保存布尔值
     * @param key     键
     * @param value   值
     */
    @SuppressLint("ApplySharedPref")
    public static void putBoolean(String key, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取布尔值
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getBoolean(String key, boolean defValue)
    {return sp.getBoolean(key, defValue);}

    /**
     * 保存long值
     * @param key     键
     * @param value   值
     */
    @SuppressLint("ApplySharedPref")
    public static void putLong(String key, long value) {
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取long值
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static long getLong(String key, long defValue) {return sp.getLong(key, defValue);}

    /**
     * 保存int值
     * @param key     键
     * @param value   值
     */
    @SuppressLint("ApplySharedPref")
    public static void putInt(String key, int value) {
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取int值
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static int getInt(String key, int defValue) {return sp.getInt(key, defValue);}


    /**
     * 获取对象
     * @param key     键
     * @param <T>     指定泛型
     * @return 泛型对象
     */
    @Nullable
    public static <T extends Serializable> T getObject(String key)
    { return base64ToObj(getString(key));}

    /**
     * 存储List集合
     * @param key 存储的键
     * @param list 存储的集合
     */
    public static void putList(String key, List<? extends Serializable> list)
    {putString(key, obj2Base64(list));}

    /**
     * 获取List集合
     * @param key 键
     * @param <E> 指定泛型
     * @return List集合
     */
    @Nullable
    public static <E extends Serializable> List<E> getList(String key)
    {return (List<E>) base64ToObj(getString(key));}

    /**
     * 存储Map集合
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <V> 指定Map的值
     */
    public static <K extends Serializable, V> void putMap(String key, Map<K, V> map)
    {putString(key, obj2Base64(map));}

    /**
     * 获取map集合
     * @param key 键
     * @param <K> 指定Map的键
     * @param <V> 指定Map的值
     * @return 存储的集合
     */
    @Nullable
    public static <K extends Serializable, V> Map<K, V> getMap(String key)
    {return (Map<K, V>) base64ToObj(getString(key));}

    /**
     * 对象转字符串
     * @param obj 任意对象
     * @return base64字符串
     */
    private static String obj2Base64(Object obj) {
        //判断对象是否为空
        if (obj == null) {return null;}
        ByteArrayOutputStream baos      = null;
        ObjectOutputStream    oos       = null;
        String                objectStr = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            // 将对象放到OutputStream中
            // 将对象转换成byte数组，并将其进行base64编码
            objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        } catch (Exception e) {e.printStackTrace();} finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {e.printStackTrace();}
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        return objectStr;
    }

    /**
     * base64转对象
     * @param base64 字符串
     * @param <T> 指定转成的类型
     * @return 指定类型对象 失败返回null
     */
    private static <T> T base64ToObj(String base64) {
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(base64)) {return null;}
        byte[]               objBytes = Base64.decode(base64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais     = null;
        ObjectInputStream    ois      = null;
        T                    t        = null;
        try {
            bais = new ByteArrayInputStream(objBytes);
            ois = new ObjectInputStream(bais);
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {e.printStackTrace();}
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        return t;
    }

    /**
     * 移除字符串
     * @param key     字符串的键
     */
    public static void removeByKey(String key) {
        Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * SP中清除所有数据
     */
    public static void clear() {
        sp.edit().clear().apply();
    }

}
