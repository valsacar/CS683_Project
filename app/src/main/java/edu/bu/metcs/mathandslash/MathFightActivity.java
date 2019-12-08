package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

//NOTE: I'm aware that the way this is implemented the player can just exit out and the fight will reset
// I'm ok with this, it's basically running away.
public class MathFightActivity extends AppCompatActivity {
    private MathBattle battle;
    private int currentTarget;
    private long potionStartTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_fight);

        findViewById(R.id.fight_choice1).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_choice2).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_choice3).setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fight_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText fightInput = findViewById(R.id.fight_input);
        fightInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    return checkAnswer(v.getText().toString().trim());
                }
                return false;
            }
        });


        if (this.battle == null) this.battle = new MathBattle(PreferenceHelper.getCurrentCharacter());
        updateHP();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Make sure that if they don't have the skill they don't see the buttons
        if (PreferenceHelper.getCurrentCharacter().getWeapon().getDivLevel() == 0) {
            findViewById(R.id.fight_div).setVisibility(View.INVISIBLE);
        }

        if (PreferenceHelper.getCurrentCharacter().getWeapon().getMultLevel() == 0) {
            findViewById(R.id.fight_mult).setVisibility(View.INVISIBLE);
        }
    }

    public void showSoftKeyboard(View view) {
        //if (view.requestFocus()) {
        view.requestFocus();
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        //}
    }

    public void hideSoftKeyboard(View view) {
       // if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);
       // }
    }

    private void updateHP() {
        TextView hp = findViewById(R.id.fight_hp);
        TextView enemyHP = findViewById(R.id.fight_enemy_hp);
        String hpString;
        MathCharacter player = PreferenceHelper.getCurrentCharacter();

        hpString = getString(R.string.char_hp) + player.getHp() + "/" + player.getMaxHP();
        hp.setText(hpString);
        hp.setTextColor(player.getHPColor());

        hpString = getString(R.string.enemy_hp) + this.battle.getEnemyHPString();
        enemyHP.setText(hpString);
        enemyHP.setTextColor(this.battle.getEnemyHPColor());
    }

    public void onClickAdd(View view) {
        doBattle(MathItem.ADDITION);
        this.currentTarget = MathBattle.FIGHT_ENEMY;
    }

    public void onClickMinus(View view) {
        doBattle(MathItem.SUBTRACTION);
        this.currentTarget = MathBattle.FIGHT_ENEMY;
    }

    public void onClickMult(View view) {
        doBattle(MathItem.MULTIPLICATION);
        this.currentTarget = MathBattle.FIGHT_ENEMY;
    }

    public void onClickDiv(View view) {
        doBattle(MathItem.DIVISION);
        this.currentTarget = MathBattle.FIGHT_ENEMY;
    }

    public void onClickPotion(View v) {
        if (PreferenceHelper.getCurrentCharacter().getPotions() <= 0) {
            Toast.makeText(this, getString(R.string.no_potions), Toast.LENGTH_LONG).show();
        } else {
            this.potionStartTime = SystemClock.elapsedRealtime();
            this.battle.doPotion();

            TextView type = findViewById(R.id.fight_type);
            type.setText(R.string.potion_healing);
            type.setVisibility(View.VISIBLE);

            showProblem();
        }
    }

    private void doBattle(String choice) {
        TextView type = findViewById(R.id.fight_type);
        type.setText(R.string.attack);
        type.setVisibility(View.VISIBLE);

        // Just in case
        if (this.battle == null) this.battle = new MathBattle(PreferenceHelper.getCurrentCharacter());

        this.battle.startAttack(choice);

        showProblem();
    }

    private Boolean checkAnswer(String ans) {
        TextView resultView = findViewById(R.id.fight_result);
        TextView typeView = findViewById(R.id.fight_type);
        String hit;

        if (this.potionStartTime != 0) {
            long time = SystemClock.elapsedRealtime() - this.potionStartTime;
            int healing = this.battle.doPotionHeal(time/1000, ans);

            hit = getString(R.string.you_healed) + " " + healing + '.';

            //If they were wrong.
            if (healing == 0) hit += " (" + getString(R.string.correct) + ": " + this.battle.getAnswer() + ")";
            this.potionStartTime = 0;
        } else {
            hit = this.battle.doAttack(this.currentTarget, ans);

            // If player hits enemy with wrong answer it's a miss, if enemy hits player with critical
            // then it was also a wrong answer
            if ((hit == MathBattle.HIT_MISS && this.currentTarget == MathBattle.FIGHT_ENEMY) ||
                    (hit == MathBattle.HIT_CRITICAL && this.currentTarget == MathBattle.FIGHT_PLAYER))
                hit += " (" + getString(R.string.correct) + ": " + this.battle.getAnswer() + ")";

            if (hit != MathBattle.HIT_MISS)
                hit = this.battle.hitModifier(this.currentTarget) + hit;
        }

        updateHP();
        resultView.setText(hit);
        resultView.setVisibility(View.VISIBLE);
        // So we can't run away and get full healed
        PreferenceHelper.save();

        //Let them read what happened
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.fight_result).setVisibility(View.INVISIBLE);
            }
        }, 2000);


        if (this.battle.getFighting()) {
            if (this.currentTarget == MathBattle.FIGHT_ENEMY) {
                this.battle.startDefense();
                this.currentTarget = MathBattle.FIGHT_PLAYER;

                typeView.setText(R.string.defense);
                showProblem();

                return true;
            } else {
                typeView.setText(R.string.attack);
                showButtons();
            }
        } else {
            Intent intent = new Intent(this, MathFightResultActivity.class);
            if (PreferenceHelper.getCurrentCharacter().getHp() <= 0){
                intent.putExtra("result", getString(R.string.result_loss));
            } else {
                intent.putExtra("result", getString(R.string.result_won));
                intent.putExtra("xp", this.battle.getXpReward());
                intent.putExtra("gold", this.battle.getGoldReward());
            }

            startActivity(intent);
            finish();
        }

        this.potionStartTime = 0;

        return false;
    }

    private void showProblem() {
        //indViewById(R.id.fight_type).setVisibility(View.INVISIBLE);
        TextView problem = findViewById(R.id.fight_problem);
        problem.setText(this.battle.getProblem());
        hideButtons();
        findViewById(R.id.fight_problem).setVisibility(View.VISIBLE);
    }

    private void hideButtons() {
        findViewById(R.id.fight_add).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_sub).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_mult).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_div).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_potion_button).setVisibility(View.INVISIBLE);

        if (this.battle.showBattleButtons() || this.potionStartTime != 0) {
            Button choice1 = findViewById(R.id.fight_choice1);
            Button choice2 = findViewById(R.id.fight_choice2);
            Button choice3 = findViewById(R.id.fight_choice3);

            List<String> answers = this.battle.getAnswers();

            choice1.setText(answers.get(0));
            choice2.setText(answers.get(1));
            choice3.setText(answers.get(2));

            choice1.setVisibility(View.VISIBLE);
            choice2.setVisibility(View.VISIBLE);
            choice3.setVisibility(View.VISIBLE);
        } else {
            EditText input = findViewById(R.id.fight_input);
            input.setText("");
            input.setVisibility(View.VISIBLE);
            showSoftKeyboard(input);
        }
    }

    private void showButtons() {
        findViewById(R.id.fight_add).setVisibility(View.VISIBLE);
        findViewById(R.id.fight_sub).setVisibility(View.VISIBLE);
        showMultDivButtons();
        findViewById(R.id.fight_potion_button).setVisibility(View.VISIBLE);

        findViewById(R.id.fight_choice1).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_choice2).setVisibility(View.INVISIBLE);
        findViewById(R.id.fight_choice3).setVisibility(View.INVISIBLE);

        EditText input = findViewById(R.id.fight_input);
        input.setVisibility(View.INVISIBLE);
        //showSoftKeyboard(input);
        findViewById(R.id.fight_problem).setVisibility(View.INVISIBLE);
        hideSoftKeyboard(findViewById(R.id.fight_input));
    }

    private void showMultDivButtons() {
        // Make sure that if they don't have the skill they don't see the buttons
        if (PreferenceHelper.getCurrentCharacter().getWeapon().getDivLevel() != 0) {
            findViewById(R.id.fight_div).setVisibility(View.VISIBLE);
        }

        if (PreferenceHelper.getCurrentCharacter().getWeapon().getMultLevel() != 0) {
            findViewById(R.id.fight_mult).setVisibility(View.VISIBLE);
        }
    }

    public void onClickChoice(View v) {
        Button btn = (Button)findViewById(v.getId());

        checkAnswer(btn.getText().toString().trim());
    }
}
