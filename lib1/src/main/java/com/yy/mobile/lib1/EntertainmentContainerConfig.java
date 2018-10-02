package com.yy.mobile.lib1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.duowan.mobile.entlive.annotation.InitAttrConfig;
import com.duowan.mobile.entlive.annotation.InitAttrConfigs;
import com.duowan.mobile.entlive.domain.InitLevel;
import com.yy.mobile.lib1.component.BasicFuncitonComponent;
import com.yy.mobile.lib1.component.ChatEmotionComponent;
import com.yy.mobile.lib1.component.LiveComponent;
import com.yy.mobile.lib1.component.ProgramInfoFragment;
import com.yy.mobile.lib1.component.TouchComponent;
import com.yy.mobile.lib1.container.BaseContainerConfig;

/**
 * Created by ericwu on 2017/4/24.
 */
@InitAttrConfigs({
        @InitAttrConfig(component = BasicFuncitonComponent.class, initLevel = InitLevel.VERY_LOW, resourceId = R2.id.show_screen_elements),
        @InitAttrConfig(component = TouchComponent.class, initLevel = InitLevel.VERY_LOW, resourceId = R2.id.basic_live_touch_component),
        @InitAttrConfig(component = ProgramInfoFragment.class, initLevel = InitLevel.VERY_HIGHT, resourceId = R2.id.basic_live_program_component),
        @InitAttrConfig(component = LiveComponent.class, initLevel = InitLevel.LOW, resourceId = R2.id.basic_live_bussiness_component),
        @InitAttrConfig(component = ChatEmotionComponent.class, initLevel = InitLevel.VERY_LOW, resourceId = R2.id.basic_live_chat_emotion_component),
})
public class EntertainmentContainerConfig extends BaseContainerConfig {

    @Override
    public void onContainerInit(@NonNull Activity activity, Bundle arguments) {
        // ICoreManagerBase.getCore(ILiveRewardCore.class).onEntertaimentTemplateInit();
        // LiveRoomDataCenter.INSTANCE.dispatch(new LiveRoomDataState_CurrentTemplateIdAction(LiveRoomDataConstant.TEMPLATE_ENTERTAIMENT));
    }

    @Override
    public void onContainerUninit(@NonNull Activity activity) {
        // LiveRoomDataCenter.INSTANCE.dispatch(new LiveRoomDataState_CurrentTemplateIdAction(""));
    }

    @Override
    public void onInitDone(@NonNull Activity activity) {
        // MLog.debug("EntertainmentContainerConfig", "onInitDone: LoginFollowGuideManager.instance().sceneInitDone");
        // LoginFollowGuideManager.instance().sceneInitDone();
    }

}
