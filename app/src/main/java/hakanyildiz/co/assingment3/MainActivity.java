package hakanyildiz.co.assingment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import hakanyildiz.co.assingment3.MyClasses.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Button btnSwitch, btnConfig;
    ImageButton btnSearch;
    String searchType = "englishToTurkish";
    EditText arananKelime, cikanKelime;
    DatabaseHelper databaseHelper;
    public static final String TAG = "HAKKE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSwitch = (Button) findViewById(R.id.btnSwitch);
        btnConfig = (Button) findViewById(R.id.btnConfig);
        arananKelime = (EditText) findViewById(R.id.arananKelime);
        cikanKelime = (EditText) findViewById(R.id.cikanKelime);

        setup();


    }

    private void setup() {

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConfigActivity.class);
                startActivity(i);
            }
        });


        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arananKelime.setText("");
                cikanKelime.setText("");

                if (searchType == "turkishToEnglish") {
                    arananKelime.setHint("Enter some text");
                    searchType = "englishToTurkish";
                    btnSwitch.setText("EN > TR");
                } else if (searchType == "englishToTurkish") {
                    arananKelime.setHint("Kelime giriniz");
                    searchType = "turkishToEnglish";
                    btnSwitch.setText("TR > EN");
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"search e tıklandı.");
                String prefix = "";
                if (searchType == "turkishToEnglish") {
                    prefix = "tr-";
                } else if (searchType == "englishToTurkish") {
                    prefix = "en-";
                }
                Log.d(TAG,"Prefix => " + prefix);

                searchWord(prefix);

            }
        });


    }

    private void searchWord(String prefix) {
        if (arananKelime.getText().toString().length() > 3) { // 3 = prefix length e.g. "tr-"
            String aranan = arananKelime.getText().toString();

            aranan = prefix + aranan; // exp : word = "hakan" olsun ... prefix koyunca
            //  tr-hakan oluyor.. database tarafında split edilip işleme alınacak..
            Log.d(TAG,"prefix + aranan => " + aranan);
            String cikan = databaseHelper.findWord(aranan);

            Log.d(TAG,"cikan kelime =>" +  cikan);
            cikanKelime.setVisibility(View.VISIBLE);
            if (!cikan.isEmpty()) {
                cikanKelime.setText(cikan);
            } else {
                cikanKelime.setText("There is no record related to '" + aranan + "'");

            }
        }
        else
        {
            cikanKelime.setVisibility(View.VISIBLE);
            cikanKelime.setText("Please, enter the word above!");
        }
    }




}
