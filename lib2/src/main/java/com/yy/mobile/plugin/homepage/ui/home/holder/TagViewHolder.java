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
@MultiLineType(type = ILivingCoreConstant.Live_MODULE_TAG_CODE, xml = Rs.layout.hp_item_living_horizontal_recyclerview, contentClass = LineData.class)
public class TagViewHolder extends HomeBaseViewHolder<LineData> {
    public TagViewHolder(View view, IMultiLinePresenter contentInfo) {
        super(view, contentInfo);
    }

    @Override
    public void onBindViewHolder(@NonNull LineData lineData) {

    }

    // RecyclerView tagView;
    // TagRecyclerViewAdapter tagAdapter;
    //
    // public TagViewHolder(View itemView, IMultiLinePresenter callback) {
    //     super(itemView, callback);
    //     tagView = (RecyclerView) itemView;
    //     AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
    //     tagView.setLayoutParams(layoutParams);
    //     tagView.setBackgroundColor(Color.WHITE);
    //     tagView.setHasFixedSize(true);
    //     tagView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    //     tagView.setPadding(0, DimenConverter.dip2px(getContext(), 10), 0, DimenConverter.dip2px(getContext(), 10));
    //     tagAdapter = new TagRecyclerViewAdapter(getContext());
    // }
    //
    // @Override
    // public void onBindViewHolder(final @NonNull LineData lineData) {
    //     List<HomeTagInfo> tagInfoList = (List<HomeTagInfo>) lineData.data;
    //     tagAdapter.setNavInfo(getNavInfo(), getSubNavInfo(), lineData.id, getFrom());
    //     tagAdapter.setData(tagInfoList);
    //     tagView.setAdapter(tagAdapter);
    //     if (!FP.empty(tagInfoList)) {
    //         tagView.setVisibility(View.VISIBLE);
    //         int left = DimenConverter.dip2px(getContext(), 4);
    //         int top = DimenConverter.dip2px(getContext(), 7);
    //         tagView.setPadding(left, top, left, top);
    //     } else {
    //         tagView.setVisibility(View.GONE);
    //     }
    // }
}
