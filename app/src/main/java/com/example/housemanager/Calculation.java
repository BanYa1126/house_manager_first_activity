package com.example.housemanager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Calculation {

    public static double calculateWaterBill(double consumption) {
        double unitPriceWater = 630;  // 상수도 사용량 단가 (원/m³)
        double waterFeeBase = 850;  // 구경별정액요금 (원)
        double waterFeeUsage = consumption * unitPriceWater;  // 상수도 사용 요금 (원)
        double waterFeeTotal = waterFeeUsage + waterFeeBase;  // 상수도 총 요금 (원)

        double unitPriceSewer = 510;  // 하수도 사용량 단가 (원/m³)
        double sewerFeeTotal = consumption * unitPriceSewer;  // 하수도 총 요금 (원)

        double unitPriceWaterCharge = 170;  // 물 사용 부담금 단가 (원/m³)
        double waterChargeFeeTotal = consumption * unitPriceWaterCharge;  // 물 사용 부담금 총 요금 (원)

        double totalFee = waterFeeTotal + sewerFeeTotal + waterChargeFeeTotal;

        return Math.round(totalFee);
    }

    public static String getCurrentSeason() {
        int month = LocalDateTime.now().getMonthValue();  // 현재 월을 가져옴
        if (month >= 3 && month <= 5) {
            return "spring";
        } else if (month >= 6 && month <= 8) {
            return "summer";
        } else if (month >= 9 && month <= 11) {
            return "fall";
        } else {
            return "winter";
        }
    }

    public static double calculateHeatingBill(double pyeong, double heatingUsage) {
        double PYEONG_TO_SQUARE_METER = 3.3058;
        double BASIC_RATE_PER_SQUARE_METER = 52.4;

        Map<String, Double> SEASON_RATES = new HashMap<>();
        SEASON_RATES.put("spring", 9951.0);
        SEASON_RATES.put("summer", 8955.0);
        SEASON_RATES.put("fall", 10453.0);
        SEASON_RATES.put("winter", 10157.0);

        String season = getCurrentSeason();

        double contractArea = pyeong * PYEONG_TO_SQUARE_METER;
        double basicFee = contractArea * BASIC_RATE_PER_SQUARE_METER;

        double usageRate = SEASON_RATES.get(season);
        double usageFee = heatingUsage * usageRate;// * 101570;

        double vat = (basicFee + usageFee) * 0.1;
        double totalFee = basicFee + usageFee + vat;

        return Math.round(totalFee);
    }

    public static double calculateGasBill(double consumption) {
        double baseRate = 1250;
        double energyConversionFactor = 0.9964;
        double energyContent = 42.772;
        double unitPrice = 20.7354;
        double usageCost = consumption * energyConversionFactor * energyContent * unitPrice;

        double vat = (baseRate + usageCost) * 0.1;
        double totalFee = baseRate + usageCost + vat;

        return Math.round(totalFee);
    }

    public static double calculateElectricityBill(double totalConsumption) {
        double baseRate = 0;
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

        double electricityBill = (baseRate +
            (consumptionTier1 * 120) +
            (consumptionTier2 * 214.6) +
            (consumptionTier3 * 307.3));
        electricityBill += weatherFee + fuelCost;

        double vat = electricityBill / 10;
        double fund = electricityBill * 3.7 / 100;
        fund = fund - (fund % 10);

        double totalFee = electricityBill + vat + fund;
        totalFee = totalFee - (totalFee % 10);

        return (int) totalFee;
    }

    public static void main(String[] args) {
        Calculation calc = new Calculation();

        double waterConsumption = 50;  // example value for water consumption in m³
        double pyeong = 25;  // example value for pyeong
        double heatingUsage = 150;  // example value for heating usage
        double gasConsumption = 100;  // example value for gas consumption
        double electricityConsumption = 350;  // example value for electricity consumption

        double waterBill = calc.calculateWaterBill(waterConsumption);
        double heatingBill = calc.calculateHeatingBill(pyeong, heatingUsage);
        double gasBill = calc.calculateGasBill(gasConsumption);
        double electricityBill = calc.calculateElectricityBill(electricityConsumption);

        System.out.println("Water Bill: " + waterBill);
        System.out.println("Heating Bill: " + heatingBill);
        System.out.println("Gas Bill: " + gasBill);
        System.out.println("Electricity Bill: " + electricityBill);
    }
}
