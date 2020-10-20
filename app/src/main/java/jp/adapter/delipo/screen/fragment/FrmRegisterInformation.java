package jp.adapter.delipo.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.dialog.DialogFamilyStructure;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.FamilyStructureEntity;
import jp.adapter.delipo.utils.AppUtils;
import jp.adapter.delipo.utils.DebugHelper;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_REGISTER_INFORMATION;

public class FrmRegisterInformation extends BaseFragment implements View.OnClickListener {

    private static final String[] ARRAY_CAREER = new String[]{"会社員", "専業主婦（主夫）", "パートアルバイト",
            "学生", "経営者・役員", "自営業", "無職", "その他"};

    private static final String[] ARRAY_INCOME = new String[]{"300万円　未満", "300万円以上　500万円未満",
            "500万円以上　700万円未満", "700万円以上　1000万円未満", "1000万円以上　1500万円未満",
            "1500万円以上", "わからない", "回答しない"};

    private ConstraintLayout mConstraintRegisInfor;
    private ConstraintLayout mConstraintConfirmInfor;
    private NestedScrollView mScrollView;
    private Button mButtonRegisterInfor;
    private Button mButtonBackToRegister;
    private Button mButtonConfirmInforRegis;
    private EditText mEditTextMemberFamily;
    private TextView mTextViewTitle;
    private TextView mTextViewPostalCode1;
    private TextView mTextViewPostalCode2;
    private TextView mSpinnerCareer;
    private TextView mSpinnerIncome;
    private TextView mTVFamilyStructure;
    private TextView mWaringCareer;
    private TextView mWarningMemberFamily;
    private TextView mWarningFamilyConstruct;
    private TextView mWarningPhoneNumber;
    private TextView mTextViewCareer;
    private TextView mTextViewMemberFamily;
    private TextView mTextViewFamilyStructure;
    private TextView mTextViewNumberPhone;
    private TextView mTextViewIncome;

    private DialogFamilyStructure dialogFamilyStructureNew;
    private ArrayList<FamilyStructureEntity> listFamilyStructure;
    private int currentScrollY = 0;
    private int valueMen;
    private int valueWomen;


    public static FrmRegisterInformation getInstance() {
        return new FrmRegisterInformation();
    }

    public static FrmRegisterInformation getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmRegisterInformation fragment = new FrmRegisterInformation();
        Bundle data = new Bundle();
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_register_information;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_REGISTER_INFORMATION;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        //Start Icon
        View imgMandatoryCareer = view.findViewById(R.id.imgMandatoryCareer);
        imgMandatoryCareer.getLayoutParams().width = activity.getSizeWithScale(52);
        imgMandatoryCareer.getLayoutParams().height = activity.getSizeWithScale(30);

        View imgMandatoryFamilySize = view.findViewById(R.id.imgMandatoryFamilySize);
        imgMandatoryFamilySize.getLayoutParams().width = activity.getSizeWithScale(52);
        imgMandatoryFamilySize.getLayoutParams().height = activity.getSizeWithScale(30);

        View imgMandatoryFamilyStructure = view.findViewById(R.id.imgMandatoryFamilyStructure);
        imgMandatoryFamilyStructure.getLayoutParams().width = activity.getSizeWithScale(52);
        imgMandatoryFamilyStructure.getLayoutParams().height = activity.getSizeWithScale(30);

        View imgMandatoryPostOffice = view.findViewById(R.id.imgMandatoryPostOffice);
        imgMandatoryPostOffice.getLayoutParams().width = activity.getSizeWithScale(52);
        imgMandatoryPostOffice.getLayoutParams().height = activity.getSizeWithScale(30);

        View imgOptionalIncome = view.findViewById(R.id.imgOptionalIncome);
        imgOptionalIncome.getLayoutParams().width = activity.getSizeWithScale(52);
        imgOptionalIncome.getLayoutParams().height = activity.getSizeWithScale(30);

