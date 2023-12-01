package ru.businesscloud.vin39.models;

import com.google.gson.annotations.SerializedName;

public class Cars {

    @SerializedName("model")
    public String model;

    @SerializedName("pk")
    public int pk;

    @SerializedName("fields")
    public CarsFields fields;

}
