package jp.adapter.delipo.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.screen.fragment.BaseFragment;
import jp.adapter.delipo.screen.fragment.FrmPolicy;
import jp.adapter.delipo.screen.fragment.FrmRules;
import jp.adapter.delipo.test.adapter.ProductAdapter;
import jp.adapter.delipo.test.listener.OnClickProductListener;
import jp.adapter.delipo.test.model.Product;
import jp.adapter.delipo.test.utils.AnimatorUtils;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_HOME;

public class FrmHome extends BaseFragment implements View.OnClickListener, OnClickProductListener {

    public static FrmHome getInstance() {
        return new FrmHome();
    }

    public static FrmHome getInstance(Bundle dataSave) {
        FrmHome fragment = new FrmHome();
        if (dataSave != null) {
            Bundle data = new Bundle();
            data.putBundle(ExtraConstants.EXTRA_SAVE_INSTANCE_STATE, dataSave);
            fragment.setArguments(data);
        }
        return fragment;
    }

    public static FrmHome getInstanceWithBackStackData(HashMap<String, Object> mapData) {
        FrmHome fragment = new FrmHome();
        if (mapData != null && mapData.containsKey(ExtraConstants.EXTRA_POSITION)) {
            Bundle data = new Bundle();
            data.putInt(ExtraConstants.EXTRA_POSITION, (Integer) mapData.get(ExtraConstants.EXTRA_POSITION));
            data.putInt(ExtraConstants.EXTRA_OFFSET, (Integer) mapData.get(ExtraConstants.EXTRA_OFFSET));
            fragment.setArguments(data);
        }
        return fragment;
    }

