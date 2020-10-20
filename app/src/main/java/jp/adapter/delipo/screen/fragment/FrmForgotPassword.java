package jp.adapter.delipo.screen.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.regex.Pattern;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ApiConstants;
import jp.adapter.delipo.utils.AppUtils;

import static jp.adapter.delipo.constants.AppConstants.PATTERN_PASSWORD;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_FORGOT_PASSWORD;

public class FrmForgotPassword extends BaseFragment implements View.OnClickListener {

    private Button btnResetPassword;
    private TextView tvMessageForgotPassword;
    private EditText edt_email;
    private ConstraintLayout mForgotPasswordField;

    public static FrmForgotPassword getInstance() {
        return new FrmForgotPassword();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_forgot_password;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_FORGOT_PASSWORD;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        mForgotPasswordField = view.findViewById(R.id.constraint_layout_forgot_password_field);
        View ivBgForgotPassword = view.findViewById(R.id.ivBgForgotPassword);
        ivBgForgotPassword.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgForgotPassword.getLayoutParams().height = activity.getSizeWithScale(2340);

        View imgLogoForgotPassword = view.findViewById(R.id.imgLogoForgotPassword);
        imgLogoForgotPassword.getLayoutParams().width = activity.getSizeWithScale(847);
        imgLogoForgotPassword.getLayoutParams().height = activity.getSizeWithScale(328);

        btnResetPassword = view.findViewById(R.id.button_reset_password);
        btnResetPassword.getLayoutParams().width = activity.getSizeWithScale(853);
        btnResetPassword.getLayoutParams().height = activity.getSizeWithScale(178);

        mForgotPasswordField.getLayoutParams().height = activity.getSizeWithScale(129);

    }

    @Override
    protected void finish() {
        try {
            activity.addFragmentToMain(FrmLogin.getInstance());
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
    public void onPermissionsGranted() {
        super.onPermissionsGranted();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_email = view.findViewById(R.id.edit_text_email_field_forgot_pass);
        tvMessageForgotPassword = view.findViewById(R.id.text_view_message_forgot_password);

        btnResetPassword.setOnClickListener(this);
        view.findViewById(R.id.linear_layout_forgot_password).setOnClickListener(this);

    }

    private void forgotPassword() {
        try {
            if (activity.isCallingApi)
                return;
            tvMessageForgotPassword.setText(null);
            String email = edt_email.getText().toString().trim();
            if (TextUtils.isEmpty(email))
                return;
            activity.forgotPassword(email, result -> {
                try {
                    if (result.statusCode == ApiConstants.STATUS_OK) {
                        if (!TextUtils.isEmpty(result.message))
                        activity.showNotice(result.message, true, dialog -> {
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        return;
                    }
                    if (!TextUtils.isEmpty(result.message))
                        tvMessageForgotPassword.setText(result.message);
                    else if (result.statusCode == ApiConstants.STATUS_ERROR_INTERNET)
                        tvMessageForgotPassword.setText(R.string.msg_error_internet);
                    else
                        tvMessageForgotPassword.setText(R.string.msg_error_server);
                } catch (Throwable ignored) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateField() {
        String edtEmail = edt_email.getText().toString().trim();
        if (TextUtils.isEmpty(edtEmail) || !AppUtils.isValidEmail(edt_email.getText())) {
            tvMessageForgotPassword.setText(R.string.text_error_register);
        } else {
            tvMessageForgotPassword.setText(null);
            forgotPassword();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_reset_password:
                validateField();
                break;
            case R.id.linear_layout_forgot_password:
                AppUtils.hideKeyboard(getView());
                break;
        }
    }

}
