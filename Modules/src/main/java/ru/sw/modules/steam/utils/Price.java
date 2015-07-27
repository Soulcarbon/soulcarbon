package ru.sw.modules.steam.utils;

import javax.persistence.Embeddable;
import java.util.Map;

@Embeddable
public class Price {
    private double rub;
    private double usd;

    public Price(double rub, double usd) {
        this.rub = rub;
        this.usd = usd;
    }

    public Price() {
    }

    public double getRub() {
        return rub;
    }

    public void setRub(double rub) {
        this.rub = rub;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }


    public void addPrice(Price p) {
        this.rub += p.getRub();
        this.usd += p.getUsd();
    }

    @Override
    public String toString() {
        return "Price{" +
                "rub=" + rub +
                ", usd=" + usd +
                '}';
    }
}
