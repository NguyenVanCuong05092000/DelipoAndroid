package jp.adapter.delipo.constants;

public interface ApiConstants {
    String API_KEY = AppConstants.TEST_MODE ? "db60c5d492311896d29d2631efa8d3de" : "46rvay2ram444yqnpa4wzcp2wnzkz7wk";
    String API_URL_VERSION = "v3/";
    String API_URL = (AppConstants.TEST_MODE ? "https://dev.delipo.info/api/" : "https://fdc.adapter-inc.com/saas/api/");
    String IMG_URL = AppConstants.TEST_MODE ? "https://adapter-inc.s3-ap-northeast-1.amazonaws.com" : "https://cdn.adapter-inc.com";
    String PERSONAL_URL = AppConstants.TEST_MODE ? "https://dev.adapter-inc.com/saas/personal_information" :
            "https://fdc.adapter-inc.com/saas/personal_information";
    String URL_LICENSE = AppConstants.TEST_MODE ? "https://dev.adapter-inc.com/saas/license_information" : "https://fdc.adapter-inc.com/saas/license_information";
    String URL_TERMS = AppConstants.TEST_MODE ? "https://adapter-inc.com/fdcterms/" : "https://adapter-inc.com/fdcterms/";
    String URL_PRIVACY = AppConstants.TEST_MODE ? "https://adapter-inc.com/privacy/" : "https://adapter-inc.com/privacy/";
    String URL_PROVIDER_COMPANY = AppConstants.TEST_MODE ? "https://adapter-inc.com/" : "https://adapter-inc.com/";
    String URL_CONTACT = AppConstants.TEST_MODE ? "https://adapter-inc.com/contact/" : "https://adapter-inc.com/contact/";
    String API_LOGIN = API_URL + "login";
    String API_LOGOUT = API_URL + "logout";
    String API_GET_LIST_PRODUCT = API_URL + "product";
    String API_GET_CATEGORIES = API_URL + "product_category";
    String API_PRODUCT = API_URL + "product";
    String API_DEL_PRODUCT = API_URL + "product/delete";
    String API_GET_LIST_USER = API_URL + "user";
    String API_USER = API_URL + "user/%s";
    String API_FORGOT = API_URL + "user/forgot";
    String API_RESET = API_URL + "user/password";
    String API_COUNT = API_URL + "upload/count";
    String API_MY_PAGE = API_URL + "user/iduser ";
    String API_REG_FiB_TOKEN_PUSH = API_URL + "user/register_token_push/%s";
    String API_GET_COUNT_UNREAD_NEWS = API_URL + "setting";
    String API_GET_LIST_NEWS = API_URL + "news";
    String API_GET_PRODUCT_BY_ID = API_URL + "product/%s";
    String API_TRACKING_LOCATION = API_URL + "location/register_location/%s";

    String API_LOGIN_FACEBOOK = API_URL + "login/login_social_facebook";
    String API_LOGIN_LINE = API_URL + "login/login_social_line";
    String API_LOGIN_GOOGLE = API_URL + "login/login_social_google";

    String API_REGISTER = API_URL + "user/register";

    String API_ACTIVE = API_URL + "user/activate";

    int API_VERSION = 1;
    //status response
    int STATUS_OK = 200;
    int STATUS_ERROR_TOKEN = 402;
    int STATUS_ERROR = 0;
    int STATUS_ERROR_INTERNET = 1;
    //
    int STATUS_EMPTY_FIELD = 601;
    int STATUS_PW_NOT_MATCH = 602;
    // paging request-response
    String LIMIT_DEFAULT = "20";
    //device type
    int ANDROID = 2;

