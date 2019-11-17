package edu.bu.metcs.activitylifecycle;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BuyWeaponFragment extends Fragment {
    private MathItem item;
    private int money;
    private String type;
    private View thisView;


    public BuyWeaponFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buy_weapon, container, false);

        this.thisView = v;

        updateTextView(R.id.addition_stats, String.valueOf(item.getAdditionLevel()));
        updateTextView(R.id.subtraction_stats, String.valueOf(item.getSubtractionLevel()));

        updateTextView(R.id.addition_cost, item.nextLevelCost("+") + "GP");
        updateTextView(R.id.subtraction_cost, item.nextLevelCost("-") + "GP");

        Button okButton = (Button) v.findViewById(R.id.weapon_ok);
        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((ViewShopActivity)getActivity()).completeItemUpgrade(type, item, money);
            }
        });

        Button cancelButton = (Button) v.findViewById(R.id.weapon_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((ViewShopActivity)getActivity()).completeItemUpgrade("none", null, 0);
            }
        });

        Button addInc = (Button) v.findViewById(R.id.add_inc_button);
        addInc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cost = item.nextLevelCost("+");

                if (cost > money) {
                    Toast.makeText(getActivity(), "Not enough money", Toast.LENGTH_SHORT).show();
                } else {
                    money -= cost;
                    item.incAdditionLevel();

                    updateTextView(R.id.addition_stats, String.valueOf(item.getAdditionLevel()));
                    updateTextView(R.id.addition_cost, item.nextLevelCost("+") + "GP");
                    ((ViewShopActivity)getActivity()).updateTextView(R.id.money, money + "GP");
                }
            }
        });

        return v;
    }

    public void setItem(MathItem item) {
        this.item = item;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected void updateTextView(int id, String update) {
        TextView tView = (TextView)thisView.findViewById(id);

        if (tView == null) {
            Log.d("tView is", "null");
        } else { Log.d("tView is", "not null");}

        tView.setText(update);
    }


}
