package com.lh.aliar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.alibaba.ailabs.ar.activity.ArActivity;
import com.alibaba.ailabs.ar.camera.CameraManager;
import com.alibaba.ailabs.ar.core.Session;
import com.alibaba.ailabs.ar.utils.ScreenUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * XTArActivity extends ArActivity.
 *
 * @author liuhe
 */
public class XTArActivity extends ArActivity {
    private static final String TAG = XTArActivity.class.getSimpleName();
AtomicBoolean
    /**
     * Toolbar for main activity.
     */
    private RelativeLayout toolbar = null;

    /**
     * Flash light button for toolbar.
     */
    private ImageButton flashBtn = null;

    /**
     * Backward button for toolbar.
     * 替换
     */
    private ImageButton backBtn = null;

    /**
     * Flash light is being turn on or turn off.
     */
    private boolean isFlashOn = false;

    /**
     * Is first login from checkFirstLoginApp.
     */
    private boolean isFirstLogin = false;

    static void start(Context context) {
        context.startActivity(new Intent(context, XTArActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Notify ar activity full screen is active.
        setFullScreenActive(true);

        // Notify window to full screen mode. 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ar activity on create.
        super.onCreate(savedInstanceState);

        // Init toolbar.
        initToolbar();

        // Init bubble bitmaps.
//        initBubbleBitmaps();
    }

    /**
     * Init toolbar for main activity.
     */
    private void initToolbar() {
        // Toolbar for main activity.
        toolbar = new RelativeLayout(this);

        // Add toolbar to main activity.
        TypedValue actionBarSize = new TypedValue();
        getTheme().resolveAttribute(R.attr.actionBarSize, actionBarSize, true);
        int left = (ScreenUtils.getScreenWidth(this) - (ScreenUtils.getScreenHeight(this) + ScreenUtils.getScreenWidth(this)) / 3) / 2;
        RelativeLayout.LayoutParams toolbarLayout = new RelativeLayout.LayoutParams((ScreenUtils.getScreenWidth(this) - 2 * left), (int) actionBarSize.getDimension(getResources().getDisplayMetrics()));
        toolbarLayout.topMargin = (int) (20 * ScreenUtils.getScreenDensity(this));
        toolbarLayout.leftMargin = left;

        // Toolbar top view for compatibility.
        RelativeLayout topView = new RelativeLayout(this);
        RelativeLayout.LayoutParams topViewLayout = new RelativeLayout.LayoutParams(ScreenUtils.getScreenWidth(this), (int) actionBarSize.getDimension(getResources().getDisplayMetrics()) + toolbarLayout.topMargin);
        this.addContentView(topView, topViewLayout);

        // Toolbar add to top view.
        topView.addView(toolbar, toolbarLayout);

        // Backward button.
        backBtn = new ImageButton(this);
        backBtn.setBackgroundResource(R.drawable.backward);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Backward button layout params.
        RelativeLayout.LayoutParams backlayout = new RelativeLayout.LayoutParams(24, 24);
        backlayout.width = (int) (24 * ScreenUtils.getScreenDensity(this));
        backlayout.height = (int) (24 * ScreenUtils.getScreenDensity(this));

        // Flash light button.
        flashBtn = new ImageButton(this);
        flashBtn.setBackgroundResource(R.drawable.light_on);
        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    showToast(R.string.app_no_flash, true);
                    return;
                }
                if (CameraManager.getInstance().onFlashStatusChanged(!isFlashOn)) {
                    flashBtn.setBackgroundResource(isFlashOn ? R.drawable.light_on : R.drawable.light_off);
                    isFlashOn = !isFlashOn;
                } else {
                    showToast(R.string.app_flash_failed, true);
                }
            }
        });

        // Flash light button layout params. 
        RelativeLayout.LayoutParams flashlayout = new RelativeLayout.LayoutParams(15, 20);
        flashlayout.width = (int) (15 * ScreenUtils.getScreenDensity(this));
        flashlayout.height = (int) (20 * ScreenUtils.getScreenDensity(this));
        flashlayout.leftMargin = (int) (toolbarLayout.width - 17 * ScreenUtils.getScreenDensity(this));

        // Add view to toolbar.
        toolbar.addView(backBtn, backlayout);
        toolbar.addView(flashBtn, flashlayout);
    }

    /**
     * Toolbar visibility should be set for ar sdk if there is toolbar exist.
     *
     * @param visibility View visibility.
     */
    @Override
    public void setToolbarVisibility(int visibility) {
        if (toolbar != null) {
            toolbar.setVisibility(visibility);
        }
    }

    /**
     * Load web view according to the actionUrl.
     *
     * @param title     web view title.
     * @param actionUrl web view url.
     */
    @Override
    public void loadWebView(String title, String actionUrl) {
        // Jump to your own web view through actionUrl.
        super.loadWebView(title, actionUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Do not start recognize until introductory button being clicked.
        if (isFirstLogin) {
            Session.getInstance().pauseTracking();
            isFirstLogin = false;
        }
    }

    private Bitmap[] bubbles = null;

//    private void initBubbleBitmaps() {
//        if (bubbles != null) {
//            Log.e(TAG, "initBubbleBitmaps: bubble is already exist");
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2;
//                TypedArray typedArray = getResources().obtainTypedArray(R.array.bubble);
//                int typedArrayLen = typedArray.length();
//                int maxCount = 12;
//                int len = typedArrayLen < maxCount ? typedArrayLen : maxCount;
//                bubbles = new Bitmap[len];
//                // Random index.
//                int index;
//                Random random = new Random();
//                Map<String, String> map = new HashMap<>();
//                // Load bitmaps.
//                for (int i = 0; i < len; i++) {
//                    index = random.nextInt(typedArrayLen * 10) % (typedArrayLen);
//                    while (map.containsKey(String.valueOf(index))) {
//                        index = random.nextInt(typedArrayLen * 10) % (typedArrayLen);
//                    }
//                    map.put(String.valueOf(index), String.valueOf(index));
//                    bubbles[i] = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(index, -1), options);
//                }
//                typedArray.recycle();
//            }
//        }).start();
//    }

    @Override
    protected Bitmap[] getBubbleBitmaps() {
        return bubbles;
    }

}
