package edu.bu.metcs.activitylifecycle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;

public class ViewShopActivity extends AppCompatActivity {
    private MathCharacter player;
    BuyWeaponFragment weaponFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop);

        //Set the toolbar as the activity's app bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.shop_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        MathCharacter character=(MathCharacter)intent.getSerializableExtra("character");

        this.player = character;

        this.weaponFragment = new BuyWeaponFragment();

        weaponFragment.setItem(this.player.getWeapon());
        weaponFragment.setMoney(this.player.getMoney());
        weaponFragment.setType("weapon");

        FragmentManager fragManager = getSupportFragmentManager();
        // get the reference to the FragmentTransaction object
        FragmentTransaction transaction = fragManager.beginTransaction();
        // add the fragment into the transaction
        transaction.add(R.id.shop_addition, weaponFragment);
        // commit the transaction.
        transaction.commit();
        transaction.hide(weaponFragment);


        updateTextView(R.id.potion_count, String.valueOf(player.getPotions()));
        updateTextView(R.id.weapon_stats, player.getWeapon().toString());
        updateTextView(R.id.armor_stats, player.getArmor().toString());
        updateTextView(R.id.money, player.getMoney() + "GP");
    }

    protected void updateTextView(int id, String update) {
        TextView tView = (TextView)findViewById(id);

        tView.setText(update);
    }

    protected void completeItemUpgrade(String type, MathItem item, int money) {
        switch (type) {
            case "weapon":
                this.player.setWeapon((MathWeapon)item);
                this.player.setMoney(money);
                updateTextView(R.id.weapon_stats, player.getWeapon().toString());
                updateTextView(R.id.money, player.getMoney() + "GP");
                break;
            case "armor":
                break;
            default:
                //Must not have updated anything, let's make sure money is right.
                updateTextView(R.id.money, player.getMoney() + "GP");
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(weaponFragment);
        //ft.hide(armorFragment);
        ft.commit();

        GridLayout gl = findViewById(R.id.shop_grid);
        gl.setVisibility(View.VISIBLE);
    }

    public void onClickWeapon(View view) {
        GridLayout gl = findViewById(R.id.shop_grid);
        gl.setVisibility(View.INVISIBLE);

        weaponFragment.setItem(this.player.getWeapon());
        weaponFragment.setMoney(this.player.getMoney());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (weaponFragment.isAdded()) { // if the fragment is already in container
            ft.show(weaponFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.shop_addition, weaponFragment, "weapon");
        }
        // Hide armor
        //if (armorFragment.isAdded()) { ft.hide(armorFragment); }
        ft.commit();
    }


}
