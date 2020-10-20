package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;

import static com.facebook.FacebookSdk.getApplicationContext;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_POLICY;

public class FrmPolicy extends BaseFragment implements View.OnClickListener {

    private TextView mTextViewWarning;
    private WebView mContentPolicy;
    private CheckBox mCheckBoxPolicy;
    private CheckBox mCheckBoxSecurity;

    public static FrmPolicy getInstance() {
        return new FrmPolicy();
    }

    public static FrmPolicy getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmPolicy fragment = new FrmPolicy();
        Bundle data = new Bundle();
        if (mapData != null && mapData.containsKey(EXTRA_ROUTE))
            data.putSerializable(ExtraConstants.EXTRA_ROUTE, (Serializable) mapData.get(ExtraConstants.EXTRA_ROUTE));
        if (arrayList != null) {
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_policy;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_POLICY;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        Button mButtonRegisterPolicy = view.findViewById(R.id.button_register_policy);
        mButtonRegisterPolicy.getLayoutParams().width = activity.getSizeWithScale(853);
        mButtonRegisterPolicy.getLayoutParams().height = activity.getSizeWithScale(178);
        mTextViewWarning = view.findViewById(R.id.text_view_warning_policy);
        mContentPolicy = view.findViewById(R.id.content_policy);
        mCheckBoxPolicy = view.findViewById(R.id.check_box_policy);
        mCheckBoxSecurity = view.findViewById(R.id.check_bot_security);
        mButtonRegisterPolicy.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentPolicy.loadUrl("file:///android_asset/index.html");
    }

    private void showRegisterAccount() {
        try {
            ArrayList<BackStackData> arrayList = null;
            HashMap<String, Object> mapData = new HashMap<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
//            mapData.put(EXTRA_RATE_PRODUCT, null);
            activity.showRegisterAccount(arrayList, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finish() {
        try {
            ArrayList<BackStackData> arrayList = new ArrayList<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList != null) {
                if (arrayList.size() >= 2) {
                    activity.backToPreviousFromBackStack(getArguments());
                } else activity.addFragmentToMain(FrmSplash.getInstance());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register_policy:
                if (mCheckBoxSecurity.isChecked() && mCheckBoxPolicy.isChecked()) {
                    mTextViewWarning.setVisibility(View.GONE);
                    showRegisterAccount();
                } else mTextViewWarning.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}