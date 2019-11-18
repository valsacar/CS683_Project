package edu.bu.metcs.activitylifecycle;

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

    public String getProblem(String type) {
        return "1+1";
    }

    public List<String> getAnswers(String problem) {
        return asList("3", "4", "2");
    }

    public int nextLevelCost(String type) {
        return 100;
    }
}
