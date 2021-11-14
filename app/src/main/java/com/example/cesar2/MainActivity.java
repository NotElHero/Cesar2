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

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, AdapterView.OnItemClickListener {
    private static final char START_MINUS = 'a';
    private static final char END_MINUS = 'z';
    private static final char START_MAYUS = 'A';
    private static final char END_MAYUS = 'Z';
    private static final int CAESAR_KEY = 2;

    public int charCase = 1;
    public String writting = "";
    public String ciphred = "";

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
        LinearLayout.LayoutParams keyboardRowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
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
        ImageButton mayusButton = new ImageButton(this);
        mayusButton.setLayoutParams(buttonParams);
        mayusButton.setImageResource(R.drawable.mayus);
        mayusButton.setOnClickListener(this.onMayusButtonClick());


        keyboardRow.addView(mayusButton);
        keyboardLayout.addView(keyboardRow);

    }

    public View.OnClickListener charButtonClick(char charClicked){
        return new View.OnClickListener(){
        public void onClick(View v){
            writting += charClicked;
            ciphred += (char)(charClicked + MainActivity.CAESAR_KEY);
            TextView writtingText = (TextView) findViewById(R.id.writting_text);
            TextView ciphredText = (TextView) findViewById(R.id.ciphred_text);
            writtingText.setText(writting);
            ciphredText.setText(ciphred);

            if(charCase == 1){
                configureKeyboard(false);
                charCase = 0;
            }
        }};
    }
    //On short click mayus button
    public View.OnClickListener onMayusButtonClick(){
        return new View.OnClickListener(){
            public void onClick(View v){
                configureKeyboard(true);
                charCase++;
                charCase %=2;
            }

        };
    }

    @Override
    public boolean onLongClick(View v) {
        configureKeyboard(true);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

/*falta onlonglistener de la tecla mayus,
botones de cifrar y descifrar,
tecla delete,
icono,
paleta de colores,
spinner que cambie el caesar_key
 */