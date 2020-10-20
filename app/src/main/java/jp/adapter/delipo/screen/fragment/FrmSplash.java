package jp.adapter.delipo.screen.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.entity.BackStackData;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_SPLASH;

public class FrmSplash extends BaseFragment implements View.OnClickListener {

    public static FrmSplash getInstance() {
        return new FrmSplash();
    }

    public static FrmPolicy getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmPolicy fragment = new FrmPolicy();
        if (arrayList != null) {
            Bundle data = new Bundle();
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_splash;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_SPLASH;
    }

    @Override
    protected void finish() {
        getActivity().finish();
    }

    @Override
    public boolean isBackPreviousEnable() {
        return true;
    }

    @Override
    public void backToPrevious() {
        finish();
    }

    @Override
    protected void loadControlsAndResize(View view) {
        Button mButtonLogin = view.findViewById(R.id.button_login_splash);
        mButtonLogin.getLayoutParams().width = activity.getSizeWithScale(853);
        mButtonLogin.getLayoutParams().height = activity.getSizeWithScale(178);
        View ivBgSplash = view.findViewById(R.id.ivBgSplash);
        ivBgSplash.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgSplash.getLayoutParams().height = activity.getSizeWithScale(2340);
        view.findViewById(R.id.button_login_splash).setOnClickListener(this);
        view.findViewById(R.id.text_view_register_account).setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.showLocationRequests(R.string.lbl_tvHeader, R.string.lbl_tvMessageTop, R.string.lbl_tvMessageBottom, false, DialogInterface::dismiss);
    }

    private void showPolicyPage() {
        try {
            ArrayList<BackStackData> arrayList = null;
            HashMap<String, Object> mapData = new HashMap<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
//            mapData.put(EXTRA_ROUTE, true);
            activity.showPolicy(arrayList, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showLoginPage() {
        try {
            ArrayList<BackStackData> arrayList = null;
            HashMap<String, Object> mapData = new HashMap<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
//            mapData.put(EXTRA_RATE_PRODUCT, null);
            activity.showLogin(arrayList, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (!isClickAble())
            return;
        switch (v.getId()) {
            case R.id.button_login_splash:
                showLoginPage();
                break;
            case R.id.text_view_register_account:
                showPolicyPage();
                break;
        }
    }
}