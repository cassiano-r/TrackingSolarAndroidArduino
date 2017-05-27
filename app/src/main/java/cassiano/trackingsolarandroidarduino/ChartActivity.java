package cassiano.trackingsolarandroidarduino;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import cassiano.trackingsolarandroidarduino.charting.charts.BarChart;
import cassiano.trackingsolarandroidarduino.charting.components.Legend;
import cassiano.trackingsolarandroidarduino.charting.components.XAxis;
import cassiano.trackingsolarandroidarduino.charting.components.YAxis;
import cassiano.trackingsolarandroidarduino.charting.data.BarData;
import cassiano.trackingsolarandroidarduino.charting.data.BarDataSet;
import cassiano.trackingsolarandroidarduino.charting.data.BarEntry;
import cassiano.trackingsolarandroidarduino.charting.data.Entry;
import cassiano.trackingsolarandroidarduino.charting.formatter.YAxisValueFormatter;
import cassiano.trackingsolarandroidarduino.charting.highlight.Highlight;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.IBarDataSet;
import cassiano.trackingsolarandroidarduino.charting.listener.OnChartValueSelectedListener;
import cassiano.trackingsolarandroidarduino.custom.MyYAxisValueFormatter;
import cassiano.trackingsolarandroidarduino.notimportant.DemoBase;


public class ChartActivity extends DemoBase implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener,AsyncResponse, AdapterView.OnItemClickListener {

    protected BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    private Typeface mTf;

    final String LOG = "ChartActivity";

