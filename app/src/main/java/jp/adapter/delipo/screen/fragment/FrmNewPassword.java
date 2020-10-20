package jp.adapter.delipo.screen.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ApiConstants;
import jp.adapter.delipo.entity.ApiResult;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.listener.ApiCallback;
import jp.adapter.delipo.utils.AppUtils;
import jp.adapter.delipo.utils.DebugHelper;

import static jp.adapter.delipo.constants.AppConstants.PATTERN_PASSWORD;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_NEW_PASSWORD;
import static jp.adapter.delipo.constants.FragmentConstants.KEY_DATA_HASH;

public class FrmNewPassword extends BaseFragment implements View.OnClickListener {
    private EditText edtNewpass;
    private EditText edtConfirmatioPassword;
    private ScrollView mScrollView;
    private TextView tvMessageNewpas;
    private TextView tvMessageConfirmationPass;
    private int currentScrollY = 0;
    private Button mBtnRePass;
    private String hash;

    public static FrmNewPassword getInstance(String hash) {
        FrmNewPassword frmNewPassword = new FrmNewPassword();
        if (!TextUtils.isEmpty(hash)) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_DATA_HASH, hash);
            frmNewPassword.setArguments(bundle);
        }
        return frmNewPassword;
    }

    public static FrmNewPassword getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {

        FrmNewPassword fragment = new FrmNewPassword();
        if (arrayList != null) {
            Bundle data = new Bundle();
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_new_password;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_NEW_PASSWORD;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View ivBgLogin = view.findViewById(R.id.ivBgResetPass);
        ivBgLogin.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgLogin.getLayoutParams().height = activity.getSizeWithScale(2340);

        mScrollView = view.findViewById(R.id.scroll_view_new_password);
        edtNewpass = view.findViewById(R.id.edit_text_new_pass);
        edtConfirmatioPassword = view.findViewById(R.id.edit_text_confirmation_password_field);
        mBtnRePass = view.findViewById(R.id.button_new_pass);
        tvMessageNewpas = view.findViewById(R.id.text_view_message_new_pass_fail);
        tvMessageConfirmationPass = view.findViewById(R.id.text_view_message_confirmation_password_fail);
        edtNewpass.setOnFocusChangeListener(focusChangeListener);
        edtConfirmatioPassword.setOnFocusChangeListener(focusChangeListener);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hash = getArguments().getString(KEY_DATA_HASH);
        }
    }

    @Override
    protected void finish() {
        try {
            activity.addFragmentToMain(FrmForgotPassword.getInstance());
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        mBtnRePass.setOnClickListener(this);

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

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                Log.d(TAG, "scrollLayoutWhenKeyboardShow: onFocusChange :");
                scrollLayoutWhenKeyboardShow(true);
            }
        }
    };

    private void scrollLayoutWhenKeyboardShow(boolean hasFocus) {
        try {
            if (!hasFocus) {
                Log.d(TAG, "scrollLayoutWhenKeyboardShow: 1 :" + hasFocus);
                hasFocus = edtNewpass.hasFocus() || edtConfirmatioPassword.hasFocus();
            }
            if (hasFocus)
                Log.d(TAG, "scrollLayoutWhenKeyboardShow: 2 :" + hasFocus);
            mScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        mScrollView.scrollTo(0, edtConfirmatioPassword.getHeight() + edtNewpass.getHeight() + mBtnRePass.getHeight());
                        currentScrollY = mScrollView.getScrollY();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }, 350);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void resetPassword() {
        String newPass = edtNewpass.getText().toString().trim();
        String confirmatioPass = edtConfirmatioPassword.getText().toString().trim();
        if (TextUtils.isEmpty(newPass) || !Pattern.compile(PATTERN_PASSWORD)
                .matcher(edtNewpass.getText().toString()).matches()) {
            tvMessageNewpas.setText(R.string.text_error_register);
            tvMessageConfirmationPass.setText(null);
        } else if (!newPass.equals(confirmatioPass)) {
            tvMessageConfirmationPass.setText(R.string.text_error_register);
            tvMessageNewpas.setText(null);
        } else {
            tvMessageConfirmationPass.setText(null);
            tvMessageNewpas.setText(null);
            DebugHelper.Log(TAG,"hash: "+hash);
            activity.resetPassword(hash, newPass, new ApiCallback() {
                @Override
                public void onFinished(ApiResult result) {
                    if (result.statusCode == ApiConstants.STATUS_OK) {
                        if (!TextUtils.isEmpty(result.message)) {
                            activity.showNotice(result.message, true, new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    activity.addFragmentToMain(FrmLogin.getInstance());
                                }
                            });
                        }
                        return;
                    }
                    if (!TextUtils.isEmpty(result.message))
                        activity.showNotice(result.message, true, null);
                    else if (result.statusCode == ApiConstants.STATUS_ERROR_INTERNET)
                        activity.showNotice(R.string.msg_error_internet, true, null);
                    else
                        activity.showNotice(R.string.msg_error_server, true, null);

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_new_pass:
                resetPassword();
                break;
        }

    }
}