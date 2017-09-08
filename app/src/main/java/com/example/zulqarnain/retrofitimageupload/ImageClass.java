package com.example.zulqarnain.retrofitimageupload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zul Qarnain on 9/7/2017.
 */

public class ImageClass {
    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}