        //End Icon
        mTextViewCareer = view.findViewById(R.id.textView_career);
        mTextViewMemberFamily = view.findViewById(R.id.tv_text_member_family);
        mTextViewFamilyStructure = view.findViewById(R.id.tvFamilyStructureConfirm);
        mTextViewNumberPhone = view.findViewById(R.id.tvNumberPhone);
        mTextViewIncome = view.findViewById(R.id.textView_income);
        mScrollView = view.findViewById(R.id.scroll_view_register_info);
        mTVFamilyStructure = view.findViewById(R.id.textViewFamilyStructure);
        mConstraintRegisInfor = view.findViewById(R.id.constraint_regis_infor);
        mConstraintConfirmInfor = view.findViewById(R.id.constraint_confirm_regis_infor);
        mTextViewTitle = view.findViewById(R.id.text_title_register_information);

        //Space Start
        int hTextViewErr = activity.getSizeWithScale(40);
        mWaringCareer = view.findViewById(R.id.text_view_warning_career);
//        mWaringCareer.getLayoutParams().height = hTextViewErr;

        mWarningMemberFamily = view.findViewById(R.id.text_view_warning_member_family);
//        mWarningMemberFamily.getLayoutParams().height = hTextViewErr;

        mWarningFamilyConstruct = view.findViewById(R.id.text_view_warning_family_construct);
        mWarningFamilyConstruct.getLayoutParams().height = hTextViewErr;

        mWarningPhoneNumber = view.findViewById(R.id.text_view_warning_phone_number);
//        mWarningPhoneNumber.getLayoutParams().height = hTextViewErr;

        Space view_warning_dummy = view.findViewById(R.id.view_warning_dummy);
//        view_warning_dummy.getLayoutParams().height = hTextViewErr;

        View spaceIncome = view.findViewById(R.id.spaceIncome);
        spaceIncome.getLayoutParams().height = hTextViewErr;

        View spacePhoneNumber = view.findViewById(R.id.spacePhoneNumber);
        spacePhoneNumber.getLayoutParams().height = hTextViewErr;

        View spaceFamilyConstruct = view.findViewById(R.id.spaceFamilyConstruct);
        spaceFamilyConstruct.getLayoutParams().height = hTextViewErr;

        View spaceMemberFamily = view.findViewById(R.id.spaceMemberFamily);
        spaceMemberFamily.getLayoutParams().height = hTextViewErr;

        View spaceCareer = view.findViewById(R.id.spaceCareer);
        spaceCareer.getLayoutParams().height = hTextViewErr;

        View ivBgRegisterInfo = view.findViewById(R.id.ivBgRegisterInfo);
        ivBgRegisterInfo.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgRegisterInfo.getLayoutParams().height = activity.getSizeWithScale(2340);

        View flRegisterStep1 = view.findViewById(R.id.flRegisterStep1);
        flRegisterStep1.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterStep1.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterStep2 = view.findViewById(R.id.flRegisterStep2);
        flRegisterStep2.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterStep2.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterStep3 = view.findViewById(R.id.flRegisterStep3);
        flRegisterStep3.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterStep3.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterConfirmStep1 = view.findViewById(R.id.flRegisterConfirmStep1);
        flRegisterConfirmStep1.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterConfirmStep1.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterConfirmStep2 = view.findViewById(R.id.flRegisterConfirmStep2);
        flRegisterConfirmStep2.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterConfirmStep2.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterConfirmStep3 = view.findViewById(R.id.flRegisterConfirmStep3);
        flRegisterConfirmStep3.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterConfirmStep3.getLayoutParams().height = activity.getSizeWithScale(123);

        View imgLogo = view.findViewById(R.id.imgLogo);
        imgLogo.getLayoutParams().width = activity.getSizeWithScale(384);
        imgLogo.getLayoutParams().height = activity.getSizeWithScale(145);

        Button btnSelectFamilyStructure = view.findViewById(R.id.btnSelectFamilyStructure);
        btnSelectFamilyStructure.getLayoutParams().width = activity.getSizeWithScale(329);
        btnSelectFamilyStructure.getLayoutParams().height = activity.getSizeWithScale(83);

