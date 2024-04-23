package com.fongmi.android.tv.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.Setting;
import com.fongmi.android.tv.databinding.FragmentSettingCustomBinding;
import com.fongmi.android.tv.event.RefreshEvent;
import com.fongmi.android.tv.impl.PasswordCallback;
import com.fongmi.android.tv.ui.base.BaseFragment;
import com.fongmi.android.tv.ui.dialog.PasswordDialog;
import com.fongmi.android.tv.utils.ResUtil;
import com.fongmi.android.tv.utils.SpUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;

public class SettingCustomFragment extends BaseFragment implements PasswordCallback {

    private FragmentSettingCustomBinding mBinding;
    private String[] size;

    private String defaultPassword="86383728";

    public static SettingCustomFragment newInstance() {
        return new SettingCustomFragment();
    }

    private String getSwitch(boolean value) {
        return getString(value ? R.string.setting_on : R.string.setting_off);
    }

    @Override
    protected ViewBinding getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return mBinding = FragmentSettingCustomBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        mBinding.sizeText.setText((size = ResUtil.getStringArray(R.array.select_size))[Setting.getSize()]);
        mBinding.danmuSyncText.setText(getSwitch(Setting.isDanmuSync()));
        mBinding.speedText.setText(getSpeedText());
        mBinding.incognitoText.setText(getSwitch(Setting.isIncognito()));
        mBinding.aggregatedSearchText.setText(getSwitch(Setting.isAggregatedSearch()));
        mBinding.homeChangeConfigText.setText(getSwitch(Setting.isHomeChangeConfig()));
        String word= SpUtils.getString("password");
        if (!TextUtils.isEmpty(word)){
            defaultPassword=word;
        }
        mBinding.homePasswordConfigText.setText(defaultPassword);
    }

    @Override
    protected void initEvent() {
        mBinding.title.setOnLongClickListener(this::onTitle);
        mBinding.size.setOnClickListener(this::setSize);
        mBinding.danmuSync.setOnClickListener(this::setDanmuSync);
        mBinding.speed.setOnClickListener(this::setSpeed);
        mBinding.speed.setOnLongClickListener(this::resetSpeed);
        mBinding.incognito.setOnClickListener(this::setIncognito);
        mBinding.aggregatedSearch.setOnClickListener(this::setAggregatedSearch);
        mBinding.homeChangeConfig.setOnClickListener(this::setHomeChangeConfig);
        mBinding.homePassword.setOnClickListener(this::onPassword);
    }

    private boolean onTitle(View view) {
        mBinding.danmuSync.setVisibility(View.VISIBLE);
        return true;
    }

    private void onPassword(View view){
        PasswordDialog.create(this).type(1).show();
    }

    private void setSize(View view) {
        new MaterialAlertDialogBuilder(getActivity()).setTitle(R.string.setting_size).setNegativeButton(R.string.dialog_negative, null).setSingleChoiceItems(size, Setting.getSize(), (dialog, which) -> {
            mBinding.sizeText.setText(size[which]);
            Setting.putSize(which);
            RefreshEvent.size();
            dialog.dismiss();
        }).show();
    }

    private void setDanmuSync(View view) {
        Setting.putDanmuSync(!Setting.isDanmuSync());
        mBinding.danmuSyncText.setText(getSwitch(Setting.isDanmuSync()));
    }

    private String getSpeedText() {
        return String.format(Locale.getDefault(), "%.2f", Setting.getPlaySpeed());
    }

    private void setSpeed(View view) {
        float speed = Setting.getPlaySpeed();
        float addon = speed >= 2 ? 1.0f : 0.1f;
        speed = speed >= 5 ? 0.2f : Math.min(speed + addon, 5.0f);
        Setting.putPlaySpeed(speed);
        mBinding.speedText.setText(getSpeedText());
    }

    private boolean resetSpeed(View view) {
        Setting.putPlaySpeed(1.0f);
        mBinding.speedText.setText(getSpeedText());
        return true;
    }

    private void setIncognito(View view) {
        Setting.putIncognito(!Setting.isIncognito());
        mBinding.incognitoText.setText(getSwitch(Setting.isIncognito()));
    }

    private void setAggregatedSearch(View view) {
        Setting.putAggregatedSearch(!Setting.isAggregatedSearch());
        mBinding.aggregatedSearchText.setText(getSwitch(Setting.isAggregatedSearch()));
    }

    private void setHomeChangeConfig(View view) {
        Setting.putHomeChangeConfig(!Setting.isHomeChangeConfig());
        mBinding.homeChangeConfigText.setText(getSwitch(Setting.isHomeChangeConfig()));
        RefreshEvent.config();
    }

    @Override
    public void setPassword(String password) {
        mBinding.homePasswordConfigText.setText(password);
    }
}
