package com.yy.mobile.plugin.homeapi.ui.multiline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by azipage on 2018/4/16.
 */

public abstract class BaseViewHolder<T, P extends IMultiLinePresenter> extends RecyclerView.ViewHolder {
    private P mPresenter;

    public BaseViewHolder(View view, P mPresenter) {
        super(view);
        this.mPresenter = mPresenter;
    }

    public P getMultiLinePresenter() {
        return mPresenter;
    }

    public abstract void onBindViewHolder(@NonNull T lineData);

    public void onViewAttachedToWindow() {

    }

    public void onViewDetachedFromWindow() {

    }

    public void onViewRecycled() {

    }
}
