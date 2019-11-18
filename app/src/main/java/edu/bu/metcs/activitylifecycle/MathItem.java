package edu.bu.metcs.activitylifecycle;

import java.io.Serializable;
import java.util.List;

public abstract class MathItem implements Serializable {
    private static final String ADDITION = "+";
    private static final String Subtraction = "-";

    private int additionLevel, subtractionLevel;

    public MathItem() {
        this.additionLevel = 0;
        this.subtractionLevel = 0;
    }

    public int getAdditionLevel() {
        return this.additionLevel;
    }

    public void setAdditionLevel(int additionLevel) {
        this.additionLevel = additionLevel;
    }

    public int getSubtractionLevel() {
        return this.subtractionLevel;
    }

    public void setSubtractionLevel(int subtractionLevel) {
        this.subtractionLevel = subtractionLevel;
    }

    public void incSubtractionLevel() {
        this.subtractionLevel++;
    }

    public void incAdditionLevel() {
        this.additionLevel++;
    }

    @Override
    public String toString() {
        return this.additionLevel + "+/" + this.subtractionLevel + "-";
    }

    public abstract String getProblem(String type);

    public abstract List<String> getAnswers(String problem);

    public abstract int nextLevelCost(String type);
}
