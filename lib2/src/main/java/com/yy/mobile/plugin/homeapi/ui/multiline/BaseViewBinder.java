package com.yy.mobile.plugin.homeapi.ui.multiline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yy.mobile.plugin.homepage.ui.home.holder.TopicsViewHolder;

/**
 * Created by azipage on 2018/1/22.
 */
public abstract class BaseViewBinder<T, VH extends RecyclerView.ViewHolder> {
    private IMultiLinePresenter mPresenter;

    public BaseViewBinder(IMultiLinePresenter contentInfo) {
        this.mPresenter = contentInfo;
    }

    public IMultiLinePresenter getMultiLinePresenter() {
        return mPresenter;
    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent);

    public abstract void onBindViewHolder(VH holder, T lineData);

    public void onViewAttachedToWindow(VH holder) {
    }

    public void onViewDetachedFromWindow(VH holder) {
    }

    public void onViewRecycled(VH holder) {
    }
}
