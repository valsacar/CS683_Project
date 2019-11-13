package edu.bu.metcs.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewCharActivity extends AppCompatActivity {
    MathCharacter player;
    int healthMod, strMod, toughMod = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_char);

        Intent intent=getIntent();
        MathCharacter character=(MathCharacter)intent.getSerializableExtra("character");

        this.player = character;
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateTextView(R.id.char_lvl, this.player.getLevel());
        updateTextView(R.id.charStr, this.player.getStrength());
        updateTextViewMax(R.id.char_hp, this.player.getHp(), this.player.getMaxHP());
        updateTextView(R.id.char_xp, this.player.getXp());
        updateTextView(R.id.char_remain, this.player.getPointsToUse());
        updateTextView(R.id.charHealth, this.player.getHealth());
        updateTextView(R.id.charTough, this.player.getToughness());

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

    private int getRemainingPoints() {
        return this.player.getPointsToUse() - healthMod - toughMod - strMod;
    }

    public void onClickMinus(View view) {
        switch (view.getId()) {
            case R.id.charButtonHealthMinus:
                if (healthMod > 0) {
                    healthMod--;
                    updateTextView(R.id.charHealth, this.player.getHealth() + healthMod);
                }
                break;
            case R.id.charButtonStrMinus:
                if (strMod > 0) {
                    strMod--;
                    updateTextView(R.id.charStr, this.player.getStrength() + strMod);
                }
                break;
            case R.id.charButtonToughMinus:
                if (toughMod > 0) {
                    toughMod--;
                    updateTextView(R.id.charTough, this.player.getToughness() + toughMod);
                }
                break;
        }

        updateTextView(R.id.char_remain, this.player.getPointsToUse() - toughMod - healthMod - strMod);
    }

    public void onClickAdd(View view) {
        switch (view.getId()) {
            case R.id.charButtonHealthPlus:
                if (getRemainingPoints() > 0) {
                    healthMod++;
                    updateTextView(R.id.charHealth, this.player.getHealth() + healthMod);
                }
                break;
            case R.id.charButtonStrPlus:
                if (getRemainingPoints() > 0) {
                    strMod++;
                    updateTextView(R.id.charStr, this.player.getStrength() + strMod);
                }
                break;
            case R.id.charButtonToughPlus:
                if (getRemainingPoints() > 0) {
                    toughMod++;
                    updateTextView(R.id.charTough, this.player.getToughness() + toughMod);
                }
                break;
        }

        updateTextView(R.id.char_remain, this.player.getPointsToUse() - toughMod - healthMod - strMod);
    }

    public void onClickSave(View view) {
        //TODO: Testing this will be removed and used in appropriate places
        this.player.addLevel();

        this.player.modHealth(healthMod);
        this.player.modStr(strMod);
        this.player.modTough(toughMod);
        this.player.modPoints(-(strMod+healthMod+toughMod));

        //This part stays
        Intent intent = new Intent();
        intent.putExtra("character", this.player);
        setResult(RESULT_OK, intent);
        finish();
    }
}
