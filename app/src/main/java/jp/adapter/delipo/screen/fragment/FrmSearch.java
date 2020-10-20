package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import jp.adapter.delipo.R;
import jp.adapter.delipo.adapter.AdapterProduct;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;
import jp.adapter.delipo.listener.LoadProductListener;
import jp.adapter.delipo.listener.OnClickProductListener;
import jp.adapter.delipo.test.FrmMyPage;
import jp.adapter.delipo.utils.AppUtils;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_SEARCH;

public class FrmSearch extends BaseFragment implements View.OnClickListener, LoadProductListener, OnClickProductListener {

    private ArrayList<ProductEntity> arrayList;

    public static FrmSearch getInstance() {
        return new FrmSearch();
    }

    public static FrmSearch getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmSearch fragment = new FrmSearch();
        if (arrayList != null) {
            Bundle data = new Bundle();
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
            if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_TAB_INDEX)) {
                data.putInt(ExtraConstants.EXTRA_TAB_INDEX, (Integer) mapData.get(ExtraConstants.EXTRA_TAB_INDEX));
                data.putInt(ExtraConstants.EXTRA_POSITION, (Integer) mapData.get(ExtraConstants.EXTRA_POSITION));
                data.putInt(ExtraConstants.EXTRA_OFFSET, (Integer) mapData.get(ExtraConstants.EXTRA_OFFSET));
            }
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_search;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_SEARCH;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSearch = view.findViewById(R.id.rvSearch);
        rvSearch.setHasFixedSize(false);
        rvSearch.setLayoutManager(new GridLayoutManager(activity, 3));
        int listPaddingL = activity.getSizeWithScale(20);
        int padding = activity.getSizeWithScale(10);
        int width = (activity.getScreenWidth() - listPaddingL - padding * 4) / 3;
        rvSearch.setPadding(listPaddingL, padding, padding, 0);
        view.findViewById(R.id.llTabBar).setPadding(listPaddingL, 0, listPaddingL, 0);
        adapterSearch = new AdapterProduct(activity, width, padding, this);
        rvSearch.setAdapter(adapterSearch);

        rvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (!activity.isCallingApi && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int totalItemCount = adapterSearch.getItemCount();
                        if (totalItemCount < activity.getCurrentTotalSearch(currentTab)) {
                            int firstVisibleItem = ((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
                            int visibleItemCount = recyclerView.getChildCount();
                            int lastInScreen = firstVisibleItem + visibleItemCount;
                            if (lastInScreen > 0 && lastInScreen >= totalItemCount) {
                                loadMoreData();
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        onTabChange(getArguments() != null && getArguments().containsKey(EXTRA_TAB_INDEX) ?
                getArguments().getInt(EXTRA_TAB_INDEX) : 0);

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchByKeyWord();
                    return true;
                }
                return false;
            }
        });

    }

    private EditText edSearch;
    private int currentTab = -1;
    private RecyclerView rvSearch;
    private AdapterProduct adapterSearch;
    private View tabFavorite;
    private View tabRegistration;
    private View tabCategory;

    @Override
    protected void loadControlsAndResize(View view) {

        edSearch = view.findViewById(R.id.edSearch);
        int searchW = activity.getSizeWithScale(270);
        int searchH = activity.getSizeWithScale(30);
        edSearch.getLayoutParams().width = searchW;
        edSearch.getLayoutParams().height = searchH;
        View frameSearch = view.findViewById(R.id.frameSearch);
        frameSearch.getLayoutParams().width = searchW;
        frameSearch.getLayoutParams().height = searchH;
        int btnSearchKeywordH = searchH * 3 / 4;
        View btnSearchKeyword = view.findViewById(R.id.btnSearchKeyword);
        btnSearchKeyword.getLayoutParams().width = 2 * btnSearchKeywordH;
        btnSearchKeyword.getLayoutParams().height = btnSearchKeywordH;
        edSearch.setPadding(btnSearchKeywordH, 0, 2 * btnSearchKeywordH, 0);

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

        tabFavorite = view.findViewById(R.id.tabSortByFavorite);
        tabRegistration = view.findViewById(R.id.tabSortByRegistration);
        tabCategory = view.findViewById(R.id.tabSortByCategory);

        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMyPage.setOnClickListener(this);
        tabFavorite.setOnClickListener(this);
        tabRegistration.setOnClickListener(this);
        tabCategory.setOnClickListener(this);
        btnSearchKeyword.setOnClickListener(this);
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

    private void loadMoreData() {
        try {
            if (activity.isCallingApi)
                return;

            activity.requestListProduct((LoadProductListener) this, getCurrentFragment(),
                    false, true, currentTab == 0 ?
                            activity.getParamsNextPageSearchSortByFavorite()
                            : currentTab == 1 ? activity.getParamsNextPageSearchSortByRegistration()
                            : activity.getParamsNextPageSearchSortByCategory());
            edSearch.setText(activity.getCurrentKeywordSearch());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Object> getDataSaveState() {
        int itemPosition = 0;
        int offset = 0;
        try {
            View view = rvSearch.getChildAt(0);
            offset = view == null ? 0 : view.getTop() - rvSearch.getPaddingTop();
            itemPosition = ((GridLayoutManager) Objects.requireNonNull(rvSearch.getLayoutManager())).findFirstVisibleItemPosition();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        HashMap<String, Object> mapIndex = new HashMap<>();
        mapIndex.put(EXTRA_TAB_INDEX, currentTab);
        mapIndex.put(EXTRA_OFFSET, offset);
        mapIndex.put(EXTRA_POSITION, itemPosition);
        return mapIndex;
    }


    private void searchByKeyWord() {
        try {
            String keyword = edSearch.getText().toString().trim();
            if (AppConstants.LOG_DEBUG)
                Log.e("searchByKeyWord", "from:" + activity.getCurrentKeywordSearch() + "->" + keyword);

            activity.requestListProduct((LoadProductListener) this, getCurrentFragment(), false, true, currentTab == 0 ? activity.getParamsRefreshSearchSortByFavorite(keyword)
                    : currentTab == 1 ? activity.getParamsRefreshSearchSortByRegistration(keyword)
                    : activity.getParamsRefreshSearchSortByCategory(keyword));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void onTabChange(int tabIndex) {
        try {
            if (currentTab == tabIndex)
                return;

            currentTab = tabIndex;
            tabFavorite.setSelected(tabIndex == 0);
            tabRegistration.setSelected(tabIndex == 1);
            tabCategory.setSelected(tabIndex == 2);
            edSearch.setText(activity.getCurrentKeywordSearch());
            edSearch.selectAll();
            ArrayList<ProductEntity> arrayList = null;
            if (currentTab == 0) {
                if (activity.getCountArrSearchSortByFavorite() > 0) {
                    arrayList = activity.getArrSearchSortByFavorite();
                }
            } else if (currentTab == 1) {
                if (activity.getCountArrSearchRegistration() > 0) {
                    arrayList = activity.getArrSearchRegistration();
                }
            } else if (activity.getCountArrSearchSortByCategory() > 0)
                arrayList = activity.getArrSearchSortByCategory();

            if (arrayList != null && !arrayList.isEmpty()) {
                adapterSearch.updateData(arrayList);
                if (getArguments() != null && getArguments().containsKey(EXTRA_POSITION)) {
                    if (AppConstants.LOG_DEBUG)
                        Log.d(TAG, "onTabChange: tab " + currentTab + "/" + getArguments().getInt(EXTRA_OFFSET));
                    int pos = getArguments().getInt(EXTRA_POSITION);
                    int offset = getArguments().getInt(EXTRA_OFFSET);
                    getArguments().remove(EXTRA_POSITION);
                    getArguments().remove(EXTRA_OFFSET);
                    if (pos > 0 || offset != 0) {
                        ((GridLayoutManager) Objects.requireNonNull(rvSearch.getLayoutManager()))
                                .scrollToPositionWithOffset(pos, offset);
                        return;
                    }
                }
                rvSearch.scrollToPosition(0);
            } else {
                adapterSearch.updateData(null);
                activity.requestListProduct((LoadProductListener) this, getCurrentFragment(),
                        false, true, currentTab == 0 ?
                                activity.getParamsNextPageSearchSortByFavorite()
                                : currentTab == 1 ? activity.getParamsNextPageSearchSortByRegistration()
                                : activity.getParamsNextPageSearchSortByCategory());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (!isClickAble())
            return;
        switch (v.getId()) {
            case R.id.tabSortByFavorite:
                onTabChange(0);
                break;
            case R.id.tabSortByRegistration:
                onTabChange(1);
                break;
            case R.id.tabSortByCategory:
                onTabChange(2);
                break;
            case R.id.btnSearchKeyword:
                searchByKeyWord();
                break;
            case R.id.btnHome:
                activity.backToHome();
                break;
            case R.id.btnSearch:
                activity.showSearch(null, null);
                break;
            case R.id.btnSearchRate:
                activity.showRateProduct(null, null);
                break;
            case R.id.btnMyPage:
                activity.addFragmentToMain(FrmMyPage.getInstance());
                break;
        }
        AppUtils.hideKeyboard(v);
    }


    @Override
    public void onDestroyView() {
        AppUtils.hideKeyboard(getView());
        super.onDestroyView();
    }

    @Override
    public void onLoadProductComplete(ArrayList<ProductEntity> arrayList, boolean isLoadMore) {
        try {
            if (isLoadMore)
                adapterSearch.addMoreData(arrayList);
            else {
                adapterSearch.updateData(arrayList);
                rvSearch.scrollToPosition(0);
            }
            edSearch.setText(activity.getCurrentKeywordSearch());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickProduct(ProductEntity productEntity) {
        try {
            if (!isClickAble())
                return;

            ArrayList<BackStackData> arrayList = null;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();

            arrayList.add(new BackStackData(getCurrentFragment(), getDataSaveState()));

            HashMap<String, Object> dataTransfer = new HashMap<>();
            dataTransfer.put(EXTRA_PRODUCT, productEntity);
            Log.d(TAG, "onClickProduct: " + arrayList + "/" + dataTransfer);
            activity.showInfoProduct(arrayList, dataTransfer);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}