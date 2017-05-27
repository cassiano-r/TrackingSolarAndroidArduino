package cassiano.trackingsolarandroidarduino;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("pid")
    public int pid;

    @SerializedName("name")
    public String name;

    @SerializedName("qty")
    public int qty;

    @SerializedName("price")
    public float price;

    @SerializedName("image_url")
    public String image_url;
}
