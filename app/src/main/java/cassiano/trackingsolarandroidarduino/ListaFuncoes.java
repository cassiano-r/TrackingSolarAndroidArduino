package cassiano.trackingsolarandroidarduino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaFuncoes extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //Variaveis para lista de funcoes
    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "UNITAU - 5º Engenharia da Computação", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        //SharedPreferences --> Input
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("caminho", "teste");
        editor.putString("ipServidor", "192.168.1.37");
        editor.commit();

        //Recupera informação da Activity Anterior
        //Intent navegar = getIntent();

        //Pega a referencia do ListView
        listView = (ListView) findViewById(R.id.list);
        //Define o Listener quando alguem clicar no item.
        listView.setOnItemClickListener(this);

        //Cria o ListView Customizado
        createListView();

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Pega o item que foi selecionado.
        ItemListView item = adapterListView.getItem(position);
        //DemostraÃ§Ã£o
        Toast.makeText(this, "Selecionado: " + item.getTexto(), Toast.LENGTH_SHORT).show();

        //Verifica se selecionou o Bluetooth
        //if(item.getTexto() == "Bluetooth")
        //{
            //Mostrando resposta em outra Activity
           // Intent navegar = new Intent(this,MenuBluetooth.class);
            //Carregando a Activity
           // startActivity(navegar);
       // }
        if(item.getTexto() =="Camera")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,Camera.class);
            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTexto() == "Mapas")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,MapsActivity.class);
            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTexto() == "Gráfico")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,MenuChartActivity.class);
            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTexto() == "Relatório")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,MenuReportActivity.class);
            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTexto() == "Teste")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,PieChartActivity.class);
            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTexto() == "Configurações")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,Configuracoes.class);
            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTexto() == "Dados Reais")
        {
            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,DadosReais.class);
            //Carregando a Activity
            startActivity(navegar);
        }
    }

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();
        //ItemListView item1 = new ItemListView("Bluetooth", R.drawable.blue,"Acompanhar on-line as informações");
        ItemListView item0 = new ItemListView("Dados Reais", R.drawable.relogio, "Acompanhe on-line as informações");
        ItemListView item2 = new ItemListView("Relatório", R.drawable.report,"Gera relatório por datas");
        ItemListView item3 = new ItemListView("Gráfico", R.drawable.chart,"Gera gráficos dos dados");
        ItemListView item4 = new ItemListView("Camera", R.drawable.picture, "Salve os resultados com fotos");
        ItemListView item5 = new ItemListView("Mapas", R.drawable.maps, "Marque as centrais solares");
        ItemListView item6 = new ItemListView("Configurações", R.drawable.engrenagem, "Configurações do Aplicativo");

        itens.add(item0);
        itens.add(item2);
        itens.add(item3);
        itens.add(item4);
        itens.add(item5);
        itens.add(item6);

        //Cria o adapter
        adapterListView = new AdapterListView(this, itens);

        //Define o Adapter
        listView.setAdapter(adapterListView);
        //Cor quando a lista Ã© selecionada para ralagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Sobre"))
        {
            //Mostra o Selecionado
            Toast.makeText(this, "Sobre", Toast.LENGTH_LONG).show();

            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,Sobre.class);

            //Carregando a Activity
            startActivity(navegar);
        }
        if(item.getTitle().toString().equals("Sair"))
        {
            //Sair do programa
            finish();
        }
        if(item.getTitle().toString().equals("Develop"))
        {
            //Mostra o Selecionado
            Toast.makeText(this, "Develop", Toast.LENGTH_LONG).show();

            //Mostrando resposta em outra Activity
            Intent navegar = new Intent(this,Develop.class);

            //Carregando a Activity
            startActivity(navegar);
        }
        return true;
    }

}
