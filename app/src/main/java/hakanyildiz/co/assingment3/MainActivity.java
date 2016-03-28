package hakanyildiz.co.assingment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import hakanyildiz.co.assingment3.MyClasses.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Button btnSwitch, btnConfig,btnLogout;
    TextView tvWelcome;
    ImageButton btnSearch;
    String searchType = "englishToTurkish";
    EditText arananKelime, cikanKelime;
    DatabaseHelper databaseHelper;
    SharedPreferences preferences; //preferences nesne referansı
    SharedPreferences.Editor editor; //preferences editor nesnesi referansı .prefernces nesnesine veri ekleyip cıkarmak için
    public static final String TAG = "HAKKE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences nesnesi oluşturuluyor ve prefernces referansına bağlanıyor
        databaseHelper = new DatabaseHelper(this);
        tvWelcome = (TextView) findViewById(R.id.tv_welcome);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSwitch = (Button) findViewById(R.id.btnSwitch);
        btnConfig = (Button) findViewById(R.id.btnConfig);
        arananKelime = (EditText) findViewById(R.id.arananKelime);
        cikanKelime = (EditText) findViewById(R.id.cikanKelime);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        setup();


    }

    private void setup() {
        String message = "";
        message = preferences.getString("email","deneme@example.com");

        tvWelcome.setText("Welcome, " + message + "");
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = preferences.edit();
                editor.putBoolean("login",false);
                editor.commit();

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

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
        if (arananKelime.getText().toString().length() > 0) {
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
