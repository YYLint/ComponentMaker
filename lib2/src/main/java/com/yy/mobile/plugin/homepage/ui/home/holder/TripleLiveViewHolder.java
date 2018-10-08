package com.yy.mobile.plugin.homepage.ui.home.holder;

import android.support.annotation.NonNull;
import android.view.View;

import com.duowan.mobile.plugin.homepage.multiline.MultiLineType;
import com.yy.mobile.lib2.Rs;
import com.yy.mobile.plugin.homeapi.ui.multiline.IMultiLinePresenter;
import com.yymobile.core.live.livedata.ILivingCoreConstant;
import com.yymobile.core.live.livedata.LineData;

/**
 * Class Name: TripleLiveViewHolder
 * Description: 一列三行直播数据
 * Author: 郑永欣
 * Date: 2017/9/15
 * Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)
 */
@MultiLineType(type = ILivingCoreConstant.Live_MODULE_TRIPLE_LIVE_CODE, xml = Rs.layout.hp_item_living_triple_live_style, contentClass = LineData.class)
public class TripleLiveViewHolder extends HomeBaseViewHolder<LineData> {
    private static final String TAG = "TripleLiveViewHolder";

    public TripleLiveViewHolder(View view, IMultiLinePresenter contentInfo) {
        super(view, contentInfo);
    }

    @Override
    public void onBindViewHolder(@NonNull LineData lineData) {

    }

