package cassiano.trackingsolarandroidarduino.custom;

import cassiano.trackingsolarandroidarduino.charting.formatter.FillFormatter;
import cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider.LineDataProvider;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.ILineDataSet;

/**
 * Created by Philipp Jahoda on 12/09/15.
 */
public class MyFillFormatter implements FillFormatter {

    private float mFillPos = 0f;

    public MyFillFormatter(float fillpos) {
        this.mFillPos = fillpos;
    }

    @Override
    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
        // your logic could be here
        return mFillPos;
    }
}
