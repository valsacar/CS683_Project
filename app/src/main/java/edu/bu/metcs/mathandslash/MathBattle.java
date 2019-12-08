package edu.bu.metcs.mathandslash;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.random;

class MathBattle {
    public static final String HIT_MISS = MathApplication.getContext().getString(R.string.hit_miss);
    public static final String HIT_GLANCING = MathApplication.getContext().getString(R.string.hit_glance);
    public static final String HIT_HIT = MathApplication.getContext().getString(R.string.hit_hit);
    public static final String HIT_CRITICAL = MathApplication.getContext().getString(R.string.hit_crit);

    public static final int FIGHT_PLAYER = 0;
    public static final int FIGHT_ENEMY = 1;

    private List<String> answers;
    private double answer;
    private String problem ;
    private MathCharacter player;
    private MathCharacter enemy;
    private Boolean fighting = true;
    private String type;
    private Boolean battleButtons = true;


    public MathBattle(MathCharacter player) {
        this.player = player;

        // Level 1 player only gets level 1 enemies
        if (player.getLevel() == 1) this.enemy = new MathCharacter(1);
        else {
            // Create an enemy that is at least half the players level
            int halfLvl = this.player.getLevel() / 2;
            this.enemy = new MathCharacter((int) (random() * (halfLvl + 3)) + halfLvl);
        }

        // Up to level 5 we give them 3 options for answers
        if (player.getLevel() > 5) this.battleButtons = false;
    }

    public String getProblem() {
        return problem;
    }

    public Boolean showBattleButtons() {
        return this.battleButtons;
    }

    public void startAttack(String type) {
        int lvl = 0;

        switch (type) {
            case MathItem.ADDITION:
                lvl = this.player.getWeapon().getAdditionLevel();
                break;
            case MathItem.SUBTRACTION:
                lvl = this.player.getWeapon().getSubtractionLevel();
                break;
            case MathItem.DIVISION:
                lvl = this.player.getWeapon().getDivLevel();
                break;
            case MathItem.MULTIPLICATION:
                lvl = this.player.getWeapon().getMultLevel();
                break;
        }

        generateProblem(this.player, type, lvl);

    }

    public void startDefense() {
        int choice = (int)(random() * 4);
        int lvl = 0;
        String type = MathItem.ADDITION;

        switch (choice) {
            case 0:
                type = MathItem.ADDITION;
                lvl = this.enemy.getWeapon().getAdditionLevel();
                break;
            case 1:
                type = MathItem.SUBTRACTION;
                lvl = this.enemy.getWeapon().getSubtractionLevel();
                break;
            case 2:

                type = MathItem.DIVISION;
                lvl = this.enemy.getWeapon().getDivLevel();
                if (this.player.getWeapon().getDivLevel() == 0) {
                    type = MathItem.SUBTRACTION;
                }
                break;
            case 3:
                type = MathItem.MULTIPLICATION;
                lvl = this.enemy.getWeapon().getMultLevel();
                if (this.player.getWeapon().getDivLevel() == 0) {
                    type = MathItem.ADDITION;
                }
                break;
        }

        //Type isn't too important, so we'll just make sure level is passed as at least 1
        generateProblem(this.enemy, type, max(1, lvl));
    }

    public void doPotion() {
        int choice = (int)(random() * 4);
        String type = MathItem.ADDITION;
        int lvl = min(5, this.player.getWeapon().getAdditionLevel());

        if (choice == 1) {
            type = MathItem.SUBTRACTION;
            lvl = min(5, this.player.getWeapon().getSubtractionLevel());
        } else if (choice == 2) { // If they don't have div we use sub
            if (this.player.getWeapon().getDivLevel() == 0) {
                type = MathItem.SUBTRACTION;
                lvl = min(5, this.player.getWeapon().getSubtractionLevel());
            } else {
                type = MathItem.DIVISION;
                lvl = min(5, this.player.getWeapon().getDivLevel());
            }
        } else if (choice == 3) { // If they don't have mult we use add
            if (this.player.getWeapon().getMultLevel() > 0) {
                type = MathItem.MULTIPLICATION;
                lvl = min(5, this.player.getWeapon().getMultLevel());
            }
        }

        generateProblem(this.player, type, lvl);
    }

