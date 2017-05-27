package cassiano.trackingsolarandroidarduino.charting.interfaces.datasets;

import cassiano.trackingsolarandroidarduino.charting.charts.ScatterChart;
import cassiano.trackingsolarandroidarduino.charting.data.Entry;

/**
 * Created by philipp on 21/10/15.
 */
public interface IScatterDataSet extends ILineScatterCandleRadarDataSet<Entry> {

    /**
     * Returns the currently set scatter shape size
     *
     * @return
     */
    float getScatterShapeSize();

    /**
     * Returns all the different scattershapes the chart uses
     *
     * @return
     */
    ScatterChart.ScatterShape getScatterShape();
}
