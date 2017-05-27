package cassiano.trackingsolarandroidarduino.custom;

import java.text.DecimalFormat;

import cassiano.trackingsolarandroidarduino.charting.components.YAxis;
import cassiano.trackingsolarandroidarduino.charting.formatter.YAxisValueFormatter;

public class MyYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return mFormat.format(value) + " $";
    }
}
