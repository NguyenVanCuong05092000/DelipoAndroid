package jp.adapter.delipo.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
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
import java.util.regex.Pattern;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ApiConstants;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.entity.ApiResult;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.screen.fragment.BaseFragment;
import jp.adapter.delipo.screen.fragment.FrmRegisterInformation;
import jp.adapter.delipo.utils.AppUtils;
import jp.adapter.delipo.utils.DebugHelper;

import static jp.adapter.delipo.constants.AppConstants.PATTERN_PASSWORD;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_REGISTER_ACCOUNT;

public class FrmRegisterAccount extends BaseFragment implements View.OnClickListener {

    private static final String[] ARRAY_YEARS = new String[]{"1920", "1921", "1922", "1923", "1924",
            "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935",
            "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946",
            "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957",
            "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968",
            "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979",
            "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990",
            "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001",
            "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012",
            "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"};

//    private static final String[] ARRAY_YEARS = new String[]{"2020", "2019", "2018", "2017", "2016",
//            "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005",
//            "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994",
//            "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983",
//            "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972",
//            "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961",
//            "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950",
//            "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939",
//            "1938", "1937", "1936", "1935", "1934", "1933", "1932", "1931", "1930", "1929", "1928",
//            "1927", "1926", "1925", "1924", "1923", "1922", "1921", "1920"};

    private static final String[] ARRAY_MONTH = new String[]{"01", "02", "03", "04",
            "05", "06", "07", "08", "09", "10", "11", "12"};

