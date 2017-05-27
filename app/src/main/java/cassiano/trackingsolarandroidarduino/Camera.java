package cassiano.trackingsolarandroidarduino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import cassiano.trackingsolarandroidarduino.ImageResizeUtils;
import cassiano.trackingsolarandroidarduino.SDCardUtils;

public class Camera extends AppCompatActivity {

    // Caminho para salvar o arquivo
    private File file;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Cria o caminho do arquivo no sdcard
        // /storage/sdcard/Android/data/br.com.livroandroid.multimidia/files/Pictures/foto.jpg
        file = SDCardUtils.getPrivateFile(getBaseContext(), "foto.jpg", Environment.DIRECTORY_PICTURES);
        // Chama a intent informando o arquivo para salvar a foto
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(i, 0);

        //Fecha Activity
        finish();

    }
}