        mSpinnerCareer = view.findViewById(R.id.text_view_career);
        mSpinnerCareer.getLayoutParams().height = activity.getSizeWithScale(70);

        mEditTextMemberFamily = view.findViewById(R.id.edit_text_member_family);
        mEditTextMemberFamily.getLayoutParams().height = activity.getSizeWithScale(70);

        mTextViewPostalCode1 = view.findViewById(R.id.edit_text_postal_code1);
        mTextViewPostalCode1.getLayoutParams().width = activity.getSizeWithScale(120);
        mTextViewPostalCode1.getLayoutParams().height = activity.getSizeWithScale(70);

        mTextViewPostalCode2 = view.findViewById(R.id.edit_text_postal_code2);
        mTextViewPostalCode2.getLayoutParams().width = activity.getSizeWithScale(169);
        mTextViewPostalCode2.getLayoutParams().height = activity.getSizeWithScale(70);

        mSpinnerIncome = view.findViewById(R.id.spinner_income);
        mSpinnerIncome.getLayoutParams().height = activity.getSizeWithScale(70);

        mButtonRegisterInfor = view.findViewById(R.id.button_register_infor);
        mButtonRegisterInfor.getLayoutParams().width = activity.getSizeWithScale(853);
        mButtonRegisterInfor.getLayoutParams().height = activity.getSizeWithScale(178);

        mButtonBackToRegister = view.findViewById(R.id.button_back_register);
        mButtonBackToRegister.getLayoutParams().width = activity.getSizeWithScale(426);
        mButtonBackToRegister.getLayoutParams().height = activity.getSizeWithScale(178);

        mButtonConfirmInforRegis = view.findViewById(R.id.button_confirm_infor);
        mButtonConfirmInforRegis.getLayoutParams().width = activity.getSizeWithScale(426);
        mButtonConfirmInforRegis.getLayoutParams().height = activity.getSizeWithScale(178);

