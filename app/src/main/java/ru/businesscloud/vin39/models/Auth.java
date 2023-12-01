package ru.businesscloud.vin39.models;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("sid")
    public String sid;

    @SerializedName("root")
    public boolean root;

    @SerializedName("error")
    public String error;

}
