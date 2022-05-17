package com.example.cupHead.model;

public class Difficulty {
    private final int type;
    private final double primitiveCupHeadHealth;
    private final double gettingDamagedCoefficient;
    private final double makingDamageCoefficient;

    public Difficulty(int difficulty) {
        type = difficulty;
        if (difficulty == 1) {
            primitiveCupHeadHealth = 10;
            gettingDamagedCoefficient = 0.5;
            makingDamageCoefficient = 1.5;
        } else if (difficulty == 2) {
            primitiveCupHeadHealth = 5;
            gettingDamagedCoefficient = 1;
            makingDamageCoefficient = 1;
        } else {
            primitiveCupHeadHealth = 2;
            gettingDamagedCoefficient = 1.5;
            makingDamageCoefficient = 0.5;
        }
    }

    public double getGettingDamagedCoefficient() {
        return gettingDamagedCoefficient;
    }

    public double getMakingDamageCoefficient() {
        return makingDamageCoefficient;
    }

    public double getPrimitiveCupHeadHealth() {
        return primitiveCupHeadHealth;
    }

    public int getType() {
        return type;
    }
}