    private ArrayList<Solar> productList;
    private ListView lvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart);
        
        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

       // mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        //setData(12, 50);
        //setData(5, 50);

        // setting data
        mSeekBarY.setProgress(50);
        mSeekBarX.setProgress(5);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

        // mChart.setDrawLegend(false);

        //SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Recupera informação da Activity Anterior (MainActivity)
        Toast.makeText(this,sharedPreferences.getString("caminho",null), Toast.LENGTH_LONG).show();

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(ChartActivity.this, this);
        //taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartWattsData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));

        try {
            if(sharedPreferences.getString("caminho",null) == "ChartWATTS")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartWattsData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ChartHUMIDADE")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartHumidadeData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ChartTEMP")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartTempData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ChartCORRENTE")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartCorrenteData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ChartTENSAO")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartTensaoData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ChartGERAL")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarChartGeralData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
        }
        catch (Exception ex)
        {
            //Toast.makeText(ChartActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinish(String s) {
        try
        {
            productList = new JsonConverter<Solar>().toArrayList(s, Solar.class);

            //SharedPreferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            //Valores de X e Y
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            ArrayList<Float> tHumidade = new ArrayList<Float>();
            ArrayList<Float> tWatts = new ArrayList<Float>();

            for(Solar value: productList){
                tHumidade.add(value.HUMIDADE);
                //Toast.makeText(ChartActivity.this, "humi=" + value.HUMIDADE, Toast.LENGTH_SHORT).show();
                tWatts.add(value.WATTS);
                //Toast.makeText(ChartActivity.this, "watts=" + value.WATTS, Toast.LENGTH_SHORT).show();

            }
            ArrayList<Float> tTemp = new ArrayList<Float>();
            for(Solar value: productList){
                tTemp.add(value.TEMP);
            }
            ArrayList<Float> tCorrente = new ArrayList<Float>();
            for(Solar value: productList){
                tCorrente.add(value.CORRENTE);
            }
            ArrayList<Float> tTensao = new ArrayList<Float>();
            for(Solar value: productList){
                tTensao.add(value.TENSAO);
            }
            ArrayList<String> tDt = new ArrayList<String>();
            for(Solar value: productList){
                tDt.add(value.DT);
            }

            //Colocando os dados
            //-----------------------------------------------------------------------------
            //Em X
            DemoBase teste = new DemoBase();
            if(sharedPreferences.getString("caminho",null) == "ChartWATTS")
            {
                for (int c = 0; c < tDt.size(); c++) {
                    teste.setmSolar(c, tDt.get(c).toString());
                    xVals.add(teste.mSolar[c % tDt.size()]);
                }
                //Em Y
                for(int i = 0; i < tWatts.size(); i++)
                {
                    yVals1.add(new BarEntry(tWatts.get(i), i));
                }
                BarDataSet set1 = new BarDataSet(yVals1, "WATTS (W)");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);

                mChart.setData(data);
            }
            if(sharedPreferences.getString("caminho",null) == "ChartHUMIDADE")
            {
                for (int c = 0; c < tDt.size(); c++) {
                    teste.setmSolar(c, tDt.get(c).toString());
                    xVals.add(teste.mSolar[c % tDt.size()]);
                }
                //Em Y
                for(int i = 0; i < tHumidade.size(); i++)
                {
                    yVals1.add(new BarEntry(tHumidade.get(i), i));
                }
                BarDataSet set1 = new BarDataSet(yVals1, "UMIDADE (%)");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);

                mChart.setData(data);
            }
            if(sharedPreferences.getString("caminho",null) == "ChartTEMP")
            {
                for (int c = 0; c < tDt.size(); c++) {
                    teste.setmSolar(c, tDt.get(c).toString());
                    xVals.add(teste.mSolar[c % tDt.size()]);
                }
                //Em Y
                for(int i = 0; i < tTemp.size(); i++)
                {
                    yVals1.add(new BarEntry(tTemp.get(i), i));
                }
                BarDataSet set1 = new BarDataSet(yVals1, "TEMPERATURA (°C)");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);

                mChart.setData(data);
            }
            if(sharedPreferences.getString("caminho",null) == "ChartCORRENTE")
            {
                for (int c = 0; c < tDt.size(); c++) {
                    teste.setmSolar(c, tDt.get(c).toString());
                    xVals.add(teste.mSolar[c % tDt.size()]);
                }
                //Em Y
                for(int i = 0; i < tCorrente.size(); i++)
                {
                    yVals1.add(new BarEntry(tCorrente.get(i), i));
                }
                BarDataSet set1 = new BarDataSet(yVals1, "CORRENTE (I)");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);

                mChart.setData(data);
            }
            if(sharedPreferences.getString("caminho",null) == "ChartTENSAO")
            {
                for (int c = 0; c < tDt.size(); c++) {
                    teste.setmSolar(c, tDt.get(c).toString());
                    xVals.add(teste.mSolar[c % tDt.size()]);
                }
                //Em Y
                for(int i = 0; i < tTensao.size(); i++)
                {
                    yVals1.add(new BarEntry(tTensao.get(i), i));
                }
                BarDataSet set1 = new BarDataSet(yVals1, "TENSÃO (V)");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);

                mChart.setData(data);
            }
            if(sharedPreferences.getString("caminho",null) == "ChartGERAL")
            {
                teste.setmSolar(0, "WATTS");
                teste.setmSolar(1, "HUMIDADE");
                teste.setmSolar(2, "TEMP");
                teste.setmSolar(3, "CORRENTE");
                teste.setmSolar(4, "TENSÃO");
                //Em X
                //teste.setmSolar(c, tDt.get(c).toString());
                xVals.add(teste.mSolar[0]);
                xVals.add(teste.mSolar[1]);
                xVals.add(teste.mSolar[2]);
                xVals.add(teste.mSolar[3]);
                xVals.add(teste.mSolar[4]);

                //Em Y
                yVals1.add(new BarEntry(tWatts.get(0), 0));
                yVals1.add(new BarEntry(tHumidade.get(1), 1));
                yVals1.add(new BarEntry(tTemp.get(2), 2));
                yVals1.add(new BarEntry(tCorrente.get(3), 3));
                yVals1.add(new BarEntry(tTensao.get(4), 4));

                BarDataSet set1 = new BarDataSet(yVals1, "GERAL)");
                set1.setBarSpacePercent(35f);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(xVals, dataSets);
                data.setValueTextSize(10f);
                data.setValueTypeface(mTf);

                mChart.setData(data);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(ChartActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        //for (int i = 0; i < count; i++) {
        //    xVals.add(mSolar[i % count]);
        //}

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "SolarTracking");
        set1.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        mChart.setData(data);
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleXIndex() + ", high: "
                        + mChart.getHighestVisibleXIndex());
    }

    public void onNothingSelected() {
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.bar, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
        String contentText = "Screenshot do Gráfico!";
        NotificationUtil.create(ChartActivity.this, contentTitle, contentText, id);
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
        String contentText = "Compartilhado Gráfico!";
        NotificationUtil.create(ChartActivity.this, contentTitle, contentText, id);
    }

}
