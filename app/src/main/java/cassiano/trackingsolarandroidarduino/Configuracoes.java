package cassiano.trackingsolarandroidarduino;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Configuracoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        //SharedPreferences --> Input
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Componentes da Tela
        TextView txtIpServidor = (TextView) findViewById(R.id.txtIpServidor);
        txtIpServidor.setText(sharedPreferences.getString("ipServidor",null));
    }

    public void clickSalvar(View v)
    {
        //Componentes da Tela
       Button btnSalvar = (Button) findViewById(R.id.btnConfSalvar);
       TextView txtIpServidor = (TextView) findViewById(R.id.txtIpServidor);

        //SharedPreferences --> Input
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ipServidor", txtIpServidor.getText().toString());
        editor.commit();

        //Mensagem na Tela
        Toast.makeText(this, "Salvo IP = " + txtIpServidor.getText().toString(), Toast.LENGTH_LONG).show();

    }
}
