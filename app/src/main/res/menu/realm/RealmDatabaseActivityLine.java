package menu.realm;

import android.os.Bundle;
import android.view.WindowManager;

import cassiano.trackingsolarandroidarduino.charting.animation.Easing;
import cassiano.trackingsolarandroidarduino.charting.charts.LineChart;
import cassiano.trackingsolarandroidarduino.charting.data.realm.implementation.RealmLineData;
import cassiano.trackingsolarandroidarduino.charting.data.realm.implementation.RealmLineDataSet;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.ILineDataSet;
import cassiano.trackingsolarandroidarduino.charting.utils.ColorTemplate;
import cassiano.trackingsolarandroidarduino.R;
import cassiano.trackingsolarandroidarduino.custom.RealmDemoData;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityLine extends RealmBaseActivity {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart_noseekbar);

        mChart = (LineChart) findViewById(R.id.chart1);
        setup(mChart);

        mChart.getAxisLeft().setAxisMaxValue(150f);
        mChart.getAxisLeft().setAxisMinValue(0f);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        // write some demo-data into the realm.io database
        writeToDB(40);

        // add data to the chart
        setData();
    }

    private void setData() {

        RealmResults<RealmDemoData> result = mRealm.allObjects(RealmDemoData.class);

        RealmLineDataSet<RealmDemoData> set = new RealmLineDataSet<RealmDemoData>(result, "value", "xIndex");
        set.setDrawCubic(false);
        set.setLabel("Realm LineDataSet");
        set.setDrawCircleHole(false);
        set.setColor(ColorTemplate.rgb("#FF5722"));
        set.setCircleColor(ColorTemplate.rgb("#FF5722"));
        set.setLineWidth(1.8f);
        set.setCircleSize(3.6f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        RealmLineData data = new RealmLineData(result, "xValue", dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
