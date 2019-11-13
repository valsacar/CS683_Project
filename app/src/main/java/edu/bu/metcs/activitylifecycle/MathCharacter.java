package edu.bu.metcs.activitylifecycle;

import java.io.Serializable;

import static java.lang.Math.round;

class MathCharacter implements Serializable {
    private int level, xp, hp, strength, toughness, health;
    private int pointsToUse, money;

    //Probably need to break this out into armor and weapon classes.
    private int wAddLvl, wSubLvl, aAddLvl, aSubLvl;

    public MathCharacter() {
        this.level = 1;
        this.xp = 0;
        this.hp = 15;
        this.strength = 1;
        this.toughness = 1;
        this.health = 1;
        this.pointsToUse = 2;
        this.money = 0;
        this.wAddLvl = 1;
        this.wSubLvl = 0;
        this.aAddLvl = 1;
        this.aSubLvl = 0;
    }

    public int getLevel() {return this.level;}

    public int getStrength() {return this.strength;}

    public int getXp() {
        return xp;
    }

    public int getHp() {
        return hp;
    }

    public int getToughness() {
        return toughness;
    }

    public int getHealth() {
        return health;
    }

    public int getPointsToUse() {
        return pointsToUse;
    }

    public int getMoney() {
        return money;
    }

    public int getwAddLvl() {
        return wAddLvl;
    }

    public int getwSubLvl() {
        return wSubLvl;
    }

    public int getaAddLvl() {
        return aAddLvl;
    }

    public int getaSubLvl() {
        return aSubLvl;
    }

    protected void modStr(int amount) {
        this.strength += amount;
    }

    protected void modTough(int amount) {
        this.toughness += amount;
    }

    protected void modHealth(int amount) {
        this.health += amount;
    }

    protected void modPoints(int amount) {
        this.pointsToUse += amount;
    }

    public int getMaxHP() {
        return 11 + (int)round(this.health * 3.5 * this.level);
    }

    //TODO: Shouldn't be public
    public void addLevel() {this.level++;}


}
