package hakanyildiz.co.assingment3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import hakanyildiz.co.assingment3.fragments.AddWordFragment;
import hakanyildiz.co.assingment3.fragments.DeleteWordFragment;
import hakanyildiz.co.assingment3.fragments.UpdateWordFragment;

public class ConfigActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Your toolbar is now an action bar and you can use it like you always do, for example:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        AddWordFragment addWordFragment = AddWordFragment.newInstance("", "");

        transaction.add(R.id.fragment, addWordFragment);
        transaction.commit();

    }

    public void selectFragment(View view) {
        Fragment newFragment;
        if (view == findViewById(R.id.btnAddWord)) {

            newFragment = AddWordFragment.newInstance("", "");
            Log.d("HAKKE", "Add Word Button Click.");
        } else if (view == findViewById(R.id.btnUpdateWord)) {
            newFragment = UpdateWordFragment.newInstance("", "");
            Log.d("HAKKE", "UpdateWordFragment Word Button Click.");

        } else if (view == findViewById(R.id.btnDeleteWord)) {
            newFragment = DeleteWordFragment.newInstance("", "");
            Log.d("HAKKE", "DeleteWordFragment Word Button Click.");

        } else {
            newFragment = AddWordFragment.newInstance("", "");
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newFragment);
        // transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

        //Another Solution 1
        /*
        int id = item.getItemId();
        if( id == android.R.id.home)
        {
            onBackPressed();
        }
        */

        //Another Solution 2
        /*
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        */
        // yada ConfigActivity e gelip bi Parent eklememiz lazım. Burayı yapmak istemiyorsak:
       /*  <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value="hakanyildiz.co.assingment3.MainActivity" />
            */

    }
}