    // RelativeLayout left;
    // YYView dividerLeft;
    // RelativeLayout medium;
    // YYView dividerRight;
    // RelativeLayout right;
    // YYLinearLayout container;
    //
    // private TripleSingleLiveVHolder leftSingleVHolder;
    // private TripleSingleLiveVHolder rightSingleVHolder;
    // private TripleSingleLiveVHolder mediumSingleVHolder;
    //
    // public TripleLiveViewHolder(View itemView, IMultiLinePresenter callback) {
    //     super(itemView, callback);
    //
    //     left = (RelativeLayout) itemView.findViewById(R.id.triple_container_left);
    //     dividerLeft = (YYView) itemView.findViewById(R.id.triple_divider_left);
    //     dividerRight = (YYView) itemView.findViewById(R.id.triple_divider_right);
    //     medium = (RelativeLayout) itemView.findViewById(R.id.triple_container_medium);
    //     right = (RelativeLayout) itemView.findViewById(R.id.triple_container_right);
    //     container = (YYLinearLayout) itemView.findViewById(R.id.ll_living_triple_live_container);
    // }
    //
    // @Override
    // public void onBindViewHolder(final @NonNull LineData lineData) {
    //     TripleItemInfo tripleInfo = (TripleItemInfo) lineData.data;
    //     initTripleLiveVHolder();
    //     bindTripleLiveVHolder(tripleInfo);
    // }
    //
    // private void initTripleLiveVHolder() {
    //     leftSingleVHolder = initSingleTripleLiveVHolder(left);
    //     mediumSingleVHolder = initSingleTripleLiveVHolder(medium);
    //     rightSingleVHolder = initSingleTripleLiveVHolder(right);
    // }
    //
    // protected TripleSingleLiveVHolder initSingleTripleLiveVHolder(ViewGroup root) {
    //     root.removeAllViews();
    //     View viewRoot = LayoutInflater.from(getContext()).inflate(R.layout.hp_item_living_triple_live_single, root, true);
    //     TripleSingleLiveVHolder viewHolder = new TripleSingleLiveVHolder(viewRoot);
    //     return viewHolder;
    // }
    //
    // private void bindTripleLiveVHolder(TripleItemInfo data) {
    //     if (data != null) {
    //         setDiscoveryLiveItemLive(leftSingleVHolder, data.left);
    //         setDiscoveryLiveItemLive(mediumSingleVHolder, data.medium);
    //         setDiscoveryLiveItemLive(rightSingleVHolder, data.right);
    //     }
    // }
    //
    // private void setDiscoveryLiveItemLive(TripleSingleLiveVHolder holder, final HomeItemInfo item) {
    //     //[bug id ANDROIDYY-19820]
    //     // 【提交类型】：陪伴中页面数据不满一行时未显示数据
    //     // 【修改内容】：更多页取消一定要满一行的逻辑
    //     // 【影响范围】：更多页
    //     if (TextUtils.isEmpty(item.thumb) && TextUtils.isEmpty(item.thumb2) && TextUtils.isEmpty(item.desc)) {
    //         holder.container.setVisibility(View.INVISIBLE);
    //         return;
    //     }
    //     ImageLoader.loadImage(item.thumb2, holder.thumb, ImageConfig.defaultImageConfig(), R.drawable.hp_living_default_bg);
    //     holder.liveDesc.setText(item.desc);
    //     holder.liveDesc.setOnClickListener(new View.OnClickListener() {
    //         @Override
    //         public void onClick(View view) {
    //             doOnClick(item);
    //         }
    //     });
    //     holder.thumb.setOnClickListener(new View.OnClickListener() {
    //         @Override
    //         public void onClick(View view) {
    //             doOnClick(item);
    //         }
    //     });
    //     setLinkMicIcon(holder, item);
    //     if (item.vr == 1) {
    //         holder.vrIcon.setVisibility(View.VISIBLE);
    //     } else {
    //         holder.vrIcon.setVisibility(View.GONE);
    //     }
    //     if (item.arGame == 1) {
    //         holder.arIcon.setVisibility(View.VISIBLE);
    //     } else {
    //         holder.arIcon.setVisibility(View.GONE);
    //     }
    //     holder.everSeen.setText(LivingClientConstant.formatCount(item.users));
    //     LivingClientConstant.setFontTypeForPeople(getContext(), holder.everSeen);
    // }
    //
    // /**
    //  * 设置连麦标识，一行双视频和一行三视频公用
    //  *
    //  * @param item
    //  */
    // private void setLinkMicIcon(TripleSingleLiveVHolder holder, HomeItemInfo item) {
    //     switch (item.linkMic) {
    //         case 0:
    //             holder.linkMicIcon.setVisibility(View.GONE);
    //             break;
    //         case 1:
    //             holder.linkMicIcon.setVisibility(View.VISIBLE);
    //             holder.linkMicIcon.setImageResource(R.drawable.hp_live_link_mic_icon);
    //             break;
    //         case 2:
    //             holder.linkMicIcon.setVisibility(View.VISIBLE);
    //             holder.linkMicIcon.setImageResource(R.drawable.hp_live_link_mic_mini_icon_se);
    //             break;
    //         case 3:
    //             holder.linkMicIcon.setVisibility(View.VISIBLE);
    //             if (holder.linkMicIcon.getDrawable() == null) {
    //                 holder.linkMicIcon.setImageResource(R.drawable.hp_live_link_mul_mini_icon_se);
    //             }
    //             break;
    //         default:
    //             holder.linkMicIcon.setVisibility(View.GONE);
    //             break;
    //     }
    // }
    //
    // private void doOnClick(HomeItemInfo item) {
    //     MLog.info(TAG, "onClick with " + item.toString());
    //     LiveModuleData moduleData = IHomePageDartsApi.getCore(IHomepageLiveCore.class).getModuleData(getPageId(), item.moduleId);
    //     List<SlipChannelInfo> mLiveData = moduleData != null ? moduleData.liveData : null;
    //     ChannelSlipUtils.addSlip((Activity) getContext(), item, mLiveData, getNavInfo(), getSubNavInfo(), getPageId());
    //     //关连进频道的上下文token 如果服务器没有返回token字段，通过JoinChannelTokenUtil生成后统计事情带上
    //     item.token = JoinChannelTokenUtil.createOrSetJoinChannelToken(item.token);
    //     ChannelUtils.joinChannel(getContext(),  new HomeToLiveInfo
    //             .Builder(item.sid, item.ssid)
    //             .recomed(item.recommend)
    //             .token(item.token)
    //             .desc(item.desc)
    //             .from(LiveTemplateConstant.SRC_HOME)
    //             .tpl(item.tpl)
    //             .anchorUid(item.uid)
    //             .liveType(item.type)
    //             .biz(getNavInfo().getBiz())
    //             .streamInfo(item.streamInfo)
    //             .create());
    //     sendStatistic(item);
    //     VHolderHiidoReportUtil.INSTANCE.sendVHolderClick(
    //             new VHolderHiidoInfo.Builder(getNavInfo(), getSubNavInfo(), getFrom(),
    //                     ILivingCoreConstant.Live_MODULE_TRIPLE_LIVE_CODE, item.moduleId)
    //                     .contentId(item.id)
    //                     .position(item.pos)
    //                     .sid(item.sid)
    //                     .ssid(item.ssid)
    //                     .uid(item.uid)
    //                     .token(String.valueOf(item.recommend))
    //                     .contentType(item.type)
    //                     .create()
    //     );
    // }
    //
    // private void sendStatistic(HomeItemInfo item) {
    //     NavigationUtils.sendStatisticByILivePluginCore((Activity) getContext(), item);
    // }
    //
    //
    // public static class TripleSingleLiveVHolder {
    //     View container;
    //     PressedRecycleImageView thumb;
    //     View scrim;
    //     YYTextView everSeen;
    //     RecycleImageView linkMicIcon;
    //     YYImageView vrIcon;
    //     YYImageView arIcon;
    //     YYTextView liveDesc;
    //
    //     public TripleSingleLiveVHolder(View itemView) {
    //         container = itemView;
    //         thumb = (PressedRecycleImageView) itemView.findViewById(R.id.triple_thumb);
    //         scrim = itemView.findViewById(R.id.triple_near_by_live_site);
    //         everSeen = (YYTextView) itemView.findViewById(R.id.triple_ever_seen);
    //         linkMicIcon = (RecycleImageView) itemView.findViewById(R.id.live_linkMic_img);
    //         vrIcon = (YYImageView) itemView.findViewById(R.id.live_vr_img);
    //         arIcon = (YYImageView) itemView.findViewById(R.id.live_ar_img);
    //         liveDesc = (YYTextView) itemView.findViewById(R.id.triple_desc);
    //         scrim.setVisibility(View.GONE);
    //         thumb.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, CoverHeightConfigUtils.getInstance().getTripleHeight()));
    //     }
    // }
}
