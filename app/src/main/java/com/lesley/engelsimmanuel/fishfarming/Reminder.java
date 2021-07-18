package com.lesley.engelsimmanuel.fishfarming;

import com.google.firebase.Timestamp;

public class Reminder {
    private String stock_name;
    private String date_stocked;
    private String currency_of_cost_of_stock;
    private String cost_of_stock;
    private String stock_stage;
    private String expected_date_of_harvest;
    private String feed_fish_frequency;
    private String feed_fish_frequency_occurrence;
    private String treat_fish_frequency;
    private String treat_fish_frequency_occurrence;
    private String change_water_frequency;
    private String change_water_frequency_occurrence;
    private String sort_fish_frequency;
    private String sort_fish_frequency_occurrence;
    private boolean reminder_booked;
    private String by;
    private Timestamp timestamp;

    // required empty constructor, needed for firebase
    public Reminder() {
    }

    public Reminder(String stock_name, String date_stocked, String currency_of_cost_of_stock, String cost_of_stock, String stock_stage, String expected_date_of_harvest, String feed_fish_frequency, String feed_fish_frequency_occurrence, String treat_fish_frequency, String treat_fish_frequency_occurrence, String change_water_frequency, String change_water_frequency_occurrence, String sort_fish_frequency, String sort_fish_frequency_occurrence, boolean reminder_booked, String by, Timestamp timestamp) {
        this.stock_name = stock_name;
        this.date_stocked = date_stocked;
        this.currency_of_cost_of_stock = currency_of_cost_of_stock;
        this.cost_of_stock = cost_of_stock;
        this.stock_stage = stock_stage;
        this.expected_date_of_harvest = expected_date_of_harvest;
        this.feed_fish_frequency = feed_fish_frequency;
        this.feed_fish_frequency_occurrence = feed_fish_frequency_occurrence;
        this.treat_fish_frequency = treat_fish_frequency;
        this.treat_fish_frequency_occurrence = treat_fish_frequency_occurrence;
        this.change_water_frequency = change_water_frequency;
        this.change_water_frequency_occurrence = change_water_frequency_occurrence;
        this.sort_fish_frequency = sort_fish_frequency;
        this.sort_fish_frequency_occurrence = sort_fish_frequency_occurrence;
        this.reminder_booked = reminder_booked;
        this.by = by;
        this.timestamp = timestamp;
    }

    public String getStock_name() {
        return stock_name;
    }

    public String getDate_stocked() {
        return date_stocked;
    }

    public String getCurrency_of_cost_of_stock() {
        return currency_of_cost_of_stock;
    }

    public String getCost_of_stock() {
        return cost_of_stock;
    }

    public String getStock_stage() {
        return stock_stage;
    }

    public String getExpected_date_of_harvest() {
        return expected_date_of_harvest;
    }

    public String getFeed_fish_frequency() {
        return feed_fish_frequency;
    }

    public String getFeed_fish_frequency_occurrence() {
        return feed_fish_frequency_occurrence;
    }

    public String getTreat_fish_frequency() {
        return treat_fish_frequency;
    }

    public String getTreat_fish_frequency_occurrence() {
        return treat_fish_frequency_occurrence;
    }

    public String getChange_water_frequency() {
        return change_water_frequency;
    }

    public String getChange_water_frequency_occurrence() {
        return change_water_frequency_occurrence;
    }

    public String getSort_fish_frequency() {
        return sort_fish_frequency;
    }

    public String getSort_fish_frequency_occurrence() {
        return sort_fish_frequency_occurrence;
    }

    public boolean isReminder_booked() {
        return reminder_booked;
    }

    public String getBy() {
        return by;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}