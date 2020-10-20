package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_AFTER_RESET_PASS;


public class FrmAfterResetPass extends BaseFragment implements View.OnClickListener {

    private Button mBtnCompletedRePass;

    public static FrmAfterResetPass getInstance() {
        return new FrmAfterResetPass();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_after_reset_pass;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_AFTER_RESET_PASS;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        mBtnCompletedRePass = view.findViewById(R.id.button_completed_reset_pass);

    }

    @Override
    protected void finish() {
        try {
            activity.addFragmentToMain(FrmLogin.getInstance());
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        mBtnCompletedRePass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_completed_reset_pass:
                activity.addFragmentToMain(FrmLogin.getInstance());
        }

    }
}