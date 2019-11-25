package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static SharedPreferences mPrefs;
    MathCharacter player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mPrefs = getPreferences(MODE_PRIVATE);

        //Currently only intend to have 1 saved character, but this will make it easier to change that later
        this.player = PreferenceHelper.loadCharacter("player1");
        this.player.setMoney(1000);
    }

    public void onClickChar(View view) {
        Intent intent = new Intent(this, ViewCharActivity.class);
        startActivity(intent);
    }

    public void onClickShop(View view) {
        Intent intent = new Intent(this, ViewShopActivity.class);
        startActivity(intent);
    }

    public void onClickFight(View view) {
        Intent intent = new Intent(this, MathFightActivity.class);
        startActivity(intent);
    }


    public static SharedPreferences getPreferences() {return mPrefs;}

}
