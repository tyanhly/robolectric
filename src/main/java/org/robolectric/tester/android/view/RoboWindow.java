package org.robolectric.tester.android.view;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import static org.fest.reflect.core.Reflection.*;

public class RoboWindow extends Window {
    public int requestedFeatureId;
    public int featureDrawableResourceFeatureId;
    public int featureDrawableResourceResId;
    public int softInputMode;
    private RoboWindowManager windowManager;
    private View contentView;
    private FrameLayout decorView;
    private ViewParent viewRootImpl;

    public RoboWindow(Context context) {
        super(context);
        windowManager = new RoboWindowManager();
    }

    @Override
    public boolean requestFeature(int featureId) {
        this.requestedFeatureId = featureId;
        return true;
    }

    @Override
    public void addFlags(int flags) {
        setFlags(flags, flags);
    }

    @Override
    public WindowManager getWindowManager() {
        return windowManager;
    }

    @Override
    public boolean isFloating() {
        return false;
    }

    @Override
    public void takeSurface(SurfaceHolder.Callback2 callback2) {
    }

    @Override
    public void takeInputQueue(InputQueue.Callback callback) {
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, getDecorView()));
    }

    @Override 
    public void setContentView(View view) {
        ViewGroup decorView = getDecorView();
        if (contentView != null) {
            decorView.removeView(contentView);
        }
        contentView = view;

        if (contentView != null) {
            if (contentView.getParent() != decorView && contentView != decorView) {
                decorView.addView(contentView);
            }
        }
    }

    @Override 
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setContentView(view);
    }

    @Override 
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        setContentView(view);
    }

    @Override
    public View getCurrentFocus() {
        return null;
    }

    @Override 
    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    @Override
    public void setTitle(CharSequence title) {
    }

    @Override
    public void setTitleColor(int textColor) {
    }

    @Override
    public void openPanel(int featureId, KeyEvent event) {
    }

    @Override
    public void closePanel(int featureId) {
    }

    @Override
    public void togglePanel(int featureId, KeyEvent event) {
    }

    @Override
    public boolean performPanelShortcut(int featureId, int keyCode, KeyEvent event, int flags) {
        return false;
    }

    @Override
    public boolean performPanelIdentifierAction(int featureId, int id, int flags) {
        return false;
    }

    @Override
    public void closeAllPanels() {
    }

    @Override
    public boolean performContextMenuIdentifierAction(int id, int flags) {
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
    }

    @Override
    public void setFeatureDrawableResource(int featureId, int resId) {
        featureDrawableResourceFeatureId = featureId;
        featureDrawableResourceResId = resId;
    }

    @Override
    public void setFeatureDrawableUri(int featureId, Uri uri) {
    }

    @Override
    public void setFeatureDrawable(int featureId, Drawable drawable) {
    }

    @Override
    public void setFeatureDrawableAlpha(int featureId, int alpha) {
    }

    @Override
    public void setFeatureInt(int featureId, int value) {
    }

    @Override
    public void takeKeyEvents(boolean get) {
    }

    @Override
    public boolean superDispatchKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchTrackballEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override public ViewGroup getDecorView() {
        if (decorView == null) {
            decorView = new FrameLayout(getContext()) {
                @Override public void requestLayout() {
                    System.out.println("request window layout!");
                    super.requestLayout();
                }
            };
            // On a typical Android device you can call:
            //   myWindow.getDecorView().findViewById(android.R.content)
            decorView.setId(R.id.content);

            viewRootImpl = createViewRootImpl(getContext());
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            field("inputFeatures").ofType(int.class).in(layoutParams)
                    .set(field("INPUT_FEATURE_NO_INPUT_CHANNEL").ofType(int.class).in(WindowManager.LayoutParams.class).get());
            method("setView").withParameterTypes(View.class, WindowManager.LayoutParams.class, View.class)
                    .in(viewRootImpl).invoke(decorView, layoutParams, null);
        }
        return decorView;
    }

    private ViewParent createViewRootImpl(Context context) {
        Class<? extends ViewParent> viewRootImplClass = type("android.view.ViewRootImpl").withClassLoader(View.class.getClassLoader()).loadAs(ViewParent.class);
        return constructor().withParameterTypes(Context.class).in(viewRootImplClass).newInstance(context);
    }

    @Override
    public View peekDecorView() {
        return null;
    }

    @Override
    public View findViewById(int id) {
        return getDecorView().findViewById(id);
    }

    @Override 
    public Bundle saveHierarchyState() {
        return null;
    }

    @Override
    public void restoreHierarchyState(Bundle savedInstanceState) {
    }

    @Override
    protected void onActive() {
    }

    @Override
    public void setChildDrawable(int featureId, Drawable drawable) {
    }

    @Override
    public void setChildInt(int featureId, int value) {
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void setVolumeControlStream(int streamType) {
    }

    @Override
    public int getVolumeControlStream() {
        return 0;
    }

    @Override
    public void setSoftInputMode(int softInputMode) {
        this.softInputMode = softInputMode;
    }

    @Override
    public void invalidatePanelMenu(int featureId) {
    }

    @Override
    public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return false;
    }

    public void alwaysReadCloseOnTouchAttr() {
    }
}