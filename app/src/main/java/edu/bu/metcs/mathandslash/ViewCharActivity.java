package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewCharActivity extends AppCompatActivity {
    private MathCharacter player;
    int origStr, origHealth, origTough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_char);


        //Set the toolbar as the activity's app bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.char_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.player = PreferenceHelper.reloadCurrentCharacter();

        updateTextView(R.id.char_lvl, this.player.getLevel());

        updateTextViewMax(R.id.char_hp, this.player.getHp(), this.player.getMaxHP());
        updateTextView(R.id.char_xp, this.player.getXp());
        updateTextView(R.id.char_remain, this.player.getPointsToUse());

        origStr = this.player.getStrength();
        updateTextView(R.id.charStr, origStr);

        origHealth = this.player.getHealth();
        updateTextView(R.id.charHealth, origHealth);

        origTough = this.player.getToughness();
        updateTextView(R.id.charTough, origTough);

    }

    private void updateTextView(int id, int update) {
        TextView tView = (TextView)findViewById(id);

        String base = tView.getText().toString();
        int index = base.indexOf(":");
        base = base.substring(0, index+1);
        tView.setText(base + " " + update);
    }

    private void updateTextViewMax(int id, int update, int max) {
        TextView tView = (TextView)findViewById(id);

        String base = tView.getText().toString();
        int index = base.indexOf(":");
        base = base.substring(0, index+1);
        tView.setText(base + " " + update + "/" + max);
    }


    public void onClickMinus(View view) {
        switch (view.getId()) {
            case R.id.charButtonHealthMinus:
                if (origHealth < this.player.getHealth()) {
                    this.player.modHealth(-1);
                    updateTextView(R.id.charHealth, this.player.getHealth());
                    updateTextView(R.id.char_hp, this.player.getMaxHP());
                    this.player.modPoints(1);
                }
                break;
            case R.id.charButtonStrMinus:
                if (origStr < this.player.getStrength()) {
                    this.player.modStr(-1);
                    updateTextView(R.id.charStr, this.player.getStrength());
                    this.player.modPoints(1);
                }
                break;
            case R.id.charButtonToughMinus:
                if (origTough < this.player.getToughness()) {
                    this.player.modTough(-1);
                    updateTextView(R.id.charTough, this.player.getToughness());
                    this.player.modPoints(1);
                }
                break;
        }

        updateTextView(R.id.char_remain, this.player.getPointsToUse());
    }

    public void onClickAdd(View view) {
        switch (view.getId()) {
            case R.id.charButtonHealthPlus:
                if (this.player.getPointsToUse() > 0) {
                    this.player.modHealth(1);
                    updateTextView(R.id.charHealth, this.player.getHealth());
                    updateTextView(R.id.char_hp, this.player.getMaxHP());
                    this.player.modPoints(-1);
                }
                break;
            case R.id.charButtonStrPlus:
                if (this.player.getPointsToUse() > 0) {
                    this.player.modStr(1);
                    updateTextView(R.id.charStr, this.player.getStrength());
                    this.player.modPoints(-1);
                }
                break;
            case R.id.charButtonToughPlus:
                if (this.player.getPointsToUse() > 0) {
                    this.player.modTough(1);
                    updateTextView(R.id.charTough, this.player.getToughness());
                    this.player.modPoints(-1);
                }
                break;
        }

        updateTextView(R.id.char_remain, this.player.getPointsToUse());
    }

    public void onClickSave(View view) {
        PreferenceHelper.save();
        finish();
    }

    public void onClickClose(View view) {
        finish();
    }
}
