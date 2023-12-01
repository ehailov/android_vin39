package ru.businesscloud.vin39.models;

import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("model")
    public String model;

    @SerializedName("pk")
    public int pk;

    @SerializedName("fields")
    public Fields fields;

}
