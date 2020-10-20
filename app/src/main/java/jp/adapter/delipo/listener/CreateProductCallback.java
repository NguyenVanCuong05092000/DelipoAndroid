package jp.adapter.delipo.listener;

import jp.adapter.delipo.entity.ProductEntity;

public interface CreateProductCallback {
    void onUploadedLabelImg(int id);
    void onCreatedProduct(ProductEntity product);
}
