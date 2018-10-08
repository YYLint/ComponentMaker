package com.yy.mobile.plugin.homepage.ui.home.holder;

import android.support.v7.widget.RecyclerView;

/**
 * Created by azipage on 2018/1/22.
 */

public abstract class HomeBaseViewBinder<T, VH extends RecyclerView.ViewHolder> extends com.yy.mobile.plugin.homeapi.ui.multiline.BaseViewBinder<T, VH> {

    public HomeBaseViewBinder(IHomeMultiLinePresenter contentInfo) {
        super(contentInfo);
    }


    // private MultiLineContentInfo getContentInfo() {
    //     return ((IHomeMultiLinePresenter) getMultiLinePresenter()).getMultiLineContentInfo();
    // }
    //
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
    // public int getPageIndex() {
    //     return getContentInfo().getPagerSubIndex();
    // }
    //
    // public String getFrom() {
    //     return getContentInfo().getFrom();
    // }
    //
    // public int getPosition() {
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
}

