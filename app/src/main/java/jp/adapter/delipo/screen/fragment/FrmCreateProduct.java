package jp.adapter.delipo.screen.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.ZoomState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.AppConstants;
import jp.adapter.delipo.constants.ExtraConstants;
import jp.adapter.delipo.constants.FragmentConstants;
import jp.adapter.delipo.entity.BackStackData;
import jp.adapter.delipo.entity.ProductEntity;
import jp.adapter.delipo.listener.CreateProductCallback;
import jp.adapter.delipo.listener.PickImageListener;
import jp.adapter.delipo.utils.CameraXCustomPreviewView;

import static jp.adapter.delipo.constants.FragmentConstants.FRM_EDIT_PRODUCT;
import static jp.adapter.delipo.constants.FragmentConstants.FRM_INFO_PRODUCT;

public class FrmCreateProduct extends BaseFragment implements View.OnClickListener, PickImageListener, CreateProductCallback {

    public static FrmCreateProduct getInstance() {
        return new FrmCreateProduct();
    }

    public static FrmCreateProduct getInstance(ArrayList<BackStackData> arrayList, HashMap<String, Object> mapData) {
        FrmCreateProduct fragment = new FrmCreateProduct();
        Bundle data = new Bundle();
        if (mapData != null) {
            if (mapData.containsKey(EXTRA_PRODUCT))
                data.putSerializable(EXTRA_PRODUCT, (ProductEntity) mapData.get(EXTRA_PRODUCT));
            if (mapData.containsKey(EXTRA_CATEGORY_ID))
                data.putString(EXTRA_CATEGORY_ID, (String) mapData.get(EXTRA_CATEGORY_ID));
        }
        if (arrayList != null)
            data.putSerializable(EXTRA_BACK_STACK, arrayList);
        fragment.setArguments(data);
        return fragment;
    }

    public static FrmCreateProduct getInstance(ProductEntity productEntity, String categoryId, int fromFragment) {
        FrmCreateProduct fragment = new FrmCreateProduct();
        Bundle data = new Bundle();
        if (productEntity != null)
            data.putSerializable(EXTRA_PRODUCT, productEntity);
        data.putString(EXTRA_CATEGORY_ID, categoryId);
        data.putInt(EXTRA_FROM, fromFragment);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_create_product;
    }

    @Override
    protected int getCurrentFragment() {
        return FragmentConstants.FRM_CREATE_PRODUCT;
    }

    private static final int[] listImageHolder = {R.drawable.img_start_capture,
            R.drawable.img_tut_capture_step_1,
            R.drawable.img_tut_capture_step_2,
            R.drawable.img_tut_capture_step_3,
            R.drawable.img_tut_capture_step_4};
    private ExecutorService cameraExecutor;
    private ProcessCameraProvider cameraProvider;
    private CameraXCustomPreviewView previewView;
    private CropImageView mCropImageView;
    private ImageView imgResult;
    private ImageView imgFaded;
    private ImageCapture imageCapture;
    private View frameCrop;
    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvNotice;
    private int cameraSize;
    private ProductEntity productEntity;
    private Animation animImgTutCapture;
    private Animation animImgTutCaptureSkipDelay;
    private View llBtnImgProduct;

    private CameraInfo mCameraInfo;
    private CameraControl mCameraControl;
    private int mCameraSelectorInt = CameraSelector.LENS_FACING_BACK;
    private static final int CHANGE_TYPE_SELECTOR = 0;
    private AppCompatButton btnCameraSelector;
    private AppCompatButton btnLight;

