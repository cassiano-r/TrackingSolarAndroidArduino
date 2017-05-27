package cassiano.trackingsolarandroidarduino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MenuChartActivity extends AppCompatActivity {

    String[] items = new String[]{
            "WATTS",
            "UMIDADE",
            "TEMPERATURA",
            "CORRENTE",
            "TENSAO",
            //"GERAL",
            "VOLTAR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chart);

        GridView grid = (GridView) findViewById(R.id.grid1);
        grid.setOnItemClickListener(onGridViewItemClick());
        // Informar o adapter para preencher o GridView
        //grid.setAdapter(new ImagemAdapter(this, imagens));
        // listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        grid.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    // Evento ao clicar no item do grid
    private AdapterView.OnItemClickListener onGridViewItemClick() {
        return new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int posicao, long id)
            {
                //SharedPreferences --> Input
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(id == 0)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuChartActivity.this,FiltroActivity.class);
                    //SharedPreferences
                    editor.putString("caminho", "ChartWATTS");
                    editor.commit();
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 1)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuChartActivity.this,FiltroActivity.class);
                    //SharedPreferences
                    editor.putString("caminho", "ChartHUMIDADE");
                    editor.commit();
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 2)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuChartActivity.this,FiltroActivity.class);
                    //SharedPreferences
                    editor.putString("caminho", "ChartTEMP");
                    editor.commit();
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 3)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuChartActivity.this,FiltroActivity.class);
                    //SharedPreferences
                    editor.putString("caminho", "ChartCORRENTE");
                    editor.commit();
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 4)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuChartActivity.this,FiltroActivity.class);
                    //SharedPreferences
                    editor.putString("caminho", "ChartTENSAO");
                    editor.commit();
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 6)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuChartActivity.this,FiltroActivity.class);
                    //SharedPreferences
                    editor.putString("caminho", "ChartGERAL");
                    editor.commit();
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 5)
                {
                    //Volta para anterior
                    finish();
                }

            }
        };
    }
}
