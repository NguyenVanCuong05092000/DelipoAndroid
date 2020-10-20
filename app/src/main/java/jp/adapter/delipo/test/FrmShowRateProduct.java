package jp.adapter.delipo.test;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.screen.fragment.BaseFragment;
import jp.adapter.delipo.test.utils.ChartView;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_SHOW_RATE_PRODUCT;

public class FrmShowRateProduct extends BaseFragment implements View.OnClickListener {

    public FrmShowRateProduct() {
        // Required empty public constructor
    }

    public static FrmShowRateProduct getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmShowRateProduct fragment = new FrmShowRateProduct();
        Bundle data = new Bundle();
        Log.d("aaaaaa", "getInstance: " + mapData);
        if (mapData != null && mapData.containsKey(EXTRA_RATE_PRODUCT))
            data.putSerializable(ExtraConstants.EXTRA_RATE_PRODUCT, (Serializable) mapData.get(ExtraConstants.EXTRA_RATE_PRODUCT));
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    final String[] KEYS = {"オリジ \n ナリティ", "値ごろ感", "価格", "美味しさ", "材料への \n こだわり", "見栄えの こだわり", "リピート意向",};
    //    static final String[] KEYS = {"1", "2", "3", "4", "5", "6", "7",};
    ChartView chartView;
    ImageView imageView;

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_show_rate;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_SHOW_RATE_PRODUCT;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View btnHome = view.findViewById(R.id.btnHome);
        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnRating = view.findViewById(R.id.btnRating);
        btnRating.getLayoutParams().width = activity.getSizeWithScale(48);
        btnRating.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.getLayoutParams().width = activity.getSizeWithScale(48);
        btnMenu.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnMyPage = view.findViewById(R.id.btnMyPage);
        btnMyPage.getLayoutParams().width = activity.getSizeWithScale(48);
        btnMyPage.getLayoutParams().height = activity.getSizeWithScale(48);

        View bottomBar = view.findViewById(R.id.bottomBar);
        int bottomBarPadding = activity.getSizeWithScale(15);
        bottomBar.setPadding(bottomBarPadding, 0, bottomBarPadding, 0);

        View btnReturn = view.findViewById(R.id.btnReturn);
        btnReturn.getLayoutParams().width = activity.getSizeWithScale(100);
        btnReturn.getLayoutParams().height = activity.getSizeWithScale(30);

        View btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.getLayoutParams().width = activity.getSizeWithScale(100);
        btnUpdate.getLayoutParams().height = activity.getSizeWithScale(30);

        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMyPage.setOnClickListener(this);
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

    public static FrmShowRateProduct getInstance() {
        return new FrmShowRateProduct();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        float[] values = {0, 0, 0, 0, 0, 0, 0};
        if (getArguments() != null) {
            values = getArguments().getFloatArray(EXTRA_RATE_PRODUCT);
            Log.d(TAG, "onViewCreated: " + Arrays.toString(values));
        }
        chartView = (ChartView) view.findViewById(R.id.radar_chart);
        imageView = view.findViewById(R.id.image_test);
        view.findViewById(R.id.btnReturn).setOnClickListener(this);
        view.findViewById(R.id.btnUpdate).setOnClickListener(this);
//        imageView.setImageResource(R.drawable.thumb_product);
        chartView.setChartStyle(Paint.Style.FILL);
        chartView.setAxisMax(100.00F);
        chartView.setAxisTick(100.00F);
        final Map<String, Float> axis = new LinkedHashMap<>(values.length); // in 1,000 pounds (Sep 19, 2016)
        axis.put(KEYS[0], values[0]);
        axis.put(KEYS[1], values[1]);
        axis.put(KEYS[2], values[2]);
        axis.put(KEYS[3], values[3]);
        axis.put(KEYS[4], values[4]);
        axis.put(KEYS[5], values[5]);
        axis.put(KEYS[6], values[6]);
        chartView.setAxis(axis);
    }

    private void showPaymentRegister() {
        try {
            ArrayList<BackStackData> arrayList = null;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
            activity.showPaymentRegister(arrayList, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReturn:
                backToPrevious();
                break;
            case R.id.btnUpdate:
                showPaymentRegister();
                break;
            case R.id.btnHome:
                activity.backToHome();
                break;
            case R.id.btnSearch:
                activity.showSearch(null, null);
                break;
            case R.id.btnRating:
                activity.showCreateProduct(null, null);
                break;
        }
    }

}
