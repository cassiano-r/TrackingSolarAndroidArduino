package cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider;

import cassiano.trackingsolarandroidarduino.charting.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isDrawHighlightArrowEnabled();
}
