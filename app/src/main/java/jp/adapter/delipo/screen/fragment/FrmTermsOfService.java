package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_TERMS_OF_SERVICE;

public class FrmTermsOfService extends BaseFragment implements View.OnClickListener {

    public static FrmTermsOfService getInstance() {
        return new FrmTermsOfService();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_terms_of_service;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_TERMS_OF_SERVICE;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.btnAgree).setOnClickListener(this);
        view.findViewById(R.id.btnDisAgree).setOnClickListener(this);
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
            case R.id.btnAgree:
//                activity.addFragmentToMain(FrmRegister.getInstance());
                break;
            case R.id.btnDisAgree:
                activity.addFragmentToMain(FrmDenyTermsOfService.getInstance());
                break;
        }
    }
}