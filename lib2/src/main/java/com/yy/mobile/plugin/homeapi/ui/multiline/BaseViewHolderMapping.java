package com.yy.mobile.plugin.homeapi.ui.multiline;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: BaseViewHolderMapping
 * Description: 类功能说明
 * Author: 郑永欣
 * Date: 2017/11/16
 * Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)
 */

public abstract class BaseViewHolderMapping {

    private final SparseArray<BaseViewBinder> viewHolderMap = new SparseArray<>();

    public BaseViewHolderMapping(IMultiLinePresenter callback) {
        init(callback);
    }

    protected abstract void init(IMultiLinePresenter callback);

    protected void register(int viewType, BaseViewBinder viewHolderInfo) {
        viewHolderMap.put(viewType, viewHolderInfo);
    }

    public BaseViewBinder getViewHolderInfo(int viewType) {
        return viewHolderMap.get(viewType);
    }

    public int getPosition(int viewType) {
        return viewHolderMap.indexOfKey(viewType);
    }

    public BaseViewBinder[] getBinders() {
        if (viewHolderMap == null) {
            return null;
        }
        List<BaseViewBinder> arrayList = new ArrayList<>(viewHolderMap.size());
        for (int i = 0; i < viewHolderMap.size(); i++) {
            arrayList.add(viewHolderMap.valueAt(i));
        }
        return arrayList.toArray(new BaseViewBinder[viewHolderMap.size()]);
    }

    public void removeViewHolderInfo(int viewType) {
        viewHolderMap.remove(viewType);
    }
}
