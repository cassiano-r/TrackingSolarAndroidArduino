package cassiano.trackingsolarandroidarduino;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BluetoothCheckActivity extends AppCompatActivity {
    protected static final String TAG = "livroandroid";
    protected BluetoothAdapter btfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_check);

        // Bluetooth adapter
        btfAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btfAdapter == null) {
            Toast.makeText(this, "Bluetooth não disponível neste dispositivo.", Toast.LENGTH_LONG).show();
            // Vamos fechar a activity neste caso
            finish();
            return;
        }
        // Se o bluetooth não está ligado
        if (btfAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth está ligado!", Toast.LENGTH_LONG).show();
        } else { // Pede pro usuário ligar o bluetooth
            // <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 0);
        }

    }
}
