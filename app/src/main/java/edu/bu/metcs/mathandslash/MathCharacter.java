package edu.bu.metcs.mathandslash;

import java.io.Serializable;

import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.Math.random;
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
        this.money = 0;
        this.potions = 1;
        this.weapon = new MathWeapon(1, 0);
        this.armor = new MathArmor(1, 0);
    }

    public MathCharacter(int level) {
        this.level = level;
        this.xp = (this.xpToNext()/2) + (int)(random() * (this.xpToNext()/2));
        this.hp = this.getMaxHP();
        this.strength = 1;
        this.toughness = 1;
        this.health = 1;

        // Give some random stats
        for (int i = level*2;i>0;i--) {
            int choice = (int)(random() * 3);
            switch (choice) {
                case 0: this.strength++; break;
                case 1: this.toughness++; break;
                case 2: this.health++; break;
            }
        }

        this.pointsToUse = 2;
        this.money = (int)((random() * level * 100) + pow(level, 1.5) + 50);
        this.potions = 1;

        this.weapon = new MathWeapon(1, 0);
        this.armor = new MathArmor(1, 0);

        // Give some random weapon/armor strengths
        int totalPoints = (int)(random() * level * 2) + 1;
        for (int i = totalPoints;i>0;i--) {
            int choice = (int)(random() * 4);
            switch (choice) {
                case 0: this.weapon.incAdditionLevel(); break;
                case 1: this.weapon.incSubtractionLevel(); break;
                case 3: this.armor.incAdditionLevel(); break;
                case 4: this.armor.incSubtractionLevel(); break;
            }
        }

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

    public int xpToNext() {
        return (int)pow(this.level, 2) * 100;
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

    private void addLevel() {
        this.level++;
        this.hp = this.getMaxHP(); // Full heal
        this.pointsToUse += 2;
        this.xp = 0; //Reset xp... this might be too harsh.
    }

    public Boolean takeDamage(int damage) {
        damage = max(1, damage);
        this.hp -= damage;

        if (this.hp <= 0) {
            this.hp = 0;

            this.xp /= 1.5; //Lose some xp

            return true;
        }

        return false;
    }

    public void gainXp(int amt) {
        this.xp += amt;

        if (this.xp >= xpToNext()) addLevel();
    }

    public void fullHeal() {
        this.hp = getMaxHP();
    }

}
