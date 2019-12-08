package edu.bu.metcs.mathandslash;

import java.io.Serializable;
import static java.lang.Math.max;
import static java.lang.Math.pow;

public class MathWeapon extends MathItem implements Serializable {

    public static final String TYPE_STRING = "weapon";

    public MathWeapon(int add, int sub) {
        super();
        this.setAdditionLevel(add);
        this.setSubtractionLevel(sub);
    }

    public MathWeapon(MathWeapon toCopy) {
        super(toCopy);
    }

    public int nextLevelCost(String type) {
        int lvl = 0;

        switch (type) {
            case MathItem.ADDITION:
                lvl = this.getAdditionLevel();
                break;
            case MathItem.SUBTRACTION:
                lvl = this.getSubtractionLevel();
                break;
            case MathItem.MULTIPLICATION:
                lvl = this.getMultLevel();
                break;
            case MathItem.DIVISION:
                lvl = this.getDivLevel();
                break;
        }


        return max(100, (int)(pow(lvl, 1.6) * 100));
    }
}
