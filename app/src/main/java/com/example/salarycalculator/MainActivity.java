package com.example.salarycalculator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
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
import java.time.Duration;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    float deductionAmount = 0.0F;
    float doublesAmount = 0.0F;
    float allowancesAmount = 0.0F;

    boolean started = false;
    boolean rotating = false;

    ObjectAnimator rotation;
    float currentRotation;

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

        MaterialCardView salCard = findViewById(R.id.card);
        MaterialCardView quesCard = findViewById(R.id.card1);
        TextInputEditText monthSal = findViewById(R.id.monthlySalary);
        TextInputEditText deduction = findViewById(R.id.deduction);
        TextInputEditText doubles = findViewById(R.id.doubles);
        TextInputEditText allowances = findViewById(R.id.allowances);
        TextView sal = findViewById(R.id.final_salary);
        TextInputLayout monthSalLayout = findViewById(R.id.monthlySalaryLayout);
        TextInputLayout deductionLayout = findViewById(R.id.deductionLayout);
        TextInputLayout doublesLayout = findViewById(R.id.doublesLayout);
        Button calculate = findViewById(R.id.calculate);
        Button back = findViewById(R.id.back);
        ImageView image = findViewById(R.id.image);

        // Image animation
        rotation = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);
        rotation.setDuration(7777);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.start();

        image.setOnClickListener(view -> {
            if(!rotating){
                    rotation.pause();
                }else {
                    rotation.resume();
                }
            rotating = !rotating;
        });

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

                // Allowances input validation
                if(!allStr.isEmpty()){
                    allowancesAmount = Float.parseFloat(allStr);
                }
                else{
                    allowancesAmount = 0.0F;
                }

                // Calculate the final salary
                float finalSalary = (salary - deductionAmount) + (doublesAmount + allowancesAmount);

                //lose focus
                monthSal.clearFocus();
                deduction.clearFocus();
                doubles.clearFocus();
                allowances.clearFocus();

                // Hide Keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View rootView = findViewById(android.R.id.content);
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

                // Deactivate the Questions Card and the Image
                quesCard.setEnabled(false);
                quesCard.setAlpha(.3F);
                image.setAlpha(.3F);

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

            // Reactivate the questions card and Image
            quesCard.setEnabled(true);
            quesCard.setAlpha(1F);
            image.setAlpha(1F);

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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof TextInputEditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}