    private static final String[] ARRAY_DAY = new String[]{"01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    private CallbackManager callbackManager;
    private int countLogin;
    private static final int REQUEST_CODE = 1;

    private View mLinearRegisterForm;
    private TextView mTextViewFemale;
    private TextView mTextViewMale;
    private TextView mWarningEmail;
    private TextView mWarningPassword;
    private TextView mWarningConfirmPassword;
    private TextView mWarningGender;
    private TextView mWarningDoB;
    private TextView mTextViewYears;
    private TextView mTextViewMonths;
    private TextView mTextViewDays;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextConfirmPassword;
    private String mGender;
    private ScrollView mScrollView;
    private int currentScrollY = 0;
    private int gender = 0;

    public static FrmRegisterAccount getInstance() {
        return new FrmRegisterAccount();
    }

    public static FrmRegisterAccount getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmRegisterAccount fragment = new FrmRegisterAccount();
        Bundle data = new Bundle();
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_register_account;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_REGISTER_ACCOUNT;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View ivBgRegister = view.findViewById(R.id.ivBgRegister);
        ivBgRegister.getLayoutParams().width = activity.getSizeWithScale(1125);
        ivBgRegister.getLayoutParams().height = activity.getSizeWithScale(2340);

        mEditTextEmail = view.findViewById(R.id.edit_text_email_register);
        mEditTextEmail.getLayoutParams().height = activity.getSizeWithScale(70);

        mEditTextPassword = view.findViewById(R.id.edit_text_password_register);
        mEditTextPassword.getLayoutParams().height = activity.getSizeWithScale(70);

        mEditTextConfirmPassword = view.findViewById(R.id.edit_text_confirm_password);
        mEditTextConfirmPassword.getLayoutParams().height = activity.getSizeWithScale(70);

        Button mButtonRegisterAccount = view.findViewById(R.id.button_register_account);
        mButtonRegisterAccount.getLayoutParams().width = activity.getSizeWithScale(853);
        mButtonRegisterAccount.getLayoutParams().height = activity.getSizeWithScale(178);

        View flRegisterStep1 = view.findViewById(R.id.flRegisterStep1);
        flRegisterStep1.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterStep1.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterStep2 = view.findViewById(R.id.flRegisterStep2);
        flRegisterStep2.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterStep2.getLayoutParams().height = activity.getSizeWithScale(123);

        View flRegisterStep3 = view.findViewById(R.id.flRegisterStep3);
        flRegisterStep3.getLayoutParams().width = activity.getSizeWithScale(328);
        flRegisterStep3.getLayoutParams().height = activity.getSizeWithScale(123);

        View imgLogo = view.findViewById(R.id.imgLogo);
        imgLogo.getLayoutParams().width = activity.getSizeWithScale(384);
        imgLogo.getLayoutParams().height = activity.getSizeWithScale(145);

        mLinearRegisterForm = view.findViewById(R.id.constraint_register_form);
        mScrollView = view.findViewById(R.id.scroll_view_register_account);
        mTextViewFemale = view.findViewById(R.id.text_view_female);
        mTextViewMale = view.findViewById(R.id.text_view_male);
        mWarningEmail = view.findViewById(R.id.warning_email);
        mWarningPassword = view.findViewById(R.id.warning_password);
        mWarningConfirmPassword = view.findViewById(R.id.warning_confirm_password);
        mWarningGender = view.findViewById(R.id.warning_gender);
        mWarningDoB = view.findViewById(R.id.warning_dob);

        mEditTextEmail.setOnFocusChangeListener(focusChangeListener);
        mEditTextPassword.setOnFocusChangeListener(focusChangeListener);
        mEditTextConfirmPassword.setOnFocusChangeListener(focusChangeListener);

        mButtonRegisterAccount.setOnClickListener(this);
        mTextViewFemale.setOnClickListener(this);
        mTextViewMale.setOnClickListener(this);

        view.findViewById(R.id.clRegisterAccount).setOnClickListener(this);
        view.findViewById(R.id.button_facbook).setOnClickListener(this);
        view.findViewById(R.id.button_line).setOnClickListener(this);
        view.findViewById(R.id.button_google).setOnClickListener(this);

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

    private View.OnFocusChangeListener focusChangeListener = (v, hasFocus) -> {
        if (hasFocus) {
            Log.d(TAG, "scrollLayoutWhenKeyboardShow: onFocusChange :");
            scrollLayoutWhenKeyboardShow(true);
        }
    };

    private void scrollLayoutWhenKeyboardShow(boolean hasFocus) {
        try {
            Log.d(TAG, "scrollLayoutWhenKeyboardShow: 1 :" + hasFocus);
            if (!hasFocus) {
                hasFocus = mEditTextEmail.hasFocus() || mEditTextPassword.hasFocus() || mEditTextConfirmPassword.hasFocus();
            }
            if (hasFocus)
                mScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            mScrollView.scrollTo(0, mLinearRegisterForm.getHeight());
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGender = "";
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTextViewYears = view.findViewById(R.id.text_view_year);
        mTextViewMonths = view.findViewById(R.id.text_view_month);
        mTextViewDays = view.findViewById(R.id.text_view_day);
        @SuppressLint("ResourceType")
        View pview = inflater.inflate(R.layout.layout_drop_down_popup, view.findViewById(R.layout.frm_register_account));
        PopupWindow pw = new PopupWindow(pview);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        mTextViewYears.setOnClickListener(v -> {
            // if onclick written here, it gives null pointer exception.
            // if onclick is written here it gives runtime exception.
            pw.showAsDropDown(v, 0, 0);
            pw.update(v.getWidth(), 400);
            ListView lst = pview.findViewById(R.id.lstview);
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_dropdown, ARRAY_YEARS);
            lst.setAdapter(adapter);
            lst.setOnItemClickListener((parent, view1, position, id) -> {
                TextView c = view1.findViewById(R.id.label);
                String playerChanged = c.getText().toString();
                mTextViewYears.setText(playerChanged);
                pw.dismiss();
            });
        });
        mTextViewMonths.setOnClickListener(v -> {
            // if onclick written here, it gives null pointer exception.
            // if onclick is written here it gives runtime exception.
            pw.showAsDropDown(v, 0, 0);
            pw.update(v.getWidth(), 400);
            ListView lst = pview.findViewById(R.id.lstview);
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_dropdown, ARRAY_MONTH);
            lst.setAdapter(adapter);
            lst.setOnItemClickListener((parent, view1, position, id) -> {
                TextView c = view1.findViewById(R.id.label);
                String playerChanged = c.getText().toString();
                mTextViewMonths.setText(playerChanged);
                pw.dismiss();
            });
        });
        mTextViewDays.setOnClickListener(v -> {
            // if onclick written here, it gives null pointer exception.
            // if onclick is written here it gives runtime exception.
            pw.showAsDropDown(v, 0, 0);
            pw.update(v.getWidth(), 400);
            ListView lst = pview.findViewById(R.id.lstview);
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.item_dropdown, ARRAY_DAY);
            lst.setAdapter(adapter);
            lst.setOnItemClickListener((parent, view1, position, id) -> {
                TextView c = view1.findViewById(R.id.label);
                String playerChanged = c.getText().toString();
                mTextViewDays.setText(playerChanged);
                pw.dismiss();
            });
        });
    }

    private void loginWithFacebook() {
        mEditTextEmail.setText("");
        AppEventsLogger.activateApp(activity.getApplication());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList(ApiConstants.PARAM_EMAIL, ApiConstants.PARAM_PUBLIC_PROFILE_FACEBOOK));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (AppConstants.LOG_DEBUG)
                    Log.e(TAG, "======Facebook login success======");
                Log.e(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
//                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList(ApiConstants.PARAM_PUBLIC_PROFILE_FACEBOOK));
                if (AccessToken.getCurrentAccessToken() != null) {
                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject object, GraphResponse response) {
                            if (object != null) {
                                Log.d(TAG, "onCompleted: " + object.toString());
                                Log.e(TAG, "ID: " + object.optString(ApiConstants.PARAM_ID_FACEBOOK)
                                        + "\nName: " + object.optString(ApiConstants.PARAM_NAME_FACEBOOK)
                                        + "\nEmail: " + object.optString(ApiConstants.PARAM_EMAIL_FACEBOOK));
                                mEditTextEmail.setText(object.optString(ApiConstants.PARAM_EMAIL_FACEBOOK));
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

    private void loginWithLine(View view) {
        mEditTextEmail.setText("");
        try {
            // App-to-app login
            Intent loginIntent = LineLoginApi.getLoginIntent(
                    view.getContext(),
                    ApiConstants.CHANNEL_ID,
                    new LineAuthenticationParams.Builder()
                            .scopes(Arrays.asList(Scope.PROFILE))
                            // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                            .build());
            startActivityForResult(loginIntent, REQUEST_CODE);
        } catch (Exception e) {
            Log.e(TAG, "Line login Exception" + e.toString());
        }
    }

    private void loginWithGoogle() {
        mEditTextEmail.setText("");
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE);
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
                        + "\n ID: " + result.getLineProfile().getUserId()
                        + "\n Name: " + result.getLineProfile().getDisplayName());
//                mEditTextEmail.setText("");
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
                Log.e(TAG, "Google Access Token: " + account.getIdToken()
                        + "\n ID: " + account.getId()
                        + "\n Name: " + account.getDisplayName()
                        + "\n Email: " + account.getEmail());
                mEditTextEmail.setText(account.getEmail());
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e(TAG, "Google login Exception" + e.toString());
            }
        }
    }

    public void showRegisterInformation() {
        try {
            ArrayList<BackStackData> arrayList = null;
            HashMap<String, Object> mapData = new HashMap<>();
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK))
                arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
            if (arrayList == null)
                arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment()));
