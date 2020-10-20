package jp.adapter.delipo.utils;

import android.util.Log;

import jp.adapter.delipo.constants.AppConstants;

public class DebugHelper {
    public static void Log(String TAG, String msg) {
        if (AppConstants.LOG_DEBUG) {
            Log.d(TAG, msg);
        }
    }
}
