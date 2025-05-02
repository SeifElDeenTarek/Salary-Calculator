package com.example.salarycalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

    Button calculate;
    MaterialCardView salCard;
    TextInputEditText monthSal, deduction, doubles;
    TextInputLayout monthSalLayout, deductionLayout, doublesLayout;
    TextView sal;
    float deductionAmount = 0.0F;
    float doublesAmount = 0.0F;


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
        monthSal = findViewById(R.id.monthlySalary);
        deduction = findViewById(R.id.deduction);
        doubles = findViewById(R.id.doubles);
        sal = findViewById(R.id.final_salary);
        monthSalLayout = findViewById(R.id.monthlySalaryLayout);
        deductionLayout = findViewById(R.id.deductionLayout);
        doublesLayout = findViewById(R.id.doublesLayout);
        calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(view -> {
            String salStr = Objects.requireNonNull(monthSal.getText()).toString().trim();
            String dedStr = Objects.requireNonNull(deduction.getText()).toString().trim();
            String dblStr = Objects.requireNonNull(doubles.getText()).toString().trim();


            monthSalLayout.setError(null);
            deductionLayout.setError(null);
            doublesLayout.setError(null);

            if (salStr.isEmpty()) {
                monthSalLayout.setError("Monthly salary is required");
                return;
            }

            try {
                float salary = Float.parseFloat(salStr);
                float daySal = salary / 30;

                if (!dedStr.isEmpty() && Float.parseFloat(dedStr) <= 30) {
                    deductionAmount = Float.parseFloat(dedStr) * daySal;
                }
                else if(dedStr.isEmpty()) {
                    deductionAmount = 0.0F;
                }else{
                    deductionLayout.setError("can't be more than 30");
                }

                if(!dblStr.isEmpty() && Float.parseFloat(dblStr) <= 30){
                    doublesAmount = Float.parseFloat(dblStr) * daySal;
                }
                else if(dblStr.isEmpty()) {
                    doublesAmount = 0.0F;
                }else{
                    doublesLayout.setError("can't be more than 30");
                }

                // Calculate the final salary
                float finalSalary = (salary - deductionAmount) + doublesAmount;

                // Display the final salary
                DecimalFormat df = new DecimalFormat("###,###.##");
                String salString = df.format(finalSalary) + " EGP";
                sal.setText(salString);

                // Show the result card
                salCard.setVisibility(MaterialCardView.VISIBLE);

            }catch (NumberFormatException e) {
                // Handle invalid number input
                Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }

        });



    }
}