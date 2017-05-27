package cassiano.trackingsolarandroidarduino.charting.interfaces.dataprovider;

import android.graphics.PointF;
import android.graphics.RectF;

import cassiano.trackingsolarandroidarduino.charting.data.ChartData;
import cassiano.trackingsolarandroidarduino.charting.formatter.ValueFormatter;

/**
 * Interface that provides everything there is to know about the dimensions,
 * bounds, and range of the chart.
 * 
 * @author Philipp Jahoda
 */
public interface ChartInterface {

    float getXChartMin();

    float getXChartMax();

    float getYChartMin();

    float getYChartMax();
    
    int getXValCount();

    int getWidth();

    int getHeight();

    PointF getCenterOfView();

    PointF getCenterOffsets();

    RectF getContentRect();
    
    ValueFormatter getDefaultValueFormatter();

    ChartData getData();
}
