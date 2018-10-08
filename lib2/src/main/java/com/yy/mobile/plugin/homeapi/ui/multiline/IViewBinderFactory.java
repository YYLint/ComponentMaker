package com.yy.mobile.plugin.homeapi.ui.multiline;

/**
 * Class Name: IViewHolderFactory
 * Description: 类功能说明
 * Author: 郑永欣
 * Date: 2017/10/12
 * Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)
 */

public interface IViewBinderFactory {
    BaseViewBinder createViewBinder(int viewType);

    int getPosition(int viewType);
}
