package jp.adapter.delipo.screen.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

import jp.adapter.delipo.R;
import jp.adapter.delipo.adapter.AdapterImgResource;
import jp.adapter.delipo.utils.DebugHelper;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_INTRO_AFTER_LOGIN;

public class FrmIntro extends BaseFragment implements View.OnClickListener {

    private int counterPageScroll;
    private int[] mImageIds =
            new int[]{R.drawable.bg_intro_step_11, R.drawable.bg_intro_step_21,
                    R.drawable.bg_intro_step_31, R.drawable.bg_intro_step_41};
    private ImageView btnIntroBack;
    private ImageView icIntroSkip;
    private Button btnIntroNext;
    private TextView btnIntroSkip;
    private ViewPager vpIntroduction;
    private ViewGroup.LayoutParams lpImages;
    private View relativeLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_intro;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_INTRO_AFTER_LOGIN;
    }

    @Override
    protected void finish() {
        try {
            activity.backToHome();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isBackPreviousEnable() {
        return true;
    }

    @Override
    public void backToPrevious() {
        finish();
    }

    @Override
    protected void loadControlsAndResize(View view) {
        relativeLayout = view.findViewById(R.id.relativeLayout);
        relativeLayout.getLayoutParams().height = activity.getSizeWithScale(245);
        vpIntroduction = view.findViewById(R.id.view_pager_intro);
        lpImages = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int mWidthImages = activity.getSizeWithScale(375);
        int mHeightImages = activity.getSizeWithScale(812);
        int mHeightScreen = activity.getScreenHeight();
        DebugHelper.Log(TAG, "onViewCreated:>>> mHeightImages: " + mHeightImages + " mHeightScreen: " + mHeightScreen);
        if (mHeightImages > mHeightScreen) {
            lpImages.width = (mWidthImages * mHeightScreen) / mHeightImages;
            lpImages.height = mHeightScreen;
        } else {
            lpImages.width = mWidthImages;
            lpImages.height = mHeightImages;
        }
        DebugHelper.Log(TAG, "onViewCreated:>>>lpImages.width: " + lpImages.width + " lpImages.height: " + lpImages.height);

        btnIntroBack = view.findViewById(R.id.image_view_back_intro);
        btnIntroNext = view.findViewById(R.id.image_view_next_intro);
        btnIntroSkip = view.findViewById(R.id.text_view_skip_intro);
        icIntroSkip = view.findViewById(R.id.image_view_skip_intro);

        btnIntroNext.getLayoutParams().width = activity.getSizeWithScale(853);
        btnIntroNext.getLayoutParams().height = activity.getSizeWithScale(178);

        btnIntroBack.getLayoutParams().width = activity.getSizeWithScale(115);
        btnIntroBack.getLayoutParams().height = activity.getSizeWithScale(82);

        View decorView = Objects.requireNonNull(getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        btnIntroBack.setOnClickListener(this);
        btnIntroNext.setOnClickListener(this);
        btnIntroSkip.setOnClickListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vpIntroduction.setAdapter(new AdapterImgResource(getContext(), mImageIds, lpImages));
        vpIntroduction.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                try {
                    if (position == mImageIds.length - 1) {
                        if (counterPageScroll != 0) {
                            finish();
                        }
                        counterPageScroll++;
                    } else {
                        counterPageScroll = 0;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {
                onPageChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        onPageChange(0);
    }

    private void onPageChange(int position) {
        try {
            if (position == 0) {
                btnIntroBack.setVisibility(View.GONE);
                btnIntroSkip.setVisibility(View.VISIBLE);
                icIntroSkip.setVisibility(View.VISIBLE);
                btnIntroNext.setBackgroundResource(R.drawable.bg_button_click_intro);
            } else if (position == mImageIds.length - 1) {
                btnIntroSkip.setVisibility(View.GONE);
                icIntroSkip.setVisibility(View.GONE);
                btnIntroBack.setVisibility(View.VISIBLE);
                btnIntroNext.setBackgroundResource(R.drawable.bg_button_click_intro_end);
            } else {
                btnIntroBack.setVisibility(View.VISIBLE);
                btnIntroSkip.setVisibility(View.VISIBLE);
                icIntroSkip.setVisibility(View.VISIBLE);
                btnIntroNext.setBackgroundResource(R.drawable.bg_button_click_intro);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void changePosition(boolean isPrevious) {
        try {
//            vpIntroduction.setCurrentItem(vpIntroduction.getCurrentItem() + (isPrevious ? -1 : 1));
            if (isPrevious) {
                vpIntroduction.setCurrentItem(vpIntroduction.getCurrentItem() - 1);
            } else if (vpIntroduction.getCurrentItem() == mImageIds.length - 1) {
                finish();
            } else {
                vpIntroduction.setCurrentItem(vpIntroduction.getCurrentItem() + 1);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (!isClickAble())
            return;
        switch (v.getId()) {
            case R.id.image_view_back_intro:
                changePosition(true);
                break;
            case R.id.image_view_next_intro:
                changePosition(false);
                break;
            case R.id.text_view_skip_intro:
                finish();
                break;
        }
    }
}
