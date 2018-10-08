package com.yymobile.core.live.livedata;

/**
 * Created by ruoshili on 12/3/2016.
 * 首页模块type和杂七杂八
 */
public interface ILivingCoreConstant {
    //首页模块类型ID
    /**
     * title
     */
    int Live_MODULE_COMMON_TITLE_CODE = 101;
    /**
     * 通栏置顶（大图模块）
     *
     * @deprecated 首页废弃模块
     */
    int Live_MODULE_COLUMN_TOP_CODE = 103;
    /**
     * 品牌专区头部
     */
    int Live_MODULE_BRAND_TITLE_CODE = 104;
    /**
     * 发现附近tab定位提示
     */
    int Live_MODULE_NEAR_LOCATE_TIPS_CODE = 105;
    /**
     * 标签页头部, 废弃模块
     */
    int Live = 106;
    /**
     * 模块间间隔
     */
    int Live_MODULE_MODULE_MARGIN_CODE = 108;
    /**
     * 尾页底部显示没有更多
     */
    int Live_MODULE_NO_MORE_CODE = 109;
    /**
     * 大顶Banner
     */
    int Live_MODULE_BIG_BANNER_CODE = 1001;
    /**
     * 预告
     */
    int Live_MODULE_PREDICTION_CODE = 1002;
    /**
     * 专题
     */
    int Live_MODULE_TOPICS_CODE = 1003;
    /**
     * 通栏
     * a
     */
    int Live_MODULE_COLUMN_CODE = 1004;
    /**
     * 普通内容模块
     * a
     */
    int Live_MODULE_CONTENT_CODE = 1005;
    /**
     * 普通Banner
     * a
     */
    int Live_MODULE_BANNER_CODE = 1006;
    /**
     * 新通栏，轮播banner
     * a
     */
    int Live_MODULE_BANNER_COLUMN_CODE = 2021;
    /**
     * 明星现场
     */
    int Live_MODULE_STAR_CONTENT_CODE = 1007;
    /**
     * 品牌专区
     */
    int Live_MODULE_BRAND_CONTENT_CODE = 1008;
    /**
     * 主播排行榜
     */
    int Live_MODULE_ANCHOR_RANKLIST_CODE = 1009;
    /**
     * 内容排行榜
     */
    int Live_MODULE_CONTENT_RANKLIST_CODE = 1010;
    /**
     * 标签模块
     */
    int Live_MODULE_TAG_CODE = 1011;
    /**
     * 导航栏图标，废弃模块
     */
    int Live_MODULE_NAV_ICON_CODE = 1111;
    /**
     * 发现Tab新增，一行三个
     */
    int Live_MODULE_TRIPLE_LIVE_CODE = 1110;
    /**
     * 侧滑模块-发现左右滑动
     */
    int Live_MODULE_SIDE_SLIP_CODE = 1114;
    /**
     * 标签列表模块-显示标签;印象列表模块, 废弃模块
     */
    int Live_MODULE_LABEL_LIST_CODE = 1115;
    /**
     * 标签数据模块-显示直播数据
     */
    int Live_MODULE_LABEL_DATA_CODE = 1116;
    /**
     * 首页黄金栏目，支持左右滑动
     * a
     */
    int Live_MODULE_GOLD_SLIP_CODE = 1118;

    int live_MODULE_NEAR_ANCHOR = 2000;

    /**
     * 排行榜滚动条
     */
    int live_MODULE_RANKING_LIST_CODE = 2004;
    /**
     * 话题模块
     */
    int Live_MODULE_TALK_CODE = 2005;

    int Live_MODULE_PLAY_ENTRY_CODE = 2006;

    /**
     * 玩法聚合列表模块
     */
    int Live_MODULE_GAME_PLAY = 2007;
    /**
     * 玩法聚合排行榜模块
     */
    int Live_MODULE_GAME_RANK = 2008;
    /**
     * 玩法聚合小视频模块
     */
    int Live_MODULE_GAME_SHORTVIDEO = 2009;
    /**
     * 首页主播之星模块（头像模块）
     * a
     */
    int Live_MODULE_ANCHOR_STAR = 2010;

    int Live_MOUDLE_NEAR_RECOMMEND_ANCHOR = 2011;

    int Live_MOUDLE_NEAR_POP_ANCHOR = 2012;

    int Live_MOUDLE_NEAR_SELECT_CITY = 2013;

    /**
     * 萌宠小视频
     */
    int Live_MODULE_SHENQU_PET = 2014;

    /**
     * 卡片模块
     */
    int Live_MODULE_CARD = 2023;

    /**
     * 关注tab的正在直播
     */
    int Live_MODULE_FOLLOW_LIVING = 2025;
    int Live_MODULE_FOLLOW_LIVING_TYPE = -2025;
    int Live_MODULE_FOLLOW_SUBSCRIBE_STATUS_TYPE = -20251;

    /**
     * 模块类型种类
     */
    int TYPE_COUNT = 30;
    //视频类型 —— 1：现场直播，2：现场录播，3：专题，4：秀场直播  8：电竞直播  9：通栏banner 19:文字banner  21:标签聚合（4合1）
    /**
     * 标签模块
     */
    int Live_MODULE_IMG_TAG = -1011;

    int LIVING_TYPE_UNKNOWN = 0;
    /**
     * 卡片形式的标签模块
     */
    int Live_MODULE_CARD_TAG = -10111;
    /**
     * 现场直播
     */
    int LIVING_TYPE_MOBILE_LIVE = 1;
    /**
     * 现场录播
     */
    int LIVING_TYPE_MOBILE_REPLAY = 2;
    /**
     * 专题
     */
    int LIVING_TYPE_TOPIC = 3;
    /**
     * 秀场直播
     */
    int LIVING_TYPE_SHOW_LIVE = 4;

    /**
     * 萌宠神曲
     */
    int PET_SHENQU_TYPE = 6;

    /**
     * 电竞直播
     */
    int LIVING_TYPE_GAME_LIVE = 8;
    /**
     * 通栏banner
     */
    int LIVING_TYPE_COLUMN_MODULE = 9;
    /**
     * 文字banner
     */
    int LIVING_TYPE_TEXT_BANNER_MODULE = 19;
    /**
     * 标签聚合（4合1）
     */
    int LIVING_TYPE_TAGS_COMBINE_MODULE = 21;

    String LIVE_URL_FIXED_IDX_STR = "idx";  //拼接导航栏地址 首页固定字段
    String FRAGMENT_TAG_IDX = "idx";    //一级页面 标识
    String FRAGMENT_TAG_SUB = "sub";    //二级页面 标识
    String FRAGMENT_TAG_MORE = "fragment_tag_more";  //所有页面只会出现一个二级更多页 作为唯一的页面数据保存
    String FRAGMENT_TAG_LABEL = "fragment_tag_label"; //标签页只会出现一个二级更多页 作为唯一的页面数据保存
    String BIZ_DISCOVER = "discover";  //发现
    String BIZ_DISCOVER2 = "discover2";  //新发现-新增侧滑和标签
    String BIZ_NEAR = "near";  //附近
    String BIZ_NEAR_CN = "附近";  //附近
    int LABEL_COUNT = 8;  //标签显示个数
    int RECOMMEND_NEAR = 2;  //附近推荐算法
    String PREVIEW_TAG_ALL = "All";  //预告全部类型标签
    //来源 type ：  1-首页  2-直播间
    String FROM_HOME_PAGE = "1";
    String FROM_LIVE_CHANNEL = "2";
    int PREVIEW_FROM_CHANNEL = -100;    //直播间进预告
}