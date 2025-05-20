package com.example.salarycalculator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PayslipsDao {
    @Insert
    void insertPayslip(Payslip payslip);

    @Query("select * from payslips_table order by dateIssued Desc")
    List<Payslip> getPayslips();
}
