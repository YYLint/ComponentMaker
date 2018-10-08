package com.yy.mobile.plugin.homepage.ui.home.holder;

import android.support.annotation.NonNull;
import android.view.View;

import com.duowan.mobile.plugin.homepage.multiline.MultiLineType;
import com.yy.mobile.lib2.Rs;
import com.yy.mobile.plugin.homeapi.ui.multiline.IMultiLinePresenter;
import com.yymobile.core.live.livedata.ILivingCoreConstant;
import com.yymobile.core.live.livedata.LineData;

/**
 * Created by azipage on 2018/1/20.
 */
@MultiLineType(type = ILivingCoreConstant.Live_MODULE_COMMON_TITLE_CODE, xml = Rs.layout.hp_item_living_module_title, contentClass = LineData.class)
public class TitleViewHolder extends HomeBaseViewHolder<LineData> {
    public TitleViewHolder(View view, IMultiLinePresenter contentInfo) {
        super(view, contentInfo);
    }

    @Override
    public void onBindViewHolder(@NonNull LineData lineData) {

    }
    // ImageView decoration;
    // TextView title;
    // //[bug id ANDROIDYY-21048]【萌宠小视频模块】部分手机没有显示模块的">"按钮review by [zhangyu4]
    // ImageView more;
    // View line;
    // View container;
    //
    // public TitleViewHolder(View itemView, IMultiLinePresenter callback) {
    //     super(itemView, callback);
    //
    //     decoration = (ImageView) itemView.findViewById(R.id.live_title_decoration);
    //     title = (TextView) itemView.findViewById(R.id.txt_living_title);
    //     more = (ImageView) itemView.findViewById(R.id.img_living_more);
    //     line = itemView.findViewById(R.id.living_module_seperator);
    //     container = itemView.findViewById(R.id.rl_living_title_container);
    // }
    //
    // @Override
    // public void onBindViewHolder(final @NonNull LineData lineData) {
    //     final CommonTitleInfo info = (CommonTitleInfo) lineData.data;
    //     title.setText(info.name);
    //     TextPaint tp = title.getPaint();
    //     tp.setFakeBoldText(true);
    //
    //     if (!FP.empty(info.url)) { //是否有点击跳转逻辑
    //         container.setEnabled(true);
    //         container.setBackgroundResource(R.drawable.hp_bg_living_title_selector);
    //         if (info.url.equals(LivingClientConstant.LIVE_MORE)) {
    //             container.setOnClickListener(new View.OnClickListener() {
    //                 @Override
    //                 public void onClick(View view) {
    //                     NavigationUtils.toLivingMorePage((Activity) getContext(), info.name, getNavInfo(), getSubNavInfo(), info.id);
    //                     VHolderHiidoReportUtil.INSTANCE.sendStatisticForTitle(
    //                             new VHolderHiidoInfo.Builder(getNavInfo(), getSubNavInfo(), getFrom(),
    //                                     info.type, info.id)
    //                                     .title(info.name)
    //                                     .create()
    //                     );
    //                 }
    //
    //             });
    //         } else {
    //             container.setOnClickListener(new View.OnClickListener() {
    //                 @Override
    //                 public void onClick(View view) {
    //                     ARouter.getInstance().build(Uri.parse(info.url)).navigation(getContext());
    //                     int mType;
    //                     if (info.type == ILivingCoreConstant.Live_MODULE_PREDICTION_CODE) {
    //                         mType = ILivingCoreConstant.Live_MODULE_PREDICTION_CODE;
    //                     } else {
    //                         mType = info.type;
    //                     }
    //                     VHolderHiidoReportUtil.INSTANCE.sendStatisticForTitle(
    //                             new VHolderHiidoInfo.Builder(getNavInfo(), getSubNavInfo(), getFrom(),
    //                                     mType, info.id)
    //                                     .title(info.name)
    //                                     .create()
    //                     );
    //                 }
    //             });
    //         }
    //         more.setVisibility(View.VISIBLE);
    //         if (more.getDrawable() == null) {
    //             more.setImageResource(R.drawable.hp_icon_live_arrow_more);
    //         }
    //     } else {
    //         container.setEnabled(false);
    //         container.setBackgroundResource(R.color.color_white);
    //         more.setVisibility(View.GONE);
    //     }
    //
    //     //设置背景颜色, 需要在点击跳转逻辑后面
    //     setTitleStyle(this, info.titleStyle);
    //
    //     //话题模块对应标题模块与其它模块差异处理
    //     if (info.type == ILivingCoreConstant.Live_MODULE_TALK_CODE) {
    //         decoration.setVisibility(View.GONE);
    //         title.setPadding(ScreenUtil.getInstance().dip2px(5), 0, 0, 0);
    //     } else {
    //         decoration.setVisibility(View.VISIBLE);
    //         title.setPadding((int) getContext().getResources().getDimension(R.dimen.txt_living_title_margin), 0, 0, 0);
    //     }
    // }
    //
    // /**
    //  * 设置标题模块的背景和字体颜色
    //  *
    //  * @param mHolder
    //  * @param titleStyle
    //  */
    // private void setTitleStyle(final TitleViewHolder mHolder, CommonTitleInfo.TitleStyle titleStyle) {
    //     if (titleStyle == null) {
    //         mHolder.title.setTextColor(getContext().getResources().getColor(R.color.color_black));
    //         return;
    //     }
    //     // 设置背景颜色
    //     if (!TextUtils.isEmpty(titleStyle.nameBgUrl) || !TextUtils.isEmpty(titleStyle.bgColor)) {
    //         mHolder.title.setBackgroundColor(getContext().getResources().getColor(R.color.home_transparent_color));
    //         if (!TextUtils.isEmpty(titleStyle.nameBgUrl)) {
    //             Glide.with(BasicConfig.getInstance().getAppContext())
    //                     .load(titleStyle.nameBgUrl)
    //                     .into(new SimpleTarget<Drawable>() {
    //                         @Override
    //                         public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
    //                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    //                                 mHolder.container.setBackground(resource);
    //                             } else {
    //                                 mHolder.container.setBackgroundDrawable(resource);
    //                             }
    //                         }
    //                     });
    //         } else if (ColorUtils.isRGB(titleStyle.bgColor)) {
    //             mHolder.container.setBackgroundColor(Color.parseColor(titleStyle.bgColor));
    //         }
    //     }
    //     // 设置字体颜色
    //     if (!TextUtils.isEmpty(titleStyle.textColor) && ColorUtils.isRGB(titleStyle.textColor)) {
    //         mHolder.title.setTextColor(Color.parseColor(titleStyle.textColor));
    //     } else {
    //         mHolder.title.setTextColor(Color.parseColor("#000000"));
    //     }
    // }
}
