package com.fongmi.android.tv.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.Setting;
import com.fongmi.android.tv.databinding.DialogPassBinding;
import com.fongmi.android.tv.databinding.DialogPasswordBinding;
import com.fongmi.android.tv.impl.BufferCallback;
import com.fongmi.android.tv.impl.PasswordCallback;
import com.fongmi.android.tv.utils.SpUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.lang.reflect.Field;

public class PasswordDialog {

    private final DialogPasswordBinding binding;
    private final PasswordCallback callback;
    private Fragment mContext;
    //0不可取消1可以取消
    private int type;

    private String defaultPassword="86383728";
    public static PasswordDialog create(Fragment fragment) {
        return new PasswordDialog(fragment);
    }

    public PasswordDialog(Fragment fragment) {
        this.mContext=fragment;
        this.callback = (PasswordCallback) fragment;
        this.binding = DialogPasswordBinding.inflate(LayoutInflater.from(fragment.getContext()));
    }

    public PasswordDialog type(int type) {
        this.type = type;
        return this;
    }

    public void show() {
        initDialog();
    }

    private void initDialog() {
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
                    String word=SpUtils.getString("password");
                    if (!TextUtils.isEmpty(word)){
                        defaultPassword=word;
                    }
                    if (password.equals(defaultPassword)){
                        dialog.cancel();
                    }else{
                        binding.password.setText("");
                        Toast.makeText(mContext.getContext(), "密码输入错误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (password.length()>=6){
                        callback.setPassword(password);
                        SpUtils.putString("password",password);
                        dialog.cancel();
                    }else{
                        Toast.makeText(mContext.getContext(), "请输入6位数密码", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }





}
