package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_NOTIF_ANALYZE;

public class FrmNotifAnalyze extends BaseFragment implements View.OnClickListener {

    public static FrmNotifAnalyze getInstance() {
        return new FrmNotifAnalyze();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        new Handler().postDelayed(() -> {
//            try {
//                showAskToAns();
//            } catch (Throwable ignored) {
//            }
//        }, 1500);
    }

    public static FrmNotifAnalyze getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmNotifAnalyze fragment = new FrmNotifAnalyze();
        Bundle data = new Bundle();
        if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_PRODUCT))
            data.putSerializable(ExtraConstants.EXTRA_PRODUCT, (ProductEntity) mapData.get(ExtraConstants.EXTRA_PRODUCT));
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    public static FrmNotifAnalyze getInstance(ProductEntity productEntity, int fromFragment) {
        FrmNotifAnalyze fragment = new FrmNotifAnalyze();
        Bundle data = new Bundle();
        data.putSerializable(ExtraConstants.EXTRA_PRODUCT, productEntity);
        data.putInt(ExtraConstants.EXTRA_FROM, fromFragment);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_notif_analyze;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_NOTIF_ANALYZE;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        view.findViewById(R.id.frameLayout).setOnClickListener(this);
    }

    @Override
    public boolean isBackPreviousEnable() {
        return true;
    }

    @Override
    public void backToPrevious() {
        finish();
    }

    private void showAskToAns() {
        try {
            ArrayList<BackStackData> arrayList = null;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
            activity.showAskToAns(arrayList, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finish() {
        try {
            activity.addFragmentToMain(FrmEditProduct.getInstance(null, null));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frameLayout:
//                showAskToAns();
                try {
                    activity.addFragmentToMain(FrmAskToAns.getInstance());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}