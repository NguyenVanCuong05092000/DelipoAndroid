package jp.adapter.delipo.screen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;
import jp.adapter.delipo.screen.activities.SplashActivity;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_NOTIF_SUCCESS;

public class FrmNotifSuccess extends BaseFragment implements View.OnClickListener {

    public static FrmNotifSuccess getInstance() {
        return new FrmNotifSuccess();
    }

    public static FrmNotifSuccess getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmNotifSuccess fragment = new FrmNotifSuccess();
        Bundle data = new Bundle();
        if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_PRODUCT))
            data.putSerializable(ExtraConstants.EXTRA_PRODUCT, (ProductEntity) mapData.get(ExtraConstants.EXTRA_PRODUCT));
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_notif_success;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_NOTIF_SUCCESS;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.textView).setOnClickListener(this);

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
            activity.addFragmentToMain(FrmTimePicker.getInstance());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                Intent intent =new Intent(getContext(), SplashActivity.class);
                startActivity(intent);
                break;
        }
    }
}