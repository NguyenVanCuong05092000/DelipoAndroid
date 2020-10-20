package jp.adapter.delipo.app;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import androidx.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
