package edu.grcc.luisjuarez.handheldsecurityterms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DictionaryActivity extends AppCompatActivity {
    //global variables
    List<String> terms;        //store strings make available to listView listener
    List<String> definitions;  //store strings same as above
    List<String> termsSorted;  //store strings (not necessary but everything else is here)
    ListView listViewTerms;         //make this global so it is available to search menuItem
    ArrayAdapter arrayAdapter; //same for arrayAdapter
    Toast definitionToast; //Toast object

    //This is to add speech
    private TextToSpeech t1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        //get SQLite database data
        terms = (ArrayList<String>) getIntent().getSerializableExtra("terms");
        definitions = (ArrayList<String>) getIntent().getSerializableExtra("definitions");
        termsSorted = new ArrayList<String>(terms);  //resize termsSorted List
        Collections.copy(terms,termsSorted);         //copy terms into termsSorted
        Collections.sort(termsSorted);               //sort alphabetically termsSorted
        //put data into listView
        listViewTerms = (ListView) findViewById(R.id.listViewTerms);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, termsSorted);
        listViewTerms.setAdapter(arrayAdapter);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = t1.setLanguage(Locale.US);
                }
            }
        });


        //create a listViewTerms listener that will make toast with term and definition
        listViewTerms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String term = listViewTerms.getItemAtPosition(position).toString();
                String definition = "";
                for(int x = 0; x < terms.size(); x++){
                    if(terms.get(x).equals(term)){
                        definition = definitions.get(x);
                    }
                }
                if (definitionToast != null) {
                    definitionToast.cancel();
                }
                definitionToast = Toast.makeText(getApplicationContext(),
                        term + " = " + definition,
                        Toast.LENGTH_LONG);
                definitionToast.show();
                String toast_string = term + " = " + definition;
                t1.speak(toast_string, TextToSpeech.QUEUE_FLUSH, null);

            }
        });



    }

    // Next two overrides will show and operate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dictionary, menu);

        //react to search icon listener
        MenuItem item = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


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
