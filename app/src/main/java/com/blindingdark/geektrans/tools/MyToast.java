package com.blindingdark.geektrans.tools;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public final class MyToast {

    private static final String TAG = MyToast.class.getName();

    public static interface OnShowListener {
        public void onShow(MyToast toast);
    }

    public static interface OnDismissListener {
        public void onDismiss(MyToast toast);
    }

    private static final int WIDTH_PADDING_IN_DIP = 25;
    private static final int HEIGHT_PADDING_IN_DIP = 15;
    private static final long DEFAULT_DURATION_MILLIS = 2000L;

    private final Context context;
    private final WindowManager windowManager;
    private View toastView;

    private int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private int mX;
    private int mY;
    private long duration = DEFAULT_DURATION_MILLIS;
    private CharSequence text = "";
    private int horizontalMargin;
    private int verticalMargin;
    private WindowManager.LayoutParams params;
    private Handler handler;
    private boolean isShowing;
    private boolean leadingInfinite;

    private OnShowListener onShowListener;
    private OnDismissListener onDismissListener;

    private final Runnable timer = new Runnable() {

        @Override
        public void run() {
            cancel();
        }
    };

    public MyToast(Context context) {
        Context mContext = context.getApplicationContext();
        if (mContext == null) {
            mContext = context;
        }
        this.context = mContext;
        windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        init();
    }

    private void init() {
        mY = context.getResources().getDisplayMetrics().widthPixels / 5;
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                //        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = android.graphics.PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("ToastHelper");
        params.alpha = 1.0f;
        // params.buttonBrightness = 1.0f;
        params.packageName = context.getPackageName();
        params.windowAnimations = android.R.style.Animation_Toast;
    }

    @SuppressWarnings("deprecation")
    @android.annotation.TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private View getDefaultToastView() {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        textView.setClickable(false);
        textView.setFocusable(false);
        textView.setFocusableInTouchMode(false);
        textView.setTextColor(android.graphics.Color.WHITE);
        // textView.setBackgroundColor(Color.BLACK);
        android.graphics.drawable.Drawable drawable = context.getResources()
                .getDrawable(android.R.drawable.toast_frame);
        if (Build.VERSION.SDK_INT < 16) {
            textView.setBackgroundDrawable(drawable);
        } else {
            textView.setBackground(drawable);
        }
        int wP = getPixFromDip(context, WIDTH_PADDING_IN_DIP);
        int hP = getPixFromDip(context, HEIGHT_PADDING_IN_DIP);
        textView.setPadding(wP, hP, wP, hP);
        return textView;
    }

    private static int getPixFromDip(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, context.getResources().getDisplayMetrics());
    }

    public void cancel() {
        removeView(true);
    }

    private void removeView(boolean invokeListener) {
        if (toastView != null && toastView.getParent() != null) {
            try {
                Log.i(TAG, "Cancelling Toast...");
                windowManager.removeView(toastView);
                handler.removeCallbacks(timer);
            } finally {
                isShowing = false;
                if (onDismissListener != null && invokeListener) {
                    onDismissListener.onDismiss(this);
                }
            }
        }
    }

    public void show() {
        if (leadingInfinite) {
            throw new InfiniteLoopException(
                    "Calling show() in OnShowListener leads to infinite loop.");
        }
        cancel();
        if (onShowListener != null) {
            leadingInfinite = true;
            onShowListener.onShow(this);
            leadingInfinite = false;
        }
        if (toastView == null) {
            toastView = getDefaultToastView();
        }
        params.gravity = android.support.v4.view.GravityCompat
                .getAbsoluteGravity(gravity, android.support.v4.view.ViewCompat
                        .getLayoutDirection(toastView));
        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            params.horizontalWeight = 1.0f;
        }
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            params.verticalWeight = 1.0f;
        }
        params.x = mX;
        params.y = mY;
        params.verticalMargin = verticalMargin;
        params.horizontalMargin = horizontalMargin;

        removeView(false);
        windowManager.addView(toastView, params);
        isShowing = true;
        if (handler == null) {
            handler = new Handler();
        }
        handler.postDelayed(timer, duration);
    }

    public boolean isShowing() {
        return isShowing;
    }

    public MyToast setDuration(long durationMillis) {
        this.duration = durationMillis;
        return this;
    }

    public MyToast setView(View view) {
        removeView(false);
        toastView = view;
        return this;
    }

    public MyToast setText(CharSequence text) {
        this.text = text;
        return this;
    }

    public void setText(int resId) {
        text = context.getString(resId);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.gravity = gravity;
        mX = xOffset;
        mY = yOffset;
    }

    public void setMargin(int horizontalMargin, int verticalMargin) {
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
    }

    public long getDuration() {
        return duration;
    }

    public int getGravity() {
        return gravity;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public int getXOffset() {
        return mX;
    }

    public int getYOffset() {
        return mY;
    }

    public View getView() {
        return toastView;
    }

    public void setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public static MyToast makeText(Context context, CharSequence text,
                                   long durationMillis) {
        MyToast helper = new MyToast(context);
        helper.setText(text);
        helper.setDuration(durationMillis);
        return helper;
    }

    public static MyToast makeText(Context context, int resId,
                                   long durationMillis) {
        String string = context.getString(resId);
        return makeText(context, string, durationMillis);
    }

    public static MyToast makeText(Context context, CharSequence text) {
        return makeText(context, text, DEFAULT_DURATION_MILLIS);
    }

    public static MyToast makeText(Context context, int resId) {
        return makeText(context, resId, DEFAULT_DURATION_MILLIS);
    }

    public static void showToast(Context context, CharSequence text) {
        makeText(context, text, DEFAULT_DURATION_MILLIS).show();
    }

    public static void showToast(Context context, int resId) {
        makeText(context, resId, DEFAULT_DURATION_MILLIS).show();
    }

    private static class InfiniteLoopException extends RuntimeException {
        private static final long serialVersionUID = 6176352792639864360L;

        private InfiniteLoopException(String msg) {
            super(msg);
        }
    }
}