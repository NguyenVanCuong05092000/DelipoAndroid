package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_WARNING_WEB;

public class FrmWarningWeb extends BaseFragment implements View.OnClickListener {

    public static FrmWarningWeb getInstance() {
        return new FrmWarningWeb();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_warning_web;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_WARNING_WEB;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.frm_warning_web).setOnClickListener(this);
    }

    @Override
    protected void finish() {
        try {
            activity.backToPreviousFromBackStack(getArguments());
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
            case R.id.frm_warning_web:
                activity.addFragmentToMain(FrmRegisterInformation.getInstance());
                break;
        }
    }
}
