package com.example.cesar2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private static final char START_MINUS = 'a';
    private static final char END_MINUS = 'z';
    private static final char START_MAYUS = 'A';
    private static final char END_MAYUS = 'Z';
    public int CAESAR_KEY = 2;

    public int charCase = 0;
    public String writting = "";
    public String ciphred = "";
    Spinner optionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean lockMayus = false;
        if(charCase >0) lockMayus = true;
        this.configureKeyboard(lockMayus);
    }

    public void configureKeyboard(boolean lockMayus){
        //Retrieves and resets layout from activity
        LinearLayout keyboardLayout = (LinearLayout) findViewById(R.id.keyboard_layout);
        keyboardLayout.removeAllViews();

        //Layout config
        LinearLayout.LayoutParams keyboardRowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        LinearLayout keyboardRow = null;

        //Add character buttons
        Button button;
        char initialChar = lockMayus ? MainActivity.START_MAYUS : MainActivity.START_MINUS;
        char endChar = lockMayus ? MainActivity.END_MAYUS : MainActivity.END_MINUS;
        for(char i = initialChar; i<=endChar; i++) {
            if((i - initialChar) % ((endChar - initialChar) / 3) == 0){
                if(keyboardRow != null){
                    keyboardLayout.addView(keyboardRow);
                }
                keyboardRow = new LinearLayout(this);
                keyboardRow.setOrientation(LinearLayout.HORIZONTAL);
                keyboardRow.setLayoutParams(keyboardRowParams);
            }
            String keyboardCharacter  = Character.toString(i);
            button = new Button(this);
            button.setOnClickListener(this.charButtonClick(i));
            button.setAllCaps(false);
            button.setText(keyboardCharacter);
            button.setLayoutParams(buttonParams);
            keyboardRow.addView(button);
        }

        //Add spetial buttons
        //Mayus
        ImageButton mayusButton = new ImageButton(this);
        mayusButton.setLayoutParams(buttonParams);
        mayusButton.setImageResource(R.drawable.mayus);
        mayusButton.setOnClickListener(this.onMayusButtonClick());
        mayusButton.setOnLongClickListener(this.onLongMayusButtonClick());


        //Code buttons and decode buttons
        Button codeButton = (Button)findViewById(R.id.code_button);
        Button decodeButton = (Button)findViewById(R.id.decode_button);
        codeButton.setOnClickListener(this.codeButtonClick());
        decodeButton.setOnClickListener(this.decodeButtonClick());

        //Spinner Spinner optionSpinner
        optionSpinner = (Spinner)findViewById(R.id.option_spinner);
        Integer[] optionArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.option_array, optionArray);
        optionSpinner.setAdapter(adapter);

        keyboardRow.addView(mayusButton);
        keyboardLayout.addView(keyboardRow);



    }
    //code button
    public View.OnClickListener codeButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView ciphredText = (TextView) findViewById(R.id.ciphred_text);
                ciphredText.setText(ciphred);
            }
        };
    }

    //decode button
    public View.OnClickListener decodeButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView ciphredText = (TextView) findViewById(R.id.ciphred_text);
                ciphredText.setText(writting);
            }
        };
    }

    //keyboard buttons
    public View.OnClickListener charButtonClick(char charClicked){
        return new View.OnClickListener(){
        public void onClick(View v){

            //System.out.print(key);
            configureKeyboard(false);
            writting += charClicked;
            int key=Integer.parseInt(optionSpinner.getSelectedItem().toString());
            //Si se cambia el CAESAR_KEY por key, el spinner funciona, pero la codificacion falla
            ciphred += (char)(charClicked + CAESAR_KEY);
            TextView writtingText = (TextView) findViewById(R.id.writting_text);
            writtingText.setText(writting);


            if (charCase == 1){
                configureKeyboard(true);
            }

        }};
    }

    //On short click mayus button
    public View.OnClickListener onMayusButtonClick(){
        return new View.OnClickListener(){
            public void onClick(View v){
                charCase = 1;
                if(charCase == 1){
                    configureKeyboard(true);
                    charCase = 0;
                }
            }

        };
    }

    //On long click mayus button
    public View.OnLongClickListener onLongMayusButtonClick(){
        return new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                configureKeyboard(true);
                charCase = 1;
                return true;
            }
        };
    }
}

/*
spinner que cambie el caesar_key
 */