package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.test.FrmMyPage;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_RATE_PRODUCT;

public class FrmRateProduct extends BaseFragment implements View.OnClickListener {

    private float value1;
    private float value2;
    private float value3;
    private float value4;
    private float value5;
    private float value6;
    private float value7;
    private float[] VALUES = new float[]{0, 0, 0, 0, 0, 0, 0};

    public FrmRateProduct() {
        // Required empty public constructor
    }

    public static FrmRateProduct getInstance() {
        return new FrmRateProduct();
    }

    public static FrmRateProduct getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmRateProduct fragment = new FrmRateProduct();
        Bundle data = new Bundle();
        if (mapData != null && mapData.containsKey(EXTRA_RATE_PRODUCT)) {
            data.putSerializable(ExtraConstants.EXTRA_RATE_PRODUCT, (Serializable) mapData.get(ExtraConstants.EXTRA_RATE_PRODUCT));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 0, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 0));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 1, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 1));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 2, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 2));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 3, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 3));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 4, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 4));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 5, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 5));
            data.putInt(ExtraConstants.EXTRA_VALUES_RATE + 6, (Integer) mapData.get(ExtraConstants.EXTRA_VALUES_RATE + 6));
        }
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_rate_product;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_RATE_PRODUCT;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View btnHome = view.findViewById(R.id.btnHome);
        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnRating = view.findViewById(R.id.btnRate);
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

        View btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.getLayoutParams().height = activity.getSizeWithScale(40);

        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMyPage.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(EXTRA_RATE_PRODUCT)) {
            int value = getArguments().getInt(EXTRA_VALUES_RATE + 0);
            Log.d(TAG, "onViewCreated: " + value);
        }
        RangeSeekBar rangeSeekBar1 = view.findViewById(R.id.sb_1);
        rangeSeekBar1.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value1 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[0] = value1;
            }
        });
        RangeSeekBar rangeSeekBar2 = view.findViewById(R.id.sb_2);
        rangeSeekBar2.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value2 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[1] = value2;
            }
        });
        RangeSeekBar rangeSeekBar3 = view.findViewById(R.id.sb_3);
        rangeSeekBar3.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value3 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[2] = value3;
            }
        });
        RangeSeekBar rangeSeekBar4 = view.findViewById(R.id.sb_4);
        rangeSeekBar4.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value4 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[3] = value4;
            }
        });
        RangeSeekBar rangeSeekBar5 = view.findViewById(R.id.sb_5);
        rangeSeekBar5.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value5 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[4] = value5;
            }
        });
        RangeSeekBar rangeSeekBar6 = view.findViewById(R.id.sb_6);
        rangeSeekBar6.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value6 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[5] = value6;
            }
        });
        RangeSeekBar rangeSeekBar7 = view.findViewById(R.id.sb_7);
        rangeSeekBar7.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                value7 = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                VALUES[6] = value7;
            }
        });
        view.findViewById(R.id.btnUpdate).setOnClickListener(this);
    }

    private HashMap<String, Object> getDataSaveState() {
        HashMap<String, Object> mapIndex = new HashMap<>();
        try {
            for (int i = 0; i < VALUES.length; i++) {
                mapIndex.put(EXTRA_VALUES_RATE + "[" + i + "]", VALUES[i]);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return mapIndex;
    }

    private void showRatedProduct() {
        try {
            ArrayList<BackStackData> arrayList = null;
            HashMap<String, Object> mapData = new HashMap<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment(), getDataSaveState()));
            mapData.put(EXTRA_RATE_PRODUCT, VALUES);
            activity.showRatedProduct(arrayList, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                Log.d(TAG, "onClick: " + Arrays.toString(VALUES));
                showRatedProduct();
                break;
            case R.id.btnHome:
                activity.backToHome();
                break;
            case R.id.btnSearch:
                activity.addFragmentToMain(FrmSearch.getInstance());
                break;
            case R.id.btnRate:
                break;
            case R.id.btnMenu:
                break;
            case R.id.btnMyPage:
                activity.addFragmentToMain(FrmMyPage.getInstance());
                break;
            default:
                break;
        }
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
}
