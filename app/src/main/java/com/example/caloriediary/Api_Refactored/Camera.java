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

    private final static String TAG = "Camera_Class";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int GALLERY_PERMISSIONS_REQUEST =2;
    static final String CLOUD_VISION_REQUEST_URL ="https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDnbrka7qti5lc6z_DZ3_Rb9wELod_Jvb0";
    Bitmap bitmap;
    Uri photoUri;
    ArrayList<String> arrayList;

    ReusableFunctions reusableFunctions = new ReusableFunctions();
    Context Calorie_Homepage_Context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            setRequestImageCapture();
            setGalleryPermissionsRequest();

            bitmap =null;

            return insets;
        });

    }


    private void openCamera() {
        Log.d(TAG, "openCamera function");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d(TAG, "error making photofile");

        }

        // Ensure that there's a camera activity to handle the intent
        if(photoFile != null){
            Log.d(TAG, "packageName: " + this.getPackageName());

            Uri photoURI = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", photoFile);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.d(TAG, "Uri made and Pic Intent");

            notifyMediaScanner(photoFile);
        }
        else{
            Log.d(TAG, "Is Null");
        }
    }

    // Picture source from camera or gallery dialog

    public void Call_Camera(View view) {
        openCamera();

    }

    public void Call_Gallery(View view){
        startGalleryChooser();
    }

    public void startGalleryChooser() {
        Log.d(TAG, "in gallery chooser");
        if (!setGalleryPermissionsRequest()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            Log.d(TAG, "startActivityForResult started");
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_PERMISSIONS_REQUEST);
            Log.d(TAG, "startActivityForResult Done");
        }else{
            Log.d(TAG, "User Has Perms On For Camera");
        }
    }

    //request user for Gallery permission
    private boolean setGalleryPermissionsRequest() {//check if camera has perms to write externally
        Log.d(TAG, "in Gallery Perms Req");
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
        Log.d(TAG, "On Activity Result");
        super.onActivityResult(requestCode, resultCode, data);//added

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File f = new File(mCurrentPhotoPath);
            photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", f);

            new msyncTask().execute(photoUri);

            Log.d(TAG, "Camera");
        }
        if (requestCode == GALLERY_PERMISSIONS_REQUEST && data != null) {
            Log.d(TAG, "OnActivityResult2");

            photoUri = data.getData();
            new msyncTask().execute(photoUri);
        }
    }


    //reduce size of img
    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {
        Log.d(TAG, "Scale Bitmap Down");
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
        Log.d(TAG, "iScale Down complete");
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    String mCurrentPhotoPath;

    //creates file to store img
    private File createImageFile() throws IOException {
        Log.d(TAG, "create imagefile");
    try{
        // Create an image file name
        Log.d(TAG, "in createImageFile");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.d(TAG, "Made Filename");
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d(TAG, "Made storage dir");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".JPEG",         /* suffix */
                storageDir      /* directory */
        );
        Log.d(TAG, "Formatted pic with name etc");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "Saved photo");

        return image;

    }catch(Exception E){
        Log.d(TAG, "ImageCreation FAILURE!!!!!!!!!!!!!!!");
        return null;
    }
    }

    // call Google Vision cloud vision api
    public void callGoogleVision(Bitmap bitmap) {
        Log.d(TAG, "callgooglevision");
        JSONObject mJsonObject = new JSONObject();
        JSONObject imageJObj = new JSONObject();
        JSONObject featuresJObj = new JSONObject();
        JSONObject typeMaxRJObj = new JSONObject();
        JSONObject concatJObj = new JSONObject();
        JSONObject[] objs = new JSONObject[]{imageJObj, featuresJObj};
        String imageEncoded = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
        Log.d(TAG, "Encode To Base 64 complete");
        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity stringEntity = null;
        Log.d(TAG, "callgooglevision Vars Made");
        try {
            imageJObj.put("image", new JSONObject().put("content", imageEncoded));
            typeMaxRJObj.put("type", "LABEL_DETECTION");
            typeMaxRJObj.put("maxResults", 6);
            featuresJObj.put("features", new JSONArray().put(typeMaxRJObj));
            Log.d(TAG, "callgooglevision objs put");
            for (JSONObject obj : objs) {
                Iterator<String> it = obj.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    concatJObj.put(key, obj.get(key));
                }
            }
            mJsonObject.put("requests", new JSONArray().put(concatJObj));

            Log.d(TAG, "JsonMy ");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "Put Json to String Array ");
            stringEntity = new StringEntity(mJsonObject.toString());
            Log.d(TAG, "success putting Json to String Array ");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Failure Putting Json to String Array (which is null)");
            e.printStackTrace();
        }

        Log.d(TAG, "Starting Client Post");
        try{
            client.post(this, CLOUD_VISION_REQUEST_URL, stringEntity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d(TAG, "response");
                    try {
                        Log.d(TAG, "Trying to get responses");
                        JSONArray jsonArray = response.getJSONArray("responses").getJSONObject(0).getJSONArray("labelAnnotations");
                        if (jsonArray != null) {
                            Log.d(TAG, "Json Not Null. Put to arraylist");
                            arrayList = convertToArrList(jsonArray);
                        /*
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        i.putExtra(TAG, "StringArrList" + arrayList);
                        i.putExtra(TAG, "BitmapUri" + photoUri.toString());
                        startActivity(i);
                        finish();
                         */
                        }else{
                            Log.d(TAG, "Error making string arraylist for jsopn output");
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "Error occured in formimng string arraylist");
                        Toast.makeText(Camera.this, "Error Occurred Try Different Picture", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (responseString != null) {
                        Log.d(TAG, "Response: " + responseString);
                    }
                    if (throwable != null) {
                        Log.d(TAG, "Error: " + throwable.getMessage());
                    }
                }
            });
            Log.d(TAG, "callGoogleVision Finished");
        }catch(Exception E){
            Log.d(TAG, "Failed Client Request");
        }
        Output_Json(arrayList);
    }


    private void Output_Json(ArrayList<String> arraylist){
        Log.d(TAG, "In Output_Json");

        try{
            for (String str : arrayList) {
                Log.d(TAG, "label" + str);
            }
            Log.d(TAG,"Done");
        }catch(Exception E){

            Log.d(TAG, "Output Failure");
        }
    }

    private ArrayList<String> convertToArrList(JSONArray jsonArray){
        Log.d(TAG, "In Convert Arraylist");
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
                bitmap = scaleBitmapDown(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 1200);

            } catch (IOException e) {
                Log.d("upload", "Image picking failed because");
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }

    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {//encodes image to base 64 string for transmission
        Log.d(TAG, "Encode Base 64");
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();

        image.compress(compressFormat, quality, byteArrayOS);
        Log.d(TAG, "Encode Base 64 COMPLETE");
        return Base64.encodeToString(byteArrayOS.toByteArray(),Base64.DEFAULT);
    }

    private void notifyMediaScanner(File photoFile) {
        Log.d(TAG, "in Notify Media Scanner");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photoFile);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    //: This inner class extends AsyncTask to handle the uploading of an image and subsequent call to the Google Vision API in a background thread.
    public class msyncTask extends AsyncTask<Uri,String,String> {

        @Override
        protected String doInBackground(Uri... params) {
            Log.d(TAG, "in doInBackground");
            uploadImage(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "in onPostExecute");
            callGoogleVision(bitmap);
        }

    }

}