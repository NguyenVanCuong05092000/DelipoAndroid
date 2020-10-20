package jp.adapter.delipo.screen.fragment;

import android.view.View;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_SHOW_RATE_PRODUCT;

public class FrmShowRate extends BaseFragment implements View.OnClickListener {

    public FrmShowRate() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {

    }

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

    }
}
