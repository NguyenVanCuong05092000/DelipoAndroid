package jp.adapter.delipo.screen.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

import static jp.adapter.delipo.constants.NotificationConstants.NOTIFICATION_DATA_TRANSFER;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        Log.d("SplashActivity","data: "+(data == null));
        if (data!=null) {
            String scheme = data.getScheme();
            String host = data.getHost();
            List<String> params = data.getPathSegments();
            Log.d("SplashActivity","scheme: "+scheme+"host: "+host+"params: "+ Arrays.toString(new List[]{params}));
        }

        new Handler().postDelayed(() -> {
            try {
                if (!isFinishing()) {
                    Intent intent = new Intent(SplashActivity.this, ActMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if (getIntent().getExtras() != null) {
                        Bundle bundlePushData = new Bundle();
                        for (String key : getIntent().getExtras().keySet()) {
                            bundlePushData.putString(key, String.valueOf(getIntent().getExtras().get(key)));
                        }
                        intent.putExtra(NOTIFICATION_DATA_TRANSFER, bundlePushData);
                    }
                    startActivity(intent);
                    finish();
                }
            } catch (Throwable ignored) {
            }
        }, 2000);
    }
}
