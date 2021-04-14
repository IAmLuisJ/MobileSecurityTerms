package edu.grcc.luisjuarez.handheldsecurityterms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class SwitchBoardActivity extends AppCompatActivity {
    List<String> terms;  //store strings
    List<String> definitions;  //store strings
    String databaseName = "TermsDefinitions.db";  //insert your database name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchboard);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this, databaseName);
        databaseAccess.open();

        terms = databaseAccess.getString("Terms", "term");
        definitions = databaseAccess.getString("Terms", "definition");

        TextView textView = (TextView) findViewById(R.id.textViewRecords);
        if(terms.size()>0){
            textView.setText("Database Connected with " + terms.size() + " records.");
        }
        else{
            textView.setText("Database Error!!");
        }


    }

    // Next two overrides will show and operate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_switchboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.menuDictionary:
                intent = new Intent(this, DictionaryActivity.class);
//To serialize an object means to convert its state to byte stream so that it can be read to get the object back.
                intent.putExtra("terms", (Serializable) terms);
                intent.putExtra("definitions", (Serializable) definitions);
                this.startActivity(intent);
                break;
            case R.id.menuQuiz:
                intent = new Intent(this, QuizActivity.class);
                intent.putExtra("terms", (Serializable) terms);
                intent.putExtra("definitions", (Serializable) definitions);
                this.startActivity(intent);
                break;
            case R.id.menuExit:
                System.exit(1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
//end of menu overrides


}
