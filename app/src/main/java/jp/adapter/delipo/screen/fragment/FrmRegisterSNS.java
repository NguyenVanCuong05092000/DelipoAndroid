package jp.adapter.delipo.screen.fragment;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import jp.adapter.delipo.R;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_REGISTER_SNS;

public class FrmRegisterSNS extends BaseFragment implements View.OnClickListener {

    public static FrmRegisterSNS getInstance() {
        return new FrmRegisterSNS();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_register_sns;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_REGISTER_SNS;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        TextView tvRegister = view.findViewById(R.id.tvRegister);
        TextView textViewName = view.findViewById(R.id.tvNickName);
        TextView textViewGender = view.findViewById(R.id.tvGender);
        TextView textViewAge = view.findViewById(R.id.tvAge);
        TextView textViewCareer = view.findViewById(R.id.tvProfession);
        TextView textViewFamilyMember = view.findViewById(R.id.tvFamilyStructure);
        TextView textViewPostalCode = view.findViewById(R.id.tvYourHousePostalCodeNumber);
        TextView textViewIncome = view.findViewById(R.id.tvFamilyIncome);
        EditText editTextName = view.findViewById(R.id.etNickName);
        EditText editTextAge = view.findViewById(R.id.etAge);
        EditText editTextCareer = view.findViewById(R.id.etProfession);
        EditText editTextFamilyMember = view.findViewById(R.id.etFamilyStructure);
        EditText editTextPostalCode = view.findViewById(R.id.etYourHousePostalCodeNumber);
        EditText editTextIncome = view.findViewById(R.id.etFamilyIncome);
        CheckBox checkBoxMale = view.findViewById(R.id.cbMale);
        CheckBox checkBoxFemale = view.findViewById(R.id.cbFemale);
        tvRegister.setOnClickListener(this);
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
            case R.id.tvRegister:
                activity.addFragmentToMain(FrmAfterRegister.getInstance());
                break;
        }
    }
}