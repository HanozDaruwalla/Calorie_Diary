package com.example.caloriediary.Api_Refactored;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caloriediary.Creating_Account_And_Login.MainActivity;
import com.example.caloriediary.R;
import com.example.caloriediary.ReusableFunctions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Camera extends AppCompatActivity {


    private final static String TAG = "Camera";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int GALLERY_PERMISSIONS_REQUEST =2;
    static  final String CLOUD_VISION_REQUEST_URL ="https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDnbrka7qti5lc6z_DZ3_Rb9wELod_Jvb0";
    Bitmap bitmap;
    Uri photoUri;
    ArrayList<String> arrayList;

    ReusableFunctions reusableFunctions = new ReusableFunctions();
    Context Calorie_Homepage_Context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            bitmap =null;


            return insets;
        });

    }


    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            Log.d(TAG, "calling createImageFile");
            photoFile = createImageFile();
            Log.d(TAG, "called createImageFile");
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d(TAG, "Input Output exception error");

        }

        // Ensure that there's a camera activity to handle the intent
        if (photoFile != null) {
            Log.d(TAG, "file not null");
            Log.d(TAG, "photoFile: " + photoFile);

            if(photoFile != null){
                Log.d(TAG, "packageName: " + this.getPackageName());

                Uri photoURI = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", photoFile);
                Log.d(TAG, "UR MADE");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d(TAG, "Taken Pic Intent");
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                Log.d(TAG, "Done act for res");
            }
            else{
                Log.d(TAG, "Is Null");
            }
        }else{
            Log.d(TAG, "Failure");
        }
    }

    // Picture source from camera or gallery dialog

    public void Call_Camera(View view) {
        Log.d(TAG, "Call_Camera opened");
        setRequestImageCapture();
        setGalleryPermissionsRequest();

        openCamera();
    }

    public void startGalleryChooser() {
        if (!setGalleryPermissionsRequest()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_PERMISSIONS_REQUEST);
        }else{
            Log.d(TAG, "User Has Perms On For Camera");
        }
    }

    //request user for Gallery permission
    private boolean setGalleryPermissionsRequest() {//check if camera has perms to write externally
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERMISSIONS_REQUEST);
            return true;
        }
        return false;
    }

    //request user for Camera Permission
    private void setRequestImageCapture(){//checks if camera has permission. and if not asks
        Log.d(TAG, "Request perms opened");
        // Check if the camera permission is already available
        try{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, request it
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);
            } else {
                Log.d(TAG, "Perms already on");
            }

        }catch(Exception e){
            Log.d(TAG, "Failure");
        }
    }
    //handles result of activity e.g. capturing an img with camera
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//added
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File f = new File(mCurrentPhotoPath);
            photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", f);

            new msyncTask().execute(photoUri);

            Log.d("OnActivityResult", "Camera");
        }
        if (requestCode == GALLERY_PERMISSIONS_REQUEST && data != null) {
            Log.d("OnActivityResult", "Gallery");

            photoUri = data.getData();
            new msyncTask().execute(photoUri);
        }
    }


    //reduce size of img
    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    String mCurrentPhotoPath;

    //creates file to store img
    private File createImageFile() throws IOException {
        // Create an image file name
        Log.d(TAG, "in createImageFile");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.d(TAG, "Made Filename");
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d(TAG, "Made storage dir");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d(TAG, "Formatted pic with name etc");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "Saved photo");
        return image;
    }


    // call Google Vision cloud vision api
    public void callGoogleVision(Bitmap bitmap) {
        JSONObject mJsonObject = new JSONObject();
        JSONObject imageJObj = new JSONObject();
        JSONObject featuresJObj = new JSONObject();
        JSONObject typeMaxRJObj = new JSONObject();
        JSONObject concatJObj = new JSONObject();
        JSONObject[] objs = new JSONObject[]{imageJObj, featuresJObj};
        String imageEncoded = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity stringEntity = null;

        try {
            imageJObj.put("image", new JSONObject().put("content", imageEncoded));
            typeMaxRJObj.put("type", "LABEL_DETECTION");
            typeMaxRJObj.put("maxResults", 6);
            featuresJObj.put("features", new JSONArray().put(typeMaxRJObj));
            for (JSONObject obj : objs) {
                Iterator<String> it = obj.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    concatJObj.put(key, obj.get(key));
                }
            }
            mJsonObject.put("requests", new JSONArray().put(concatJObj));

            Log.d("JsonMy", imageEncoded);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            stringEntity = new StringEntity(mJsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(this, CLOUD_VISION_REQUEST_URL, stringEntity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Response", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("responses").getJSONObject(0).getJSONArray("labelAnnotations");
                    if (jsonArray != null) {
                        arrayList = convertToArrList(jsonArray);
                        for (String str : arrayList) {
                            Log.d("Label", str);
                        }
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        i.putExtra("StringArrList", arrayList);
                        i.putExtra("BitmapUri", photoUri.toString());
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Camera.this, "Error Occurred Try Different Picture", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failed Response", responseString);
            }
        });
    }

    private ArrayList<String> convertToArrList(JSONArray jsonArray){
        ArrayList<String> labelList = new ArrayList<>();
        for(int i =0; i<jsonArray.length();i++){
            try {
                labelList.add(jsonArray.getJSONObject(i).getString("description"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return labelList;
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                1200);

            } catch (IOException e) {
                Log.d("upload", "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("upload", "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }

    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {//encodes image to base 64 string for transmission
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();

        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(),Base64.DEFAULT);
    }



    //: This inner class extends AsyncTask to handle the uploading of an image and subsequent call to the Google Vision API in a background thread.
    public class msyncTask extends AsyncTask<Uri,String,String> {



        @Override
        protected String doInBackground(Uri... params) {
            uploadImage(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            callGoogleVision(bitmap);



        }
    }

}