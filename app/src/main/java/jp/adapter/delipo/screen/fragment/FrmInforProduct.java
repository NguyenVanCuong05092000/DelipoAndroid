package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.adapter.AdapterVPImgProduct;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_HOME;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_INFO_PRODUCT;

public class FrmInforProduct extends BaseFragment implements View.OnClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.frm_infor_product;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_INFO_PRODUCT;
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

    public static FrmInforProduct getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmInforProduct fragment = new FrmInforProduct();
        Bundle data = new Bundle();
        if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_PRODUCT))
            data.putSerializable(ExtraConstants.EXTRA_PRODUCT, (ProductEntity) mapData.get(ExtraConstants.EXTRA_PRODUCT));
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    private View btnPrevious;
    private View btnNext;
    private TextView tvSaaseName;
    private TextView tvCreatedDate;
    private TextView tvCategory;
    private TextView tvComment;
    private EditText edProductName;
    private EditText edPrice;
    private EditText edTax;
    private EditText edPrice100;
    private EditText edWeight;
    private TextView tvExpiration;
    private TextView tvProcessing;
    private EditText edManufacturer;
//    private EditText edStore;
    private ViewPager viewPager;

    @Override
    protected void loadControlsAndResize(View view) {
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnPrevious.getLayoutParams().width = activity.getSizeWithScale(52);
        btnPrevious.getLayoutParams().height = btnPrevious.getLayoutParams().width;
        btnNext = view.findViewById(R.id.btnNext);
        btnNext.getLayoutParams().width = btnPrevious.getLayoutParams().width;
        btnNext.getLayoutParams().height = btnPrevious.getLayoutParams().width;
        View btnOK = view.findViewById(R.id.btnOK);
        btnOK.getLayoutParams().width = btnPrevious.getLayoutParams().width;
        btnOK.getLayoutParams().height = btnPrevious.getLayoutParams().width;
        int frBtnPadding = activity.getSizeWithScale(25);
        view.findViewById(R.id.frBtnBottom).setPadding(frBtnPadding, 0, frBtnPadding, 0);
        int vpPageMargin = activity.getSizeWithScale(5);
        int vpMarginTop = activity.getSizeWithScale(5);
        int vpPadding = activity.getSizeWithScale(20);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(vpPadding, 0, vpPadding, 0);
        viewPager.setPageMargin(vpPageMargin);
        View frameContainerImg = view.findViewById(R.id.frameContainerImg);
        frameContainerImg.getLayoutParams().height = activity.getScreenWidth() + vpMarginTop - vpPadding * 2;
        frameContainerImg.setPadding(0, vpMarginTop, 0, 0);
        view.findViewById(R.id.llInfoProduct).setPadding(vpPadding, 0, vpPadding, vpPadding);
        view.findViewById(R.id.llTitleInfoProduct).getLayoutParams().width =
                (activity.getScreenWidth() - vpPadding * 2) * 3 / 10;
        tvComment = view.findViewById(R.id.tvComment);
        tvCategory = view.findViewById(R.id.tvCategoryName);
        edProductName = view.findViewById(R.id.edProductName);
        edPrice = view.findViewById(R.id.edPrice);
        edTax = view.findViewById(R.id.edTax);
        edPrice100 = view.findViewById(R.id.edPrice100);
        edWeight = view.findViewById(R.id.edWeight);
        tvExpiration = view.findViewById(R.id.tvExpiration);
        tvProcessing = view.findViewById(R.id.tvProcessing);
        edManufacturer = view.findViewById(R.id.edManufacturer);

        btnOK.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        View vClickAble = view.findViewById(R.id.vClickAble);
        vClickAble.setVisibility(View.VISIBLE);
        vClickAble.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        activity.checkUnreadNews(btnMenu);
    }

    private ProductEntity productEntity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() == null) {
            finish();
            return;
        }
        productEntity = (ProductEntity) getArguments().getSerializable(ExtraConstants.EXTRA_PRODUCT);
        if (productEntity == null) {
            finish();
            return;
        }
        viewPager.setAdapter(new AdapterVPImgProduct(activity, productEntity.listImage));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onPageChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        onPageChange(0);
        showInfoProduct();
