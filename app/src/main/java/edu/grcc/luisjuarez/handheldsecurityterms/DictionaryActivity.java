package edu.grcc.luisjuarez.handheldsecurityterms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DictionaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
    }

    // Next two overrides will show and operate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dictionary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.menuSearch:
                //We will be adding this code to search later

                break;
            case R.id.menuExit:
                System.exit(1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
//end of menu overides

}
