package com.example.salarycalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button calculate, back;
    MaterialCardView salCard, quesCard;
    TextInputEditText monthSal, deduction, doubles, allowances;
    TextInputLayout monthSalLayout, deductionLayout, doublesLayout, allowancesLayout;
    TextView sal;
    float deductionAmount = 0.0F;
    float doublesAmount = 0.0F;
    float allowancesAmount = 0.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        salCard = findViewById(R.id.card);
        quesCard = findViewById(R.id.card1);
        monthSal = findViewById(R.id.monthlySalary);
        deduction = findViewById(R.id.deduction);
        doubles = findViewById(R.id.doubles);
        allowances = findViewById(R.id.allowances);
        sal = findViewById(R.id.final_salary);
        monthSalLayout = findViewById(R.id.monthlySalaryLayout);
        deductionLayout = findViewById(R.id.deductionLayout);
        doublesLayout = findViewById(R.id.doublesLayout);
        allowancesLayout = findViewById(R.id.allowancesLayout);
        calculate = findViewById(R.id.calculate);
        back = findViewById(R.id.back);

        calculate.setOnClickListener(view -> {
            String salStr = Objects.requireNonNull(monthSal.getText()).toString().trim();
            String dedStr = Objects.requireNonNull(deduction.getText()).toString().trim();
            String dblStr = Objects.requireNonNull(doubles.getText()).toString().trim();
            String allStr = Objects.requireNonNull(allowances.getText()).toString().trim();

            try {
                // Salary Input Validation
                if (salStr.isEmpty()) {
                    monthSalLayout.setError("Monthly salary is required");
                    Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                float salary = Float.parseFloat(salStr);
                float daySal = salary / 30;

                //Deduction input Validation
                if (!dedStr.isEmpty() && Float.parseFloat(dedStr) <= 30) {
                    deductionAmount = Float.parseFloat(dedStr) * daySal;
                }
                else if(dedStr.isEmpty()) {
                    deductionAmount = 0.0F;
                }else{
                    deductionLayout.setError("can't be more than 30");
                    return;
                }

                // Doubles input validation
                if(!dblStr.isEmpty() && Float.parseFloat(dblStr) <= 30){
                    doublesAmount = Float.parseFloat(dblStr) * daySal;
                }
                else if(dblStr.isEmpty()) {
                    doublesAmount = 0.0F;
                }else{
                    doublesLayout.setError("can't be more than 30");
                    return;
                }

                // Deactivate the Questions Card
                quesCard.setEnabled(false);
                quesCard.setAlpha(.3F);

                // Allowances input validation
                if(!allStr.isEmpty()){
                    allowancesAmount = Float.parseFloat(allStr);
                }
                else{
                    allowancesAmount = 0.0F;
                }

                // Calculate the final salary
                float finalSalary = (salary - deductionAmount) + (doublesAmount + allowancesAmount);

                // Display the final salary
                DecimalFormat df = new DecimalFormat("###,###.##");
                String salString = df.format(finalSalary) + " EGP";
                sal.setText(salString);

                // Show the salary card
                salCard.setVisibility(MaterialCardView.VISIBLE);

            }catch (NumberFormatException e) {
                // Handle invalid number input
                Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(view -> {
            // hide the salary card
            salCard.setVisibility(MaterialCardView.GONE);

            // Reactivate the questions card
            quesCard.setEnabled(true);
            quesCard.setAlpha(1F);

            // Resets the errors
            monthSalLayout.setError(null);
            deductionLayout.setError(null);
            doublesLayout.setError(null);

            // Clear the text fields
            monthSal.setText(null);
            deduction.setText(null);
            doubles.setText(null);
            allowances.setText(null);
        });
    }

}