package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.test2.ui.login.MainActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageSelect extends AppCompatActivity {

    Button BSelectImage;
    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_select);

        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user selects the image from imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                // Get the url of the image from data
                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    //comment below line out if using bit conversion instead
                    IVPreviewImage.setImageURI(selectedImageUri);
                    InputStream iStream = null;
                    try {
                        iStream = getContentResolver().openInputStream(selectedImageUri);
                        byte[] inputData = getBytes(iStream);

                        //log various info in logcat
                        Log.w("filestreampath", selectedImageUri.getPath());
                        Log.w("URI", String.valueOf(selectedImageUri));
                        Log.w("byte[] data", String.valueOf(inputData));

                        //uncomment the below section if using bit conversion instead
                        /*
                        Bitmap bmp = BitmapFactory.decodeByteArray(inputData, 0, inputData.length);
                        ImageView image = (ImageView) findViewById(R.id.IVPreviewImage);
                        //image.setAdjustViewBounds(true);
                        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
                        image.setAdjustViewBounds(true);
                        image.setCropToPadding(true);

                         */
                    } catch (Exception e) {
                        Log.w("byte[] data", "FAILED");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


}
