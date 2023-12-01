package ru.businesscloud.vin39.models;

import com.google.gson.annotations.SerializedName;

public class Profil {

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("username")
    public String email;

    @SerializedName("email")
    public String phone;
}
