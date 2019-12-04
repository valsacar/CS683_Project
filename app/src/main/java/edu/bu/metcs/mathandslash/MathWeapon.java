package edu.bu.metcs.mathandslash;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

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
        return 100;
    }
}
