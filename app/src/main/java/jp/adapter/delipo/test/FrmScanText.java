package jp.adapter.delipo.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jp.adapter.delipo.R;
import jp.adapter.delipo.constants.RequestConstants;
import jp.adapter.delipo.screen.fragment.BaseFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FrmScanText extends BaseFragment implements View.OnClickListener {

    private ImageView imageView;
    private TextView textReadCamera;
    private TextView textReadImg;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private ScrollView svTextReadImg;
    private ScrollView svTextReadCamera;
    private TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

    public static FrmScanText getInstance() {
        return new FrmScanText();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frm_scan_text;
    }

    @Override
    protected int getCurrentFragment() {
        return 0;
    }

    @Override
    protected void loadControlsAndResize(View view) {

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
        finish();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);
        Button btnScanImg = view.findViewById(R.id.btnScanImg);
        Button btnScan = view.findViewById(R.id.btnScan);
        textReadCamera = view.findViewById(R.id.textReadCamera);
        textReadImg = view.findViewById(R.id.textReadImg);
        surfaceView = view.findViewById(R.id.surfaceView);
        svTextReadImg = view.findViewById(R.id.svTextReadImg);
        svTextReadCamera = view.findViewById(R.id.svTextReadCamera);

        btnScanImg.setOnClickListener(this);
        btnScan.setOnClickListener(this);

    }

    @Override
    public void onPermissionsGranted() {
        startCameraSource();
    }

    private void startCameraSource() {
        //Create the TextRecognizer
        if (!textRecognizer.isOperational()) {
            Log.e(TAG, "Detector dependencies not loaded yet");
        } else {
            //Initialize camera source to use high resolution and set Auto focus on.
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1024, 1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RequestConstants.RC_PERMISSIONS);
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                /** Release resources for cameraSource */
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            //Set the TextRecognizer's Processor.
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                /** Detect all the text from camera using TextBlock and the values into a stringBuilder which will then be set to the textView. */
                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        textReadCamera.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                textReadCamera.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");
            } else {
                Log.e("Scan", "Scanned");
                textReadImg.setText(result.getContents());
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            //the case is because you might be handling multiple request codes here
            if (requestCode == 111) {
                if (data == null || data.getData() == null) {
                    Log.e("TAG", "The uri is null, probably the user cancelled the image selection process using the back button.");
                    return;
                }
                Uri uri = data.getData();
                launchMediaScanIntent(uri);
                try {
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(image);
                    Bitmap bitmap = decodeBitmapUri(getApplicationContext(), uri);
                    if (textRecognizer.isOperational() && bitmap != null) {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);
                        String blocks = "";
                        for (int index = 0; index < textBlocks.size(); index++) {
                            TextBlock tBlock = textBlocks.valueAt(index);
                            blocks = blocks + tBlock.getValue() + "\n" + "\n";
                        }
                        if (textBlocks.size() == 0) {
                            textReadImg.setText("Scan Failed: Found nothing to scan");
                        } else {
                            textReadImg.setText(textReadImg.getText() + blocks + "\n");
                        }
                    } else {
                        textReadImg.setText("Could not set up the detector!");
                    }
                } catch (FileNotFoundException e) {
                    Log.e("TAG", "can not open file" + uri.toString(), e);
                }
            }
        }
    }

    private void launchMediaScanIntent(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        getApplicationContext().sendBroadcast(mediaScanIntent);
    }

    private Bitmap decodeBitmapUri(Context context, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, bmOptions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScanImg:
                imageView.setVisibility(View.VISIBLE);
                svTextReadImg.setVisibility(View.VISIBLE);
                surfaceView.setVisibility(View.GONE);
                svTextReadCamera.setVisibility(View.GONE);
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, 111);
                break;
            case R.id.btnScan:
                if (!activity.isNeedPermissions())
                    startCameraSource();
                imageView.setVisibility(View.GONE);
                svTextReadImg.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
                svTextReadCamera.setVisibility(View.VISIBLE);
                break;
        }
    }
}
