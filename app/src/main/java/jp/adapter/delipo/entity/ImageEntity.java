package jp.adapter.delipo.entity;

import org.json.JSONObject;

import java.io.Serializable;

import jp.adapter.delipo.constants.ApiConstants;

public class ImageEntity implements Serializable {
    private static final long serialVersionUID = -2940818745339594322L;
    public int type;
    private String url;
    public ImageEntity(JSONObject json) throws Exception{
        url = json.optString(ApiConstants.PARAM_URL);
        type = json.optInt(ApiConstants.PARAM_TYPE);
    }
    public String getUrl(){
        return ApiConstants.IMG_URL+url;
    }
}