//        view.findViewById(R.id.vLabel).setVisibility(productEntity.adminEdited == 0 ? View.VISIBLE : View.GONE);
    }

    private void onPageChange(int position) {
        try {
            if (productEntity.listImage == null || productEntity.listImage.isEmpty()
                    || productEntity.listImage.size() == 1) {
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                return;
            }
            if (position == 0) {
                btnPrevious.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
            } else if (position == productEntity.listImage.size() - 1) {
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
            } else {
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void changePosition(boolean isPrevious) {
        try {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + (isPrevious ? -1 : 1));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showInfoProduct() {
        tvComment.setText(productEntity.remarks);
//        setText(tvSaaseName, productEntity.saasesName);
//        setText(tvCreatedDate, productEntity.getShortCreateAt());
        setText(tvCategory, productEntity.categoryName);
        setText(edProductName, productEntity.name);
        setText(edPrice, productEntity.price + "円");
        setText(edTax, productEntity.priceTotal + "円");
        setText(edPrice100, productEntity.pricePer100 + "円/100g");
        setText(edWeight, productEntity.weight + "g");
        setText(tvExpiration, productEntity.getShortExpirationDate());
        setText(tvProcessing, productEntity.getShortProcessingDate());
        setText(edManufacturer, productEntity.manufacturer);
//        setText(edStore, productEntity.storeName);
    }

    private void setText(TextView textView, String text) {
        textView.setText(TextUtils.isEmpty(text) || text.equals("null") ? "-" : text);
    }

    private void onClickInfoProduct() {
//        try {
//            if(productEntity == null)
//                return;
//            if(productEntity.corporateEmployeesId.equals(activity.getCurrentUserId())){
//                activity.showConfirm(R.string.titleTutInfoProduct, false, R.string.msgTutInfoProduct,
//                        R.string.label_btn_edit, R.string.label_btn_close, new ActionCallback() {
//                            @Override
//                            public void onActionFinished() {
//                                try {
//                                    ArrayList<BackStackData> arrayList = null;
//                                    if(getArguments()!=null&&getArguments().containsKey(EXTRA_BACK_STACK))
//                                        arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
//                                    if(arrayList==null)
//                                        arrayList = new ArrayList<>();
//                                    HashMap<String, Object> dataSaveState = new HashMap<>();
//                                    dataSaveState.put(EXTRA_PRODUCT, productEntity);
//                                    arrayList.add(new BackStackData(getCurrentFragment(), dataSaveState));
//
//                                    HashMap<String, Object> dataTransfer = new HashMap<>();
//                                    dataTransfer.put(EXTRA_PRODUCT, productEntity.clone());
//                                    activity.showEditProduct(arrayList, dataTransfer);
//                                }catch (Throwable e){e.printStackTrace();}
//                            }
//                        });
//            }else {
//                activity.showTutorialWithoutCheckbox(R.string.titleTutInfoProduct, false,
//                        R.string.msgTutInfoProduct,null);
//            }
//        }catch (Throwable e){e.printStackTrace();}
    }

    @Override
    public void onClick(View v) {
        if (!isClickAble())
            return;
        switch (v.getId()) {
            case R.id.btnHome:
                try {
                    activity.backToHome();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnOK:
                try {
                    if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK)) {
                        ArrayList<BackStackData> arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
                        if (arrayList != null && !arrayList.isEmpty()) {
                            BackStackData data = arrayList.get(arrayList.size() - 1);
                            if (data.fromFragment == FRM_HOME) {
                                activity.showSearch(arrayList, null);
                                return;
                            }
                        }
                    }
                    finish();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnNext:
                changePosition(false);
                break;
            case R.id.btnPrevious:
                changePosition(true);
                break;
            case R.id.vClickAble:
                onClickInfoProduct();
                break;
            case R.id.btnMenu:
//                try {
//                    activity.showMenu();
//                }catch (Throwable e){e.printStackTrace();}
                break;
        }
    }
}
