package cassiano.trackingsolarandroidarduino;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CASSIANO on 09/02/2016.
 */
public class Solar implements Serializable {

    @SerializedName("ID")
    public int ID;

    @SerializedName("DT")
    public String DT;

    @SerializedName("WATTS")
    public float WATTS;

    @SerializedName("HUMIDADE")
    public float HUMIDADE;

    @SerializedName("TEMP")
    public float TEMP;

    @SerializedName("CORRENTE")
    public float CORRENTE;

    @SerializedName("TENSAO")
    public float TENSAO;
}
