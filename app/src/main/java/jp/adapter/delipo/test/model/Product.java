package jp.adapter.delipo.test.model;


import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 729285939867258837L;

    private String mCategory;
    private String mDetail;
    private int mImage;

    public Product(String mCategory, String mDetail, int mImage) {
        this.mCategory = mCategory;
        this.mDetail = mDetail;
        this.mImage = mImage;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }
}
