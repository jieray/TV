package com.fongmi.android.tv.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.Setting;
import com.fongmi.android.tv.databinding.DialogPasswordBinding;
import com.fongmi.android.tv.impl.PasswordCallback;
import com.fongmi.android.tv.utils.DateUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PasswordDialog {

    private final DialogPasswordBinding binding;
    private final PasswordCallback callback;
    private Activity mContext;
    //0不可取消1可以取消
    private int type;

    private String defaultPassword="";
    public static PasswordDialog create(Activity activity) {
        return new PasswordDialog(activity);
    }

    public PasswordDialog(Activity activity) {
        this.mContext=activity;
        this.callback = (PasswordCallback) activity;
        this.binding = DialogPasswordBinding.inflate(LayoutInflater.from(activity));
    }

    public PasswordDialog type(int type) {
        this.type = type;
        return this;
    }

    public void show() {
        initDialog();
    }

    private void initDialog() {
        defaultPassword= "57"+DateUtil.getStringDateShort5();
        Log.e("jieray","defaultPassword-----"+defaultPassword);
        AlertDialog dialog = new MaterialAlertDialogBuilder(binding.getRoot().getContext()).setTitle(R.string.setting_home_password).setView(binding.getRoot()).setPositiveButton(R.string.dialog_positive, null).create();
        if (type==0){
            dialog.getWindow().setDimAmount(1f);
            dialog.setCancelable(false);
        }else{
            dialog.getWindow().setDimAmount(0);
            dialog.setCancelable(true);
        }
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.password.getText().toString();

                if (type==0){
                    String word= Setting.getPassword();
                    if (!TextUtils.isEmpty(word)){
                        defaultPassword=word;
                    }
                    if (password.equals(defaultPassword)){
                        Setting.putPassword(password);
                        dialog.cancel();
                    }else{
                        binding.password.setText("");
                        Toast.makeText(mContext, "密码输入错误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (password.length()>=6){
                        callback.setPassword(password);
                        Setting.putPassword(password);
                        dialog.cancel();
                    }else{
                        Toast.makeText(mContext, "请输入6位数密码", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }





}
