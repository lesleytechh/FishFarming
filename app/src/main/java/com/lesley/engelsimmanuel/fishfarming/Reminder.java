package com.lesley.engelsimmanuel.fishfarming;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "stock_name")
    private String stockName;

    @ColumnInfo(name = "date_stocked")
    private String dateStocked;

    @ColumnInfo(name = "currency_of_cost_of_stock")
    private String currencyOfCostOfStock;

    @ColumnInfo(name = "cost_of_stock")
    private String costOfStock;

    @ColumnInfo(name = "stock_stage")
    private String stockStage;

    @ColumnInfo(name = "expected_date_of_harvest")
    private String expectedDateOfHarvest;

    @ColumnInfo(name = "feed_fish_frequency")
    private String feedFishFrequency;

    @ColumnInfo(name = "feed_fish_frequency_occurrence")
    private String feedFishFrequencyOccurrence;

    @ColumnInfo(name = "treat_fish_frequency")
    private String treatFishFrequency;

    @ColumnInfo(name = "treat_fish_frequency_occurrence")
    private String treatFishFrequencyOccurrence;

    @ColumnInfo(name = "change_water_frequency")
    private String changeWaterFrequency;

    @ColumnInfo(name = "change_water_frequency_occurrence")
    private String changeWaterFrequencyOccurrence;

    @ColumnInfo(name = "sort_fish_frequency")
    private String sortFishFrequency;

    @ColumnInfo(name = "sort_fish_frequency_occurrence")
    private String sortFishFrequencyOccurrence;

    public Reminder(String stockName, String dateStocked, String currencyOfCostOfStock, String costOfStock, String stockStage, String expectedDateOfHarvest, String feedFishFrequency, String feedFishFrequencyOccurrence, String treatFishFrequency, String treatFishFrequencyOccurrence, String changeWaterFrequency, String changeWaterFrequencyOccurrence, String sortFishFrequency, String sortFishFrequencyOccurrence) {
        this.stockName = stockName;
        this.dateStocked = dateStocked;
        this.currencyOfCostOfStock = currencyOfCostOfStock;
        this.costOfStock = costOfStock;
        this.stockStage = stockStage;
        this.expectedDateOfHarvest = expectedDateOfHarvest;
        this.feedFishFrequency = feedFishFrequency;
        this.feedFishFrequencyOccurrence = feedFishFrequencyOccurrence;
        this.treatFishFrequency = treatFishFrequency;
        this.treatFishFrequencyOccurrence = treatFishFrequencyOccurrence;
        this.changeWaterFrequency = changeWaterFrequency;
        this.changeWaterFrequencyOccurrence = changeWaterFrequencyOccurrence;
        this.sortFishFrequency = sortFishFrequency;
        this.sortFishFrequencyOccurrence = sortFishFrequencyOccurrence;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public String getDateStocked() {
        return dateStocked;
    }

    public String getCurrencyOfCostOfStock() {
        return currencyOfCostOfStock;
    }

    public String getCostOfStock() {
        return costOfStock;
    }

    public String getStockStage() {
        return stockStage;
    }

    public String getExpectedDateOfHarvest() {
        return expectedDateOfHarvest;
    }

    public String getFeedFishFrequency() {
        return feedFishFrequency;
    }

    public String getFeedFishFrequencyOccurrence() {
        return feedFishFrequencyOccurrence;
    }

    public String getTreatFishFrequency() {
        return treatFishFrequency;
    }

    public String getTreatFishFrequencyOccurrence() {
        return treatFishFrequencyOccurrence;
    }

    public String getChangeWaterFrequency() {
        return changeWaterFrequency;
    }

    public String getChangeWaterFrequencyOccurrence() {
        return changeWaterFrequencyOccurrence;
    }

    public String getSortFishFrequency() {
        return sortFishFrequency;
    }

    public String getSortFishFrequencyOccurrence() {
        return sortFishFrequencyOccurrence;
    }
}