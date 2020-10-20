package jp.adapter.delipo.screen.fragment;

import android.view.View;
import android.widget.Button;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_AFTER_REGISTER;

public class FrmAfterRegister extends BaseFragment implements View.OnClickListener {

    public static FrmAfterRegister getInstance() {
        return new FrmAfterRegister();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_after_register;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_AFTER_REGISTER;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View ivBgAfterRegister = view.findViewById(R.id.ivBgAfterRegister);
        ivBgAfterRegister.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgAfterRegister.getLayoutParams().height = activity.getSizeWithScale(2340);

        Button button = view.findViewById(R.id.button_after_register);
        button.getLayoutParams().width = activity.getSizeWithScale(853);
        button.getLayoutParams().height = activity.getSizeWithScale(178);
        button.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_after_register:
                activity.addFragmentToMain(FrmLogin.getInstance());
                break;
        }
    }
}