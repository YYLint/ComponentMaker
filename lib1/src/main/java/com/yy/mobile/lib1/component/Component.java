package com.yy.mobile.lib1.component;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yy.mobile.ui.basicchanneltemplate.component.IComponent;

/**
 * Created by ericwu on 2018/5/4.
 */
public abstract class Component extends Fragment implements IComponent {
    private static final String TAG = "Component";

    public Component() {
        // lifecycle事件通知有需要的模块
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    // @Override
    // public IComponentContainer getParent() {
    //     return mParent;
    // }
    //
    // @Override
    // public void setParent(IComponentContainer parent) {
    //     mParent = parent;
    // }
    //
    // @Override
    // public IComponentRoot getRoot() {
    //     return mRoot;
    // }
    //
    // @Override
    // public void setTemplate(IComponentRoot template) {
    //     mRoot = template;
    // }
    //
    // @Override
    // public Fragment getContent() {
    //     return this;
    // }
    //
    // /**
    //  * 标识改组件是否是以懒加载的形式进行
    //  *
    //  * @return
    //  */
    // @Override
    // public boolean isInitHidden() {
    //     return mInitHidden;
    // }
    //
    // @Override
    // public void setInitHidden(boolean hidden) {
    //     mInitHidden = hidden;
    // }
    //
    // @Override
    // public void onConfigurationChanged(Configuration newConfig) {
    //     super.onConfigurationChanged(newConfig);
    //     if (mOrientation != newConfig.orientation) {
    //         mOrientation = newConfig.orientation;
    //         onOrientationChanged(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    //     }
    // }
    //
    // /**
    //  * 表示当前component是处于横屏or竖屏的工作模式下，不能完全代表手机系统现在是横屏还是竖屏显示。
    //  *
    //  * @return
    //  */
    // public boolean isLandScapeMode() {
    //     return mOrientation == Configuration.ORIENTATION_LANDSCAPE;
    // }
    //
    // @Override
    // public void onOrientationChanged(boolean isLandscape) {
    // }
    //
    // public void showSelf() {
    //     if (!getFragmentManager().isDestroyed()) {
    //         getFragmentManager().beginTransaction().show(this).commitAllowingStateLoss();
    //     }
    //
    //
    // }
    //
    // public void hideSelf() {
    //     if (!getFragmentManager().isDestroyed()) {
    //         getFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
    //     }
    // }
    //
    // /**
    //  * 获取当前组件fragment的尺寸
    //  *
    //  * @return
    //  */
    // public ComponentDimension getDimension() {
    //     if (dimension == null) {
    //         dimension = new ComponentDimension();
    //     }
    //     if (getView() != null) {
    //         dimension.x = getView().getX();
    //         dimension.y = getView().getY();
    //         dimension.width = getView().getWidth();
    //         dimension.height = getView().getHeight();
    //     }
    //     return dimension;
    // }
    //
    // /**
    //  * 當前組件是否創建了
    //  *
    //  * @return
    //  */
    // @Override
    // public boolean isComponentCreated() {
    //     return isComponentCreated;
    // }
    //
    // /**
    //  * 設置當前組件是否創建了
    //  *
    //  * @param componentCreated
    //  */
    // @Override
    // public void setComponentCreated(boolean componentCreated) {
    //     isComponentCreated = componentCreated;
    // }
    //
    // /**
    //  * 获取当前组件容器activity的上下文，此上下文为activity的一个hashcode，用于区分不同的页面用
    //  *
    //  * @return
    //  */
    // public int getActivityContext() {
    //     int context = 0;
    //     if (getContext() != null) {
    //         context = getContext().hashCode();
    //         MLog.info(TAG, "[getActivityContext] context== " + context);
    //     }
    //     return context;
    // }
    //
    // @Override
    // public void onDestroy() {
    //     setComponentCreated(false);
    //     mOrientation = Configuration.ORIENTATION_PORTRAIT;
    //     super.onDestroy();
    // }
    //
    // @Override
    // public boolean onBackPressed() {
    //     return BackHandlerHelper.handleBackPress(this) || onHandleBackPressed();
    // }
    //
    // protected boolean onHandleBackPressed() {
    //     return false;
    // }
    //
    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    //     return false;
    // }
}
