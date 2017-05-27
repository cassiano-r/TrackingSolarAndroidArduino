package cassiano.trackingsolarandroidarduino.realm;

import android.os.Bundle;
import android.view.WindowManager;

import cassiano.trackingsolarandroidarduino.charting.animation.Easing;
import cassiano.trackingsolarandroidarduino.charting.charts.BubbleChart;
import cassiano.trackingsolarandroidarduino.charting.data.realm.implementation.RealmBubbleData;
import cassiano.trackingsolarandroidarduino.charting.data.realm.implementation.RealmBubbleDataSet;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.IBubbleDataSet;
import cassiano.trackingsolarandroidarduino.charting.utils.ColorTemplate;
import cassiano.trackingsolarandroidarduino.R;
import cassiano.trackingsolarandroidarduino.custom.RealmDemoData;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityBubble extends RealmBaseActivity {

    private BubbleChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bubblechart_noseekbar);

        mChart = (BubbleChart) findViewById(R.id.chart1);
        setup(mChart);

        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.setPinchZoom(true);
    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        // write some demo-data into the realm.io database
        writeToDBBubble(10);

        // add data to the chart
        setData();
    }

    private void setData() {

        RealmResults<RealmDemoData> result = mRealm.allObjects(RealmDemoData.class);

        RealmBubbleDataSet<RealmDemoData> set = new RealmBubbleDataSet<RealmDemoData>(result, "value", "xIndex", "bubbleSize");
        set.setLabel("Realm BubbleDataSet");
        set.setColors(ColorTemplate.COLORFUL_COLORS, 110);

        ArrayList<IBubbleDataSet> dataSets = new ArrayList<IBubbleDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        RealmBubbleData data = new RealmBubbleData(result, "xValue", dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
