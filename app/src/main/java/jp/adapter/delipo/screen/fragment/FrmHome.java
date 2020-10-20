package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_HOME;

public class  FrmHome extends BaseFragment implements View.OnClickListener {

    public static FrmHome getInstance() {
        return new FrmHome();
    }

    public static FrmHome getInstance(Bundle dataSave) {
        FrmHome fragment = new FrmHome();
        if (dataSave != null) {
            Bundle data = new Bundle();
//            data.putBundle(ExtraConstants.EXTRA_SAVE_INSTANCE_STATE, dataSave);
            fragment.setArguments(data);
        }
        return fragment;
    }

    private RecyclerView recycler;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.recycler);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(mLayoutManager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_home;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_HOME;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View btnHome = view.findViewById(R.id.btnHome);
        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnRating = view.findViewById(R.id.btnHomeRate);
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

        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMyPage.setOnClickListener(this);
        view.findViewById(R.id.tvRule).setOnClickListener(this);
        view.findViewById(R.id.topBar).setOnClickListener(this);
        view.findViewById(R.id.tvPolicy).setOnClickListener(this);
    }

    @Override
    protected void finish() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
                activity.addFragmentToMain(FrmRateProduct.getInstance());
                break;
            case R.id.btnSearch:
                activity.addFragmentToMain(FrmSearch.getInstance());
                break;
            case R.id.btnHomeRate:
                activity.addFragmentToMain(FrmCreateProduct.getInstance());
                break;
            case R.id.btnMenu:
//                activity.addFragmentToMain(FrmEditProduct.getInstance());
                break;
            case R.id.btnMyPage:
                activity.addFragmentToMain(FrmNotifConnectSNS.getInstance());
                break;
            case R.id.topBar:
                activity.addFragmentToMain(FrmRules.getInstance());
                break;
            case R.id.tvPolicy:
                activity.addFragmentToMain(FrmPolicy.getInstance());
                break;
        }
    }
}
