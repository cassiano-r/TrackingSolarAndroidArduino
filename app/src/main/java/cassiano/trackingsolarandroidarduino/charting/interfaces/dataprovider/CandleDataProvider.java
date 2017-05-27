package cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider;

import cassiano.trackingsolarandroidarduino.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
