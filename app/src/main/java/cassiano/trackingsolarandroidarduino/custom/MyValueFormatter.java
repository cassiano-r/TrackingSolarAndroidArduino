package cassiano.trackingsolarandroidarduino.custom;

import cassiano.trackingsolarandroidarduino.charting.data.Entry;
import cassiano.trackingsolarandroidarduino.charting.formatter.ValueFormatter;
import cassiano.trackingsolarandroidarduino.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;
    
    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " $";
    }
}
