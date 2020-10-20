package jp.adapter.delipo.entity;

import org.json.JSONObject;

import androidx.annotation.NonNull;

public class ApiResult {
    public int statusCode;
    public String message;
    public String token;
    public String session;
    public Object response;
    public int total;

    public ApiResult(int statusCode) {
        this.statusCode = statusCode;
    }

    public ApiResult(int statusCode, String message, String token, JSONObject response, int total) {
        this.statusCode = statusCode;
        this.message = message;
        this.token = token;
//        this.session = session;
        this.response = response;
        this.total = total;
    }

    @NonNull
    @Override
    public String toString() {
        return "statusCode:" + statusCode + " - token:" + token + " - message:" + message+ " - response:" + response.toString();
    }
}

