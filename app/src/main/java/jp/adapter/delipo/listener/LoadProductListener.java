package jp.adapter.delipo.listener;

import java.util.ArrayList;

import jp.adapter.delipo.entity.ProductEntity;

public interface LoadProductListener {
    void onLoadProductComplete(ArrayList<ProductEntity> arrayList, boolean isLoadMore);
}