    @Override
    protected void loadControlsAndResize(View view) {
        previewView = view.findViewById(R.id.preview);
        mCropImageView = view.findViewById(R.id.cropImageView);
        imgResult = view.findViewById(R.id.imgResult);
        imgFaded = view.findViewById(R.id.imgFaded);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvMessage = view.findViewById(R.id.tvMessage);
        tvNotice = view.findViewById(R.id.tvNotice);
        frameCrop = view.findViewById(R.id.frameCrop);

//        llBtnImgProduct = view.findViewById(R.id.llBtnImgProduct);
//        llBtnImgProduct.setPadding(activity.getSizeWithScale(25),
//                0, activity.getSizeWithScale(25), 0);

//        View btnCancel = view.findViewById(R.id.btnCancel);
//        btnCancel.getLayoutParams().width = activity.getSizeWithScale(70);
//        btnCancel.getLayoutParams().height = activity.getSizeWithScale(30);

//        View btnSendProductImg = view.findViewById(R.id.btnSendProductImg);
//        btnSendProductImg.getLayoutParams().width = activity.getSizeWithScale(70);
//        btnSendProductImg.getLayoutParams().height = activity.getSizeWithScale(30);

//        btnCancel.setOnClickListener(this);
//        btnSendProductImg.setOnClickListener(this);

        View bottomBar = view.findViewById(R.id.bottomBar);
//        bottomBar.getLayoutParams().height = activity.getSizeWithScale(107);
        btnCameraSelector = view.findViewById(R.id.btn_camera_selector);
//        btnHome.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnHome.getLayoutParams().height = activity.getSizeWithScale(48);
//        View btnSearch = view.findViewById(R.id.btnSearch);
//        btnSearch.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnSearch.getLayoutParams().height = activity.getSizeWithScale(48);
        View btnCapture = view.findViewById(R.id.btnCapture);
//        btnCapture.getLayoutParams().width = activity.getSizeWithScale(60);
//        btnCapture.getLayoutParams().height = activity.getSizeWithScale(99);
//        View btnMenu = view.findViewById(R.id.btnMenu);
//        btnMenu.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnMenu.getLayoutParams().height = activity.getSizeWithScale(48);
        btnLight = view.findViewById(R.id.btn_light);
//        btnMyPage.getLayoutParams().width = activity.getSizeWithScale(48);
//        btnMyPage.getLayoutParams().height = activity.getSizeWithScale(48);

        int textTitleH = (int) ((16 + 2 * 2 + 20 + 10) * activity.getScreenDensity());
        int textMessageH = (int) ((14 * 2 + 2 * 4 + 10) * activity.getScreenDensity());
        int textNoticeH = (int) ((14 + 2 * 2 + 10 * 2) * activity.getScreenDensity());
        int cameraW = activity.getScreenWidth() - activity.getSizeWithScale(40);
        int cameraH = activity.getScreenHeight()
                - btnCameraSelector.getLayoutParams().height
                - textTitleH - textMessageH - textNoticeH
                - bottomBar.getLayoutParams().height;
        cameraSize = Math.min(cameraW, cameraH);
        previewView.getLayoutParams().width = cameraSize;
        previewView.getLayoutParams().height = cameraSize;
        imgResult.getLayoutParams().width = cameraSize;
        imgResult.getLayoutParams().height = cameraSize;
        imgFaded.getLayoutParams().width = cameraSize;
        imgFaded.getLayoutParams().height = cameraSize;

        btnCameraSelector.setOnClickListener(this);
//        btnSearch.setOnClickListener(this);
        btnCapture.setOnClickListener(this);
//        btnMenu.setOnClickListener(this);
        btnLight.setOnClickListener(this);


        animImgTutCapture = AnimationUtils.loadAnimation(activity, R.anim.fade_out_holder);
        animImgTutCaptureSkipDelay = AnimationUtils.loadAnimation(activity,
                R.anim.fade_out_holder_without_delay);

    }

