package com.yymobile.core.live.livedata;

/**
 * Created by shobal on 2016/1/11.
 */
public class LineData implements Cloneable {
    //@SerializedName("id")
    public int id;
    //@SerializedName("moduleType")
    public int moduleType;
    //@SerializedName("data")
    public Object data;
    //@SerializedName("sort")
    public int sort;
    //@SerializedName("noDulication")
    public int noDulication;
    //@SerializedName("silentPlay")
    public int silentPlay;

    //public ContentStyleInfo contentStyle;

    public LineData(int id, int type) {
        this.id = id;
        this.moduleType = type;
    }

    public LineData() {

    }

    @Override
    public LineData clone() {
        try {
            return (LineData) super.clone();
        } catch (CloneNotSupportedException e) {
            android.util.Log.e("AudioRecorder", "printStackTrace", e);
        }
        return null;
    }


    @Override
    public String toString() {
        return "LineData{" +
                "id=" + id +
                ", moduletype=" + moduleType +
                ", data=" + data +
                '}';
    }

    // public static class LineDataBuilder {
    //     public int id;
    //     public int moduleType;
    //     public Object data;
    //     public int sort;
    //     public int noDulication;
    //     public ContentStyleInfo contentStyle;
    //     public int silentPlay;
    //
    //     public int gameStyle;
    //
    //     public LineDataBuilder(int id, int moduleType) {
    //         this.id = id;
    //         this.moduleType = moduleType;
    //     }
    //
    //     public LineDataBuilder(int id, int moduleType, Object data, int sort, int noDulication, ContentStyleInfo contentStyle) {
    //         this.id = id;
    //         this.moduleType = moduleType;
    //         this.data = data;
    //         this.sort = sort;
    //         this.noDulication = noDulication;
    //         this.contentStyle = contentStyle;
    //     }
    //
    //     public LineDataBuilder id(int id) {
    //         this.id = id;
    //         return this;
    //     }
    //
    //     public LineDataBuilder moduleType(int moduleType) {
    //         this.moduleType = moduleType;
    //         return this;
    //     }
    //
    //     public LineDataBuilder data(Object data) {
    //         this.data = data;
    //         return this;
    //     }
    //
    //     public LineDataBuilder sort(int sort) {
    //         this.sort = sort;
    //         return this;
    //     }
    //
    //     public LineDataBuilder noDulication(int noDulication) {
    //         this.noDulication = noDulication;
    //         return this;
    //     }
    //
    //     public LineDataBuilder contentStyle(ContentStyleInfo contentStyle) {
    //         this.contentStyle = contentStyle;
    //         return this;
    //     }
    //
    //     public LineDataBuilder silentPlay(int silentPlay) {
    //         this.silentPlay = silentPlay;
    //         return this;
    //     }
    //
    //     public int getGameStyle() {
    //         return gameStyle;
    //     }
    //
    //     public void setGameStyle(int gameStyle) {
    //         this.gameStyle = gameStyle;
    //     }
    //
    //     public LineData createLineData() {
    //         LineData lineData = new LineData(id, moduleType);
    //         lineData.data = data;
    //         lineData.sort = sort;
    //         lineData.noDulication = noDulication;
    //         lineData.contentStyle = contentStyle;
    //         lineData.silentPlay = silentPlay;
    //         return lineData;
    //     }
    // }
}


