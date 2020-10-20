package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_ASK_TO_ANS;

public class FrmAskToAns extends BaseFragment implements View.OnClickListener {

    public static FrmAskToAns getInstance() {
        return new FrmAskToAns();
    }

    public static FrmAskToAns getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmAskToAns fragment = new FrmAskToAns();
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
        return R.layout.frm_ask_to_answer;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_ASK_TO_ANS;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.tvYes).setOnClickListener(this);
        view.findViewById(R.id.tvNo).setOnClickListener(this);
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
            activity.addFragmentToMain(FrmNotifAnalyze.getInstance());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showTimePicker() {
        try {
            ArrayList<BackStackData> arrayList = null;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
            activity.showTimePicker(arrayList, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvYes:
//                showTimePicker();
                try {
                    activity.addFragmentToMain(FrmTimePicker.getInstance());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvNo:
                activity.backToHome();
                break;
        }
    }
}