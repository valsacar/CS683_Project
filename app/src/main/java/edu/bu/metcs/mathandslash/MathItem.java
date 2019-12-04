package edu.bu.metcs.mathandslash;

import java.io.Serializable;
import java.util.List;

public abstract class MathItem implements Serializable {
    public static final String ADDITION = "+";
    public static final String SUBTRACTION = "-";

    private int additionLevel, subtractionLevel;

    public MathItem() {
        this.additionLevel = 0;
        this.subtractionLevel = 0;
    }

    public MathItem(MathItem toCopy) {
        this.additionLevel = toCopy.additionLevel;
        this.subtractionLevel = toCopy.subtractionLevel;
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

    public abstract int nextLevelCost(String type);
}
