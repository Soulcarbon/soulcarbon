package ru.sw.modules.steam.utils;

import java.util.Map;

public class ValuteEntry {
    private double rub;
    private double usd;

    public ValuteEntry(double rub, double usd) {
        this.rub = rub;
        this.usd = usd;
    }

    public ValuteEntry() {
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
}
