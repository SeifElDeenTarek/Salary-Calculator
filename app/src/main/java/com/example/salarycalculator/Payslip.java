package com.example.salarycalculator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payslips_table")
public class Payslip {
    @PrimaryKey(autoGenerate = true)
    int id;
    double netSalary;
    double bonus;
    double allowance;
    double nationalHolidays;
    double overTime;
    double deduction;
    double finalSalary;
    String dateIssued;

    public Payslip(double netSalary, double bonus, double allowance, double nationalHolidays,
                   double overTime, double deduction, double finalSalary, String dateIssued) {

        this.netSalary = netSalary;
        this.bonus = bonus;
        this.allowance = allowance;
        this.nationalHolidays = nationalHolidays;
        this.overTime = overTime;
        this.deduction = deduction;
        this.finalSalary = finalSalary;
        this.dateIssued = dateIssued;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getNationalHolidays() {
        return nationalHolidays;
    }

    public void setNationalHolidays(double nationalHolidays) {
        this.nationalHolidays = nationalHolidays;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(double finalSalary) {
        this.finalSalary = finalSalary;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }
}
