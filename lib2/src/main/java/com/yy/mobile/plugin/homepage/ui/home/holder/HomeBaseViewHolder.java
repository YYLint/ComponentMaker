package com.yy.mobile.plugin.homepage.ui.home.holder;

import android.view.View;

/**
 * Created by azipage on 2018/1/20.
 */

public abstract class HomeBaseViewHolder<T> extends com.yy.mobile.plugin.homeapi.ui.multiline.BaseViewHolder<T, IHomeMultiLinePresenter> {

    private static final String TAG = "HomeBaseViewHolder";

    public HomeBaseViewHolder(View view, com.yy.mobile.plugin.homeapi.ui.multiline.IMultiLinePresenter contentInfo) {
        super(view, (IHomeMultiLinePresenter) contentInfo);
    }

    private MultiLineContentInfo getContentInfo() {
        return getMultiLinePresenter().getMultiLineContentInfo();
    }

    // public Context getContext() {
    //     return getContentInfo().getContext();
    // }
    //
    // public LiveNavInfo getNavInfo() {
    //     return getContentInfo().getNavInfo();
    // }
    //
    // public String getPageId() {
    //     return getContentInfo().getPageId();
    // }
    //
    // public int getPageSubIndex() {
    //     return getContentInfo().getPagerSubIndex();
    // }
    //
    // public int getPositionInParent() {
    //     return getContentInfo().getPositionInParent();
    // }
    //
    // public String getFrom() {
    //     return getContentInfo().getFrom();
    // }
    //
    // public int getPos() {
    //     return getContentInfo().getPosition();
    // }
    //
    // public SubLiveNavItem getSubNavInfo() {
    //     return getContentInfo().getSubNavInfo();
    // }
    //
    // public IDataChange getDataChange() {
    //     return getContentInfo().getDataChange();
    // }
    //
    // public abstract void onBindViewHolder(@NonNull T lineData);
    //
    // public boolean isCurrentPager() {
    //     boolean isCurrentPager = false;
    //     if (getNavInfo() != null && !StringUtils.isEmpty(getNavInfo().biz)) {
    //         boolean isMainActivityAndCurrentPager = CoreLinkConstants.FRAGMENT_HOME.equals(getFrom()) // 是否为首页
    //                 && getNavInfo().biz.equals(IHomePageDartsApi.getCore(IHomepageLiveCore.class).getCurNavBiz())
    //                 && (IHomePageDartsApi.getCore(IHomepageLiveCore.class).getCurPos() == getPageSubIndex()
    //                 || IHomePageDartsApi.getCore(IHomepageLiveCore.class).getSubNavSelected(getNavInfo().biz) == getPageSubIndex());
    //         boolean isSubActivityAndCurrentPager = CoreLinkConstants.FRAGMENT_SUB_HOME.equals(getFrom())// 是否为SubNavHome页
    //                 && IHomePageDartsApi.getCore(IHomeCore.class).getSubActivityIndex() == getPageSubIndex();
    //         boolean isLivingSubFragment = CoreLinkConstants.FRAGMENT_SUB_NAV.equals(getFrom()) // 是否为有二级导航的每个子tab
    //                 && IHomePageDartsApi.getCore(IHomepageLiveCore.class).getSubNavSelected(getNavInfo().biz) == getPageSubIndex()
    //                 && IHomePageDartsApi.getCore(IHomepageLiveCore.class).getCurNavBiz().equals(getNavInfo().biz);
    //         boolean isLabelFragment = CoreLinkConstants.FRAGMENT_LABEL.equals(getFrom()); // 是否为二级标签页
    //         boolean isAloneLivingSubNavFragment = CoreLinkConstants.FRAGMENT_SUB_NAV_ALONE.equals(getFrom());
    //         boolean isMorePagerFragment = CoreLinkConstants.FRAGMENT_MORE.equals(getFrom());
    //         boolean isNewNavPageFragment = CoreLinkConstants.FRAGMENT_NAV_PAGE.equals(getFrom())
    //                 && getNavInfo().biz.equals(IHomePageDartsApi.getCore(IHomepageLiveCore.class).getCurNavBiz());
    //         isCurrentPager = isMainActivityAndCurrentPager || isSubActivityAndCurrentPager || isLivingSubFragment
    //                 || isLabelFragment || isAloneLivingSubNavFragment || isMorePagerFragment || isNewNavPageFragment;
    //     }
    //     MLog.debug(TAG, "[isCurrentPager] pageId = " + getPageId() + ", isCurrentPager = " + isCurrentPager);
    //     return isCurrentPager;
    // }

}

