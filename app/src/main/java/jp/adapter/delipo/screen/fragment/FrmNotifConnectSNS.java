package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_NOTIF_CONNECT_SNS;

public class FrmNotifConnectSNS extends BaseFragment implements View.OnClickListener {

    public static FrmNotifConnectSNS getInstance() {
        return new FrmNotifConnectSNS();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new Handler().postDelayed(() -> {
//            try {
//                if (!activity.isFinishing()) {
//                    activity.addFragmentToMain(FrmRegisterSNS.getInstance());
//                    finish();
//                }
//            } catch (Throwable ignored) {
//            }
//        }, 2000);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_notif_connect_sns;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_NOTIF_CONNECT_SNS;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.frm_notif_connect_sns).setOnClickListener(this);
    }

    @Override
    protected void finish() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frm_notif_connect_sns:
                activity.addFragmentToMain(FrmRegisterSNS.getInstance());
                break;
        }
    }
}