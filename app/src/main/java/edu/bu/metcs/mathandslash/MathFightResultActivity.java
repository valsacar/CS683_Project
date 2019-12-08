package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MathFightResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_fight_result);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        TextView resultTV = findViewById(R.id.result_result);
        resultTV.setText(result);

        ImageView image = findViewById(R.id.result_image);


        if (result.equals(getString(R.string.result_won))) {
            image.setImageDrawable(getDrawable(R.drawable.result_player_won));
            MathCharacter player = PreferenceHelper.getCurrentCharacter();
            int currentLvl = player.getLevel();
            int xp = intent.getIntExtra("xp", 0);
            int gold = intent.getIntExtra("gold", 0);

            player.gainXp(xp);
            player.gainMoney(gold);

            if (player.getLevel() > currentLvl) findViewById(R.id.result_level).setVisibility(View.VISIBLE);
            else findViewById(R.id.result_level).setVisibility(View.INVISIBLE);

            TextView resultGold = findViewById(R.id.result_gold);
            TextView resultXP = findViewById(R.id.result_xp);

            resultGold.setText(getString(R.string.gold_gain) + gold);
            resultGold.setVisibility(View.VISIBLE);

            resultXP.setText(getString(R.string.xp_gain) + xp);
            resultXP.setVisibility(View.VISIBLE);
        } else {
            image.setImageDrawable(getDrawable(R.drawable.result_player_lost));
            findViewById(R.id.result_level).setVisibility(View.INVISIBLE);

            findViewById(R.id.result_gold).setVisibility(View.INVISIBLE);
            findViewById(R.id.result_xp).setVisibility(View.INVISIBLE);
        }

        PreferenceHelper.getCurrentCharacter().fullHeal();
        PreferenceHelper.save();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClickOk(View view) {
        finish();
    }
}
