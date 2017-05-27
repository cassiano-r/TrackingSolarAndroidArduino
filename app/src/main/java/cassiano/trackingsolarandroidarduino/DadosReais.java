package cassiano.trackingsolarandroidarduino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DadosReais extends AppCompatActivity implements AsyncResponse {

    //implements AsyncResponse
       private ArrayList<Solar> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_reais);

        //SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final PostResponseAsyncTask taskRead = new PostResponseAsyncTask(DadosReais.this,this);

        //Componentes da tela
        TextView txtWatts = (TextView) findViewById(R.id.txtWatts);
        TextView txtTemperatura = (TextView) findViewById(R.id.txtTemperatura);
        TextView txtHumidade = (TextView) findViewById(R.id.txtHumidade);
        SpeedometerGauge speed = (SpeedometerGauge) findViewById(R.id.speedometer);
        speed.setMaxSpeed(50);
        speed.setMajorTickStep(1);
        //speed.setMinorTicks(30);
        //speed.addColoredRange(30, 90, Color.GREEN);
        //speed.addColoredRange(90, 180, Color.YELLOW);
        //speed.addColoredRange(180, 300, Color.RED);
        speed.setSpeed(10.0, true);
        txtWatts.setText("Watts: " + "10 W");
        txtTemperatura.setText("Temperatura: " + "24°C");
        txtHumidade.setText("Umidade: " + "10%");

        //Irá repetir a cada 3 segundos
        //-------------------------------------------------------------------------
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //yout method here
                            //Toast.makeText(DadosReais.this, "teste", Toast.LENGTH_LONG).show();
                            //buscaDados();
                            taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarDadosReais.php");
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000);
        //---------------------------------------------------------------------------
    }

    public void buscaDados()
    {
        try
        {
            //SharedPreferences
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final PostResponseAsyncTask taskRead = new PostResponseAsyncTask(DadosReais.this,this);
            taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarDadosReais.php");
        }
        catch (Exception ex)
        {
            //Toast.makeText(ChartActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }
    }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.bar, menu);
        getMenuInflater().inflate(R.menu.compartilhar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.atualizar: {
                //Atualiza Dados
                buscaDados();
                break;
            }
            case R.id.actionSave: {
                //Tira Foto
                takeScreenshot("screenShot");
                break;
            }
            case R.id.compartilhar:{
                //Tira Foto
                takeScreenshot("compartilhar");
                break;
            }
            case R.id.voltar: {
                //Fecha tela
                finish();
            }
        }
        return true;
    }

    private void takeScreenshot(String acao) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            if(acao == "screenShot")
            {
                openScreenshot(imageFile);
            }
            if(acao == "compartilhar")
            {
                shareScreenShot(imageFile);
            }

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
        //Notificação
        int id = 1;
        String contentTitle = "Tracking Solar";
        String contentText = "Screenshot!";
        NotificationUtil.create(DadosReais.this, contentTitle, contentText, id);
    }

    private void shareScreenShot(File imageFile)
    {
        // create bitmap screen capture
        View v = getWindow().getDecorView().getRootView();
        // Image
        v.setDrawingCacheEnabled(true);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + "temporary_file.jpg");
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Share
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        String filel = "file://" + Environment.getExternalStorageDirectory()
                + File.separator + "temporary_file.jpg";
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(filel));

        startActivity(Intent.createChooser(share, "Share Image"));

        //Notificação
        int id = 1;
        String contentTitle = "Tracking Solar";
        String contentText = "Compartilhando...";
        NotificationUtil.create(DadosReais.this, contentTitle, contentText, id);
    }

    @Override
    public void processFinish(String s)
    {
        try
        {
            productList = new JsonConverter<Solar>().toArrayList(s, Solar.class);
            //Componentes da tela
            TextView txtWatts = (TextView) findViewById(R.id.txtWatts);
            TextView txtTemperatura = (TextView) findViewById(R.id.txtTemperatura);
            TextView txtHumidade = (TextView) findViewById(R.id.txtHumidade);
            SpeedometerGauge speed = (SpeedometerGauge) findViewById(R.id.speedometer);
            speed.setMaxSpeed(50);
            speed.setMajorTickStep(1);

            for(Solar value: productList){
                speed.setSpeed(value.WATTS, true);
                txtWatts.setText("Watts: " + value.WATTS + " W");
                txtTemperatura.setText("Temperatura: " + value.TEMP + " °C");
                txtHumidade.setText("Umidade: " + value.HUMIDADE + " %");
            }
        }
        catch (Exception ex)
        {
            //Toast.makeText(ChartActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
