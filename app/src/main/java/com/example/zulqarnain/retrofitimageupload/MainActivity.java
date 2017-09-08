package com.example.zulqarnain.retrofitimageupload;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bUpload, bChoose;
    EditText mTitle;
    ImageView imageView;
    private Bitmap bitmap;

    private static final int IMAAGE_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bUpload = (Button) findViewById(R.id.upload_image);
        bChoose = (Button) findViewById(R.id.chose_image);
        mTitle = (EditText) findViewById(R.id.mtitle);

        imageView = (ImageView) findViewById(R.id.image_view);

        bUpload.setOnClickListener(this);
        bChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.chose_image:
                selectImage();
                break;
            case R.id.upload_image:
                uploadImage();
                break;
        }
    }

    private void selectImage() {
        Intent intenet = new Intent();
        intenet.setType("image/*");
        intenet.setAction(intenet.ACTION_GET_CONTENT);
        startActivityForResult(intenet, IMAAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                bChoose.setEnabled(false);
                bUpload.setEnabled(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
     }

    private void uploadImage(){
        String Image = imageToString();
        String title = mTitle.getText().toString();

        ApiInterface apiInterface = ApiClint.getApiClint().create(ApiInterface.class);
        Call<ImageClass> call =  apiInterface.uploadImage(title,Image);

        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                ImageClass resp= response.body();
                Toast.makeText(MainActivity.this,"Response "+resp.getResponse(),Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.GONE);
                bUpload.setEnabled(false);
                bChoose.setEnabled(true);
            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {

            }
        });
    }
}

