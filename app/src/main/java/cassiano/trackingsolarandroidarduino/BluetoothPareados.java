package cassiano.trackingsolarandroidarduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BluetoothPareados extends BluetoothCheckActivity implements AdapterView.OnItemClickListener {
    // devices pareados
    protected List<BluetoothDevice> lista;
    private ListView listView;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_bluetooth_pareados);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // O BluetoothAdapter é iniciado na classe-mãe
        lista = new ArrayList<BluetoothDevice>(btfAdapter.getBondedDevices());
        // Cria o array com o nome de cada device
        List<String> nomes = new ArrayList<String>();
        for (BluetoothDevice device : lista) {
            // Neste exemplo, esta variável boolean sempre será true, pois esta lista é
            // somente dos pareados.
            boolean pareado = device.getBondState() == BluetoothDevice.BOND_BONDED;
            nomes.add(device.getName() + " - " + device.getAddress() +
                    (pareado ? " *pareado" : ""));
        }
        // Cria o adapter para popular o ListView
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layout, nomes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Recupera o device selecionado
        BluetoothDevice device = lista.get(position);
        String msg = device.getName() + " - " + device.getAddress();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        // Abre a tela do chat
        //Intent intent = new Intent(this, BluetoothChatClientActivity.class);
        //intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
        //startActivity(intent);
    }
}
