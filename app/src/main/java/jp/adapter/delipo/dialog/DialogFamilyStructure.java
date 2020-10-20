package jp.adapter.delipo.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.adapter.delipo.R;
import jp.adapter.delipo.adapter.AdapterFamilyStructure;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.entity.FamilyStructureEntity;
import jp.adapter.delipo.screen.activities.BaseActivity;

public class DialogFamilyStructure extends Dialog implements View.OnClickListener {
    private static DialogFamilyStructure _instance;
    private BaseActivity mActivity;
    private RecyclerView rvFamilyStructure;
    private AdapterFamilyStructure adapterFamilyStructure;
    private ArrayList<FamilyStructureEntity> listFamilyStructure;
    private OnConfirmClickListener onConfirmClickListener;

    public interface OnConfirmClickListener{
        void onConfirmClicked();
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public static DialogFamilyStructure getInstance(@NonNull BaseActivity activity, @NonNull ArrayList<FamilyStructureEntity> listFamilyStructure) {
        if (_instance == null || _instance.mActivity == null) {
            _instance = new DialogFamilyStructure(activity, listFamilyStructure);
        }
        return _instance;
    }

    private DialogFamilyStructure(@NonNull BaseActivity activity, @NonNull ArrayList<FamilyStructureEntity> listFamilyStructure) {
        super(activity, R.style.dialogNotice);
        this.mActivity = activity;
        this.listFamilyStructure = listFamilyStructure;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_family_structure);
        //
        Button btnBack = findViewById(R.id.btnBack);
        Button btnConfirm = findViewById(R.id.btnConfirm);
        View imgMen = findViewById(R.id.imgMen);
        View imgWomen = findViewById(R.id.imgWomen);
        //
        btnBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        //
        btnBack.getLayoutParams().width = getSizeWithScale(426);
        btnBack.getLayoutParams().height = getSizeWithScale(178);

        btnConfirm.getLayoutParams().width = getSizeWithScale(426);
        btnConfirm.getLayoutParams().height = getSizeWithScale(178);

        imgMen.getLayoutParams().width = getSizeWithScale(106);
        imgMen.getLayoutParams().height = getSizeWithScale(174);

        imgWomen.getLayoutParams().width = getSizeWithScale(107);
        imgWomen.getLayoutParams().height = getSizeWithScale(174);
        //
        rvFamilyStructure = findViewById(R.id.rvFamilyStructure);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvFamilyStructure.setLayoutManager(layoutManager);
        //
        adapterFamilyStructure = new AdapterFamilyStructure(getSizeWithScale(73),getSizeWithScale(79), getSizeWithScale(86), listFamilyStructure);
        rvFamilyStructure.setAdapter(adapterFamilyStructure);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack: {
                dismiss();
                break;
            }
            case R.id.btnConfirm: {
                if (onConfirmClickListener!=null)
                    onConfirmClickListener.onConfirmClicked();
                dismiss();
                break;
            }
        }
    }

    public DialogFamilyStructure setCancel(boolean allowCancel) {
        setCancelable(allowCancel);
        return this;
    }

    public void release() {
        mActivity = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        resize();
    }

    private void resize() {
        try {
            Objects.requireNonNull(getWindow()).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(getWindow().getAttributes());
            layoutParams.width = getSizeWithScale(1000);
            layoutParams.height = (int) (getScreenHeight() * layoutParams.width * 1f) / getScreenWidth();
            getWindow().setAttributes(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    private float scaleValue = 0;
    private DisplayMetrics displayMetrics;

    private DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null)
            displayMetrics = mActivity.getResources().getDisplayMetrics();
        return displayMetrics;
    }

    private int screenWidth = 0;

    private int getScreenWidth() {
        if (screenWidth == 0)
            screenWidth = getDisplayMetrics().widthPixels;
        return screenWidth;
    }


    private float getScaleValue() {
        if (scaleValue == 0)
            scaleValue = getScreenWidth() * 1f / AppConstants.SCREEN_WIDTH_DESIGN;
        return scaleValue;
    }

    private int getSizeWithScale(double sizeDesign) {
        return (int) (sizeDesign * getScaleValue());
    }

    private int screenHeight = 0;

    public int getScreenHeight() {
        if (screenHeight == 0) {
            int statusBarHeight = 0;
            try {
                int resourceId = mActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = mActivity.getResources().getDimensionPixelSize(resourceId);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            screenHeight = getDisplayMetrics().heightPixels - statusBarHeight;
        }
        return screenHeight;
    }
}
