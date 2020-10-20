package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_NOTIF_CONFIRM_EMAIL;

public class FrmNotifConfirmEmail extends BaseFragment implements View.OnClickListener {

    public static FrmNotifConfirmEmail getInstance() {
        return new FrmNotifConfirmEmail();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_notif_confirm_email;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_NOTIF_CONFIRM_EMAIL;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.frm_notif_confirm_email).setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frm_notif_confirm_email:
                activity.addFragmentToMain(FrmWarningWeb.getInstance());
                break;
        }
    }
}