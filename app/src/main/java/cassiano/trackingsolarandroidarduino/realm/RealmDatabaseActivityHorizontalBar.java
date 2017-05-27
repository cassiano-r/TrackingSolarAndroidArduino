package cassiano.trackingsolarandroidarduino.realm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import cassiano.trackingsolarandroidarduino.charting.animation.Easing;
import cassiano.trackingsolarandroidarduino.charting.charts.HorizontalBarChart;
import cassiano.trackingsolarandroidarduino.charting.data.realm.implementation.RealmBarData;
import cassiano.trackingsolarandroidarduino.charting.data.realm.implementation.RealmBarDataSet;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.IBarDataSet;
import cassiano.trackingsolarandroidarduino.charting.utils.ColorTemplate;
import cassiano.trackingsolarandroidarduino.R;
import cassiano.trackingsolarandroidarduino.custom.RealmDemoData;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityHorizontalBar extends RealmBaseActivity {

    private HorizontalBarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_horizontalbarchart_noseekbar);

        mChart = (HorizontalBarChart) findViewById(R.id.chart1);
        setup(mChart);

        mChart.getAxisLeft().setStartAtZero(true);
        mChart.setDrawValueAboveBar(false);
    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        // write some demo-data into the realm.io database
        writeToDBStack(50);

        // add data to the chart
        setData();
    }

    private void setData() {

        RealmResults<RealmDemoData> result = mRealm.allObjects(RealmDemoData.class);

        //RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "stackValues", "xIndex"); // normal entries
        RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "stackValues", "xIndex", "floatValue"); // stacked entries
        set.setColors(new int[]{ColorTemplate.rgb("#8BC34A"), ColorTemplate.rgb("#FFC107"), ColorTemplate.rgb("#9E9E9E")});
        set.setLabel("Mobile OS distribution");
        set.setStackLabels(new String[]{"iOS", "Android", "Other"});

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        RealmBarData data = new RealmBarData(result, "xValue", dataSets);
        styleData(data);
        data.setValueTextColor(Color.WHITE);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
