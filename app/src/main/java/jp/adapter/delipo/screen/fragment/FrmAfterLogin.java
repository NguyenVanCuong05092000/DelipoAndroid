package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_AFTER_LOGIN;

public class FrmAfterLogin extends BaseFragment implements View.OnClickListener {

    public static FrmAfterLogin getInstance() {
        return new FrmAfterLogin();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_after_login;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_AFTER_LOGIN;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View ivBgAfterLogin = view.findViewById(R.id.ivBgAfterLogin);
        ivBgAfterLogin.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgAfterLogin.getLayoutParams().height = activity.getSizeWithScale(2340);

        View imgLogoAfterLogin = view.findViewById(R.id.imgLogoAfterLogin);
        imgLogoAfterLogin.getLayoutParams().width = activity.getSizeWithScale(847);
        imgLogoAfterLogin.getLayoutParams().height = activity.getSizeWithScale(328);

        Button button_start_DELIPO = view.findViewById(R.id.button_start_DELIPO);
        button_start_DELIPO.getLayoutParams().width = activity.getSizeWithScale(853);
        button_start_DELIPO.getLayoutParams().height = activity.getSizeWithScale(178);

        button_start_DELIPO.setOnClickListener(this);
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
    protected void finish() {
        try {
            activity.addFragmentToMain(new FrmIntro());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        activity.addFragmentToMain(new FrmIntro());
    }
}
