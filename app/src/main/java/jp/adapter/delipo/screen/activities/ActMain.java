package jp.adapter.delipo.screen.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ApiConstants;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.constants.FragmentConstants;
import jp.adapter.delipo.constants.PrefConstants;
import jp.adapter.delipo.constants.RequestConstants;
import jp.adapter.delipo.data.PrefManager;
import jp.adapter.delipo.entity.ApiResult;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;
import jp.adapter.delipo.entity.api.LoginResponseEntity;
import jp.adapter.delipo.entity.param.ParamString;
import jp.adapter.delipo.entity.prefentity.PrefString;
import jp.adapter.delipo.listener.ApiCallback;
import jp.adapter.delipo.listener.CreateProductCallback;
import jp.adapter.delipo.listener.HandleResultCallback;
import jp.adapter.delipo.listener.LoadProductListener;
import jp.adapter.delipo.listener.PickImageListener;
import jp.adapter.delipo.network.ApiClient;
import jp.adapter.delipo.screen.fragment.BaseFragment;
import jp.adapter.delipo.screen.fragment.FrmAfterLogin;
import jp.adapter.delipo.screen.fragment.FrmAskToAns;
import jp.adapter.delipo.screen.fragment.FrmCreateProduct;
import jp.adapter.delipo.screen.fragment.FrmEditProduct;
import jp.adapter.delipo.screen.fragment.FrmInforProduct;
import jp.adapter.delipo.screen.fragment.FrmLogin;
import jp.adapter.delipo.screen.fragment.FrmNewPassword;
import jp.adapter.delipo.screen.fragment.FrmNotifAnalyze;
import jp.adapter.delipo.screen.fragment.FrmNotifSuccess;
import jp.adapter.delipo.screen.fragment.FrmPassBook;
import jp.adapter.delipo.screen.fragment.FrmPaymentRegister;
import jp.adapter.delipo.screen.fragment.FrmPolicy;
import jp.adapter.delipo.screen.fragment.FrmRateProduct;
import jp.adapter.delipo.screen.fragment.FrmRegisterInformation;
import jp.adapter.delipo.screen.fragment.FrmSearch;
import jp.adapter.delipo.screen.fragment.FrmSplash;
import jp.adapter.delipo.screen.fragment.FrmTimePicker;
import jp.adapter.delipo.test.FrmCreateProductTest;
import jp.adapter.delipo.test.FrmHome;
import jp.adapter.delipo.test.FrmRegisterAccount;
import jp.adapter.delipo.test.FrmShowRateProduct;
import jp.adapter.delipo.utils.AppUtils;
import jp.adapter.delipo.utils.DebugHelper;

import static jp.adapter.delipo.constants.ApiConstants.ANDROID;
import static jp.adapter.delipo.constants.ApiConstants.API_FORGOT;
import static jp.adapter.delipo.constants.ApiConstants.API_LOGOUT;
import static jp.adapter.delipo.constants.ApiConstants.API_PRODUCT;
import static jp.adapter.delipo.constants.ApiConstants.API_RESET;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_ACCESS_TOKEN;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_CATEGORY;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_CODE;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_DEVICE_TYPE;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_DEVICE_UUID;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_EMAIL;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_EXPIRATION_DATE;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_HASH;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_LAT;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_LNG;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_MANUFACTURER;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_NAME;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_PASSWORD;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_PASSWORD_CONFIRM;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_POST_TYPE;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_POST_URL;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_PRICE;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_PRICE_100;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_PRICE_TOTAL;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_PROCESSING_DATE;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_REMARKS;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_SOCIAL_ID;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_STORE_NAME;
import static jp.adapter.delipo.constants.ApiConstants.PARAM_WEIGHT;
import static jp.adapter.delipo.constants.ApiConstants.STATUS_ERROR;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_CREATE_PRODUCT;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_LOGIN;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_PAYMENT_REGISTER;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_POLICY;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_REGISTER_ACCOUNT;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_REGISTER_INFORMATION;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_SHOW_RATE_PRODUCT;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_SPLASH;
import static jp.adapter.delipo.constants.NotificationConstants.NOTIFICATION_DATA_TRANSFER;
import static jp.adapter.delipo.constants.NotificationConstants.NOTIFICATION_PRODUCT_ID;

public class ActMain extends BaseActivity {

