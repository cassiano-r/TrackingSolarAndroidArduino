package cassiano.trackingsolarandroidarduino;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

//import com.nostra13.universalimageloader.core.ImageLoader;


public class ReportActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener{

    final String LOG = "ReportActivity";

    private ArrayList<Solar> productList;
    private ListView lvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(ReportActivity.this, this);

        try {
            if(sharedPreferences.getString("caminho",null) == "ReportWATTS")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarReportWattsData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ReportHUMIDADE")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarReportHumidadeData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ReportTEMP")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarReportTempData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ReportCORRENTE")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarReportCorrenteData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ReportTENSAO")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarReportTensaoData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
            if(sharedPreferences.getString("caminho",null) == "ReportGERAL")
            {
                taskRead.execute("http://" + sharedPreferences.getString("ipServidor",null) + "/trackingsolar/solarReportGeralData.php?data1=" + sharedPreferences.getString("data1",null) + "&data2="+ sharedPreferences.getString("data2",null));
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(ReportActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void processFinish(String s) {

        try
        {
            productList = new JsonConverter<Solar>().toArrayList(s, Solar.class);

            //SharedPreferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            BindDictionary<Solar> dict = new BindDictionary<Solar>();

            if(sharedPreferences.getString("caminho",null) == "ReportWATTS")
            {
                dict.addStringField(R.id.DT, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "DT: " + product.DT;
                    }
                });
                dict.addStringField(R.id.WATTS, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "W: " + product.WATTS;
                    }
                });
                //FunDapter
                FunDapter<Solar> adapter = new FunDapter<>(ReportActivity.this, productList, R.layout.layout_report_watts_data, dict);

                lvProduct = (ListView)findViewById(R.id.lvProduct);
                lvProduct.setAdapter(adapter);
                lvProduct.setOnItemClickListener(this);
            }
            if(sharedPreferences.getString("caminho",null) == "ReportHUMIDADE")
            {
                dict.addStringField(R.id.DT, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "DT: " + product.DT;
                    }
                });
                dict.addStringField(R.id.HUMIDADE, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "H: " + product.HUMIDADE;
                    }
                });
                //FunDapter
                FunDapter<Solar> adapter = new FunDapter<>(ReportActivity.this, productList, R.layout.layout_report_humidade_data, dict);

                lvProduct = (ListView)findViewById(R.id.lvProduct);
                lvProduct.setAdapter(adapter);
                lvProduct.setOnItemClickListener(this);
            }
            if(sharedPreferences.getString("caminho",null) == "ReportTEMP")
            {
                dict.addStringField(R.id.DT, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "DT: " + product.DT;
                    }
                });
                dict.addStringField(R.id.TEMP, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "TMP: " + product.TEMP;
                    }
                });
                //FunDapter
                FunDapter<Solar> adapter = new FunDapter<>(ReportActivity.this, productList, R.layout.layout_report_temp_data, dict);

                lvProduct = (ListView)findViewById(R.id.lvProduct);
                lvProduct.setAdapter(adapter);
                lvProduct.setOnItemClickListener(this);
            }
            if(sharedPreferences.getString("caminho",null) == "ReportCORRENTE")
            {
                dict.addStringField(R.id.DT, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "DT: " + product.DT;
                    }
                });
                dict.addStringField(R.id.CORRENTE, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "C: " + product.CORRENTE;
                    }
                });
                //FunDapter
                FunDapter<Solar> adapter = new FunDapter<>(ReportActivity.this, productList, R.layout.layout_report_corrente_data, dict);

                lvProduct = (ListView)findViewById(R.id.lvProduct);
                lvProduct.setAdapter(adapter);
                lvProduct.setOnItemClickListener(this);
            }
            if(sharedPreferences.getString("caminho",null) == "ReportTENSAO")
            {
                dict.addStringField(R.id.DT, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "DT: " + product.DT;
                    }
                });
                dict.addStringField(R.id.TENSAO, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "TS: " + product.TENSAO;
                    }
                });
                //FunDapter
                FunDapter<Solar> adapter = new FunDapter<>(ReportActivity.this, productList, R.layout.layout_report_tensao_data, dict);

                lvProduct = (ListView)findViewById(R.id.lvProduct);
                lvProduct.setAdapter(adapter);
                lvProduct.setOnItemClickListener(this);
            }
            if(sharedPreferences.getString("caminho",null) == "ReportGERAL")
            {
                dict.addStringField(R.id.ID, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "ID: " + product.ID;
                    }
                });
                dict.addStringField(R.id.DT, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "DT: " + product.DT;
                    }
                });
                dict.addStringField(R.id.WATTS, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "W: " + product.WATTS;
                    }
                });
                dict.addStringField(R.id.HUMIDADE, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "H: " + product.HUMIDADE;
                    }
                });
                dict.addStringField(R.id.TEMP, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "TMP: " + product.TEMP;
                    }
                });
                dict.addStringField(R.id.CORRENTE, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "C: " + product.CORRENTE;
                    }
                });
                dict.addStringField(R.id.TENSAO, new StringExtractor<Solar>() {
                    @Override
                    public String getStringValue(Solar product, int position) {
                        return "TS: " + product.TENSAO;
                    }
                });
                //FunDapter
                FunDapter<Solar> adapter = new FunDapter<>(ReportActivity.this, productList, R.layout.layout_solar, dict);

                lvProduct = (ListView)findViewById(R.id.lvProduct);
                lvProduct.setAdapter(adapter);
                lvProduct.setOnItemClickListener(this);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(ReportActivity.this, "Dados não encontrados!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Product selectedProduct = productList.get(position);
        //Intent in = new Intent(ListActivity.this, DetailActivity.class);
        //in.putExtra("product", (Serializable) selectedProduct);
        //startActivity(in);
    }


}
