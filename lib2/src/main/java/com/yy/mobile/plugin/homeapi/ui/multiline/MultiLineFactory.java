package com.yy.mobile.plugin.homeapi.ui.multiline;

/**
 * Class Name: HomeViewHolderFactoryEx
 * Description: 类功能说明
 * Author: 郑永欣
 * Date: 2017/11/15
 * Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)
 */

public class MultiLineFactory implements IViewBinderFactory {
    private static final String TAG = "HomeViewHolderFactoryEx";
    // private BaseViewHolderMapping mViewHolderMappingEx;
    //
    // private MultiLineFactory(BaseViewHolderMapping baseViewHolderMapping) {
    //     mViewHolderMappingEx = baseViewHolderMapping;
    // }

    // public static MultiLineFactory getInstance(BaseViewHolderMapping baseViewHolderMapping) {
    //     return new MultiLineFactory(baseViewHolderMapping);
    // }
    //
    // public ItemViewBinder[] getBinders() {
    //     return mViewHolderMappingEx.getBinders();
    // }

    @Override
    public BaseViewBinder createViewBinder(int viewType) {
        //BaseViewBinder subViewHolderFactory = (BaseViewBinder) mViewHolderMappingEx.getViewHolderInfo(viewType);
        return null;
    }

    @Override
    public int getPosition(int viewType) {
        //return mViewHolderMappingEx.getPosition(viewType);
        return 0;
    }
}
