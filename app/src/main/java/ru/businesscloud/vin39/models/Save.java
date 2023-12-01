package ru.businesscloud.vin39.models;

import com.google.gson.annotations.SerializedName;

public class Save {

    @SerializedName("state")
    public String state;

    @SerializedName("error")
    public String error;

    @SerializedName("has_error")
    public boolean hasError;

}
