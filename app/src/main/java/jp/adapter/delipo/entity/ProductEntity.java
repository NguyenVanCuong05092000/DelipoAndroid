package jp.adapter.delipo.entity;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import jp.adapter.delipo.constants.ApiConstants;

public class ProductEntity implements Serializable {

    public int id = -1;
    public String name;
    public ImageEntity imgThumb;
    public ArrayList<ImageEntity> listImage;
    public int categoryId;
    public String categoryName;
    public long price;
    public double priceTotal;
    public long pricePer100;
    public long weight;
    public String expirationDate;
    public int expirationYear;
    public int expirationMonth;
    public int expirationDay;
    public int expirationHour;
    public int expirationMinute;
    public int expirationSecond;
    public String processingDate;
    public int processingYear;
    public int processingMonth;
    public int processingDay;
    public int processingHour;
    public int processingMinute;
    public int processingSecond;
    public String createAt;
    public int createYear;
    public int createMonth;
    public int createDay;
    public int createHour;
    public int createMinute;
    public int createSecond;
    public String displayCreateAt;
    public String manufacturer;
    public String storeName;
    public String remarks;
    public String saasesName;
    public String corporateEmployeesId;
    public int countImgProduct = 0;
    public boolean needUpdate = false;
    public int adminEdited = 0;

    public ProductEntity() {
    }

    public ProductEntity clone() {
        ProductEntity entity = new ProductEntity();
        entity.id = id;
        entity.name = name;
        entity.imgThumb = imgThumb;
        entity.listImage = listImage;
        entity.categoryId = categoryId;
        entity.categoryName = categoryName;
        entity.price = price;
        entity.priceTotal = priceTotal;
        entity.pricePer100 = pricePer100;
        entity.weight = weight;
        entity.expirationDate = expirationDate;
        entity.expirationYear = expirationYear;
        entity.expirationMonth = expirationMonth;
        entity.expirationDay = expirationDay;
        entity.expirationHour = expirationHour;
        entity.expirationMinute = expirationMinute;
        entity.expirationSecond = expirationSecond;
        entity.processingDate = processingDate;
        entity.processingYear = processingYear;
        entity.processingMonth = processingMonth;
        entity.processingDay = processingDay;
        entity.processingHour = processingHour;
        entity.processingMinute = processingMinute;
        entity.processingSecond = processingSecond;
        entity.createAt = createAt;
        entity.createYear = createYear;
        entity.createMonth = createMonth;
        entity.createDay = createDay;
        entity.createHour = createHour;
        entity.createMinute = createMinute;
        entity.createSecond = createSecond;
        entity.displayCreateAt = displayCreateAt;
        entity.manufacturer = manufacturer;
        entity.storeName = storeName;
        entity.remarks = remarks;
        entity.saasesName = saasesName;
        entity.corporateEmployeesId = corporateEmployeesId;
        entity.countImgProduct = countImgProduct;
        entity.needUpdate = needUpdate;
        entity.adminEdited = adminEdited;
        return entity;
    }

