package com.example.bloodbank.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.EndPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MakeRequest extends AppCompatActivity {
    private EditText message;
    private ImageView myIMG;
    private TextView choose_img_2;
    private Button post;
    private String  picturePath;
    public final static int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        AndroidNetworking.initialize(getApplicationContext());


        message = findViewById(R.id.message);
        myIMG = findViewById(R.id.img);
        choose_img_2 = findViewById(R.id.choose_2);
        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVaild())
                {
                   //uplaod post
                    uploadRequest(message.getText().toString());
                }
            }
        });

        choose_img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               permession();
            }
        });


    }

  //--------------------------------------------------------------------------------------
  //to get on image and path
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Glide.with(this).load(selectedImage).into(myIMG);  // to get image

            String[] projection = {MediaStore.Images.Media.DATA}; // get the path
            try {
                Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(projection[0]);
                 picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.d("Picture Path", picturePath);
            }
            catch(Exception e) {
                Log.e("Path Error", e.toString());
            }
        }
    }

//-------------------------------------------------------------------------------
  //to get permession while runtime
    private void permession()
    {
        if (ActivityCompat.checkSelfPermission(this ,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            Intent i = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

  //-----------------------------------------------------------------------------------
  //to get path image
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2 ){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }else{
                showMSG("permission disenabled");
            }

        }
    }

    //------------------------------------------------------------------------------------------
    private boolean isVaild()
    {
        if(message.getText().toString().isEmpty()){
            showMSG("message is empty");
            return false;
        }
        return true;
    }

    //--------------------------------------------------------------------------------------

    private void showMSG(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //----------------------------------------------------------------------------
      //upload file by libarary ( fast - network - android )
    private void uploadRequest(String message){

        String number = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("number","12345");
        AndroidNetworking.upload(EndPoint.url_upload_request)
                .addMultipartFile("file",new File(picturePath))
                .addQueryParameter("message",message)
                .addQueryParameter("number",number)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {  // time update
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        long progress = (bytesUploaded/totalBytes)*100;
                        choose_img_2.setText(String.valueOf(progress)+" %");
                        choose_img_2.setOnClickListener(null);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                showMSG("Successfull");
                                MakeRequest.this.finish();
                            }else{
                                showMSG(response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
