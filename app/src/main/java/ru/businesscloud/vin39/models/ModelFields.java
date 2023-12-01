package ru.businesscloud.vin39.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class ModelFields {

    @SerializedName("model")
    public JSONArray model;



    @SerializedName("image0")
    public String file0;

    @SerializedName("image1")
    public String file1;

    @SerializedName("image2")
    public String file2;

    @SerializedName("file3")
    public String file3;

    public Bitmap bitmap;
    public boolean hasDownload;

}
