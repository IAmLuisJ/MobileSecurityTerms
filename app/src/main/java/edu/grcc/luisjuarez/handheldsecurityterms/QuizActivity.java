package edu.grcc.luisjuarez.handheldsecurityterms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    //global variables
    List<String> terms;  //store strings
    List<String> definitions;  //store strings
    String term;
    String definition;
    List<String> possibleDefinitions;
    boolean cheat = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        refreshQuiz();

        //set a listener to the textview that says {New Word}
        TextView mNewWordTextview = (TextView) findViewById(R.id.textViewNewWord);
        mNewWordTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshQuiz();
                String toastString = "Created a new word.";
                Toast.makeText(QuizActivity.this, toastString, Toast.LENGTH_SHORT).show();
            }
        });

        //this code will react to radiobutton pushes
        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //only run this if a radio button is clicked
                if (null != rb && isRadioChecked()) {
                    String toastString;
                    //if correct
                    if (rb.getText().toString().equals(definition)) {
                        toastString = "Correct! " + term + " = " + definition + ". Creating a new word.";
                        //this creates a pause and then puts a new question on the screen
                        //     you have to run in seperate thread or it won't work right.
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }   //pause for 3 seconds
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshQuiz();
                                    }
                                });
                            }
                        }).start();
                    } else {
                        toastString = "Wrong, try again.";
                    }
                    Toast.makeText(QuizActivity.this, toastString, Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    // Next two overrides will show and operate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_quiz, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuExit:
                System.exit(1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //This method puts new questions/answers on screen
    void refreshQuiz() {
        //if terms and definitions are not filled, they are passed by intent
        if (terms == null) {
            terms = (ArrayList<String>) getIntent().getSerializableExtra("terms");
            definitions = (ArrayList<String>) getIntent().getSerializableExtra("definitions");
        }
        //get random term and definition
        Random rand = new Random();
        int index = rand.nextInt(terms.size());
        term = terms.get(index);
        definition = definitions.get(index);
        //clear possibleDefinitions, add definition and 3 other random definitions
        possibleDefinitions = new ArrayList();
        possibleDefinitions.add(definition);
        for (int x = 0; x < 3; x++) {
            index = rand.nextInt(terms.size());
            possibleDefinitions.add(definitions.get(index));
        }
        //shuffle possibleDefinitions
        Collections.shuffle(possibleDefinitions);
        //create link to radio group and clear all checks
        RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.clearCheck();
        //get links to textviews and radiobuttons
        TextView textViewTerm = (TextView) findViewById(R.id.textViewTerm);
        RadioButton radiobutton_1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton radiobutton_2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radiobutton_3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton radiobutton_4 = (RadioButton) findViewById(R.id.radioButton4);
        //set values for textviews and radiobuttons
        if (cheat) textViewTerm.setText("Term is:    " + term + "  (" + definition + ")");
        else textViewTerm.setText("Term is:    " + term);
        radiobutton_1.setText(possibleDefinitions.get(0));
        radiobutton_2.setText(possibleDefinitions.get(1));
        radiobutton_3.setText(possibleDefinitions.get(2));
        radiobutton_4.setText(possibleDefinitions.get(3));
    }

    //I created this method because the normal RadioButtonGroup.getCheckedRadioButtonId() == -1
//                      has some issues!!!
    private boolean isRadioChecked() {
        boolean isChecked = false;
        final RadioButton mButton1 = (RadioButton) findViewById(R.id.radioButton1);
        final RadioButton mButton2 = (RadioButton) findViewById(R.id.radioButton2);
        final RadioButton mButton3 = (RadioButton) findViewById(R.id.radioButton3);
        final RadioButton mButton4 = (RadioButton) findViewById(R.id.radioButton4);
        return mButton1.isChecked() | mButton2.isChecked() | mButton3.isChecked() | mButton4.isChecked();

    }
}
