package cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider;

import cassiano.trackingsolarandroidarduino.charting.components.YAxis.AxisDependency;
import cassiano.trackingsolarandroidarduino.charting.data.BarLineScatterCandleBubbleData;
import cassiano.trackingsolarandroidarduino.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    BarLineScatterCandleBubbleData getData();
}
