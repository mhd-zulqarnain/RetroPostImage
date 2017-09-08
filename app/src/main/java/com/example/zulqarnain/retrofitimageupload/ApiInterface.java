package com.example.zulqarnain.retrofitimageupload;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Zul Qarnain on 9/7/2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("upload.php")
    Call<ImageClass> uploadImage(@Field("title") String title, @Field("image") String image);
}
