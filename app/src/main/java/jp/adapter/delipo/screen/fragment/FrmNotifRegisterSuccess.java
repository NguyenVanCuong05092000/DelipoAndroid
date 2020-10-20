package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_NOTIF_REGISTER_SUCCESS;

public class FrmNotifRegisterSuccess extends BaseFragment implements View.OnClickListener {

    public static FrmNotifRegisterSuccess getInstance() {
        return new FrmNotifRegisterSuccess();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_notif_register_success;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_NOTIF_REGISTER_SUCCESS;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.frm_notif_register_success).setOnClickListener(this);
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
            case R.id.frm_notif_register_success:
                activity.addFragmentToMain(FrmLogin.getInstance());
                break;
        }
    }
}