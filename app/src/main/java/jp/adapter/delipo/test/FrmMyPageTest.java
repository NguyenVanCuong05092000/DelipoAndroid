package jp.adapter.delipo.test;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.screen.fragment.BaseFragment;
import jp.adapter.delipo.screen.fragment.FrmPassBook;
import jp.adapter.delipo.screen.fragment.FrmRateProduct;

public class FrmMyPageTest extends BaseFragment implements View.OnClickListener {

    public static FrmPassBook getInstance() {
        return new FrmPassBook();
    }

    public static FrmMyPageTest getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmMyPageTest fragment = new FrmMyPageTest();
        Bundle data = new Bundle();
//        if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_PRODUCT))
//            data.putSerializable(ExtraConstants.EXTRA_PRODUCT, (ProductEntity) mapData.get(ExtraConstants.EXTRA_PRODUCT));
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_my_page_test;
    }

    @Override
    protected int getCurrentFragment() {
        return 0;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View btnHome = view.findViewById(R.id.btnHome);
        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnRating = view.findViewById(R.id.btnSearchRate);
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

        view.findViewById(R.id.btnMove).setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
//        btnMenu.setOnClickListener(this);
//        btnMyPage.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
                activity.backToHome();
                break;
            case R.id.btnSearch:
                activity.showSearch(null, null);
                break;
            case R.id.btnSearchRate:
                activity.addFragmentToMain(FrmRateProduct.getInstance());
                break;
            case R.id.btnMove:
                activity.backToHome();
                break;
        }
    }
}