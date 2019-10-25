package com.comp90018.photostyle.helpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;


public class ImageList {


    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("src")
    @Expose
    private List<String> src = null;
    @SerializedName("image_label")
    @Expose
    private List<String> imageLabel = null;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<String> getSrc() {
        return src;
    }

    public void setSrc(List<String> src) {
        this.src = src;
    }

    public List<String> getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(List<String> imageLabel) {
        this.imageLabel = imageLabel;
    }
}
