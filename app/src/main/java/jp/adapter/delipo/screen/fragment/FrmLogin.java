package jp.adapter.delipo.screen.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.BuildConfig;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ApiConstants;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.entity.ApiResult;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.api.LoginResponseEntity;
import jp.adapter.delipo.listener.ApiCallback;
import jp.adapter.delipo.utils.AppUtils;
import jp.adapter.delipo.utils.DebugHelper;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_LOGIN;

public class FrmLogin extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private final String TAG = this.getClass().getSimpleName();
    private CallbackManager callbackManager;
    private int countLogin;
    private TextView tvMessage;
    private EditText edEmail;
    private EditText edPassword;
    private ScrollView mScrollView;
    private int currentScrollY = 0;
    private LinearLayout login_sns;
    ConstraintLayout mLoginField;

    public static FrmLogin getInstance() {
        return new FrmLogin();
    }

    public static FrmLogin getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmLogin fragment = new FrmLogin();
        if (arrayList != null) {
            Bundle data = new Bundle();
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
            fragment.setArguments(data);
        }
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_login;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_LOGIN;
    }

    @Override
    protected void finish() {
        try {
            activity.addFragmentToMain(FrmSplash.getInstance());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View ivBgLogin = view.findViewById(R.id.ivBgLogin);
        ivBgLogin.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgLogin.getLayoutParams().height = activity.getSizeWithScale(2340);

        View imgLogo = view.findViewById(R.id.imgLogo);
        imgLogo.getLayoutParams().width = activity.getSizeWithScale(847);
        imgLogo.getLayoutParams().height = activity.getSizeWithScale(328);

        mScrollView = view.findViewById(R.id.scroll_view_login);
        mLoginField = view.findViewById(R.id.constraint_layout_login_field);
        mLoginField.getLayoutParams().height = activity.getSizeWithScale(259);

        login_sns = view.findViewById(R.id.login_sns);

        Button mButtonLogin = view.findViewById(R.id.button_login);
        mButtonLogin.getLayoutParams().width = activity.getSizeWithScale(853);
        mButtonLogin.getLayoutParams().height = activity.getSizeWithScale(178);
    }

    @Override
    public boolean isBackPreviousEnable() {
        return true;
    }

    @Override
    public void backToPrevious() {
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edEmail = view.findViewById(R.id.edit_text_email_field_login);
        edPassword = view.findViewById(R.id.edit_text_password);
        tvMessage = view.findViewById(R.id.text_view_message_login_fail);
        edEmail.setText(activity.getEmail());
        edPassword.setText(activity.getPassword());

        view.findViewById(R.id.button_facbook).setOnClickListener(this);
        view.findViewById(R.id.button_line).setOnClickListener(this);
        view.findViewById(R.id.button_google).setOnClickListener(this);
        view.findViewById(R.id.scroll_view_login).setOnClickListener(this);
        view.findViewById(R.id.button_login).setOnClickListener(this);
        view.findViewById(R.id.text_view_forgot_password_login).setOnClickListener(this);
        view.findViewById(R.id.text_view_register_account).setOnClickListener(this);

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
                hasFocus = edEmail.hasFocus() || edPassword.hasFocus();
            }

            if (hasFocus)
                Log.d(TAG, "scrollLayoutWhenKeyboardShow: 2 :" + hasFocus);
            mScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {

                        mScrollView.scrollTo(0, login_sns.getHeight());
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

    private void login() {
        try {
            if (activity.isCallingApi)
                return;

            tvMessage.setText(null);
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                return;
            activity.login(email, password, this::handleLoginResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLoginResult(ApiResult result) {
        try {
            if (result.statusCode == ApiConstants.STATUS_OK && result.response instanceof JSONObject) {
                JSONObject jsResponse = (JSONObject) result.response;
                String token = jsResponse.optString(ApiConstants.PARAM_TOKEN);
                DebugHelper.Log(TAG, "token: " + token);
                if (!TextUtils.isEmpty(token)) {
                    activity.onLoginSuccess(new LoginResponseEntity(
                            jsResponse.optString(ApiConstants.PARAM_ID),
                            jsResponse.optString(ApiConstants.PARAM_NAME),
                            jsResponse.optString(ApiConstants.PARAM_EMAIL),
                            jsResponse.optString(ApiConstants.PARAM_STATUS),
                            jsResponse.optString(ApiConstants.PARAM_GENDER),
                            jsResponse.optString(ApiConstants.PARAM_BIRTHDAY),
                            jsResponse.optString(ApiConstants.PARAM_JOB),
                            jsResponse.optString(ApiConstants.PARAM_FAMILY_NUMBER),
                            jsResponse.optString(ApiConstants.PARAM_FAMILY_MEMBER),
                            jsResponse.optString(ApiConstants.PARAM_ZIP_CODE),
                            jsResponse.optString(ApiConstants.PARAM_FAMILY_INCOME),
                            jsResponse.optString(ApiConstants.PARAM_LAST_LOGIN_FOR_API),
                            jsResponse.optString(ApiConstants.PARAM_CREATE_AT),
                            jsResponse.optString(ApiConstants.PARAM_UPDATE_AT),
                            token
                    ));
                    return;
                }
            }
            if (!TextUtils.isEmpty(result.message)) {
                tvMessage.setText(result.message);
            } else if (result.statusCode == ApiConstants.STATUS_ERROR_INTERNET)
                tvMessage.setText(R.string.msg_error_internet);
            else
                tvMessage.setText(R.string.msg_error_server);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void loginWithFacebook() {
        AppEventsLogger.activateApp(activity.getApplication());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(ApiConstants.PARAM_EMAIL, ApiConstants.PARAM_PUBLIC_PROFILE_FACEBOOK));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (AppConstants.LOG_DEBUG)
                    Log.e(TAG, "======Facebook login success======");
                Log.e(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                if (AccessToken.getCurrentAccessToken() != null) {
                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject object, GraphResponse response) {
                            if (object != null) {
                                Log.d(TAG, "onCompleted: " + object.toString());
                                Log.e(TAG, "ID: " + object.optString(ApiConstants.PARAM_ID_FACEBOOK)
                                        + "\nName: " + object.optString(ApiConstants.PARAM_NAME_FACEBOOK)
                                        + "\nEmail: " + object.optString(ApiConstants.PARAM_EMAIL_FACEBOOK));

                                activity.loginFacebook(loginResult.getAccessToken().getToken(), object.optString(ApiConstants.PARAM_ID_FACEBOOK), FrmLogin.this::handleLoginResult);
                            }
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString(ApiConstants.PARAM_FIELDS_FACEBOOK, ApiConstants.PARAM_FIELDS);
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {
                activity.showNotice(getString(R.string.noticeFacebookCancel), true, null);
            }

            @Override
            public void onError(FacebookException exception) {
                activity.showNotice(getString(R.string.noticeFacebookError), true, null);
                Log.e(TAG, "Facebook login Error: " + exception.toString());
            }
        });
    }

    private void loginWithLine() {
        try {
            // App-to-app login
            Intent loginIntent = LineLoginApi.getLoginIntent(
                    activity,
                    ApiConstants.CHANNEL_ID,
                    new LineAuthenticationParams.Builder()
                            .scopes(Arrays.asList(Scope.OPENID_CONNECT, Scope.OC_EMAIL,
                                    Scope.OC_BIRTHDATE, Scope.PROFILE))
                            // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                            .build());
            startActivityForResult(loginIntent, REQUEST_CODE);
        } catch (Exception e) {
            Log.e(TAG, "Line login Exception" + e.toString());
        }
    }

    private void loginWithGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE);
    }

    private void callBackFacebook(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void callBackLine(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Line Unsupported Request");
            return;
        }
        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
        switch (result.getResponseCode()) {
            case SUCCESS:
                // Login successful
                Log.e(TAG, "======Line login success======");
                Log.e(TAG, "Line Access Token: "
                        + result.getLineCredential().getAccessToken().getTokenString()
                        + "\n Line ID Token:  " + result.getLineIdToken().getRawString()
                        + "\n ID: " + result.getLineProfile().getUserId()
                        + "\n Email: " + result.getLineIdToken().getEmail()
                        + "\n Name: " + result.getLineProfile().getDisplayName());
                if (result.getLineIdToken() != null)
                    activity.loginLine(result.getLineIdToken().getRawString(), new ApiCallback() {
                        @Override
                        public void onFinished(ApiResult result) {
                            handleLoginResult(result);
                        }
                    });
                else {
                    activity.showNotice(getString(R.string.noticeLineError), true, null);
                }
                break;
            case AUTHENTICATION_AGENT_ERROR:
                // Login canceled due to other error
                activity.showNotice(getString(R.string.noticeLineError), true, null);
                Log.e(TAG, "Line login Error: " + result.getErrorData().toString());
                break;
            case CANCEL:
                // Login canceled by user
                activity.showNotice(getString(R.string.noticeLineCancel), true, null);
                break;
        }
    }

    private void callBackGoogle(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = googleSignInAccountTask.getResult(ApiException.class);
                // Signed in successfully, show authenticated UI.
                Log.e(TAG, "======Google login success======");
                Log.e(TAG, "Google Access Token: " + account.getIdToken().toString()
                        + "\n ID: " + account.getId()
                        + "\n Name: " + account.getDisplayName()
                        + "\n Email: " + account.getEmail());
                if (account.getIdToken() != null)
                    activity.loginGoogle(account.getIdToken(), new ApiCallback() {
                        @Override
                        public void onFinished(ApiResult result) {
                            handleLoginResult(result);
                        }
                    });
                else {
                    activity.showNotice(getString(R.string.noticeGoogleError), true, null);
                }
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                activity.showNotice(getString(R.string.noticeGoogleError), true, null);
                Log.e(TAG, "Google login Exception: " + e.toString());
            }
        }
    }

    private void showMyPage() {
        try {
            ArrayList<BackStackData> arrayList = null;
            HashMap<String, Object> mapData = new HashMap<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
//            mapData.put(EXTRA_RATE_PRODUCT, null);
            activity.showPolicy(arrayList, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (countLogin == 0)
            callBackFacebook(requestCode, resultCode, data);
        if (countLogin == 1)
            callBackLine(requestCode, resultCode, data);
        if (countLogin == 2)
            callBackGoogle(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        if (!isClickAble())
            return;
        switch (v.getId()) {
            case R.id.button_login:
                login();
                break;
            case R.id.text_view_forgot_password_login:
                try {
                    activity.addFragmentToMain(FrmForgotPassword.getInstance());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
            case R.id.text_view_register_account:
                showMyPage();
                break;
            case R.id.scroll_view_login:
                AppUtils.hideKeyboard(getView());
                break;
            case R.id.button_facbook:
                countLogin = 0;
                loginWithFacebook();
                break;
            case R.id.button_line:
                countLogin = 1;
                loginWithLine();
                break;
            case R.id.button_google:
                countLogin = 2;
                loginWithGoogle();
                break;
        }
    }
}
