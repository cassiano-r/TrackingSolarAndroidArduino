package cassiano.trackingsolarandroidarduino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuBluetooth extends AppCompatActivity  {

    String[] items = new String[]{
            "Verificar e ativar Bluetooth",
            "Lista de Devices pareados",
            "Buscar devices",
            "Ficar visível",
            "Iniciar servidor Chat",
            "Voltar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bluetooth);

        GridView grid = (GridView) findViewById(R.id.grid1);
        grid.setOnItemClickListener(onGridViewItemClick());
        // Informar o adapter para preencher o GridView
        //grid.setAdapter(new ImagemAdapter(this, imagens));
        // listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        grid.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
    }

    // Evento ao clicar no item do grid
    private AdapterView.OnItemClickListener onGridViewItemClick() {
        return new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int posicao, long id)
            {
                if(id == 0)
                {
                    //Toast.makeText(MenuBluetooth.this, "Imagem selecionada: " + id, Toast.LENGTH_SHORT).show();
                     //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuBluetooth.this,BluetoothCheckActivity.class);
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 1)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuBluetooth.this,BluetoothPareados.class);
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 2)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuBluetooth.this,BluetoothBuscar.class);
                    //Carregando a Activity
                    startActivity(navegar);
                }
                if(id == 3)
                {
                    // Garante que alguém pode te encontrar
                    BluetoothUtil.makeVisible(MenuBluetooth.this, 300);
                }
                if(id == 4)
                {
                    //Mostrando resposta em outra Activity
                    Intent navegar = new Intent(MenuBluetooth.this,BluetoothSendReceive.class);
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