    public int doPotionHeal(long timeInSeconds, String choice) {
        double mult = 1;

        //Incorrect answer doesn't heal
        if (Integer.parseInt(choice) != this.answer) return 0;

        if (timeInSeconds >= 10) mult = 0.1;
        else if (timeInSeconds >= 8) mult = 0.3;
        else if (timeInSeconds <= 3) mult = 1.2;

        // Got to heal at least 1
        int healing = max(1, (int)(this.player.getMaxHP() * mult / 2));
        this.player.doHealing(healing);

        //Now we take a potion away
        this.player.addPotion(-1);

        return healing;
    }


    private void generateProblem(MathCharacter character, String type, int lvl) {
        this.type = type;

        int max = lvl * 5 + 1; // Highest the individual numbers can be
        int maxNumbers = (int)(lvl / 7) + 2; // This is the most number of elements we can have.
        //Now we'll figure out how many we will have.
        maxNumbers = max(2, (int)(random() * maxNumbers) + 1);
        // And we never want more than 5
        maxNumbers = min(5, maxNumbers);

        String problem = "";
        String myType = "";
        Boolean allowNeg = (PreferenceHelper.getCurrentCharacter().getWeapon().getSubtractionLevel() >= 5);

        ArrayList<String> types = new ArrayList<>(Arrays.asList(type));

        // Lets see if we can make this math more complicated.
        if (maxNumbers >= 3 && lvl >= 10) {
            // Yes, I am aware that this means one will be listed twice, that is intended
            if (character.getWeapon().getAdditionLevel() >= 10) types.add(MathItem.ADDITION);
            if (character.getWeapon().getSubtractionLevel() >= 10) types.add(MathItem.SUBTRACTION);
            if (character.getWeapon().getMultLevel() >= 10) types.add(MathItem.MULTIPLICATION);
            if (character.getWeapon().getDivLevel() >= 10) types.add(MathItem.DIVISION);
        }

        Random rand = new Random();
        int a = 0;
        int b = (int)(random() * max);
        for (int i = 1;i < maxNumbers;i++) {
            a = (int)(random() * max);
            if (allowNeg && ((int)random() * 100) <= 15) a = 0 - a;

            if (i == 1) { // First one is always the chosen type
                problem += a +  " " + type + " ";
                myType = type;
            } else {
                myType = types.get(rand.nextInt(types.size()));

                if (a == 0 && myType.equals(MathItem.DIVISION)) { // Bad bad bad
                    //We'll just remove division, pick a new one, add it back
                    types.remove(MathItem.DIVISION);
                    myType = types.get(rand.nextInt(types.size()));
                    types.add(MathItem.DIVISION);
                }

                problem += a +  " " + myType + " ";
            }
        }

        // Make sure we aren't dividing and b is 0
        if (myType.equals(MathItem.DIVISION)) b = max(1, b);

        // We'll be nice to lower levels.
        if (type.equals(MathItem.SUBTRACTION) && maxNumbers == 2 && !allowNeg && b > a) {
            // This will ensure lower levels don't get negative numbers as an answer
            problem += max(0, (a - 1));
        } else problem += b;

        // We won't allow decimals until they have division over level 6
        if (type.equals(MathItem.DIVISION) && (character.getWeapon().getDivLevel() <= 6) && (a % b) != 0) {
            // Since it's under level 6 we know it's only two numbers, so we'll cheat.
            b = (int)(random() * max/4) + 1;
            a = (int)(random() * max/2) * b;

            while (a > max) a -= b; // Make sure we didn't go over our max number.

            problem = a + " " + MathItem.DIVISION + " " +b;
        }

        // Let's make sure we don't have any /0 in there, shouldn't but... just in case
        double answer = eval(problem);
        if (answer == Double.NEGATIVE_INFINITY || answer == Double.POSITIVE_INFINITY || answer == Double.NaN) {
            // Just start over.

            generateProblem(character, type, lvl);
        } else {
            this.problem = problem;
            this.answer = eval(this.problem);

            generateAnswers(this.answer);
        }

    }

    private void generateAnswers(double answer) {
        this.answers = new ArrayList<String>();
        String ans = Double.toString(answer);

        if (this.player.getWeapon().getDivLevel() <= 6) { //No decimals for low levels
            ans = Integer.toString((int)answer);
        }

        this.answers.add(ans);
        this.answers.add(makeAnswer(answer));
        this.answers.add(makeAnswer(answer));

        Collections.shuffle(this.answers);
    }