        btnSelectFamilyStructure.setOnClickListener(this);
        view.findViewById(R.id.clRegisterInformation).setOnClickListener(this);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    View view = getView();
                    if (view == null)
                        return;
                    if (view.getHeight() < activity.getScreenHeight()) {
                        Log.d(TAG, "scrollLayoutWhenKeyboardShow: Observer :");
                        scrollLayoutWhenKeyboardShow(false);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void scrollLayoutWhenKeyboardShow(boolean hasFocus) {
        try {
            if (!hasFocus)
                hasFocus = mEditTextMemberFamily.hasFocus() || mTextViewPostalCode1.hasFocus()
                        || mTextViewPostalCode2.hasFocus();
            if (hasFocus)
                mScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (currentScrollY != mScrollView.getScrollY()) {
                                mScrollView.scrollTo(0, mScrollView.getScrollY()
                                        + mEditTextMemberFamily.getHeight());
                                currentScrollY = mScrollView.getScrollY();
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }, 350);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void prepareListFamilyStructure() {
        if (listFamilyStructure == null) {
            listFamilyStructure = new ArrayList<>();
        }
        listFamilyStructure.add(new FamilyStructureEntity("小学生以下"));
        listFamilyStructure.add(new FamilyStructureEntity("小学生"));
        listFamilyStructure.add(new FamilyStructureEntity("中学生"));
        listFamilyStructure.add(new FamilyStructureEntity("15-20歳"));
        listFamilyStructure.add(new FamilyStructureEntity("21-30歳"));
        listFamilyStructure.add(new FamilyStructureEntity("31-40歳"));
        listFamilyStructure.add(new FamilyStructureEntity("41-50歳"));
        listFamilyStructure.add(new FamilyStructureEntity("51-60歳"));
        listFamilyStructure.add(new FamilyStructureEntity("60歳以上"));
    }

    private void showDialogFamilyStructure() {
        valueMen = 0;
        valueWomen = 0;
        //
        if (dialogFamilyStructureNew == null) {
            dialogFamilyStructureNew = DialogFamilyStructure.getInstance(activity, listFamilyStructure).setCancel(true);
        }
        dialogFamilyStructureNew.setOnConfirmClickListener(new DialogFamilyStructure.OnConfirmClickListener() {
            @Override
            public void onConfirmClicked() {
                StringBuilder stringBuilder = new StringBuilder();
                String format = "%s    男性 %d 人   女性 %d 人";
                String formatMen = "%s    男性 %d 人";
                String formatWomen = "%s    女性 %d 人";
//                for (FamilyStructureEntity structureEntity : listFamilyStructure) {
                for (int i = 0; i <  listFamilyStructure.size(); i++) {
                    FamilyStructureEntity structureEntity = listFamilyStructure.get(i);
                    if (structureEntity.getValueMen() != 0 && structureEntity.getValueWomen() != 0) {
                        stringBuilder.append("\n");
                        stringBuilder.append(String.format(format, structureEntity.getTitle(), structureEntity.getValueMen(), structureEntity.getValueWomen()));
                        stringBuilder.append("\n");
                        valueMen += structureEntity.getValueMen();
                        valueWomen += structureEntity.getValueWomen();
                    }
                    if (structureEntity.getValueMen() == 0 && structureEntity.getValueWomen() != 0) {
                        stringBuilder.append("\n");
                        stringBuilder.append(String.format(formatWomen, structureEntity.getTitle(), structureEntity.getValueWomen()));
                        stringBuilder.append("\n");
                        valueWomen += structureEntity.getValueWomen();
                    }
                    if (structureEntity.getValueMen() != 0 && structureEntity.getValueWomen() == 0) {
                        stringBuilder.append("\n");
                        stringBuilder.append(String.format(formatMen, structureEntity.getTitle(), structureEntity.getValueMen()));
                        stringBuilder.append("\n");
                        valueMen += structureEntity.getValueMen();
                    }
                }
                if (TextUtils.isEmpty(stringBuilder.toString())) {
                    mTVFamilyStructure.setVisibility(View.GONE);
                } else {
                    mTVFamilyStructure.setVisibility(View.VISIBLE);
                }

                mTVFamilyStructure.setText(stringBuilder.toString().trim());
                DebugHelper.Log(TAG, "onConfirmClicked: " + stringBuilder.toString());
            }
        });
        dialogFamilyStructureNew.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        prepareListFamilyStructure();
        //
        mButtonConfirmInforRegis.setOnClickListener(this);
        mButtonRegisterInfor.setOnClickListener(this);
        mButtonBackToRegister.setOnClickListener(this);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ResourceType")
        View pview = inflater.inflate(R.layout.layout_drop_down_popup, view.findViewById(R.layout.frm_register_account));
        PopupWindow pw = new PopupWindow(pview);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        mSpinnerCareer.setOnClickListener(v -> {
            AppUtils.hideKeyboard(v);
            pw.showAsDropDown(v, 0, 0);
            pw.update(v.getWidth(), 400);
            ListView lst = pview.findViewById(R.id.lstview);
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_dropdown, ARRAY_CAREER);
            lst.setAdapter(adapter);
            lst.setOnItemClickListener((parent, view1, position, id) -> {
                TextView c = view1.findViewById(R.id.label);
                String valueSpinnerCareer = c.getText().toString();
                mSpinnerCareer.setText(valueSpinnerCareer);
                pw.dismiss();
            });
        });
        mSpinnerIncome.setOnClickListener(v -> {
            AppUtils.hideKeyboard(v);
            pw.showAsDropDown(v, 0, 0);
            pw.update(v.getWidth(), 400);
            ListView lst = pview.findViewById(R.id.lstview);
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_dropdown, ARRAY_INCOME);
            lst.setAdapter(adapter);
            lst.setOnItemClickListener((parent, view1, position, id) -> {
                TextView c = view1.findViewById(R.id.label);
                String valueSpinnerIncome = c.getText().toString();
                mSpinnerIncome.setText(valueSpinnerIncome);
                pw.dismiss();
            });
        });
    }

