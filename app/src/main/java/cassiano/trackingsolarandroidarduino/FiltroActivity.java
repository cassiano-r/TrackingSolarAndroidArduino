package cassiano.trackingsolarandroidarduino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FiltroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        //Recupera informação da Activity Anterior (MainActivity)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toast.makeText(this,sharedPreferences.getString("caminho",null), Toast.LENGTH_LONG).show();

    }

    public void clickGerar(View v)
    {
        CalendarView cv1 = (CalendarView) findViewById(R.id.calendarViewInicio);
        CalendarView cv2 = (CalendarView) findViewById(R.id.calendarViewFinal);
        SimpleDateFormat sFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result1 = sFormat.format(new Date(cv1.getDate()));
        String result2 = sFormat.format(new Date(cv2.getDate()));

        if(cv1.getDate() > cv2.getDate())
        {
            Toast.makeText(FiltroActivity.this, "Data Inicial tem que ser MENOR que Data Final!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //SharedPreferences --> Input
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("data1", result1);
            editor.putString("data2", result2);
            editor.commit();

            if(sharedPreferences.getString("caminho",null) == "ReportWATTS" ||
                    sharedPreferences.getString("caminho",null) == "ReportHUMIDADE" ||
                    sharedPreferences.getString("caminho",null) == "ReportTEMP" ||
                    sharedPreferences.getString("caminho",null) == "ReportCORRENTE" ||
                    sharedPreferences.getString("caminho",null) == "ReportTENSAO" ||
                    sharedPreferences.getString("caminho",null) == "ReportGERAL")
            {
                //Mostrando resposta em outra Activity
                Intent navegar = new Intent(this,ReportActivity.class);
                //Carregando a Activity
                startActivity(navegar);
            }
            else
            {
                //Mostrando resposta em outra Activity
                Intent navegar = new Intent(this,ChartActivity.class);
                //Carregando a Activity
                startActivity(navegar);
            }

        }
    }
}
