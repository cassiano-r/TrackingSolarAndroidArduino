
package cassiano.trackingsolarandroidarduino.charting.buffer;

import cassiano.trackingsolarandroidarduino.charting.data.Entry;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.ILineDataSet;

public class CircleBuffer extends AbstractBuffer<ILineDataSet> {

    public CircleBuffer(int size) {
        super(size);
    }

    protected void addCircle(float x, float y) {
        buffer[index++] = x;
        buffer[index++] = y;
    }

    @Override
    public void feed(ILineDataSet data) {

        int size = (int)Math.ceil((mTo - mFrom) * phaseX + mFrom);

        for (int i = mFrom; i < size; i++) {

            Entry e = data.getEntryForIndex(i);
            addCircle(e.getXIndex(), e.getVal() * phaseY);
        }
        
        reset();
    }
}
