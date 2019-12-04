package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//NOTE: I'm aware that the way this is implemented the player can just exit out and the fight will reset
// I'm ok with this, it's basically running away.
public class MathFightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_fight);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fight_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText fightInput = findViewById(R.id.fight_input);
        fightInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    Log.i("TESTT", "IME_ACTION_DONE");
                    return true;
                }
                return false;
            }
        });
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public void onClickAdd(View view) {
        Toast.makeText(this, "I pressed +", Toast.LENGTH_SHORT).show();
        hideButtons();
    }

    public void onClickMinus(View view) {
        Toast.makeText(this, "I pressed -", Toast.LENGTH_SHORT).show();
        hideButtons();
    }

    private void hideButtons() {
        findViewById(R.id.fight_add).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_sub).setVisibility(View.INVISIBLE);

        EditText input = findViewById(R.id.fight_input);
        input.setVisibility(View.VISIBLE);
        showSoftKeyboard(input);
    }

    private void showButtons() {
        findViewById(R.id.fight_add).setVisibility(View.VISIBLE);
        findViewById(R.id.fight_sub).setVisibility(View.VISIBLE);

        EditText input = findViewById(R.id.fight_input);
        input.setVisibility(View.INVISIBLE);
        showSoftKeyboard(input);
    }
}