    String PARAM_API_VERSION = "api_version";
    String PARAM_API = "api_key";
    String PARAM_TOKEN = "token";
    String PARAM_DEVICE_UUID = "device_uuid";
    String PARAM_DEVICE_TYPE = "device_type";
    String PARAM_EMAIL = "email";
    String PARAM_PASSWORD = "password";
    String PARAM_PASSWORD_CONFIRM = "confirm_password";
    String PARAM_PASSWORD_OLD = "oldPassword";
    String PARAM_TEL = "tel";
    String PARAM_STATUS = "status";
    String PARAM_MESSAGE = "message";
    String PARAM_RESPONSE = "response";
    String PARAM_EMPLOYEE_NAME = "employee_name";
    String PARAM_EMPLOYEE_ID = "employee_id";
    String PARAM_ID = "id";
    String PARAM_NAME = "name";
    String PARAM_HASH = "hash";
    String PARAM_PRODUCT_NAME = "product_name";
    String PARAM_CATEGORY = "category";
    String PARAM_CATEGORY_NAME = "category_name";
    String PARAM_PRICE = "price";
    String PARAM_PRICE_TOTAL = "price_total";
    String PARAM_PRICE_100 = "price_per_100g";
    String PARAM_WEIGHT = "weight";
    String PARAM_PRICE_MORE = "price_or_more";
    String PARAM_PRICE_LESS = "price_or_less";
    String PARAM_PRICE_LESS_THAN = "price_less_than";
    String PARAM_MANUFACTURER = "manufacturer";
    String PARAM_STORE_NAME = "store_name";
    String PARAM_REMARKS = "remarks";
    String PARAM_EXPIRATION_DATE = "expiration_date";
    String PARAM_PROCESSING_DATE = "processing_date";
    String PARAM_IMAGES_JSON = "images_json";
    String PARAM_TYPE = "type";
    String PARAM_LAT = "lat";
    String PARAM_LNG = "lng";
    String PARAM_URL = "url";
    String PARAM_CREATE_AT = "create_at";
    String PARAM_ORDER_BY = "order_by";
    String PARAM_SORT = "sort";
    String PARAM_OFFSET = "offset";
    String PARAM_LIMIT = "limit";
    String PARAM_SAASES_NAME = "saases_name";
    String PARAM_CORPORATE_EMPLOYEES_ID = "corporate_employees_id";
    String PARAM_COUNT = "count";
    String PARAM_TOTAL = "total";
    String PARAM_FIREBASE_TOKEN = "firebase_token";
    String PARAM_POST_TYPE = "type[]";
    String PARAM_POST_URL = "image_url";
    String PARAM_PENDING = "pending";
    String PARAM_RESP_COUNT_UNREAD_NEWS = "count_unread_news";
    String PARAM_RESP_NEWS_DATA = "news";
    String PARAM_RESP_CONTENT = "content";
    String PARAM_RESP_UPDATE_AT = "update_at";
    String PARAM_RESP_UNREAD = "unread";
    String PARAM_CORPORATES_ID = "corporates_id";
    String PARAM_DEPARTMENT = "department";
    String PARAM_CORPORATES_NAME = "corporates_name";
    String PARAM_USER_AMOUNT = "user_amount";
    String PARAM_UPLOAD_COUNT = "upload_count";
    String PARAM_RESPONSE_EXPIRATION = "expiration_at";
    String PARAM_ASC = "asc";
    String PARAM_DESC = "desc";
    String PARAM_ADMIN_EDITED = "admin_edited";

    String SORT_TYPE_SAAS_NAME = "saas_name";


    String PARAM_FAVORITE = "favorite";
    String PARAM_REGISTRATION = "registration";


    String PARAM_PUBLIC_PROFILE_FACEBOOK = "public_profile";
    String PARAM_ID_FACEBOOK = "id";
    String PARAM_NAME_FACEBOOK = "name";
    String PARAM_EMAIL_FACEBOOK = "email";
    String PARAM_FIELDS_FACEBOOK = "fields";
    String PARAM_FIELDS = "id,name,link,email";

    String CHANNEL_ID = "1654896470";
    String CONSUMER_KEY = "fsNiT8iwZnO4pr14FsHbFERez";
    String CONSUMER_SECRET = "Txs8XkKZ3jOqtBnMPF9k6nEFAtR4jYiG23zUxidKSRB3kI0yXm";

    String PARAM_ACCESS_TOKEN = "access_token";
    String PARAM_SOCIAL_ID = "social_id";

    String PARAM_GENDER = "gender";
    String PARAM_BIRTHDAY = "birthday";
    String PARAM_JOB = "job";
    String PARAM_FAMILY_NUMBER = "family_number";
    String PARAM_FAMILY_MEMBER = "family_member";
    String PARAM_ZIP_CODE = "zip_code";
    String PARAM_CODE = "code";
    String PARAM_FAMILY_INCOME = "family_income";
    String PARAM_LAST_LOGIN_FOR_API = "last_login_for_api";
    String PARAM_UPDATE_AT = "update_at";

}
