package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewShopActivity extends AppCompatActivity {
    private MathCharacter player;
    BuyMathItemFragment weaponFragment;
    BuyMathItemFragment armorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop);

        //Set the toolbar as the activity's app bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.shop_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.player = PreferenceHelper.getCurrentCharacter();

        this.weaponFragment = new BuyMathItemFragment();

        weaponFragment.setItem(new MathWeapon(this.player.getWeapon()));
        weaponFragment.setMoney(this.player.getMoney());
        weaponFragment.setType(MathWeapon.TYPE_STRING);

        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();
        // add the weapon fragment into the transaction
        transaction.add(R.id.shop_addition, weaponFragment, MathWeapon.TYPE_STRING);
        transaction.hide(weaponFragment);


        this.armorFragment = new BuyMathItemFragment();

        armorFragment.setItem(new MathArmor(this.player.getArmor()));
        armorFragment.setMoney(this.player.getMoney());
        armorFragment.setType(MathArmor.TYPE_STRING);

        // add the armor fragment into the transaction
        transaction.add(R.id.shop_addition, armorFragment, MathArmor.TYPE_STRING);
        transaction.hide(armorFragment);
        // commit the transaction.
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        this.player = PreferenceHelper.reloadCurrentCharacter();

        updateTextView(R.id.potion_count, String.valueOf(player.getPotions()));
        updateTextView(R.id.weapon_stats, player.getWeapon().toString());
        updateTextView(R.id.armor_stats, player.getArmor().toString());
        updateTextView(R.id.money, player.getMoney() + "GP");
        updateTextView(R.id.potion_cost, player.getPotionCost() + "GP");
    }

    protected void updateTextView(int id, String update) {
        TextView tView = (TextView)findViewById(id);

        tView.setText(update);
    }

    protected void completeItemUpgrade(String type, MathItem item, int money) {
        switch (type) {
            case MathWeapon.TYPE_STRING:
                this.player.setWeapon((MathWeapon)item);
                this.player.setMoney(money);
                updateTextView(R.id.weapon_stats, player.getWeapon().toString());
                updateTextView(R.id.money, player.getMoney() + "GP");
                break;
            case MathArmor.TYPE_STRING:
                this.player.setArmor((MathArmor)item);
                this.player.setMoney(money);
                updateTextView(R.id.armor_stats, player.getArmor().toString());
                updateTextView(R.id.money, player.getMoney() + "GP");
                break;
            default:
                //Must not have updated anything, let's make sure money is right.
                updateTextView(R.id.money, player.getMoney() + "GP");
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(weaponFragment);
        ft.hide(armorFragment);
        ft.commit();

        showButtons();

        GridLayout gl = findViewById(R.id.shop_grid);
        gl.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(R.string.shop_button);
    }

    public void onClickWeapon(View view) {
        String title = getResources().getString(R.string.shop_button);

        GridLayout gl = findViewById(R.id.shop_grid);
        gl.setVisibility(View.INVISIBLE);

        weaponFragment.setItem(new MathWeapon(this.player.getWeapon()));
        weaponFragment.setMoney(this.player.getMoney());
        weaponFragment.resetText();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (weaponFragment.isAdded()) { // if the fragment is already in container
            ft.show(weaponFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.shop_addition, weaponFragment, MathWeapon.TYPE_STRING);
        }
        // Hide armor
        if (armorFragment.isAdded()) { ft.hide(armorFragment); }

        //Hide buttons
        hideButtons();
        ft.commit();

        //Update ActionBar
        getSupportActionBar().setTitle(title + " - " + capitalize(MathWeapon.TYPE_STRING));
    }

    private void hideButtons() {
        findViewById(R.id.shop_button_save).setVisibility(View.INVISIBLE);
        findViewById(R.id.shop_button_cancel).setVisibility(View.INVISIBLE);
    }

    private void showButtons() {
        findViewById(R.id.shop_button_save).setVisibility(View.VISIBLE);
        findViewById(R.id.shop_button_cancel).setVisibility(View.VISIBLE);
    }

    public void onClickArmor(View view) {
        String title = getResources().getString(R.string.shop_button);

        GridLayout gl = findViewById(R.id.shop_grid);
        gl.setVisibility(View.INVISIBLE);

        armorFragment.setItem(new MathArmor(this.player.getArmor()));
        armorFragment.setMoney(this.player.getMoney());
        armorFragment.resetText();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (armorFragment.isAdded()) { // if the fragment is already in container
            ft.show(armorFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.shop_addition, armorFragment, MathArmor.TYPE_STRING);
        }
        // Hide weapon
        if (weaponFragment.isAdded()) { ft.hide(weaponFragment); }

        //Hide buttons
        hideButtons();

        ft.commit();

        //Update ActionBar
        getSupportActionBar().setTitle(title + " - " + capitalize(MathArmor.TYPE_STRING));
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void onClickPotion(View view) {
        int cost = player.getPotionCost();
        if (cost > player.getMoney()) {
            Toast.makeText(this, R.string.no_money, Toast.LENGTH_SHORT).show();
        } else {
            this.player.addPotion(1);

            updateTextView(R.id.potion_count, String.valueOf(this.player.getPotions()));
            updateTextView(R.id.money, this.player.spendMoney(cost) + "GP");
        }
    }

    public void onClickSave(View view) {
        PreferenceHelper.save();
        finish();
    }

    public void onClickCancel(View view) {finish();}


}
