package ru.businesscloud.vin39.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CarsFields {

    @SerializedName("model")
    public ArrayList<String> model;

    @SerializedName("date")
    public String date;

    @SerializedName("years")
    public String release;

    @SerializedName("vin")
    public String vin;

    @SerializedName("price")
    public String price;

    @SerializedName("auction")
    public String auction;

    @SerializedName("generation")
    public String generation;

    @SerializedName("equipment")
    public String equipment;

    @SerializedName("mileage")
    public String mileage;

    @SerializedName("body_type")
    public String body_type;

    @SerializedName("condition")
    public String condition;

    @SerializedName("owners")
    public String owners;

    @SerializedName("number_of_doors")
    public String number_of_doors;

    @SerializedName("engine_type")
    public String engine_type;

    @SerializedName("engine_capacity")
    public String engine_capacity;

    @SerializedName("drive")
    public String drive;

    @SerializedName("rudder")
    public String rudder;

    @SerializedName("city")
    public String city;

    @SerializedName("сolor")
    public String color;

    @SerializedName("image1")
    public String file1;

    @SerializedName("image2")
    public String file2;

    @SerializedName("image3")
    public String file3;

    @SerializedName("image4")
    public String file4;

    @SerializedName("image5")
    public String file5;

    @SerializedName("image6")
    public String file6;

    @SerializedName("image7")
    public String file7;

    @SerializedName("image8")
    public String file8;

    @SerializedName("image9")
    public String file9;

    @SerializedName("image10")
    public String file10;

    @SerializedName("image11")
    public String file11;

    @SerializedName("image12")
    public String file12;

    public Bitmap bitmap;

}
