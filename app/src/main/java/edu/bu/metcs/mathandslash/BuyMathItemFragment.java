package edu.bu.metcs.mathandslash;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BuyMathItemFragment extends Fragment {
    private MathItem item;
    private int money;
    private String type;
    private View thisView;


    public BuyMathItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buy_mathitem, container, false);

        this.thisView = v;

        this.resetText();

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
                int cost = item.nextLevelCost(MathItem.ADDITION);

                if (cost > money) {
                    Toast.makeText(getActivity(), R.string.no_money, Toast.LENGTH_SHORT).show();
                } else {
                    money -= cost;
                    item.incAdditionLevel();

                    updateTextView(R.id.addition_stats, String.valueOf(item.getAdditionLevel()));
                    updateTextView(R.id.addition_cost, item.nextLevelCost(MathItem.ADDITION) + "GP");
                    ((ViewShopActivity)getActivity()).updateTextView(R.id.money, money + "GP");
                }
            }
        });

        Button subInc = (Button) v.findViewById(R.id.sub_inc_button);
        subInc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cost = item.nextLevelCost(MathItem.SUBTRACTION);

                if (cost > money) {
                    Toast.makeText(getActivity(), R.string.no_money, Toast.LENGTH_SHORT).show();
                } else {
                    money -= cost;
                    item.incSubtractionLevel();

                    updateTextView(R.id.subtraction_stats, String.valueOf(item.getSubtractionLevel()));
                    updateTextView(R.id.subtraction_cost, item.nextLevelCost(MathItem.SUBTRACTION) + "GP");
                    ((ViewShopActivity)getActivity()).updateTextView(R.id.money, money + "GP");
                }
            }
        });

        Button multInc = (Button) v.findViewById(R.id.mult_inc_button);
        multInc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cost = item.nextLevelCost(MathItem.MULTIPLICATION);

                if (cost > money) {
                    Toast.makeText(getActivity(), R.string.no_money, Toast.LENGTH_SHORT).show();
                } else {
                    money -= cost;
                    item.incMultLevel();

                    updateTextView(R.id.multiplication_stats, String.valueOf(item.getMultLevel()));
                    updateTextView(R.id.multiplication_cost, item.nextLevelCost(MathItem.MULTIPLICATION) + "GP");
                    ((ViewShopActivity)getActivity()).updateTextView(R.id.money, money + "GP");
                }
            }
        });

        Button divInc = (Button) v.findViewById(R.id.div_inc_button);
        divInc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cost = item.nextLevelCost(MathItem.DIVISION);

                if (cost > money) {
                    Toast.makeText(getActivity(), R.string.no_money, Toast.LENGTH_SHORT).show();
                } else {
                    money -= cost;
                    item.incDivLevel();

                    updateTextView(R.id.division_stats, String.valueOf(item.getDivLevel()));
                    updateTextView(R.id.division_cost, item.nextLevelCost(MathItem.DIVISION) + "GP");
                    ((ViewShopActivity)getActivity()).updateTextView(R.id.money, money + "GP");
                }
            }
        });

        return v;
    }

    public void resetText() {
        updateTextView(R.id.addition_stats, String.valueOf(item.getAdditionLevel()));
        updateTextView(R.id.subtraction_stats, String.valueOf(item.getSubtractionLevel()));
        updateTextView(R.id.division_stats, String.valueOf(item.getDivLevel()));
        updateTextView(R.id.multiplication_stats, String.valueOf(item.getMultLevel()));

        updateTextView(R.id.addition_cost, item.nextLevelCost(MathItem.ADDITION) + "GP");
        updateTextView(R.id.subtraction_cost, item.nextLevelCost(MathItem.SUBTRACTION) + "GP");
        updateTextView(R.id.division_cost, item.nextLevelCost(MathItem.DIVISION) + "GP");
        updateTextView(R.id.multiplication_cost, item.nextLevelCost(MathItem.MULTIPLICATION) + "GP");
    }



    public void setItem(MathItem item) {this.item = item;}

    public void setMoney(int money) {
        this.money = money;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected void updateTextView(int id, String update) {
        TextView tView = (TextView)thisView.findViewById(id);
        tView.setText(update);
    }


}