    private RecyclerView recycler;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    private CircleImageView fab;
    private RelativeLayout rootLayout;
    private ArcLayout arcLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.recycler);
        setData();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(productAdapter);

        arcLayout = view.findViewById(R.id.arc_layout);
        arcLayout.setOnClickListener(this);
        arcLayout.setOnClickListener(this);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    private void setData() {
        productList = new ArrayList<>();
        productList.add(new Product("お弁当", "幕の内・鮭弁・ハンバーグ弁当\n唐揚げ弁当・オムライス弁当など", R.drawable.thumb_product1));
        productList.add(new Product("丼もの", "かつ丼・天丼・うな丼\n 親子丼・ビビンバ丼など", R.drawable.thumb_product2));
        productList.add(new Product("お寿司・お刺身", "生ずし・手巻き寿司・助六\nお刺身セットなど", R.drawable.thumb_product3));

        productList.add(new Product("麺類", "おそば・うどん・ラーメン\nパスタ・アジアン麺など", R.drawable.thumb_product1));
        productList.add(new Product("お惣菜", "コロッケ・からあげ・天ぷら\nとんかつ・和え物・炒め物など", R.drawable.thumb_product4));
        productList.add(new Product("サラダ", "XXXX・XXXX・XXXX\nXXXXなど", R.drawable.thumb_product3));

        productList.add(new Product("ごはん・\nおにぎり", "おこわ・おにぎり・お赤飯\nパエリア・XXXXなど", R.drawable.thumb_product1));
        productList.add(new Product("調理パン", "サンドイッチ・焼きそばパン\n※　メーカー製造のパンは除く", R.drawable.thumb_product2));
        productList.add(new Product("デザート", "ケーキ・おはぎ・パイ\n※　メーカー製造のパンは除く", R.drawable.thumb_product3));

        productList.add(new Product("スナック", "焼きそば・焼き鳥\nたこ焼き・お好み焼き・ピザなど", R.drawable.thumb_product1));
        productList.add(new Product("盛合せ\nオードブル", "", R.drawable.thumb_product2));
        productList.add(new Product("迷ったら？", "カテゴリー分けに迷ったら\nここに登録してください", R.drawable.thumb_product3));

        productAdapter = new ProductAdapter(productList, getActivity(), this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_home;
    }

    @Override
    protected int getCurrentFragment() {
        return FRM_HOME;
    }

    @Override
    protected void loadControlsAndResize(View view) {
        View btnHome = view.findViewById(R.id.btnHome);
//        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnSearch = view.findViewById(R.id.btnSearch);
//        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnRating = view.findViewById(R.id.btnHomeRate);
//        btnRating.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnRating.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnMenu = view.findViewById(R.id.btnMenu);
//        btnMenu.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnMenu.getLayoutParams().height = activity.getSizeWithScale(48);

        View btnMyPage = view.findViewById(R.id.btnMyPage);
//        btnMyPage.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnMyPage.getLayoutParams().height = activity.getSizeWithScale(48);

        View bottomBar = view.findViewById(R.id.bottomBar);
//        int bottomBarPadding = activity.getSizeWithScale(15);
//        bottomBar.setPadding(bottomBarPadding, 0, bottomBarPadding, 0);


        btnHome.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRating.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMyPage.setOnClickListener(this);
        view.findViewById(R.id.topBar).setOnClickListener(this);
        view.findViewById(R.id.tvPolicy).setOnClickListener(this);

        view.findViewById(R.id.ciFriedFood).setOnClickListener(this);
        view.findViewById(R.id.ciSteamedFood).setOnClickListener(this);
        view.findViewById(R.id.ciBoiledFood).setOnClickListener(this);
        view.findViewById(R.id.ciStirFry).setOnClickListener(this);
        view.findViewById(R.id.ciPottery).setOnClickListener(this);
        view.findViewById(R.id.ciTopping).setOnClickListener(this);

        fab = view.findViewById(R.id.fab);
        rootLayout = view.findViewById(R.id.root_layout);

        fab.setOnClickListener(this);
        rootLayout.setOnClickListener(this);

    }

    @Override
    protected void finish() {
        getActivity().finish();
    }

    @Override
    public boolean isBackPreviousEnable() {
        return true;
    }

    @Override
    public void backToPrevious() {
        if (rootLayout.getVisibility() == View.VISIBLE) {
            hideMenu();
        } else finish();
    }

    private void showRateProduct() {
        try {
            ArrayList<BackStackData> arrayList = new ArrayList<>();
            arrayList.add(new BackStackData(getCurrentFragment(), null));
            activity.showRateProduct(arrayList, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }

    private void signOut() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.arc_layout:
//            case R.id.btnHome:
//                if (rootLayout.getVisibility() == View.VISIBLE) {
//                    hideMenu();
//                }
//                break;
//            case R.id.btnSearch:
//                signOut();
//                disconnectFromFacebook();
//                break;
//            case R.id.btnHomeRate:
//                showRateProduct();
//                break;
//            case R.id.btnMenu:
//                activity.addFragmentToMain(FrmScanCode.getInstance());
//                break;
//            case R.id.btnMyPage:
//                activity.addFragmentToMain(FrmMyPage.getInstance());
//                break;
//            case R.id.topBar:
//                activity.addFragmentToMain(FrmRules.getInstance());
//                break;
//            case R.id.tvPolicy:
//                activity.addFragmentToMain(FrmPolicy.getInstance());
//                break;
            case R.id.fab:
                hideMenu();
                break;
//            case R.id.ciFriedFood:
//            case R.id.ciSteamedFood:
//            case R.id.ciBoiledFood:
//            case R.id.ciStirFry:
//            case R.id.ciPottery:
//            case R.id.ciTopping:
//                activity.showCreateProduct(null, null);
//                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("NewApi")
    private void showMenu() {
        rootLayout.setVisibility(View.VISIBLE);
        List<Animator> animList = new ArrayList<>();
        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {
        List<Animator> animList = new ArrayList<>();
        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
    }

    private Animator createShowItemAnimator(View item) {
        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();
        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);
        return ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        return anim;
    }

    @Override
    public void onClickProduct(Product product, int position) {
        switch (position) {
            case 0:
            case 3:
            case 6:
            case 9:
                showMenu();
                fab.setImageResource(R.drawable.thumb_product1);
                break;
            case 1:
            case 7:
            case 10:
                showMenu();
                fab.setImageResource(R.drawable.thumb_product2);
                break;
            case 2:
            case 5:
            case 8:
            case 11:
                showMenu();
                fab.setImageResource(R.drawable.thumb_product3);
                break;
            case 4:
                showMenu();
                fab.setImageResource(R.drawable.thumb_product4);
                break;
        }
    }

}
