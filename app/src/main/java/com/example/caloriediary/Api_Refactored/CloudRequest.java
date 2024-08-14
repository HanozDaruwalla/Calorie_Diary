package com.example.caloriediary.Api_Refactored;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudRequest {

    static String TAG = "Create_Json_Obj";
    JSONObject Image; //json version of image
    String Content_Of_Img; //serialises data
    JSONArray Features_Of_Image;
    JSONObject Complete_JSONObject;
    String Type_Of_Detection;
    String No_Of_Maximum_Results;

    public CloudRequest (String content){
        this.Content_Of_Img = content;

        JSONObject Content_Object = new JSONObject();
        JSONObject Feature_Object = new JSONObject();
        JSONObject Content_And_Feature_Object = new JSONObject();
        try {
            Content_Object.put("content",content);
            Image.put("image",Content_Object);
            Feature_Object.put("type","LABEL_DETECTION");
            Feature_Object.put("maxResults",1);

            Features_Of_Image.put(Feature_Object);
            Content_And_Feature_Object.put("feature", Features_Of_Image);
            Complete_JSONObject.put("0", Image).accumulate("0",Content_And_Feature_Object);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Making Json Obj");
        }
    }


    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        CloudRequest.TAG = TAG;
    }

    public JSONObject getImage() {
        return Image;
    }

    public void setImage(JSONObject image) {
        Image = image;
    }

    public String getContent_Of_Img() {
        return Content_Of_Img;
    }

    public void setContent_Of_Img(String content_Of_Img) {
        Content_Of_Img = content_Of_Img;
    }

    public JSONArray getFeatures_Of_Image() {
        return Features_Of_Image;
    }

    public void setFeatures_Of_Image(JSONArray features_Of_Image) {
        Features_Of_Image = features_Of_Image;
    }

    public JSONObject getComplete_JSONObject() {
        return Complete_JSONObject;
    }

    public void setComplete_JSONObject(JSONObject complete_JSONObject) {
        Complete_JSONObject = complete_JSONObject;
    }

    public String getType_Of_Detection() {
        return Type_Of_Detection;
    }

    public void setType_Of_Detection(String type_Of_Detection) {
        Type_Of_Detection = type_Of_Detection;
    }

    public String getNo_Of_Maximum_Results() {
        return No_Of_Maximum_Results;
    }

    public void setNo_Of_Maximum_Results(String no_Of_Maximum_Results) {
        No_Of_Maximum_Results = no_Of_Maximum_Results;
    }



    @Override
    public String toString() {
        return Complete_JSONObject.toString();
    }
}