    private ActMain _activity;
    private int currentFragment;
    public boolean isCallingApi = false;
    private String _userId = AppConstants.UNKNOWN_USER_ID;
    private String currentToken = null;
    //    private String currentSession = null;
    private String currentEmail = null;
    private String currentPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (prefIsContainsKey(PrefConstants.TOKEN)) {
            currentToken = prefGetString(PrefConstants.TOKEN);
        }
        navigationApp();
        //
        handleDynamicLink();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //
        setIntent(intent);
        handleDynamicLink();
    }

    private final String DYNAMIC_LINK_ACTIVE_PREFIX = "https://delipoapp.page.link/verifyRegister?code=";
    private final String DYNAMIC_LINK_RESET_PASS_PREFIX = "https://delipoapp.page.link/resetPassword?hash=";

    private void handleDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        String dynamicLink = getIntent().getDataString();
                        if (!TextUtils.isEmpty(dynamicLink)) {
                            if (dynamicLink.contains("/resetPassword")) {
                                String hash = dynamicLink.replace(DYNAMIC_LINK_RESET_PASS_PREFIX, "");
                                if (!TextUtils.isEmpty(hash)) {
                                    addFragmentToMain(FrmNewPassword.getInstance(hash));
                                }
                            } else if (dynamicLink.contains("/verifyRegister")) {
                                String code = dynamicLink.replace(DYNAMIC_LINK_ACTIVE_PREFIX, "");
                                if (!TextUtils.isEmpty(code)) {
                                    activeAccount(code, ActMain.this::handleActiveAccount);
                                }
                            }

                        }
                        DebugHelper.Log("handleDynamicLink:2 ", "dynamicLink: " + dynamicLink +" ");
                        if (pendingDynamicLinkData!=null)
                        DebugHelper.Log("handleDynamicLink:2 ", "pendingDynamicLinkData: " + pendingDynamicLinkData.getLink() +" ");
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void handleActiveAccount(ApiResult result) {
        try {
            if (result.statusCode == ApiConstants.STATUS_OK && result.response instanceof JSONObject) {
                JSONObject jsResponse = (JSONObject) result.response;
                String token = jsResponse.optString(ApiConstants.PARAM_TOKEN);
                DebugHelper.Log("TAG", "token: " + token);
                if (!TextUtils.isEmpty(token)) {
                    onActiveSuccess(new LoginResponseEntity(
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
                showNotice(result.message, true, null);
            } else if (result.statusCode == ApiConstants.STATUS_ERROR_INTERNET)
            showNotice(R.string.msg_error_internet, true, null);
            else
            showNotice(R.string.msg_error_server, true, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void activeAccount(String code, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        currentToken = null;
        RequestParams params = getRequestParam();
        params.put(PARAM_CODE, code);
        callAPI(params, ApiConstants.API_ACTIVE,false, listener);
    }

    public void setCurrentScreen(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    public void addFragmentToMain(Fragment f) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_menu_container);
            if (currentFragment != null) {
                fragmentManager.beginTransaction()
                        .remove(currentFragment)
                        .commitAllowingStateLoss();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        addOrReplaceFragment(R.id.frame_parent, f);
    }

    public void addFragmentToMenuContainer(Fragment f) {
        addOrReplaceFragment(R.id.frame_menu_container, f);
    }

    private RequestParams getRequestParam() {
        RequestParams params = new RequestParams();
        params.put(ApiConstants.PARAM_API, ApiConstants.API_KEY);
        params.put(ApiConstants.PARAM_API_VERSION, ApiConstants.API_VERSION);
        params.put(PARAM_DEVICE_TYPE, ANDROID);
        params.put(PARAM_DEVICE_UUID, AppUtils.getAndroidId(ActMain.this));
        return params;
    }

    private RequestParams getRequestParamWithToken() {
        RequestParams params = getRequestParam();
        if (!TextUtils.isEmpty(currentToken))
            params.put(ApiConstants.PARAM_TOKEN, currentToken);
        return params;
    }

    private void getNewToken(ApiCallback callback) {
//        currentToken = null;
//        currentSession = null;
        RequestParams params = getRequestParam();
        params.put(PARAM_EMAIL, getEmail());
        params.put(ApiConstants.PARAM_PASSWORD, getPassword());
        ApiClient.requestApi(this, ApiConstants.API_LOGIN, true, params, callback);
    }

    private void handleApiResult(ApiResult result, boolean isRetry, String tag, HandleResultCallback callback) {
        if (AppConstants.LOG_DEBUG) Log.e(tag, "result->" + result.toString());
        if (result.statusCode == ApiConstants.STATUS_OK) {
//            if (!TextUtils.isEmpty(result.token)) {
//                currentToken = result.token;
//            if (!TextUtils.isEmpty(result.session))
//                currentSession = result.session;
            isCallingApi = false;
            if (callback != null) {
//                callback.onSuccess(result.response, result.total);
                return;
            }
//            }
        } else if (!isRetry && result.statusCode == ApiConstants.STATUS_ERROR_TOKEN) {
            getNewToken(resultNewToken -> {
                if (AppConstants.LOG_DEBUG)
                    Log.e("getNewToken", "result->" + resultNewToken.toString());
                if (resultNewToken.statusCode == ApiConstants.STATUS_OK) {
//                    if (!TextUtils.isEmpty(resultNewToken.token)) {
//                        currentToken = resultNewToken.token;
//                    parseUserDataAfterLogin(resultNewToken.response);
//                    if (!TextUtils.isEmpty(resultNewToken.session))
//                        currentSession = resultNewToken.session;
                    if (callback != null) {
                        callback.onGetNewToken();
                        return;
                    }
//                    }
                }
                showError(result);
                hideProgress();
                isCallingApi = false;
                if (callback != null)
                    callback.onFailed();
            });
            return;
        }
        hideProgress();
        isCallingApi = false;
        showError(result);
        if (callback != null)
            callback.onFailed();
    }

    private void showError(ApiResult result) {
        if (result.statusCode == ApiConstants.STATUS_ERROR_TOKEN) {
            showNotice(result.message, false, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    addFragmentToMain(FrmLogin.getInstance());
                }
            });
        } else if (result.statusCode == ApiConstants.STATUS_ERROR_INTERNET)
            showNotice(R.string.msg_error_internet, true, null);
        else if (!TextUtils.isEmpty(result.message))
            showNotice(result.message, true, null);
        else
            showNotice(R.string.msg_error_server, true, null);
    }

    private void navigationApp() {
//        if (prefIsContainsKey(PrefConstants.EMAIL)
//                && prefIsContainsKey(PrefConstants.PASSWORD)
//                && prefIsContainsKey(PrefConstants.TOKEN)) {
//            addFragmentToMain(new FrmSplash());
//        } else
        addFragmentToMain(new FrmSplash());

    }

    public String getEmail() {
        if (TextUtils.isEmpty(currentEmail))
            currentEmail = prefGetString(PrefConstants.EMAIL);
        return currentEmail;
    }

    public void updateEmail(String email) {
        currentEmail = email;
        prefWriteString(PrefConstants.EMAIL, email);
    }

    public String getPassword() {
        if (TextUtils.isEmpty(currentPassword))
            currentPassword = prefGetString(PrefConstants.PASSWORD);
        return currentPassword;
    }

    public void updatePassword(String password) {
        currentPassword = password;
        prefWriteString(PrefConstants.PASSWORD, password);
    }

    private void callAPI(RequestParams params, String url,boolean isPost, ApiCallback listener) {
        ApiClient.requestApi(this, url, isPost, params, new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                if (AppConstants.LOG_DEBUG)
                    Log.e("callAPI", "result->" + result.toString());
                hideProgress();
                isCallingApi = false;
                if (listener != null)
                    listener.onFinished(result);
            }
        });
    }

    //TODO Register

    public void register(String email, String password, int gender, String birthday, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        RequestParams params = getRequestParam();
        params.put(ApiConstants.PARAM_EMAIL, email);
        params.put(ApiConstants.PARAM_PASSWORD, password);
        params.put(ApiConstants.PARAM_GENDER, gender);
        params.put(ApiConstants.PARAM_BIRTHDAY, birthday);
        callAPI(params, ApiConstants.API_REGISTER,true, listener);
    }

    //TODO Login

    public void login(String email, String password, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        currentToken = null;
        currentEmail = email;
        currentPassword = password;
        RequestParams params = getRequestParam();
        params.put(PARAM_EMAIL, email);
        params.put(ApiConstants.PARAM_PASSWORD, password);
        callAPI(params, ApiConstants.API_LOGIN,true, listener);
    }

    public void loginFacebook(String accessToken, String socialID, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        currentToken = null;
        RequestParams params = getRequestParam();
        params.put(PARAM_ACCESS_TOKEN, accessToken);
        params.put(PARAM_SOCIAL_ID, socialID);
        callAPI(params, ApiConstants.API_LOGIN_FACEBOOK,true, listener);
    }

    public void loginGoogle(String accessToken, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        currentToken = null;
        RequestParams params = getRequestParam();
        params.put(PARAM_ACCESS_TOKEN, accessToken);
        callAPI(params, ApiConstants.API_LOGIN_GOOGLE,true, listener);
    }

    public void loginLine(String accessToken, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        currentToken = null;
        RequestParams params = getRequestParam();
        params.put(PARAM_ACCESS_TOKEN, accessToken);
        callAPI(params, ApiConstants.API_LOGIN_LINE,true, listener);
    }

    public void onLoginSuccess(LoginResponseEntity result) { // only call this function from login screen, if reuse in other case need add more check exception first same as login screen
        currentToken = result.token;
        prefWriteArr(new PrefString(PrefConstants.EMAIL, currentEmail), new PrefString(PrefConstants.PASSWORD, currentPassword), new PrefString(PrefConstants.TOKEN, currentToken));
        _userId = result.id;
        Bundle bundle = getPushData(getIntent(), "onLoginSuccess");
        if (bundle != null) {
            Log.e("TAG", "onLoginSuccess: " + bundle.toString());
            handlePushData(bundle);
        } else {
            addFragmentToMain(FrmAfterLogin.getInstance());
        }
    }

    public void onActiveSuccess(LoginResponseEntity result) { // only call this function from login screen, if reuse in other case need add more check exception first same as login screen
        currentToken = result.token;
        prefWriteArr(new PrefString(PrefConstants.EMAIL, currentEmail), new PrefString(PrefConstants.PASSWORD, currentPassword), new PrefString(PrefConstants.TOKEN, currentToken));
        _userId = result.id;
        addFragmentToMain(FrmRegisterInformation.getInstance());
    }

    //TODO Logout

    public void logout(ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        RequestParams params = getRequestParamWithToken();
        params.put(PARAM_DEVICE_UUID, AppUtils.getAndroidId(ActMain.this));
        ApiClient.requestApi(this, API_LOGOUT, true, params, new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                if (AppConstants.LOG_DEBUG) Log.e("API_LOGOUT", "result->" + result.toString());
                hideProgress();
                isCallingApi = false;
                if (listener != null)
                    listener.onFinished(result);
            }
        });
    }

    public void forgotPassword(String email, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        RequestParams params = getRequestParam();
        params.put(PARAM_EMAIL, email);
        ApiClient.requestApi(this, API_FORGOT, true, params, new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                if (AppConstants.LOG_DEBUG) Log.e("API_FORGOT", "result->" + result.toString());
                hideProgress();
                isCallingApi = false;
                if (listener != null)
                    listener.onFinished(result);
            }
        });
    }

    public void resetPassword(String hash, String password, ApiCallback listener) {
        isCallingApi = true;
        showProgress(false);
        RequestParams params = getRequestParam();
        params.put(PARAM_HASH, hash);
        params.put(PARAM_PASSWORD, password);
        params.put(PARAM_PASSWORD_CONFIRM, password);
        ApiClient.requestApi(this, API_RESET, true, params, new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                if (AppConstants.LOG_DEBUG) Log.e("API_FORGOT", "result->" + result.toString());
                hideProgress();
                isCallingApi = false;
                if (listener != null)
                    listener.onFinished(result);
            }
        });
    }

    public Bundle getPushData(Intent intent, String from) {
        if (intent != null && intent.hasExtra(NOTIFICATION_DATA_TRANSFER)) {
            if (AppConstants.LOG_DEBUG) Log.e(getClassName(), "hasPushData." + from);
            Bundle bundle = intent.getBundleExtra(NOTIFICATION_DATA_TRANSFER);
            intent.removeExtra(NOTIFICATION_DATA_TRANSFER);
            return bundle;
        }
        return null;
    }

    public void handlePushData(Bundle bundle) {
        try {
            String productId = bundle.getString(NOTIFICATION_PRODUCT_ID, "");
            if (AppConstants.LOG_DEBUG) Log.d(getClassName(), "productId : " + productId);
//            if (!TextUtils.isEmpty(productId)) {
//                getProductById(productId, true, result -> {
//                    try {
//                        if(result!=null){
//                            JSONObject json = new JSONObject(result.response);
//                            ProductEntity product = new ProductEntity(json, getDateFormat(), getTmpCalendar());
//                            ArrayList<BackStackData> arrayList = null;
//                            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameParent);
//                            if (currentFragment != null) {
//                                Bundle argument = currentFragment.getArguments();
//                                if(argument!=null&&argument.containsKey(EXTRA_BACK_STACK))
//                                    arrayList = (ArrayList<BackStackData>) argument.getSerializable(EXTRA_BACK_STACK);
//                            }
//                            if(arrayList == null){
//                                arrayList = new ArrayList<>();
//                                arrayList.add(new BackStackData(FRM_HOME));
//                            }
//                            HashMap<String, Object> mapData = new HashMap<>();
//                            mapData.put(EXTRA_PRODUCT, product);
//                            showInfoProduct(arrayList, mapData);
//                            return;
//                        }
//                    } catch (Throwable e) {
//                        e.printStackTrace();
//                    }
//                    backToHome();
//                });
//            }else {
//                addFragmentToMenuContainer(FrmNotice.getInstance(true));
//            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                try {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment menuFragment = fragmentManager.findFragmentById(R.id.frame_menu_container);
                    if (menuFragment != null && menuFragment instanceof BaseFragment) {
                        if (((BaseFragment) menuFragment).isBackPreviousEnable()) {
                            ((BaseFragment) menuFragment).backToPrevious();
                            return true;
                        }
                    } else {
                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_parent);
                        if (currentFragment != null && currentFragment instanceof BaseFragment) {
                            if (((BaseFragment) currentFragment).isBackPreviousEnable()) {
                                ((BaseFragment) currentFragment).backToPrevious();
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                onBackPressed();
                return true;
            }
            return super.dispatchKeyEvent(event);
        }
        return false;
    }

    //== List product entity of each tab in search screen ======
    private String searchKeyword = "";
    private ArrayList<ProductEntity> mArrSearchSortByFavorite;
    private int searchSortByFavoriteOffset = 0;
    private int searchSortByFavoriteTotal = 0;

    public ArrayList<ProductEntity> getArrSearchSortByFavorite() {
        return mArrSearchSortByFavorite;
    }

    public void setArrSearchSortByFavorite(ArrayList<ProductEntity> arrayList) {
        mArrSearchSortByFavorite = arrayList;
    }

    public int getCountArrSearchSortByFavorite() {
        return mArrSearchSortByFavorite != null ? mArrSearchSortByFavorite.size() : 0;
    }

    //------------------------------------------------------
    private ArrayList<ProductEntity> mArrSearchRegistration;
    private int searchSortByRegistrationOffset = 0;
    private int searchSortByRegistrationTotal = 0;

    public ArrayList<ProductEntity> getArrSearchRegistration() {
        return mArrSearchRegistration;
    }

    public void setArrSearchRegistration(ArrayList<ProductEntity> arrayList) {
        mArrSearchRegistration = arrayList;
    }

    public int getCountArrSearchRegistration() {
        return mArrSearchRegistration != null ? mArrSearchRegistration.size() : 0;
    }

    //------------------------------------------------------
    private ArrayList<ProductEntity> mArrSearchSortByCategory;
    private int searchSortByCategoryOffset = 0;
    private int searchSortByCategoryTotal = 0;


    public ArrayList<ProductEntity> getArrSearchSortByCategory() {
        return mArrSearchSortByCategory;
    }

    public void setArrSearchSortByCategory(ArrayList<ProductEntity> arrayList) {
        mArrSearchSortByCategory = arrayList;
    }

    public int getCountArrSearchSortByCategory() {
        return mArrSearchSortByCategory != null ? mArrSearchSortByCategory.size() : 0;
    }

    //------------------------------------------------------
    public String getCurrentKeywordSearch() {
        return searchKeyword;
    }

    public int getCurrentTotalSearch(int type) {
        return type == 0 ? searchSortByFavoriteTotal : type == 1 ? searchSortByCategoryTotal : searchSortByRegistrationTotal;
    }

    //------------------------------------------------------
    public void releaseDataSearch() {
        searchKeyword = "";

        mArrSearchSortByFavorite = null;
        searchSortByFavoriteOffset = 0;
        searchSortByFavoriteTotal = 0;

        mArrSearchRegistration = null;
        searchSortByRegistrationOffset = 0;
        searchSortByRegistrationTotal = 0;

        mArrSearchSortByCategory = null;
        searchSortByCategoryOffset = 0;
        searchSortByCategoryTotal = 0;
    }

    //------------------------------------------------------
    private void updateSearchParams(ParamString[] listQuery, ArrayList<ProductEntity> arrayList, int total) {
        int type = 0;
        int offset = 1;
        String key = "";
        try {
            if (listQuery != null && listQuery.length > 0) {
                for (ParamString entity : listQuery) {
                    switch (entity.key) {
                        case ApiConstants.PARAM_OFFSET:
                            offset = Integer.valueOf(entity.value);
                            break;
                        case ApiConstants.PARAM_SORT:
                            if (ApiConstants.PARAM_FAVORITE.equals(entity.value))
                                type = 0;
                            else if (ApiConstants.PARAM_REGISTRATION.equals(entity.value))
                                type = 1;
                            else
                                type = 2;
                            break;
                        case ApiConstants.PARAM_PRODUCT_NAME:
                            key = entity.value;
                            break;
                    }
                }
            }
        } catch (Throwable e) {
        }
        if (offset <= 1 && !key.equals(searchKeyword)) {
            releaseDataSearch();
        }
        searchKeyword = key;
        if (type == 0) {
            searchSortByFavoriteOffset = offset;
            searchSortByFavoriteTotal = total;
            if (searchSortByFavoriteOffset > 1) {
                if (arrayList != null && !arrayList.isEmpty()) {
                    if (mArrSearchSortByFavorite == null)
                        mArrSearchSortByFavorite = new ArrayList<>();
                    mArrSearchSortByFavorite.addAll(arrayList);
                }
            } else
                setArrSearchSortByFavorite(arrayList);
        } else if (type == 1) {
            searchSortByRegistrationOffset = offset;
            searchSortByRegistrationTotal = total;
            if (searchSortByRegistrationOffset > 1) {
                if (arrayList != null && !arrayList.isEmpty()) {
                    if (mArrSearchRegistration == null)
                        mArrSearchRegistration = new ArrayList<>();
                    mArrSearchRegistration.addAll(arrayList);
                }
            } else
                setArrSearchRegistration(arrayList);
        } else {
            searchSortByCategoryOffset = offset;
            searchSortByCategoryTotal = total;
            if (searchSortByCategoryOffset > 1) {
                if (arrayList != null && !arrayList.isEmpty()) {
                    if (mArrSearchSortByCategory == null)
                        mArrSearchSortByCategory = new ArrayList<>();
                    mArrSearchSortByCategory.addAll(arrayList);
                }
            } else
                setArrSearchSortByCategory(arrayList);
        }
    }

    //------------------------------------------------------
    public ParamString[] getParamsNextPageSearchSortByFavorite() {
        return new ParamString[]{new ParamString(ApiConstants.PARAM_OFFSET, (searchSortByFavoriteOffset + 1) + ""),
                new ParamString(ApiConstants.PARAM_SORT, ApiConstants.PARAM_FAVORITE),
                new ParamString(ApiConstants.PARAM_ORDER_BY, ApiConstants.PARAM_DESC),
                new ParamString(ApiConstants.PARAM_PRODUCT_NAME, searchKeyword)};
    }

    public ParamString[] getParamsNextPageSearchSortByRegistration() {
        return new ParamString[]{new ParamString(ApiConstants.PARAM_OFFSET, (searchSortByRegistrationOffset + 1) + ""),
                new ParamString(ApiConstants.PARAM_SORT, ApiConstants.PARAM_REGISTRATION),
                new ParamString(ApiConstants.PARAM_ORDER_BY, ApiConstants.PARAM_DESC),
                new ParamString(ApiConstants.PARAM_PRODUCT_NAME, searchKeyword)};
    }

    public ParamString[] getParamsNextPageSearchSortByCategory() {
        return new ParamString[]{new ParamString(ApiConstants.PARAM_OFFSET, (searchSortByCategoryOffset + 1) + ""),
                new ParamString(ApiConstants.PARAM_SORT, PARAM_CATEGORY),
                new ParamString(ApiConstants.PARAM_ORDER_BY, ApiConstants.PARAM_DESC),
                new ParamString(ApiConstants.PARAM_PRODUCT_NAME, searchKeyword)};
    }

    //------------------------------------------------------
    public ParamString[] getParamsRefreshSearchSortByFavorite(String key) {
        return new ParamString[]{new ParamString(ApiConstants.PARAM_OFFSET, "1"),
                new ParamString(ApiConstants.PARAM_SORT, ApiConstants.PARAM_FAVORITE),
                new ParamString(ApiConstants.PARAM_ORDER_BY, ApiConstants.PARAM_DESC),
                new ParamString(ApiConstants.PARAM_PRODUCT_NAME, key)};
    }

    public ParamString[] getParamsRefreshSearchSortByRegistration(String key) {
        return new ParamString[]{new ParamString(ApiConstants.PARAM_OFFSET, "1"),
                new ParamString(ApiConstants.PARAM_SORT, ApiConstants.PARAM_REGISTRATION),
                new ParamString(ApiConstants.PARAM_ORDER_BY, ApiConstants.PARAM_DESC),
                new ParamString(ApiConstants.PARAM_PRODUCT_NAME, key)};
    }

    public ParamString[] getParamsRefreshSearchSortByCategory(String key) {
        return new ParamString[]{new ParamString(ApiConstants.PARAM_OFFSET, "1"),
                new ParamString(ApiConstants.PARAM_SORT, PARAM_CATEGORY),
                new ParamString(ApiConstants.PARAM_ORDER_BY, ApiConstants.PARAM_DESC),
                new ParamString(ApiConstants.PARAM_PRODUCT_NAME, key)};
    }

    //===========================================
    public void requestListProduct(LoadProductListener listener, int frmId, boolean isRetry, boolean showProgress, ParamString... listQuery) {
        if (!isRetry) {
            isCallingApi = true;
            if (showProgress)
                showProgress(false);
        }
        RequestParams params = getRequestParamWithToken();
        int offset = 1;
        if (listQuery != null && listQuery.length > 0)
            for (ParamString entity : listQuery) {
                params.put(entity.key, entity.value);
                try {
                    if (entity.key.equals(ApiConstants.PARAM_OFFSET))
                        offset = Integer.parseInt(entity.value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        final boolean isLoadMore = offset > 1;
        ApiClient.requestApi(this, ApiConstants.API_GET_LIST_PRODUCT, false, params, new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                Log.d("ActivityMain", "onFinished: " + result.toString());
                Log.d("ActivityMain", "onFinished: " + currentToken);
                handleApiResult(result, isRetry, "API_GET_LIST_PRODUCT", new HandleResultCallback() {
                    @Override
                    public void onGetNewToken() {
                        requestListProduct(listener, frmId, true, false, listQuery);
                    }

                    @Override
                    public void onSuccess(String response, int total) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                ArrayList<ProductEntity> arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ProductEntity entity = new ProductEntity(jsonArray.getJSONObject(i), getDateFormat(), getTmpCalendar());
                                    arrayList.add(entity);
                                }
                                if (frmId == FragmentConstants.FRM_SEARCH) {
                                    updateSearchParams(listQuery, arrayList, total);
                                }
                                if (listener != null)
                                    listener.onLoadProductComplete(arrayList, isLoadMore);
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        hideProgress();
                    }
                });
            }
        });
    }

    private Location mCurrentLocation;
    private int countRetry = 0;

    public void getProductWithId(CreateProductCallback listener, String id, boolean isRetry, boolean showProgress) {
        if (!isRetry) {
            isCallingApi = true;
            if (showProgress)
                showProgress(false);
        }
        ApiClient.requestApi(this, API_PRODUCT + "/" + id, false, getRequestParamWithToken(), new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                handleApiResult(result, isRetry, countRetry + ".getProduct:" + id, new HandleResultCallback() {
                    @Override
                    public void onGetNewToken() {
                        getProductWithId(listener, id, true, false);
                    }

                    @Override
                    public void onSuccess(String response, int total) {
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.has(ApiConstants.PARAM_PENDING)) {
                                if (json.getBoolean(ApiConstants.PARAM_PENDING)) {
                                    delayToGetProduct(listener, id);
                                    return;
                                }
                            } else {
                                ProductEntity product = new ProductEntity(json, getDateFormat(), getTmpCalendar());
                                if (listener != null)
                                    listener.onCreatedProduct(product);
                                hideProgress();
                                return;
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        hideProgress();
                        showError(new ApiResult(STATUS_ERROR));
                    }
                });
            }
        });
    }

    private void delayToGetProduct(CreateProductCallback listener, String id) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        if (isFinishing())
                            return;
                        countRetry++;
                        getProductWithId(listener, id + "", false, false);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void checkInitCurrentLocation() {
        if (mCurrentLocation == null) {
            try {
                float lat = prefGetFloat(PrefManager.LAST_KNOWN_LAT, 1);
                float lng = prefGetFloat(PrefManager.LAST_KNOWN_LAT, 1);
                mCurrentLocation = new Location("");
                mCurrentLocation.setLatitude(lat);
                mCurrentLocation.setLongitude(lng);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    public String getCurrentLat() {
        checkInitCurrentLocation();
        return mCurrentLocation != null ? mCurrentLocation.getLatitude() + "" : "1";
    }

    public String getCurrentLng() {
        checkInitCurrentLocation();
        return mCurrentLocation != null ? mCurrentLocation.getLongitude() + "" : "1";
    }


    public void uploadProductImage(CreateProductCallback listener, String productId, ArrayList<File> file, String type, String categoryId, boolean isRetry, boolean showProgress) {
        if (!isRetry) {
            isCallingApi = true;
            if (showProgress)
                showProgress(false);
        }
        RequestParams params = getRequestParamWithToken();
        params.put(PARAM_LAT, getCurrentLat());
        params.put(PARAM_LNG, getCurrentLng());
        params.put(PARAM_CATEGORY, categoryId);
        params.put(PARAM_POST_TYPE, type);
        try {
            for (int i = 0; i < file.size(); i++) {
                params.put(PARAM_POST_URL + "[" + i + "]", file.get(i));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        ApiClient.requestApi(this, API_PRODUCT + (TextUtils.isEmpty(productId) ? "" : "/" + productId), true, params, new ApiCallback() {
            @Override
            public void onFinished(ApiResult result) {
                handleApiResult(result, isRetry, "uploadProductImage", new HandleResultCallback() {
                    @Override
                    public void onGetNewToken() {
                        Log.e("uploadProductImage", "onGetNewToken");
                        uploadProductImage(listener, productId, file, type, "2", true, false);
                    }

                    @Override
                    public void onSuccess(String response, int total) {
                        try {
                            Log.e("uploadProductImage", "onSuccess");
                            countRetry = 1;
                            if (TextUtils.isEmpty(productId)) {
                                int id = Integer.parseInt(response);
                                if (type.equals("1")) {
                                    if (listener != null)
                                        listener.onUploadedLabelImg(id);
                                    hideProgress();
                                } else
                                    getProductWithId(listener, id + "", false, false);
                                return;
                            } else {
                                getProductWithId(listener, productId, false, false);
                            }
                            return;
                        } catch (
                                Throwable e) {
                            e.printStackTrace();
                        }
                        hideProgress();
                        showError(new ApiResult(STATUS_ERROR));
                    }
                });
            }
        });
    }

    public void updateProductInfo(CreateProductCallback listener, String productId, File file,
                                  int categoryId, String cmt, String name, String manufacturer,
                                  String store, String price, String tax, String price100,
                                  String weight, String expiration, String processing,
                                  boolean requestPush, boolean requestInfo,
                                  boolean isRetry, boolean showProgress) {
        if (!isRetry) {
            isCallingApi = true;
            if (showProgress)
                showProgress(false);
        }
        RequestParams params = getRequestParamWithToken();
        params.put(PARAM_CATEGORY, categoryId);
        params.put(PARAM_REMARKS, cmt);
        params.put(PARAM_NAME, name);
        params.put(PARAM_MANUFACTURER, manufacturer);
        params.put(PARAM_STORE_NAME, store);
        params.put(PARAM_PRICE, price);
        params.put(PARAM_PRICE_TOTAL, tax);
        params.put(PARAM_PRICE_100, price100);
        params.put(PARAM_WEIGHT, weight);
        if (!TextUtils.isEmpty(expiration))
            params.put(PARAM_EXPIRATION_DATE, expiration);
        if (!TextUtils.isEmpty(processing))
            params.put(PARAM_PROCESSING_DATE, processing);
        if (file != null) {
            params.put(PARAM_POST_TYPE, "2");
            try {
                params.put(PARAM_POST_URL, file);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        ApiClient.requestApi(this, API_PRODUCT + (TextUtils.isEmpty(productId) ? "" : "/" + productId),
                true, params, new ApiCallback() {
                    @Override
                    public void onFinished(ApiResult result) {
                        handleApiResult(result, isRetry, "uploadProductImage", new HandleResultCallback() {
                            @Override
                            public void onGetNewToken() {
                                updateProductInfo(listener, productId, file, categoryId, cmt, name,
                                        manufacturer, store, price, tax, price100,
                                        weight, expiration, processing,
                                        requestPush, requestInfo,
                                        true, false);
                            }

                            @Override
                            public void onSuccess(String response, int total) {
                                try {
                                    if (requestInfo) {
                                        countRetry = 1;
                                        getProductWithId(listener, productId, false, false);
                                    } else {
                                        if (listener != null)
                                            listener.onCreatedProduct(null);
                                        hideProgress();
                                    }
                                    return;
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                                hideProgress();
                                showError(new ApiResult(STATUS_ERROR));
                            }
                        });
                    }
                });
    }


    //TODO date time format
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat shortDateFormat;
    private Calendar tmpCalendar;

    public Calendar getTmpCalendar() {
        if (tmpCalendar == null)
            tmpCalendar = Calendar.getInstance();
        return tmpCalendar;
    }

    public SimpleDateFormat getDateFormat() {
        if (dateFormat == null)
            dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        return dateFormat;
    }

    public SimpleDateFormat getShortDateFormat() {
        if (shortDateFormat == null)
            shortDateFormat = new SimpleDateFormat(AppConstants.SHORT_DATE_TIME_FORMAT);
        return shortDateFormat;
    }

    public void showLogin(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmLogin.getInstance(arrayList, mapData));
    }

    public void showNewPassword(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmNewPassword.getInstance(arrayList, mapData));
    }

    public void showCreateProduct(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmCreateProduct.getInstance(arrayList, mapData));
    }

    public void showEditProduct(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmEditProduct.getInstance(arrayList, mapData));
    }

    public void showNotifAnalyze(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmNotifAnalyze.getInstance(arrayList, mapData));
    }

    public void showAskToAns(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmAskToAns.getInstance(arrayList, mapData));
    }

    public void showTimePicker(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmTimePicker.getInstance(arrayList, mapData));
    }

    public void showNotifSuccess(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmNotifSuccess.getInstance(arrayList, mapData));
    }

    public void showSearch(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmSearch.getInstance(arrayList, mapData));
    }

    public void showRateProduct(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmRateProduct.getInstance(arrayList, mapData));
    }

    public void showRatedProduct(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmShowRateProduct.getInstance(arrayList, mapData));
    }

    public void showPaymentRegister(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmPaymentRegister.getInstance(arrayList, mapData));
    }

    public void showPassBook(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmPassBook.getInstance(arrayList, mapData));
    }

    public void showInfoProduct(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmInforProduct.getInstance(arrayList, mapData));
    }

    public void showPolicy(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmPolicy.getInstance(arrayList, mapData));
    }

    public void showRegisterAccount(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmRegisterAccount.getInstance(arrayList, mapData));
    }

    public void showRegisterInformation(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        addFragmentToMain(FrmRegisterInformation.getInstance(arrayList, mapData));
    }

    public void backToHome() {
        addFragmentToMain(FrmHome.getInstance(null));
    }

    public void backToPreviousFromBackStack(Bundle arguments) {
        try {
            if (arguments != null && arguments.containsKey(ExtraConstants.EXTRA_BACK_STACK)) {
                backToPreviousFromBackStack((ArrayList<BackStackData>) arguments.getSerializable(ExtraConstants.EXTRA_BACK_STACK));
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        addFragmentToMain(FrmHome.getInstance(null));
    }

    public void backToPreviousFromBackStack(ArrayList<BackStackData> arrayList) {
        try {
            if (arrayList != null && !arrayList.isEmpty()) {
                BackStackData data = arrayList.remove(arrayList.size() - 1);
                BaseFragment fragment;
                switch (data.fromFragment) {
                    case FRM_SPLASH:
                        fragment = FrmSplash.getInstance(arrayList, data.mapData);
                        break;
                    case FRM_LOGIN:
                        fragment = FrmLogin.getInstance(arrayList, data.mapData);
                        break;
                    case FragmentConstants.FRM_EDIT_PRODUCT:
                        fragment = FrmEditProduct.getInstance(arrayList, data.mapData);
                        break;
                    case FRM_CREATE_PRODUCT:
                        fragment = FrmCreateProductTest.getInstance(arrayList, data.mapData);
                        break;
//                    case FRM_MY_PRODUCT:
//                        fragment = FrmMyProduct.getInstance(arrayList, data.mapData);
//                        break;
                    case FRM_POLICY:
                        fragment = FrmPolicy.getInstance(arrayList, data.mapData);
                        break;
                    case FragmentConstants.FRM_SEARCH:
                        fragment = FrmSearch.getInstance(arrayList, data.mapData);
                        break;
                    case FragmentConstants.FRM_RATE_PRODUCT:
                        fragment = FrmRateProduct.getInstance(arrayList, data.mapData);
                        break;
                    case FRM_SHOW_RATE_PRODUCT:
                        fragment = FrmShowRateProduct.getInstance(arrayList, data.mapData);
                        break;
                    case FRM_PAYMENT_REGISTER:
                        fragment = FrmPaymentRegister.getInstance(arrayList, data.mapData);
                        break;
                    case FRM_REGISTER_ACCOUNT:
                        fragment = FrmRegisterAccount.getInstance(arrayList, data.mapData);
                        break;
                    case FRM_REGISTER_INFORMATION:
                        fragment = FrmRegisterInformation.getInstance(arrayList, data.mapData);
                        break;
//                    case FRM_INFO_PRODUCT:
//                        fragment = FrmInfoProduct.getInstance(arrayList, data.mapData);
//                        break;
                    default:
                        fragment = FrmHome.getInstanceWithBackStackData(data.mapData);
                        break;
                }
                addFragmentToMain(fragment);
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        addFragmentToMain(FrmHome.getInstance(null));
    }

    //TODO size manager
    private float scaleValue = 0;
    private DisplayMetrics displayMetrics;

    private DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null)
            displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics;
    }

    private float screenDensity = 0;

    public float getScreenDensity() {
        if (screenDensity == 0)
            screenDensity = getDisplayMetrics().density;
        return screenDensity;
    }

    private int screenWidth = 0;

    public int getScreenWidth() {
        if (screenWidth == 0)
            screenWidth = getDisplayMetrics().widthPixels;
        return screenWidth;
    }

    private int screenHeight = 0;

    public int getScreenHeight() {
        if (screenHeight == 0) {
            int statusBarHeight = 0;
            try {
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = getResources().getDimensionPixelSize(resourceId);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            screenHeight = getDisplayMetrics().heightPixels - statusBarHeight;
        }
        return screenHeight;
    }

    private float getScaleValue() {
        if (scaleValue == 0)
            scaleValue = getScreenWidth() * 1f / AppConstants.SCREEN_WIDTH_DESIGN;
        return scaleValue;
    }

    public int getSizeWithScale(double sizeDesign) {
        return (int) (sizeDesign * getScaleValue());
    }

    public int countUnreadNews = -1;

    //TODO check permissions

    @Override
    protected void onResume() {
        super.onResume();
        _activity = this;
        countUnreadNews = -1;
        if (isNeedPermissions()) {
            requestPermissions("onResume");
        }
    }

    private boolean isRequestingPermission = false;

    public void requestPermissions(String from) {
        if (AppConstants.LOG_DEBUG)
            Log.e("requestPermissions", from + "-isRequestingPermission:" + isRequestingPermission);
        if (isRequestingPermission)
            return;
        isRequestingPermission = true;
        ActivityCompat.requestPermissions(this, listRequest, RequestConstants.RC_PERMISSIONS);
    }

    private String listRequest[] = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public boolean isNeedPermissions() {
        return Build.VERSION.SDK_INT >= 23
                && (ActivityCompat.checkSelfPermission(this, listRequest[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, listRequest[1]) != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestConstants.RC_PERMISSIONS)
            checkPermissionsResult();
    }

    private void checkPermissionsResult() {
        try {
            String msg = "";
            if (ActivityCompat.checkSelfPermission(this, listRequest[0]) != PackageManager.PERMISSION_GRANTED)
                msg = getString(R.string.msg_request_permission_camera);
            if (ActivityCompat.checkSelfPermission(this, listRequest[1]) != PackageManager.PERMISSION_GRANTED) {
                if (!TextUtils.isEmpty(msg))
                    msg += "\n";
                msg += getString(R.string.msg_request_permission_storage);
            }
            if (TextUtils.isEmpty(msg)) {
                onPermissionGranted();
                return;
            }

            showNotice(msg, false, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    gotoAppSetting();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void onPermissionGranted() {
        try {
            isRequestingPermission = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_parent);
            if (currentFragment != null)
                ((BaseFragment) currentFragment).onPermissionsGranted();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void gotoAppSetting() {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, RequestConstants.RC_APP_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
            isRequestingPermission = false;
        }
    }

    private PickImageListener mPickImageListener;

    public void pickImageFromGallery(PickImageListener listener) {
        mPickImageListener = listener;
        Intent intentPick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentPick, RequestConstants.RC_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if (AppConstants.LOG_DEBUG) Log.d("TAG", "onActivityResult: " + requestCode);
//        if (requestCode == RequestConstants.RC_APP_SETTINGS) {
//            if (!isNeedPermissions()) {
//                onPermissionGranted();
//            } else {
//                releaseDataAndShowLogin();
//            }
//            isRequestingPermission = false;
//        } else if (requestCode == RequestConstants.RC_PICK_IMAGE) {
//            if (resultCode == RESULT_OK && data != null && data.getData() != null && mPickImageListener != null) {
//                mPickImageListener.onPickedImage(data.getData());
//                mPickImageListener = null;
//            }
//        }else if (requestCode == RequestConstants.RC_ENABLE_GPS){
//            TrackingLocationManager.getInstance(ActMain.this).setCheckingGps(false);
//            if (resultCode == RESULT_OK){
//                if (!isNeedPermissions())
//                    startTrackingLocation();
//            }else {
//                releaseDataAndShowLogin();
//            }
//        }else if (requestCode == RequestConstants.RC_WIRELESS_SETTINGS || requestCode == RequestConstants.RC_LOCATION_SETTINGS){
//            if (!isNeedPermissions())
//                startTrackingLocation();
//        }
    }


}
