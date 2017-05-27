package cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider;

import cassiano.trackingsolarandroidarduino.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