//            mapData.put(EXTRA_RATE_PRODUCT, null);
            activity.showRegisterInformation(arrayList, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
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

    private void validateField() {
        if (!AppUtils.isValidEmail(mEditTextEmail.getText())) {
            mWarningEmail.setText(R.string.text_error_register);
        } else mWarningEmail.setText("");
        if (TextUtils.isEmpty(mEditTextPassword.getText()) || !Pattern.compile(PATTERN_PASSWORD).matcher(mEditTextPassword.getText().toString()).matches()) {
            mWarningPassword.setText(R.string.text_error_register);
        } else mWarningPassword.setText("");
        if (TextUtils.isEmpty(mEditTextPassword.getText()) || !mEditTextConfirmPassword.getText().toString().equals(mEditTextPassword.getText().toString())) {
            mWarningConfirmPassword.setText(R.string.text_error_register);
        } else mWarningConfirmPassword.setText("");
        if (TextUtils.isEmpty(mGender) || mGender.equals("")) {
            mWarningGender.setText(R.string.text_error_register);
        } else mWarningGender.setText("");
        if (TextUtils.isEmpty(mTextViewYears.getText()) || TextUtils.isEmpty(mTextViewMonths.getText()) || TextUtils.isEmpty(mTextViewDays.getText()) || !checkDateOfBirth()) {
            mWarningDoB.setText(R.string.text_error_register);
        } else mWarningDoB.setText("");
        if (validate()) {
            registerAccount();
        }
    }

    private void registerAccount() {
        try {
            if (activity.isCallingApi)
                return;
            String email = mEditTextEmail.getText().toString().trim();
            String password = mEditTextPassword.getText().toString().trim();
            int mGenderTemp = gender;
            String birthday = mTextViewYears.getText().toString().trim()
                    + "-" + mTextViewMonths.getText().toString().trim()
                    + "-" + mTextViewDays.getText().toString().trim();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                return;
            activity.register(email, password, mGenderTemp, birthday, this::handleRegisterResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRegisterResult(ApiResult result) {
        try {
            if (result.statusCode == ApiConstants.STATUS_OK && result.response instanceof Integer) {
                int response = (int) result.response;
                DebugHelper.Log(TAG, "response: " + response);
                activity.showNotice(result.message, true, dialog -> {
                    try {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else if (!TextUtils.isEmpty(result.message)) {
                activity.showNotice(result.message, true, null);
            } else if (result.statusCode == ApiConstants.STATUS_ERROR_INTERNET)
                activity.showNotice(getString(R.string.msg_error_internet), true, null);
            else
                activity.showNotice(getString(R.string.msg_error_server), true, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        boolean check = true;
        if (!AppUtils.isValidEmail(mEditTextEmail.getText())) check = false;
        if (TextUtils.isEmpty(mEditTextPassword.getText()) ||
                !Pattern.compile(PATTERN_PASSWORD).matcher(mEditTextPassword.getText().toString()).matches())
            check = false;
        if (TextUtils.isEmpty(mEditTextPassword.getText()) ||
                !mEditTextConfirmPassword.getText().toString().equals(mEditTextPassword.getText().toString()))
            check = false;
        if (TextUtils.isEmpty(mGender) || mGender.equals("")) check = false;
        if (TextUtils.isEmpty(mTextViewYears.getText()) || TextUtils.isEmpty(mTextViewMonths.getText())
                || TextUtils.isEmpty(mTextViewDays.getText()) || !checkDateOfBirth())
            check = false;
        return check;
    }

    private boolean checkDateOfBirth() {
        String day = (String) mTextViewDays.getText();
        String month = (String) mTextViewMonths.getText();
        int year = Integer.parseInt((String) mTextViewYears.getText());
        if (day.equals("31") && (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11"))) {
            return false; // only 1,3,5,7,8,10,12 has 31 days
        } else if (month.equals("2") || month.equals("02")) {
            //leap year
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                if (day.equals("30") || day.equals("31")) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (day.equals("29") || day.equals("30") || day.equals("31")) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clRegisterAccount:
                AppUtils.hideKeyboard(v);
                break;
            case R.id.button_register_account:
                AppUtils.hideKeyboard(v);
                validateField();
                break;
            case R.id.text_view_female:
                AppUtils.hideKeyboard(v);
                mTextViewFemale.setBackgroundResource(R.drawable.border_text_view_female);
                mTextViewMale.setBackgroundResource(R.drawable.border_text_view_right);
                mGender = "female";
                gender = 2;
                break;
            case R.id.text_view_male:
                AppUtils.hideKeyboard(v);
                mTextViewMale.setBackgroundResource(R.drawable.border_text_view_male);
                mTextViewFemale.setBackgroundResource(R.drawable.border_text_view_left);
                mGender = "male";
                gender = 1;
                break;
            case R.id.button_facbook:
                AppUtils.hideKeyboard(v);
                countLogin = 0;
                loginWithFacebook();
                break;
            case R.id.button_line:
                AppUtils.hideKeyboard(v);
                countLogin = 1;
                loginWithLine(v);
                break;
            case R.id.button_google:
                AppUtils.hideKeyboard(v);
                countLogin = 2;
                loginWithGoogle();
                break;
            default:
                break;
        }
    }
}