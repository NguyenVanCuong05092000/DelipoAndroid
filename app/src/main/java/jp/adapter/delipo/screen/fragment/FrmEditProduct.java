package jp.adapter.delipo.screen.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.adapter.AdapterVPImgProduct;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;
import jp.adapter.delipo.listener.CreateProductCallback;
import jp.adapter.delipo.utils.AppUtils;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_EDIT_PRODUCT;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_HOME;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_INFO_PRODUCT;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_MY_PRODUCT;

public class FrmEditProduct extends BaseFragment implements View.OnClickListener, CreateProductCallback {

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_edit_product;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_EDIT_PRODUCT;
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

    public static FrmEditProduct getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmEditProduct fragment = new FrmEditProduct();
        Bundle data = new Bundle();
        if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_PRODUCT))
            data.putSerializable(ExtraConstants.EXTRA_PRODUCT, (ProductEntity) mapData.get(ExtraConstants.EXTRA_PRODUCT));
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    public static FrmEditProduct getInstance(ProductEntity productEntity, int fromFragment) {
        FrmEditProduct fragment = new FrmEditProduct();
        Bundle data = new Bundle();
        data.putSerializable(ExtraConstants.EXTRA_PRODUCT, productEntity);
        data.putInt(ExtraConstants.EXTRA_FROM, fromFragment);
        fragment.setArguments(data);
        return fragment;
    }

    private TextView tvCategory;
    private EditText edComment;
    private TextView tvComment;
    private EditText edProductName;
    private EditText edPrice;
    private EditText edTax;
    private EditText edPrice100;
    private EditText edWeight;
    private TextView tvExpiration;
    private TextView tvProcessing;
    private EditText edManufacturer;
    private ScrollView scrollView;
    private View rlBtnRegisterProduct;
    private View rlBtnOK;

    @Override
    protected void loadControlsAndResize(View view) {
        int vpPageMargin = activity.getSizeWithScale(5);
        int vpMarginTop = activity.getSizeWithScale(5);
        int vpPadding = activity.getSizeWithScale(20);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(vpPadding, 0, vpPadding, 0);
        viewPager.setPageMargin(vpPageMargin);
        View frameContainerImg = view.findViewById(R.id.frameContainerImg);
        frameContainerImg.getLayoutParams().height = activity.getScreenWidth() + vpMarginTop - vpPadding * 2;
        frameContainerImg.setPadding(0, vpMarginTop, 0, 0);
        View btnRegisterProduct = view.findViewById(R.id.btnRegisterProduct);
        btnRegisterProduct.getLayoutParams().height = activity.getSizeWithScale(40);
        View btnOK = view.findViewById(R.id.btnOK);
        btnOK.getLayoutParams().height = activity.getSizeWithScale(40);
        view.findViewById(R.id.llInfoProduct).setPadding(vpPadding, 0, vpPadding, vpPadding);
        view.findViewById(R.id.llTitleInfoProduct).getLayoutParams().width = (activity.getScreenWidth() - vpPadding * 2) * 3 / 10;
        edComment = view.findViewById(R.id.edComment);
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
        scrollView = view.findViewById(R.id.scrollView);
        rlBtnRegisterProduct = view.findViewById(R.id.rlBtnRegisterProduct);
        rlBtnOK = view.findViewById(R.id.rlBtnOK);


        edComment.setOnFocusChangeListener(focusChangeListener);
        edProductName.setOnFocusChangeListener(focusChangeListener);
        edPrice.setOnFocusChangeListener(focusChangeListener);
        edTax.setOnFocusChangeListener(focusChangeListener);
        edPrice100.setOnFocusChangeListener(focusChangeListener);
        edWeight.setOnFocusChangeListener(focusChangeListener);
        edManufacturer.setOnFocusChangeListener(focusChangeListener);
        edComment.setEnabled(true);
        edProductName.setEnabled(true);
        edPrice.setEnabled(true);
        edTax.setEnabled(true);
        edPrice100.setEnabled(true);
        edWeight.setEnabled(true);
        edManufacturer.setEnabled(true);

        tvCategory.setOnClickListener(this);
        tvExpiration.setOnClickListener(this);
        tvProcessing.setOnClickListener(this);
        btnRegisterProduct.setOnClickListener(this);
        btnOK.setOnClickListener(this);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    View view = getView();
                    if (view == null)
                        return;
                    if (view.getHeight() < activity.getScreenHeight())
                        scrollLayoutWhenKeyboardShow(false);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus)
                scrollLayoutWhenKeyboardShow(true);
        }
    };

    private int currentScrollY = 0;

    private void scrollLayoutWhenKeyboardShow(boolean hasFocus) {
        try {
            if (!hasFocus) {
                hasFocus = edManufacturer.hasFocus() || edWeight.hasFocus()
                        || edPrice100.hasFocus() || edTax.hasFocus() || edPrice.hasFocus()
                        || edProductName.hasFocus() || edComment.hasFocus();
            }

            if (hasFocus)
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (currentScrollY != scrollView.getScrollY()) {
                                scrollView.scrollTo(0, scrollView.getScrollY() + edProductName.getHeight());
                                currentScrollY = scrollView.getScrollY();
                            }
                        } catch (Throwable e) {
                        }
                    }
                }, 350);
        } catch (Throwable e) {
        }
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
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new AdapterVPImgProduct(activity, productEntity.listImage));
        showInfoProduct();
    }

    private boolean isEditAble() {
        return rlBtnOK.isShown();
    }

    private void showInfoProduct() {
        edComment.setText(productEntity.remarks);
        setText(tvCategory, productEntity.categoryName);
        setText(tvExpiration, productEntity.getShortExpirationDate());
        setText(tvProcessing, productEntity.getShortProcessingDate());
        if (isEditAble()) {
            tvComment.setText(null);
            edComment.setEnabled(true);
            edComment.setVisibility(View.VISIBLE);
            edProductName.setEnabled(true);
            edPrice.setEnabled(true);
            edTax.setEnabled(true);
            edPrice100.setEnabled(true);
            edWeight.setEnabled(true);
            edManufacturer.setEnabled(true);
            setTextEditable(edProductName, productEntity.name);
            setTextEditable(edPrice, productEntity.price + "");
            setTextEditable(edTax, productEntity.priceTotal + "");
            setTextEditable(edPrice100, productEntity.pricePer100 + "");
            setTextEditable(edWeight, productEntity.weight + "");
            setTextEditable(edManufacturer, productEntity.manufacturer);
        } else {
            tvComment.setText(productEntity.remarks);
            edComment.setEnabled(false);
            edComment.setVisibility(View.GONE);
            edProductName.setEnabled(false);
            edPrice.setEnabled(false);
            edTax.setEnabled(false);
            edPrice100.setEnabled(false);
            edWeight.setEnabled(false);
            edManufacturer.setEnabled(false);
            setText(edProductName, productEntity.name);
            setText(edPrice, productEntity.price + "円");
            setText(edTax, productEntity.priceTotal + "円");
            setText(edPrice100, productEntity.pricePer100 + "円/100g");
            setText(edWeight, productEntity.weight + "g");
            setText(edManufacturer, productEntity.manufacturer);
        }
    }

    private void setTextEditable(TextView textView, String text) {
        textView.setText(TextUtils.isEmpty(text) || text.equals("null") ? null : text);
    }

    private void setText(TextView textView, String text) {
        textView.setText(TextUtils.isEmpty(text) || text.equals("null") ? "-" : text);
    }

    private void showDatePicker(boolean isProcessing) {
        try {
            int y, M, d, h, m;
            if (isProcessing && productEntity.processingYear > 0) {
                y = productEntity.processingYear;
                M = productEntity.processingMonth;
                d = productEntity.processingDay;
                h = productEntity.processingHour;
                m = productEntity.processingMinute;
            } else if (!isProcessing && productEntity.expirationYear > 0) {
                y = productEntity.expirationYear;
                M = productEntity.expirationMonth;
                d = productEntity.expirationDay;
                h = productEntity.expirationHour;
                m = productEntity.expirationMinute;
            } else {
                Calendar calendar = Calendar.getInstance();
                y = calendar.get(Calendar.YEAR);
                M = calendar.get(Calendar.MONTH) + 1;
                d = calendar.get(Calendar.DAY_OF_MONTH);
                h = calendar.get(Calendar.HOUR_OF_DAY);
                m = calendar.get(Calendar.MINUTE);
            }
            new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    try {
                        if (AppConstants.LOG_DEBUG)
                            Log.e("onDateSet:", year + "-" + (month + 1) + "-" + dayOfMonth);
                        if (isProcessing) {
                            if (year != productEntity.processingYear
                                    || month + 1 != productEntity.processingMonth
                                    || dayOfMonth != productEntity.processingDay) {
                                productEntity.needUpdate = true;
                                productEntity.processingYear = year;
                                productEntity.processingMonth = month + 1;
                                productEntity.processingDay = dayOfMonth;
                                setText(tvProcessing, productEntity.getShortProcessingDate());
                            }
                        } else {
                            if (year != productEntity.expirationYear
                                    || month + 1 != productEntity.expirationMonth
                                    || dayOfMonth != productEntity.expirationDay) {
                                productEntity.needUpdate = true;
                                productEntity.expirationYear = year;
                                productEntity.expirationMonth = month + 1;
                                productEntity.expirationDay = dayOfMonth;
                                setText(tvExpiration, productEntity.getShortExpirationDate());
                            }
                        }
                        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                try {
                                    if (AppConstants.LOG_DEBUG)
                                        Log.e("onTimeSet:", hourOfDay + "-" + minute);
                                    if (isProcessing) {
                                        if (hourOfDay != productEntity.processingHour
                                                || minute != productEntity.processingMinute) {
                                            productEntity.needUpdate = true;
                                            productEntity.processingHour = hourOfDay;
                                            productEntity.processingMinute = minute;
                                            setText(tvProcessing, productEntity.getShortProcessingDate());
                                        }
                                    } else {
                                        if (hourOfDay != productEntity.expirationHour
                                                || minute != productEntity.expirationMinute) {
                                            productEntity.needUpdate = true;
                                            productEntity.expirationHour = hourOfDay;
                                            productEntity.expirationMinute = minute;
                                            setText(tvExpiration, productEntity.getShortExpirationDate());
                                        }
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }, h, m, true).show();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }, y, M - 1, d).show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void onEditAbleChange(boolean isEnable) {
        try {
            if (isEnable) {
                rlBtnRegisterProduct.setVisibility(View.GONE);
                rlBtnOK.setVisibility(View.VISIBLE);
                showInfoProduct();
            } else {
                checkUpdateValues();
                rlBtnRegisterProduct.setVisibility(View.VISIBLE);
                rlBtnOK.setVisibility(View.GONE);
                showInfoProduct();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void checkUpdateValues() {
        try {
            String cmt = edComment.getText().toString();
            if (!productEntity.remarks.equals(cmt)) {
                productEntity.remarks = cmt;
                productEntity.needUpdate = true;
            }
            String name = edProductName.getText().toString().trim();
            if (!productEntity.name.equals(name)) {
                productEntity.name = name;
                productEntity.needUpdate = true;
            }
            String manufacturer = edManufacturer.getText().toString().trim();
            if (!productEntity.manufacturer.equals(manufacturer)) {
                productEntity.manufacturer = manufacturer;
                productEntity.needUpdate = true;
            }
            long price = Long.parseLong(edPrice.getText().toString().trim());
            if (productEntity.price != price) {
                productEntity.price = price;
                productEntity.needUpdate = true;
            }
            double tax = Double.parseDouble(edTax.getText().toString().trim());
            if (productEntity.priceTotal != tax) {
                productEntity.priceTotal = tax;
                productEntity.needUpdate = true;
            }
            long price100 = Long.parseLong(edPrice100.getText().toString().trim());
            if (productEntity.pricePer100 != price100) {
                productEntity.pricePer100 = price100;
                productEntity.needUpdate = true;
            }
            long weight = Long.parseLong(edWeight.getText().toString().trim());
            if (productEntity.weight != weight) {
                productEntity.weight = weight;
                productEntity.needUpdate = true;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void updateInfoProduct() {
        try {
            boolean requestPush = false;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK)) {
                ArrayList<BackStackData> arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
                if (arrayList != null && !arrayList.isEmpty()) {
                    BackStackData data = arrayList.get(arrayList.size() - 1);
                    if (data.fromFragment == FRM_HOME)
                        requestPush = true;
                }
            }

            if (productEntity.needUpdate || requestPush)
                activity.updateProductInfo(this, productEntity.id + "", null,
                        productEntity.categoryId, productEntity.remarks, productEntity.name, productEntity.manufacturer,
                        productEntity.storeName, productEntity.price + "", productEntity.priceTotal + "", productEntity.pricePer100 + "",
                        productEntity.weight + "", productEntity.getExpirationDate(),
                        productEntity.getProcessingDate(), requestPush, false,
                        false, true);
            else
                onUpdateFinished();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUploadedLabelImg(int id) {

    }

    @Override
    public void onCreatedProduct(ProductEntity product) {
        onUpdateFinished();
    }

    private void onUpdateFinished() {
        try {
            ArrayList<BackStackData> arrayList = null;
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList != null && !arrayList.isEmpty()) {
                BackStackData data = arrayList.get(arrayList.size() - 1);
                if (data.fromFragment == FRM_INFO_PRODUCT) {
                    arrayList.remove(arrayList.size() - 1);
                    activity.backToPreviousFromBackStack(arrayList);
                    return;
                }
                if (data.fromFragment == FRM_MY_PRODUCT) {
                    finish();
                    return;
                }
            }
            activity.showNotifAnalyze(arrayList, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
                return;
            case R.id.btnOK:
                onEditAbleChange(false);
                break;
            case R.id.btnRegisterProduct:
                updateInfoProduct();
//                try {
//                    activity.addFragmentToMain(FrmNotifAnalyze.getInstance());
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.tvExpiration:
                showDatePicker(false);
                break;
            case R.id.tvProcessing:
                showDatePicker(true);
                break;
            case R.id.tvCategoryName:
                break;

        }
        AppUtils.hideKeyboard(v);
    }


    @Override
    public void onDestroyView() {
        AppUtils.hideKeyboard(getView());
        super.onDestroyView();
    }
}