    @Override
    protected void finish() {
        if (mConstraintConfirmInfor.isShown()) {
            mTextViewTitle.setText(R.string.title_register_information);
            mConstraintConfirmInfor.setVisibility(View.GONE);
            mConstraintRegisInfor.setVisibility(View.VISIBLE);
        } else {
            try {
                activity.backToPreviousFromBackStack(getArguments());
            } catch (Throwable e) {
                e.printStackTrace();
            }
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
    public void onDestroy() {
        super.onDestroy();
        if (dialogFamilyStructureNew != null) {
            dialogFamilyStructureNew.release();
            dialogFamilyStructureNew = null;
        }
    }

    private void validateField() {
        String stringMemberFamily = mEditTextMemberFamily.getText().toString();
        int intMemberFamily = 0;
        if (TextUtils.isEmpty(mSpinnerCareer.getText()))
            mWaringCareer.setText(R.string.text_error_register);
        else
            mWaringCareer.setText("");
        if (TextUtils.isEmpty(mEditTextMemberFamily.getText())) {
            mWarningMemberFamily.setText(R.string.text_error_register);
        } else {
            mWarningMemberFamily.setText("");
            intMemberFamily = Integer.parseInt(stringMemberFamily);
            if (intMemberFamily != valueMen + valueWomen)
                mWarningMemberFamily.setText(R.string.text_error_register);
            else
                mWarningMemberFamily.setText("");
        }
        if (TextUtils.isEmpty(mTVFamilyStructure.getText()))
            mWarningFamilyConstruct.setText(R.string.text_error_register);
        else
            mWarningFamilyConstruct.setText("");
        if (TextUtils.isEmpty(mTextViewPostalCode1.getText()) ||
                TextUtils.isEmpty(mTextViewPostalCode2.getText()))
            mWarningPhoneNumber.setText(R.string.text_error_register);
        else
            mWarningPhoneNumber.setText("");
        if (validate(intMemberFamily))
            showConfirmInformation();
    }

    private boolean validate(int intMemberFamily) {
        boolean check = true;
        if (TextUtils.isEmpty(mSpinnerCareer.getText()))
            check = false;
        if (TextUtils.isEmpty(mEditTextMemberFamily.getText()))
            check = false;
        if (intMemberFamily != valueMen + valueWomen)
            check = false;
        if (TextUtils.isEmpty(mTVFamilyStructure.getText()))
            check = false;
        if (TextUtils.isEmpty(mTextViewPostalCode1.getText())
                || TextUtils.isEmpty(mTextViewPostalCode2.getText()))
            check = false;
        return check;
    }

    private void showConfirmInformation() {
        mTextViewTitle.setText(R.string.text_title_confirm_register_infor);
        mTextViewCareer.setText(mSpinnerCareer.getText());
        mTextViewMemberFamily.setText(mEditTextMemberFamily.getText() + "   人");
        mTextViewFamilyStructure.setText(mTVFamilyStructure.getText());
        mTextViewNumberPhone.setText(mTextViewPostalCode1.getText() + " - " + mTextViewPostalCode2.getText());
        mTextViewIncome.setText(mSpinnerIncome.getText());
        mConstraintRegisInfor.setVisibility(View.GONE);
        mConstraintConfirmInfor.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clRegisterInformation:
                AppUtils.hideKeyboard(v);
                break;
            case R.id.button_register_infor:
                AppUtils.hideKeyboard(v);
                validateField();
                break;
            case R.id.button_back_register:
                mTextViewTitle.setText(R.string.title_register_information);
                mConstraintRegisInfor.setVisibility(View.VISIBLE);
                mConstraintConfirmInfor.setVisibility(View.GONE);
                break;
            case R.id.button_confirm_infor:
                activity.addFragmentToMain(new FrmAfterRegister());
                break;
            case R.id.btnSelectFamilyStructure:
                AppUtils.hideKeyboard(v);
                showDialogFamilyStructure();
                break;
            default:
                break;
        }
    }

}