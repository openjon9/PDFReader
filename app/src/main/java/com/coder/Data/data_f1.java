package com.coder.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rey on 2018/10/2.
 */

public class data_f1 implements Parcelable {

    private String name;
    private String imgPath;
    private String mp4;

    public String getMp4() {
        return mp4;
    }

    public data_f1(String name, String imgPath, String mp4) {
        this.name = name;
        this.imgPath = imgPath;
        this.mp4 = mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;

    }

    public data_f1() {
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    protected data_f1(Parcel in) {
        name = in.readString();
    }

    public static final Creator<data_f1> CREATOR = new Creator<data_f1>() {
        @Override
        public data_f1 createFromParcel(Parcel in) {
            return new data_f1(in);
        }

        @Override
        public data_f1[] newArray(int size) {
            return new data_f1[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
