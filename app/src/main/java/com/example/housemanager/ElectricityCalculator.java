package com.example.housemanager;
// ElectricityCalculator.java
//병신ㅁㄴㅇㄻㄴㄻㄴㄻㄴㄻㄴㄻㄴ
public class ElectricityCalculator {

    public static double calculateElectricityBill(double totalConsumption) {
        double baseRate;
        double consumptionTier1 = 0;
        double consumptionTier2 = 0;
        double consumptionTier3 = 0;

        if (totalConsumption > 450) {
            consumptionTier3 = totalConsumption - 450;
            consumptionTier2 = 150;
            consumptionTier1 = 300;
            baseRate = 7300;
        } else if (totalConsumption > 300) {
            consumptionTier2 = totalConsumption - 300;
            consumptionTier1 = 300;
            baseRate = 1600;
        } else {
            consumptionTier1 = totalConsumption;
            baseRate = 910;
        }

        double weatherFee = totalConsumption * 9.0;
        double fuelCost = 5.0 * totalConsumption;

        double electricityBill = baseRate + (consumptionTier1 * 120) + (consumptionTier2 * 214.6) + (consumptionTier3 * 307.3);
        electricityBill += weatherFee + fuelCost;

        double vat = electricityBill / 10;
        double fund = electricityBill / 100 * 3.7;
        fund -= fund % 10;

        double totalFee = electricityBill + vat + fund;
        totalFee -= totalFee % 10;

        return totalFee;
    }
}