    private String makeAnswer(double answer) {

        if (answer == 0) answer = (random() * 5) + 1; //Otherwise we get all 0 and 1s

        double ret = answer;
        int looper = 2;
        String retString;

        while (ret == answer && looper <= 6) { //Added looper to make sure we don't go infinate
            ret = (int)(random() * answer * looper + 1);
            looper++;
        }

        retString = Double.toString(ret);

        if (this.player.getWeapon().getDivLevel() <= 6) { //No decimals for low levels
            retString = Integer.toString((int)ret);
        }

        return retString;
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    // Boolean to check and see if still fighting, or if someone died.
    public Boolean getFighting() {
        return this.fighting;
    }

    public double getAnswer() {
        return this.answer;
    }

    public String doAttack(int target, String choice) {
        String retString = MathBattle.HIT_HIT;
        double damageMult = 1;
        int hitRoll = (int)(random() * 100);
        int def = 0, atk = 0;
        double choiceInt = Double.parseDouble(choice);

        MathCharacter targetChar;
        MathCharacter actionChar;

        if (target == MathBattle.FIGHT_PLAYER) {
            targetChar = this.player;
            actionChar = this.enemy;

            hitRoll-= min(15, targetChar.getToughness());

            if (choiceInt != this.answer) {
                retString = MathBattle.HIT_CRITICAL;
                damageMult = 1.2;
            }

        }
        else {
            targetChar = this.enemy;
            actionChar = this.player;

            hitRoll-= targetChar.getToughness();

            if (choiceInt != this.answer) {
                retString = MathBattle.HIT_MISS;
                damageMult = 0;
            }
        }

        if (choiceInt == this.answer) {
            if (hitRoll >= 90) {
                // Since we use hit string to determine if the player got the answer wrong, we'll not call this critical for the enemy
                if (target == MathBattle.FIGHT_ENEMY)
                    retString = MathBattle.HIT_CRITICAL;
                damageMult = 1.2;
            } else if (hitRoll <= 15) {
                //Since enemy have no chance of missing, we'll make one
                if (target == MathBattle.FIGHT_PLAYER && (random() * 100) >= 50) {
                    retString = MathBattle.HIT_MISS;
                    damageMult = 0;
                } else {
                    retString = MathBattle.HIT_GLANCING;
                    damageMult = 0.2;
                }
            }
        }


        if (this.type == MathItem.ADDITION) {
            def = targetChar.getArmor().getAdditionLevel();
            atk = actionChar.getWeapon().getAdditionLevel();
        } else if (this.type == MathItem.SUBTRACTION) {
            def = targetChar.getArmor().getSubtractionLevel();
            atk = actionChar.getWeapon().getSubtractionLevel();
        } else if (this.type == MathItem.MULTIPLICATION) {
            def = targetChar.getArmor().getMultLevel();
            atk = actionChar.getWeapon().getMultLevel();
        }else if (this.type == MathItem.DIVISION) {
            def = targetChar.getArmor().getDivLevel();
            atk = actionChar.getWeapon().getDivLevel();
        }


        int dmg = (int)((random() * (10 + atk)) + (actionChar.getStrength() * 1.7)) + 1;
        def += targetChar.getToughness();
        dmg = max(1, dmg - (int)(random() * (5 + (def * 2))));

        dmg *= damageMult;

        targetChar.takeDamage(dmg);

        if (targetChar.getHp() <= 0)
            this.fighting = false;

        return retString;
    }

    public int getGoldReward() {
        int gold = this.enemy.getLevel() * 100;

        gold = (int)(random() * (gold / 2) + (gold / 2));

        return gold;
    }

    public int getXpReward() {
        return this.enemy.getXp();
    }

    public int getEnemyHP() {
        return this.enemy.getHp();
    }

    public String getEnemyHPString() {
        return this.enemy.getHp() + "/" + this.enemy.getMaxHP();
    }

    public int getEnemyHPColor() {
        return this.enemy.getHPColor();
    }

    public String hitModifier(int targetType) {
        MathCharacter target;

        if (targetType == MathBattle.FIGHT_ENEMY) target = this.enemy;
        else target = this.player;

        if (this.type.equals(target.weakestArmor()))
            return MathApplication.getContext().getString(R.string.strong) + " ";
        else if (this.type.equals(target.strongestArmor()))
            return MathApplication.getContext().getString(R.string.weak) + " ";

        return "";
    }

    /*
    Obtained this from StackOverflow user Boann who released it under CCO 1.0
    https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
    Slightly modified for my purposes
     */
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else if (eat('÷')) x /= parseFactor(); // division ÷
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
