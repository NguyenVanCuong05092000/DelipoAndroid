package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_TIME_PICKER;

public class FrmTimePicker extends BaseFragment implements View.OnClickListener {

    public static FrmTimePicker getInstance() {
        return new FrmTimePicker();
    }

    public static FrmTimePicker getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmTimePicker fragment = new FrmTimePicker();
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
        return R.layout.frm_time_picker;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_TIME_PICKER;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.cl0_5).setOnClickListener(this);
        view.findViewById(R.id.cl1_0).setOnClickListener(this);
        view.findViewById(R.id.cl1_5).setOnClickListener(this);
        view.findViewById(R.id.cl2_0).setOnClickListener(this);
        view.findViewById(R.id.cl2_5).setOnClickListener(this);
        view.findViewById(R.id.cl3_0).setOnClickListener(this);
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
            activity.addFragmentToMain(FrmAskToAns.getInstance());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showNotifSuccess() {
        try {
            ArrayList<BackStackData> arrayList = null;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
            activity.showNotifSuccess(arrayList, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl0_5:
            case R.id.cl1_5:
            case R.id.cl2_5:
            case R.id.cl1_0:
            case R.id.cl2_0:
            case R.id.cl3_0:
//                showNotifSuccess();
                try {
                    activity.addFragmentToMain(FrmNotifSuccess.getInstance());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}