    @Override
    protected void finish() {
        try {
            activity.backToPreviousFromBackStack(getArguments());
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
        try {
            if (frameCrop.isShown()) {
                showHideCrop(null);
                return;
            }

            if (!imgResult.isShown() || imgResult.isShown()) {
                if (productEntity != null && listImageCaptured != null
                        && !listImageCaptured.isEmpty()) {
                    imgResult.setImageURI(Uri.fromFile(listImageCaptured.get(listImageCaptured.size() - 1)));
                    return;
                }
                if (listImageCaptured != null && !listImageCaptured.isEmpty()) {
                    listImageCaptured.remove(listImageCaptured.size() - 1);
                    if (productEntity != null) {
                        showViewsCaptureLabel();
                    } else {
                        forceStopAndClearImgTut(false);
                        showViewsCaptureProduct();
                    }
                    return;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        finish();
    }

    private Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                forceStopAndClearImgTut(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    private void showImgTutCapture(int resId) {
        try {
            imgFaded.setImageResource(resId);
            imgFaded.setVisibility(View.VISIBLE);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void forceStopAndClearImgTut(boolean isClearImg) {
        try {
            animImgTutCaptureSkipDelay.setAnimationListener(null);
            animImgTutCapture.setAnimationListener(null);
            imgFaded.clearAnimation();
            if (isClearImg) {
                imgFaded.setImageBitmap(null);
                imgFaded.setVisibility(View.GONE);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void startFadeoutImgTutCapture(boolean isSkipDelay) {
        try {
            if (imgFaded.isShown()) {
                if (isSkipDelay) {
                    animImgTutCaptureSkipDelay.setAnimationListener(getAnimationListener());
                    imgFaded.startAnimation(animImgTutCaptureSkipDelay);
                } else {
                    animImgTutCapture.setAnimationListener(getAnimationListener());
                    imgFaded.startAnimation(animImgTutCapture);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsGranted() {
        initPreviewView();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() == null) {
            finish();
            return;
        }
        productEntity = (ProductEntity) getArguments().getSerializable(ExtraConstants.EXTRA_PRODUCT);
        cameraExecutor = Executors.newSingleThreadExecutor();
        if (!activity.isNeedPermissions())
            initPreviewView();

//        if (productEntity == null)
        showViewsCaptureLabel();
//        else
//            showViewsCaptureProduct();
    }

    private void showViewsCaptureLabel() {
        try {
            showImgTutCapture(listImageHolder[0]);
            imgResult.setImageBitmap(null);
            imgResult.setVisibility(View.GONE);
            tvTitle.setText(R.string.titleCaptureLabel);
            tvMessage.setText(R.string.msgCaptureLabel);
            tvNotice.setText(R.string.noticeCaptureLabel);
            startFadeoutImgTutCapture(false);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showViewsCaptureProduct() {
        try {
            forceStopAndClearImgTut(false);
            imgResult.setImageBitmap(null);
            imgResult.setVisibility(View.GONE);
            tvTitle.setText(R.string.titleCaptureProduct);
            tvMessage.setText(R.string.msgCaptureProduct);
            tvNotice.setText(R.string.noticeCaptureProduct);
            int count = countImgProductCaptured();
            showImgTutCapture(listImageHolder[Math.min(count, 4)]);
            startFadeoutImgTutCapture(false);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private int countImgProductCaptured() {
        int count = 0;
        if (productEntity != null)
            count += productEntity.countImgProduct;
        if (listImageCaptured != null)
            count += listImageCaptured.size();
        return count;
    }

    private void initPreviewView() {
        try {
            previewView.post(new Runnable() {
                @Override
                public void run() {
                    setUpCamera();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            cameraExecutor.shutdown();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize CameraX, and prepare to bind the camera use cases
     */
    private void setUpCamera() {
        try {
            ListenableFuture<ProcessCameraProvider> cameraProviderFuture
                    = ProcessCameraProvider.getInstance(activity);
            cameraProviderFuture.addListener(new Runnable() {
                @Override
                public void run() {
                    // CameraProvider
                    try {
                        cameraProvider = cameraProviderFuture.get();
                        // Build and bind the camera use cases
                        bindCameraUseCases();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }, ContextCompat.getMainExecutor(activity));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Declare and bind preview, capture and analysis use cases
     */
    private void bindCameraUseCases() {
        try {
            if (AppConstants.LOG_DEBUG) Log.e("cameraSize", "->" + cameraSize);
            Size size = new Size(Math.max(1024, cameraSize), Math.max(1024, cameraSize));

            // Preview
            Preview mPreview = new Preview.Builder()
                    .setTargetResolution(size)
                    .build();

            // ImageCapture
            imageCapture = new ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .setTargetResolution(size)
                    .build();

            // ChangeCamera
            CameraSelector mCameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(mCameraSelectorInt)
                    .build();


            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll();
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            Camera camera = cameraProvider.bindToLifecycle(this, mCameraSelector,
                    mPreview, imageCapture);

            mCameraInfo = camera.getCameraInfo();
            mCameraControl = camera.getCameraControl();

            // Listener
            initCameraListener();

            // Attach the viewfinder's surface provider to preview use case
            mPreview.setSurfaceProvider(previewView.createSurfaceProvider());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private ArrayList<File> listImageCaptured;
    private File folderSaveFile;

    private File getFolderSaveFile() {
        if (folderSaveFile == null) {
            folderSaveFile = new File(Environment.getExternalStorageDirectory(), "AdapterInc");
            if (!folderSaveFile.exists())
                folderSaveFile.mkdirs();
        }
        return folderSaveFile;
    }

    private SimpleDateFormat dateFormat;

    private String getNewImgName() {
        try {
            if (dateFormat == null)
                dateFormat = new SimpleDateFormat(AppConstants.IMG_NAME_FORMAT);
            return dateFormat.format(new Date()) + ".jpg";
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() + ".jpg";
    }

    private void captureImg() {
        try {
            Log.e("captureImg", "->" + cameraSize);
            if (imgResult.isShown()) {
                imgResult.setImageBitmap(null);
                imgResult.setVisibility(View.GONE);
                return;
            }
            activity.showProgress(false);
            // Create output options object which contains file
            File file = new File(getFolderSaveFile(), getNewImgName());
            ImageCapture.OutputFileOptions outputOptions
                    = new ImageCapture.OutputFileOptions.Builder(file).build();

            // Setup image capture listener which is triggered after photo has been taken
            imageCapture.takePicture(outputOptions, cameraExecutor,
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            previewView.post(new Runnable() {
                                @Override
                                public void run() {
                                    handleImgCaptured(file);
                                }
                            });
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            if (activity != null && !activity.isFinishing())
                                activity.hideProgress();
                        }
                    });
        } catch (Throwable e) {
            e.printStackTrace();
            if (activity != null && !activity.isFinishing())
                activity.hideProgress();
        }
    }

    private void handleImgCaptured(File file) {
        try {
            if (file.exists()) {
                int rotate = 0;
                ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
                handleBmResult(BitmapFactory.decodeFile(file.getAbsolutePath()),
                        rotate, file, "imageCaptured");
            }
            activity.hideProgress();
        } catch (Throwable e) {
            e.printStackTrace();
            if (activity != null && !activity.isFinishing())
                activity.hideProgress();
        }
    }

    private void showViewResult() {
        imgResult.setVisibility(View.VISIBLE);
    }

    private void pickImageFromGallery() {
        try {
            activity.pickImageFromGallery(this);
        } catch (Throwable e) {
        }
    }

    private void rotateImageCrop(boolean isRotateLeft) {
        try {
            mCropImageView.rotateImage(isRotateLeft ? -90 : 90);
        } catch (Throwable e) {
        }
    }

    private void showHideCrop(Uri uri) {
        try {
            if (uri == null) {
                frameCrop.setVisibility(View.GONE);
                mCropImageView.clearImage();
            } else {
                frameCrop.setVisibility(View.VISIBLE);
                mCropImageView.setImageUriAsync(uri);
            }
        } catch (Throwable e) {
        }
    }

    private void cropImage() {
        try {
            handleBmResult(mCropImageView.getCroppedImage(), 0,
                    new File(getFolderSaveFile(), getNewImgName()), "CropImage");
            showHideCrop(null);
        } catch (Throwable e) {
        }
    }

    private void handleBmResult(Bitmap bitmap, int rotate, File fileSave, String tag) throws Throwable {
        int currentWidth = bitmap.getWidth();
        int currentHeight = bitmap.getHeight();
        if (AppConstants.LOG_DEBUG)
            Log.e(tag, "rotate:" + rotate + " - " + currentWidth + "x" + currentHeight);
        int sizeTarget = productEntity != null ? 512 : 1024;
        if (currentWidth != sizeTarget || rotate > 0) {
            Matrix matrix = new Matrix();
            if (currentWidth != sizeTarget)
                matrix.postScale((float) sizeTarget / (float) currentWidth,
                        (float) sizeTarget / (float) currentHeight);
            if (rotate > 0)
                matrix.postRotate(rotate);
            Bitmap bitmapScaled = Bitmap.createBitmap(bitmap, 0, 0,
                    currentWidth, currentHeight, matrix, true);
            bitmap.recycle();
            FileOutputStream out = new FileOutputStream(fileSave.getAbsolutePath());
            bitmapScaled.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            imgResult.setImageBitmap(bitmapScaled);
        } else {
            if (!fileSave.exists()) {
                FileOutputStream out = new FileOutputStream(fileSave.getAbsolutePath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
            imgResult.setImageBitmap(bitmap);
        }
//        imgResult.setVisibility(View.VISIBLE);
        if (listImageCaptured == null)
            listImageCaptured = new ArrayList<>();
        listImageCaptured.add(fileSave);
        try {
            MediaScannerConnection.scanFile(activity, new String[]{fileSave.getAbsolutePath()},
                    new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg")}, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        llBtnImgProduct.setVisibility(View.VISIBLE);

        if (countImgProductCaptured() == 5) {
            showViewResult();
        } else {
            showViewsCaptureProduct();
        }
    }

    private void uploadProductImage() {
        if (listImageCaptured == null || listImageCaptured.isEmpty()
                || !listImageCaptured.get(0).exists())
            return;
        if (listImageCaptured.size() > 2) {
            uploadProductImage(listImageCaptured);
            return;
        }
        activity.showProgress(false);
    }

    private void uploadProductImage(ArrayList<File> fileUpload) {
        try {
            activity.uploadProductImage(this, productEntity != null && productEntity.id != -1 ? productEntity.id + "" : null, fileUpload, "2", "1", false, true);
        } catch (Throwable e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onUploadedLabelImg(int id) {
        productEntity = new ProductEntity();
        productEntity.id = id;
        listImageCaptured = null;
        showViewsCaptureProduct();
    }

    @Override
    public void onCreatedProduct(ProductEntity product) {
        try {
            if (getArguments() != null && getArguments().containsKey(EXTRA_BACK_STACK)) {
                ArrayList<BackStackData> arrayList = (ArrayList<BackStackData>) getArguments().getSerializable(EXTRA_BACK_STACK);
                if (arrayList != null && !arrayList.isEmpty()) {
                    BackStackData data = arrayList.get(arrayList.size() - 1);
                    if (data.fromFragment == FRM_EDIT_PRODUCT) {
                        if (data.mapData == null)
                            data.mapData = new HashMap<>();
                        data.mapData.put(EXTRA_PRODUCT, product);
                        if (arrayList.size() >= 2) {
                            BackStackData dataInfoScreen = arrayList.get(arrayList.size() - 2);
                            if (dataInfoScreen.fromFragment == FRM_INFO_PRODUCT) {
                                if (dataInfoScreen.mapData == null)
                                    dataInfoScreen.mapData = new HashMap<>();
                                dataInfoScreen.mapData.put(EXTRA_PRODUCT, product.clone());
                            }
                        }
                    } else {
                        HashMap<String, Object> mapData = new HashMap<>();
                        mapData.put(EXTRA_PRODUCT, product);
                        arrayList.add(new BackStackData(FRM_EDIT_PRODUCT, mapData));
                    }
                    activity.backToPreviousFromBackStack(arrayList);
                    return;
                }
            }
            HashMap<String, Object> mapData = new HashMap<>();
            mapData.put(EXTRA_PRODUCT, product);
            activity.showEditProduct(null, mapData);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera_selector:
                setCameraSelector();
                break;
            case R.id.btn_light:
                setFlashMode();
                break;
            case R.id.btnCapture:
                forceStopAndClearImgTut(true);
                captureImg();
//                timerCaptureImg();
                break;
//            case R.id.btnSendProductImg:
//                uploadProductImage();
//                break;
//            case R.id.btnCancel:
//                backToPrevious();
//                break;
//            case R.id.btnSearch:
//                activity.showSearch(null, null);
//                break;
        }
    }

    private void initCameraListener() {
        LiveData<ZoomState> zoomState = mCameraInfo.getZoomState();
        previewView.setCustomTouchListener(new CameraXCustomPreviewView.CustomTouchListener() {
            @Override
            public void zoom(float delta) {
                float currentZoomRatio = zoomState.getValue().getZoomRatio();
                mCameraControl.setZoomRatio(currentZoomRatio * delta);
            }

            @Override
            public void doubleClick(float x, float y) {
                // 双击放大缩小
                float currentZoomRatio = zoomState.getValue().getZoomRatio();
                if (currentZoomRatio > zoomState.getValue().getMinZoomRatio()) {
                    mCameraControl.setLinearZoom(0f);
                } else {
                    mCameraControl.setLinearZoom(0.5f);
                }
            }

        });
    }

    private void setCameraSelector() {
        switch (mCameraSelectorInt) {
            case CameraSelector.LENS_FACING_BACK:
                mCameraSelectorInt = CameraSelector.LENS_FACING_FRONT;
                btnCameraSelector.setText("After");
                break;
            case CameraSelector.LENS_FACING_FRONT:
                mCameraSelectorInt = CameraSelector.LENS_FACING_BACK;
                btnCameraSelector.setText("Before");
                break;
        }
        changeCameraConfig(CHANGE_TYPE_SELECTOR);
    }

    private void changeCameraConfig(int changeType) {
        if (changeType == CHANGE_TYPE_SELECTOR) {
            bindCameraUseCases();
        }
    }

    private void setFlashMode() {
        switch (imageCapture.getFlashMode()) {
            case ImageCapture.FLASH_MODE_AUTO:
                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_ON);
                btnLight.setText("Flash: On");
                break;
            case ImageCapture.FLASH_MODE_ON:
                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_OFF);
                btnLight.setText("Flash: Off");
                break;
            case ImageCapture.FLASH_MODE_OFF:
                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_AUTO);
                btnLight.setText("Flash: Auto");
                break;
        }
    }

    private void timerCaptureImg() {
        new CountDownTimer(5000, 1000) {
            @Override
            public void onFinish() {
                forceStopAndClearImgTut(true);
                captureImg();
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }

    @Override
    public void onPickedImage(Uri uri) {
        showHideCrop(uri);
    }
}