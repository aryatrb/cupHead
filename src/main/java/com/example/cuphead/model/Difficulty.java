package com.example.cuphead.model;

public enum Difficulty {
    cupHead(10, 0.5, 1.5),
    plasticHead(5, 1, 1),
    noHead(2, 1.5, 0.5);
    private final double primitiveCupHeadHealth;
    private final double gettingDamagedCoefficient;
    private final double makingDamageCoefficient;

    Difficulty(double primitiveCupHeadHealth,
               double gettingDamagedCoefficient,
               double makingDamageCoefficient) {
        this.primitiveCupHeadHealth = primitiveCupHeadHealth;
        this.gettingDamagedCoefficient = gettingDamagedCoefficient;
        this.makingDamageCoefficient = makingDamageCoefficient;
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
}
