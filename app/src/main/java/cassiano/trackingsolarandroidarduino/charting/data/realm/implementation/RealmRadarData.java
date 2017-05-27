package cassiano.trackingsolarandroidarduino.charting.data.realm.implementation;

import cassiano.trackingsolarandroidarduino.charting.data.RadarData;
import cassiano.trackingsolarandroidarduino.charting.data.realm.base.RealmUtils;
import cassiano.trackingsolarandroidarduino.charting.interfaces.datasets.IRadarDataSet;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 19/12/15.
 */
public class RealmRadarData extends RadarData{

    public RealmRadarData(RealmResults<? extends RealmObject> result, String xValuesField, List<IRadarDataSet> dataSets) {
        super(RealmUtils.toXVals(result, xValuesField), dataSets);
    }
}
