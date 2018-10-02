package com.yy.mobile.ui.basicchanneltemplate.generate;

/**
 * Created by ericwu on 2017/4/17.
 */

public class InitConfig {
    private int level;
    private int resourceId;

    public InitConfig(int level, int resourceId) {
        this.level = level;
        this.resourceId = resourceId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
