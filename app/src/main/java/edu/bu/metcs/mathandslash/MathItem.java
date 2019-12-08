package edu.bu.metcs.mathandslash;

import java.io.Serializable;

public abstract class MathItem implements Serializable {
    public static final String ADDITION = "+";
    public static final String SUBTRACTION = "-";
    public static final String MULTIPLICATION = "*";
    public static final String DIVISION = "÷";

    private int additionLevel, subtractionLevel, multLevel, divLevel;

    public MathItem() {
        this.additionLevel = 0;
        this.subtractionLevel = 0;
        this.multLevel = 0;
        this.divLevel = 0;
    }

    public MathItem(MathItem toCopy) {
        this.additionLevel = toCopy.additionLevel;
        this.subtractionLevel = toCopy.subtractionLevel;
        this.multLevel = toCopy.multLevel;
        this.divLevel = toCopy.divLevel;
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

    public int getMultLevel() {
        return this.multLevel;
    }

    public void setMultLevel(int multLevel) {
        this.multLevel = multLevel;
    }

    public int getDivLevel() {
        return this.divLevel;
    }

    public void setDivLevel(int divLevel) {
        this.divLevel = divLevel;
    }

    public void incSubtractionLevel() {
        this.subtractionLevel++;
    }

    public void incAdditionLevel() {
        this.additionLevel++;
    }

    public void incMultLevel() {
        this.multLevel++;
    }

    public void incDivLevel() {
        this.divLevel++;
    }

    @Override
    public String toString() {
        return this.additionLevel + "+/" + this.subtractionLevel + "-/" + this.multLevel + "*/" + this.divLevel + "÷" ;
    }

    public abstract int nextLevelCost(String type);
}
