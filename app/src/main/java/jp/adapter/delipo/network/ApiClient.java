package jp.adapter.delipo.network;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.adapter.delipo.constants.ApiConstants;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.entity.ApiResult;
import jp.adapter.delipo.listener.ApiCallback;
import jp.adapter.delipo.screen.activities.ActMain;
import jp.adapter.delipo.utils.AppUtils;

public class ApiClient implements ApiConstants {

    public static void requestApi(Context context, String url, boolean isPost, RequestParams params, ApiCallback callback) {
        try {
            if (AppConstants.LOG_DEBUG)
                Log.e("ApiClient", "requestApi->" + url + "\n->params:" + (params != null ? params.toString() : "null"));
            if (!AppUtils.isNetworkAvailable(context)) {
                callback.onFinished(new ApiResult(STATUS_ERROR_INTERNET));
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
//            if (!TextUtils.isEmpty(session))
//                client.addHeader("cookie", session);

            TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (AppConstants.LOG_DEBUG)
                        Log.e("ApiClient", "onFailure->statusCode:" + statusCode
                                + "\n-->response:" + responseString + "\n-->headers:" + getSession(headers));
                    if (callback != null)
                        callback.onFinished(new ApiResult(STATUS_ERROR));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        if (AppConstants.LOG_DEBUG)
                            Log.e("ApiClient", "onSuccess->statusCode" + statusCode
                                    + "\n-->urlRequest:" + url
                                    + "\n-->response:" + responseString + "\n-->headers:" + getSession(headers));
                        JSONObject json = new JSONObject(responseString);
                        Object objResponse = json.opt(PARAM_RESPONSE);
                        if (callback != null) {
                            ApiResult result = new ApiResult(json.optInt(PARAM_STATUS));
                            result.message = json.optString(PARAM_MESSAGE);
                            if (objResponse != null) {
                                result.response = objResponse;
                            }
                            callback.onFinished(result);
                        }
                        return;
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    if (callback != null)
                        callback.onFinished(new ApiResult(STATUS_ERROR));
                }
            };

            if (isPost)
                client.post(url, params, responseHandler);
            else
                client.get(url, params, responseHandler);
        } catch (Throwable e) {
            e.printStackTrace();
            callback.onFinished(new ApiResult(STATUS_ERROR));
        }
    }

    private static String getSession(Header[] headers) {
        try {
            if (headers != null && headers.length > 0)
                for (Header header : headers) {
                    if (header.getName().equalsIgnoreCase("Set-Cookie")) {
                        String value = header.getValue();
                        String[] arrValue = value.split(";");
                        for (String element : arrValue) {
                            if (element.contains("ci_session=")) {
                                return element;
                            }
                        }
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
