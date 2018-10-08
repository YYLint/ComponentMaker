package com.yy.mobile.plugin.homepage.ui.home.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.duowan.mobile.plugin.homepage.multiline.MultiLineType;
import com.yy.mobile.lib2.Rs;
import com.yy.mobile.plugin.homeapi.ui.multiline.IMultiLinePresenter;
import com.yymobile.core.live.livedata.ILivingCoreConstant;
import com.yymobile.core.live.livedata.LineData;

/**
 * Created by azipage on 2018/1/20.
 */
@MultiLineType(type = ILivingCoreConstant.Live_MODULE_TOPICS_CODE, xml = Rs.layout.hp_item_living_horizontal_recyclerview, contentClass = LineData.class)
public class TopicsViewHolder extends HomeBaseViewHolder<LineData> {
    private RecyclerView rv;

    public TopicsViewHolder(View view, IMultiLinePresenter contentInfo) {
        super(view, contentInfo);
    }

    @Override
    public void onBindViewHolder(@NonNull LineData lineData) {

    }

    // public TopicsViewHolder(View itemView, IMultiLinePresenter callback) {
    //     super(itemView, callback);
    //     rv = (RecyclerView) itemView;
    //     rv.setBackgroundColor(Color.WHITE);
    //     ListView.LayoutParams lp = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, DimenConverter.dip2px(getContext(), 120));
    //     rv.setPadding(DimenConverter.dip2px(getContext(), 3), 0, 0, DimenConverter.dip2px(getContext(), 8));
    //     rv.setLayoutParams(lp);
    //     rv.setHasFixedSize(true);
    //     RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    //     rv.setLayoutManager(linearLayoutManager);
    //     final TopicItemAdapter topicItemAdapter = new TopicItemAdapter(getContext());
    //     rv.setAdapter(topicItemAdapter);
    // }
    //
    // @Override
    // public void onBindViewHolder(final @NonNull LineData lineData) {
    //     ArrayList<HomeItemInfo> topicDatas = (ArrayList<HomeItemInfo>) lineData.data;
    //     setContentStyle(lineData.contentStyle, rv);
    //     final TopicItemAdapter topicItemAdapter = (TopicItemAdapter) rv.getAdapter();
    //     topicItemAdapter.setData(topicDatas);
    //     topicItemAdapter.setNavInfo(getNavInfo(), getSubNavInfo(), lineData.id, getFrom());
    //     topicItemAdapter.notifyDataSetChanged();
    //     rv.clearOnScrollListeners();
    //     rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
    //         @Override
    //         public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    //             super.onScrolled(recyclerView, dx, dy);
    //         }
    //
    //         @Override
    //         public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    //             super.onScrollStateChanged(recyclerView, newState);
    //             if (newState == RecyclerView.SCROLL_STATE_IDLE) {
    //
    //                 if (recyclerView.getAdapter() != null) {
    //                     recyclerView.getAdapter().notifyDataSetChanged();
    //                 }
    //             }
    //         }
    //     });
    //
    //     //专题模块格子曝光上报
    //     for (HomeItemInfo homeItemInfo : topicDatas) {
    //         if (!TextUtils.isEmpty(homeItemInfo.adId)) {
    //             IHomePageDartsApi.getCore(IAdPosMonitorCore.class).reportTo3rd(homeItemInfo.adId, true, true, "mobile-topic");
    //         }
    //     }
    // }
    //
    // /**
    //  * 为专题模块、黄金栏目、话题模块设置背景或底色。
    //  *
    //  * @param contentStyleInfo
    //  * @param container
    //  */
    // private void setContentStyle(ContentStyleInfo contentStyleInfo, final View container) {
    //     if (contentStyleInfo == null) {
    //         return;
    //     }
    //     if (!TextUtils.isEmpty(contentStyleInfo.contentBgUrl)) {
    //         Glide.with(BasicConfig.getInstance().getAppContext())
    //                 .load(contentStyleInfo.contentBgUrl)
    //                 .into(new SimpleTarget<Drawable>() {
    //                     @Override
    //                     public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
    //                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    //                             container.setBackground(resource);
    //                         } else {
    //                             container.setBackgroundDrawable(resource);
    //                         }
    //                     }
    //                 });
    //
    //     } else if (ColorUtils.isRGB(contentStyleInfo.bgColor)) {
    //         container.setBackgroundColor(Color.parseColor(contentStyleInfo.bgColor));
    //     } else {
    //         container.setBackgroundColor(Color.parseColor("#FFFFFF"));
    //     }
    // }

}
