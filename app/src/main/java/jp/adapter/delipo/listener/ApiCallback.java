package jp.adapter.delipo.listener;

import jp.adapter.delipo.entity.ApiResult;

public interface ApiCallback {
    void onFinished(ApiResult result);
}
