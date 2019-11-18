package edu.bu.metcs.activitylifecycle;

import java.io.Serializable;

import static java.lang.Math.round;

class MathCharacter implements Serializable {
    private int level, xp, hp, strength, toughness, health;
    private int pointsToUse, money, potions;

    private MathWeapon weapon;
    private MathArmor armor;

    public MathCharacter() {
        this.level = 1;
        this.xp = 0;
        this.hp = 15;
        this.strength = 1;
        this.toughness = 1;
        this.health = 1;
        this.pointsToUse = 2;
        this.money = 1000; //TODO: Set this back to 0
        this.potions = 1;
        this.weapon = new MathWeapon(1, 0);
        this.armor = new MathArmor(1, 0);
    }

    public int getLevel() {return this.level;}

    public int getStrength() {return this.strength;}

    public int getXp() {
        return this.xp;
    }

    public int getHp() {
        return this.hp;
    }

    public int getToughness() {
        return this.toughness;
    }

    public int getHealth() {
        return this.health;
    }

    public int getPointsToUse() {
        return this.pointsToUse;
    }

    public int getPotions() {return this.potions;}

    public int getMoney() {
        return this.money;
    }

    public MathWeapon getWeapon() {
        return this.weapon;
    }

    public MathArmor getArmor() {
        return this.armor;
    }

    protected void setWeapon(MathWeapon weapon) {
        this.weapon = weapon;
    }

    protected void setArmor(MathArmor armor) {
        this.armor = armor;
    }

    protected void setMoney(int money) {
        this.money = money;
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
        return calculateMaxHP(this.health);
    }

    public int calculateMaxHP(int health) {
        return 11 + (int)round(health * 3.5 * this.level);
    }

    public void addPotion(int number) {
        this.potions += number;
    }

    public int getPotionCost() {return 500;}

    public int spendMoney(int amount) {
        return this.money -= amount;
    }

    public int gainMoney(int amount) {
        return this.money += amount;
    }

    private void addLevel() {this.level++;}


}
