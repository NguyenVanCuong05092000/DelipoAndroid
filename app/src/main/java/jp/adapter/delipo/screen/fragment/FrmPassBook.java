package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_PASS_BOOK;

public class FrmPassBook extends BaseFragment implements View.OnClickListener {

    public static FrmPassBook getInstance() {
        return new FrmPassBook();
    }

    public static FrmPassBook getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmPassBook fragment = new FrmPassBook();
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
        return R.layout.frm_pass_book;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_PASS_BOOK;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View btnHome = view.findViewById(R.id.btnHome);
        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnRating = view.findViewById(R.id.btnPassBookRate);
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

        View btnMove = view.findViewById(R.id.btnMove);
        btnMove.getLayoutParams().width = activity.getSizeWithScale(100);
        btnMove.getLayoutParams().height = activity.getSizeWithScale(30);

        view.findViewById(R.id.consideringProcedures).setVisibility(View.INVISIBLE);
        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
        btnMove.setOnClickListener(this);
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
            case R.id.btnPassBookRate:
                activity.showRateProduct(null, null);
                break;
            case R.id.btnMove:
                activity.backToHome();
                break;
        }
    }
}