package cassiano.trackingsolarandroidarduino;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, AsyncResponse {

    private GoogleMap mMap;
    //Google Services
    private GoogleApiClient mGoogleApiClient;
    private LatLng minhaLocalizacao;
    //Banco de Dados
    String NomeBanco = "TrankingSolar";
    SQLiteDatabase BancoDados = null;
    private ArrayList<Solar> productList;
    public String watts, temp, umidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final PostResponseAsyncTask taskRead = new PostResponseAsyncTask(MapsActivity.this,this);

        try
        {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarDadosReais.php");
        }
        catch (Exception e)
        {
        }

        //Configura objeto googleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //cria objeto para utilizar o serviço de localização
        //Location l  = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //armazena a localização em
       // LatLng aqui = new LatLng(l.getLatitude(),l.getLongitude());

        //obtem referencia do ampa no fragmento
        mMap = mapFragment.getMap();
        //permite obter localização
        //mMap.setMyLocationEnabled(true);
        //define tipo de visualização do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //define gerenciador de click para mapa para a distancia atual
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void processFinish(String s) {
        try {
            productList = new JsonConverter<Solar>().toArrayList(s, Solar.class);

            for (Solar value : productList) {
                watts = "Watts: " + value.WATTS + " W";
                temp = "Temperatura: " + value.TEMP + " °C";
                umidade = "Umidade: " + value.HUMIDADE + " %";
            }
            // Adiciona um marcador
            LatLng sydney = new LatLng(-23.2207741 , -45.9000698);
            LatLng brisbane = new LatLng(-27, 153);
            //Adiciona o Marcador
            mMap.addMarker(new MarkerOptions().position(sydney).title("My Tracking Solar").icon(BitmapDescriptorFactory.fromResource(R.drawable.maps)).snippet(watts +" - "+ umidade + " - "+ temp));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        } catch (Exception ex) {
            //Toast.makeText(ChartActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Adiciona um marcador
        //LatLng sydney = new LatLng(-23.2207741 , -45.9000698);
        //LatLng brisbane = new LatLng(-27, 153);

        //Adiciona o Marcador
        //mMap.addMarker(new MarkerOptions().position(sydney).title("My Tracking Solar").icon(BitmapDescriptorFactory.fromResource(R.drawable.maps)).snippet(watts +" - "+ umidade + " - "+ temp));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void dialogBoxMarcacao()
    {
        //Mensagem de alerta
        AlertDialog.Builder pergunta = new AlertDialog.Builder(this);
        pergunta.setTitle("Confirmar marcação...");
        pergunta.setMessage("Deseja marcar esse ponto?");
        pergunta.setIcon(R.drawable.cast_ic_notification_0);

        //Caso seja Sim
        pergunta.setPositiveButton("SIM", new DialogInterface.OnClickListener(
        ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Define latitude e longitude
                LatLng nova = new LatLng(minhaLocalizacao.latitude,minhaLocalizacao.longitude);

                //Cria objeto e já adiciona
                Marker Nova = mMap.addMarker(new MarkerOptions()
                        .position(nova) //posicao do marcador
                        .title("Trancking Solar") //título da janela do evento click
                        .snippet("Position("+minhaLocalizacao.latitude+","+minhaLocalizacao.longitude+")") //mensagem para evento click
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.maps))); //imagem

                //adiciona marcador e texto referente ao marcador(que será mostrado quando o marcador fo selecionado)
                //mMap.addMarker(new MarkerOptions().position(nova).title("posicao click"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(nova));
                Toast.makeText(getApplicationContext(),"Marcação Realizada!",Toast.LENGTH_SHORT).show();
            }
        });
        //Caso negativa
        pergunta.setNegativeButton("NÃO", new DialogInterface.OnClickListener(
                ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Marcação Cancelada!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //Mostra a pergunta
        pergunta.show();
    }

    @Override
    public void onMapClick(LatLng latLng)
    {
       //Define latitude e longitude
        minhaLocalizacao = new LatLng(latLng.latitude,latLng.longitude);

        dialogBoxMarcacao();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getApplicationContext(),"Conectado ao Serviço Google Play Services",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(),"Interrompido Google Play Services",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Erro Google Play Services",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop()
    {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

}
