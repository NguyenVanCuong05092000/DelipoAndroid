package jp.adapter.delipo.screen.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.screen.activities.ActMain;

public abstract class BaseFragment extends Fragment implements ExtraConstants {
    protected String TAG = this.getClass().getSimpleName();

    protected ActMain activity;
    private boolean mIsClickAble = true;
    private Handler mHandlerClick = new Handler();
    private Runnable changeStateClickAble = new Runnable() {
        @Override
        public void run() {
            mIsClickAble = true;
        }
    };

    protected abstract int getLayoutResId();

    protected abstract int getCurrentFragment();

    protected abstract void loadControlsAndResize(View view);

    protected abstract void finish();

    public boolean isBackPreviousEnable() {
        return false;
    }

    public void backToPrevious() {
    }

    public boolean isMenu() {
        return false;
    }

    public void onPermissionsGranted() {
    }

    public boolean isClickAble() {
        if (mIsClickAble) {
            mIsClickAble = false;
            mHandlerClick.removeCallbacks(changeStateClickAble);
            mHandlerClick.postDelayed(changeStateClickAble, 200);
            return true;
        }

        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ActMain) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        if (activity == null)
            activity = (ActMain) getActivity();
        loadControlsAndResize(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (activity == null)
            activity = (ActMain) getActivity();

        if (!isMenu() && getCurrentFragment() != -1)
            activity.setCurrentScreen(getCurrentFragment());
    }

    @Override
    public void onDestroy() {
        try {
            mHandlerClick.removeCallbacks(changeStateClickAble);
            System.gc();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
