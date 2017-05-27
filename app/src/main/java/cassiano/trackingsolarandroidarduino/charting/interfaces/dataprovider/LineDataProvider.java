package cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider;

import cassiano.trackingsolarandroidarduino.charting.components.YAxis;
import cassiano.trackingsolarandroidarduino.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