    public ProductEntity(JSONObject json, SimpleDateFormat dateFormat, Calendar tmpCalendar) throws Exception {
        id = json.optInt(ApiConstants.PARAM_ID);
        name = json.optString(ApiConstants.PARAM_NAME);
        if (TextUtils.isEmpty(name) || name.equals("null")) name = "";
        categoryId = json.optInt(ApiConstants.PARAM_CATEGORY);
        categoryName = json.optString(ApiConstants.PARAM_CATEGORY_NAME);
        if (TextUtils.isEmpty(categoryName) || categoryName.equals("null")) categoryName = "";
        price = json.optLong(ApiConstants.PARAM_PRICE);
        priceTotal = json.optDouble(ApiConstants.PARAM_PRICE_TOTAL);
        pricePer100 = json.optLong(ApiConstants.PARAM_PRICE_100);
        weight = json.optLong(ApiConstants.PARAM_WEIGHT);
        expirationDate = json.optString(ApiConstants.PARAM_EXPIRATION_DATE);
        if (TextUtils.isEmpty(expirationDate) || expirationDate.equals("null")) expirationDate = "";
        int[] value1 = getDateTimeValues(expirationDate, dateFormat, tmpCalendar);
        expirationYear = value1[0];
        expirationMonth = value1[1];
        expirationDay = value1[2];
        expirationHour = value1[3];
        expirationMinute = value1[4];
        expirationSecond = value1[5];
        processingDate = json.optString(ApiConstants.PARAM_PROCESSING_DATE);
        if (TextUtils.isEmpty(processingDate) || processingDate.equals("null")) processingDate = "";
        int[] value2 = getDateTimeValues(processingDate, dateFormat, tmpCalendar);
        processingYear = value2[0];
        processingMonth = value2[1];
        processingDay = value2[2];
        processingHour = value2[3];
        processingMinute = value2[4];
        processingSecond = value2[5];
        createAt = json.optString(ApiConstants.PARAM_CREATE_AT);
        if (TextUtils.isEmpty(createAt) || createAt.equals("null")) createAt = "";
        int[] value3 = getDateTimeValues(createAt, dateFormat, tmpCalendar);
        createYear = value3[0];
        createMonth = value3[1];
        createDay = value3[2];
        createHour = value3[3];
        createMinute = value3[4];
        createSecond = value3[5];
        displayCreateAt = getDisplayCreateAt();
        manufacturer = json.optString(ApiConstants.PARAM_MANUFACTURER);
        if (TextUtils.isEmpty(manufacturer) || manufacturer.equals("null")) manufacturer = "";
        storeName = json.optString(ApiConstants.PARAM_STORE_NAME);
        if (TextUtils.isEmpty(storeName) || storeName.equals("null")) storeName = "";
        remarks = json.optString(ApiConstants.PARAM_REMARKS);
        if (TextUtils.isEmpty(remarks) || remarks.equals("null")) remarks = "";
        saasesName = json.optString(ApiConstants.PARAM_SAASES_NAME);
        if (TextUtils.isEmpty(saasesName) || saasesName.equals("null")) saasesName = "";
        corporateEmployeesId = json.optString(ApiConstants.PARAM_CORPORATE_EMPLOYEES_ID);
        adminEdited = json.optInt(ApiConstants.PARAM_ADMIN_EDITED);
        try {
            JSONArray jsonArray = new JSONArray(json.optString(ApiConstants.PARAM_IMAGES_JSON));
            if (jsonArray.length() > 0) {
                listImage = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ImageEntity imageEntity = new ImageEntity(jsonArray.getJSONObject(i));
                    listImage.add(imageEntity);
                    if (imageEntity.type == 2) {
                        countImgProduct++;
                        if (imgThumb == null)
                            imgThumb = imageEntity;
                    }
                }
                if (imgThumb == null)
                    imgThumb = listImage.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] getDateTimeValues(String dateTimeData, SimpleDateFormat dateFormat, Calendar tmpCalendar) {
        try {
            if (!TextUtils.isEmpty(dateTimeData)) {
                tmpCalendar.setTime(dateFormat.parse(dateTimeData));
                return new int[]{tmpCalendar.get(Calendar.YEAR), tmpCalendar.get(Calendar.MONTH) + 1,
                        tmpCalendar.get(Calendar.DAY_OF_MONTH), tmpCalendar.get(Calendar.HOUR_OF_DAY),
                        tmpCalendar.get(Calendar.MINUTE), tmpCalendar.get(Calendar.SECOND)};
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new int[]{0, 0, 0, 0, 0, 0};
    }

    private String getShortDateTimeText(int y, int M, int d, int h, int m) {
        if (y < 1000)
            return "";
        return y + "-"
                + (M > 9 ? M : "0" + M)
                + "-" + (d > 9 ? d : "0" + d)
                + " " + (h > 9 ? h : "0" + h)
                + ":" + (m > 9 ? m : "0" + m);
    }

    private String getDateTimeText(int y, int M, int d, int h, int m, int s) {
        String shortTime = getShortDateTimeText(y, M, d, h, m);
        if (TextUtils.isEmpty(shortTime))
            return "";
        return shortTime
                + ":" + (s > 9 ? s : "0" + s);
    }

    public String getExpirationDate() {
        return getDateTimeText(expirationYear, expirationMonth, expirationDay,
                expirationHour, expirationMinute, expirationSecond);
    }

    public String getShortExpirationDate() {
        return getShortDateTimeText(expirationYear, expirationMonth, expirationDay,
                expirationHour, expirationMinute);
    }

    public String getProcessingDate() {
        return getDateTimeText(processingYear, processingMonth, processingDay,
                processingHour, processingMinute, processingSecond);
    }

    public String getShortProcessingDate() {
        return getShortDateTimeText(processingYear, processingMonth, processingDay,
                processingHour, processingMinute);
    }

    public String getCreateAt() {
        return getDateTimeText(createYear, createMonth, createDay,
                createHour, createMinute, createSecond);
    }

    public String getShortCreateAt() {
        return getShortDateTimeText(createYear, createMonth, createDay,
                createHour, createMinute);
    }

    private String getDisplayCreateAt() {
        return getShortDateTimeText(createYear, createMonth, createDay,
                createHour, createMinute).replace(" ", "\n");
    }

}
