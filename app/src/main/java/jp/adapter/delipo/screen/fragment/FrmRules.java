package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_RULES;

public class FrmRules extends BaseFragment implements View.OnClickListener {

    public static FrmRules getInstance() {
        return new FrmRules();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_rules;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_RULES;
    }

    @Override
    protected void loadControlsAndResize(View view) {

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

    }
}