package edu.bu.metcs.mathandslash;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.random;

class MathBattle {
    public static final String HIT_MISS = "Miss";  //TODO: These need to be @string
    public static final String HIT_GLANCING = "Glancing Hit";
    public static final String HIT_HIT = "Hit";
    public static final String HIT_CRITICAL = "Critical Hit!";

    public static final int FIGHT_PLAYER = 0;
    public static final int FIGHT_ENEMY = 1;

    private List<String> answers;
    private String answer;
    private String problem ;
    private MathCharacter player;
    private MathCharacter enemy;
    private Boolean fighting = true;
    private String type;


    public MathBattle(MathCharacter player) {
        this.player = player;

        // Create an enemy that is at least half the players level
        int halfLvl = this.player.getLevel() / 2;
        this.enemy = new MathCharacter((int)(random() * (halfLvl + 5)) + halfLvl);
    }

    public String getProblem() {
        return problem;
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
        }

        generateProblem(type, lvl);

    }

    public void startDefense() {
        int choice = (int)(random() * 2);
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
        }

        generateProblem(type, lvl);
    }

    private void generateProblem(String type, int lvl) {
        this.type = type;
        this.problem = "1 + 1"; //TODO: Make this real
        this.answer = "2";
        generateAnswers(this.answer);
    }

    private void generateAnswers(String answer) {
        this.answers = new ArrayList<String>();

        this.answers.add(answer);
        this.answers.add(makeAnswer(answer));
        this.answers.add(makeAnswer(answer));
    }

    private String makeAnswer(String answer) {
        int ans = Integer.getInteger(answer);
        int ret = ans;

        while (ret == ans) {
            ret = (int)(random() * ans * 2);
        }

        return Integer.toString(ret);
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    // Boolean to check and see if still fighting, or if someone died.
    public Boolean getFighting() {
        return this.fighting;
    }


    public String doAttack(int target, String choice) {

        String retString = MathBattle.HIT_HIT;
        double damageMult = 1;
        int hitRoll = (int)(random() * 100);
        int def = 0, atk = 0;

        MathCharacter targetChar;
        MathCharacter actionChar;

        if (target == MathBattle.FIGHT_PLAYER) {
            targetChar = this.player;
            actionChar = this.enemy;

            hitRoll-= targetChar.getToughness();

            if (choice != this.answer) {
                retString = MathBattle.HIT_CRITICAL;
                damageMult = 1.2;
            }

        }
        else {
            targetChar = this.enemy;
            actionChar = this.player;

            hitRoll-= targetChar.getToughness();

            if (choice != this.answer) {
                retString = MathBattle.HIT_MISS;
                damageMult = 0;
            }
        }

        if (choice == this.answer) {
            if (hitRoll >= 90) {
                retString = MathBattle.HIT_CRITICAL;
                damageMult = 1.2;
            } else if (hitRoll <= 10) {
                retString = MathBattle.HIT_GLANCING;
                damageMult = 0.2;
            }
        }


        if (this.type == MathItem.ADDITION) {
            def = targetChar.getArmor().getAdditionLevel();
            atk = actionChar.getWeapon().getAdditionLevel();
        }


        int dmg = (int)(random() * (10 + atk)) + 1;
        dmg = max(1, dmg - (int)(random() * (5 + def)));

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

    /*
    Obtained this from StackOverflow user Boann who released it under CCO 1.0
    https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
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
