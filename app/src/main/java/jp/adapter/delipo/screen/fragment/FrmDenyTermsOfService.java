package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_DENY_TERMS_OF_SERVICE;

public class FrmDenyTermsOfService extends BaseFragment implements View.OnClickListener {

    public static FrmDenyTermsOfService getInstance() {
        return new FrmDenyTermsOfService();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_deny_terms_of_service;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_DENY_TERMS_OF_SERVICE;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.frm_deny_terms_of_service).setOnClickListener(this);
    }

    @Override
    protected void finish() {
        try {
            activity.addFragmentToMain(FrmTermsOfService.getInstance());
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
            case R.id.frm_deny_terms_of_service:
                activity.addFragmentToMain(FrmTermsOfService.getInstance());
                break;
        }
    }
}