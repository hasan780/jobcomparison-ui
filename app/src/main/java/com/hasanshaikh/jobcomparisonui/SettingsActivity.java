package com.hasanshaikh.jobcomparisonui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Six weights, 0 to 9, used to rank and compare jobs. */
public class SettingsActivity extends AppCompatActivity {

    private EditText salaryWeight;
    private EditText bonusWeight;
    private EditText healthInsuranceWeight;
    private EditText employeeDiscountWeight;
    private EditText adoptionAssistanceWeight;
    private EditText dentalInsuranceWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        salaryWeight = findViewById(R.id.salaryWeightID);
        bonusWeight = findViewById(R.id.bonusWeightID);
        healthInsuranceWeight = findViewById(R.id.healthInsuranceWeightID);
        employeeDiscountWeight = findViewById(R.id.employeeDiscountWeightID);
        adoptionAssistanceWeight = findViewById(R.id.adoptionAssistanceWeightID);
        dentalInsuranceWeight = findViewById(R.id.dentalInsuranceWeightID);

        JobStore store = JobStore.get();
        salaryWeight.setText(String.valueOf(store.getSalaryWeight()));
        bonusWeight.setText(String.valueOf(store.getBonusWeight()));
        healthInsuranceWeight.setText(String.valueOf(store.getHealthInsuranceWeight()));
        employeeDiscountWeight.setText(String.valueOf(store.getEmployeeDiscountWeight()));
        adoptionAssistanceWeight.setText(String.valueOf(store.getAdoptionAssistanceWeight()));
        dentalInsuranceWeight.setText(String.valueOf(store.getDentalInsuranceWeight()));

        Button save = findViewById(R.id.saveSettingsButtonID);
        Button cancel = findViewById(R.id.cancelSettingsButtonID);
        save.setOnClickListener(v -> saveWeights());
        cancel.setOnClickListener(v -> finish());
    }

    private void saveWeights() {
        try {
            JobStore.get().setWeights(
                    weight(salaryWeight, "Salary weight"),
                    weight(bonusWeight, "Bonus weight"),
                    weight(healthInsuranceWeight, "Health insurance weight"),
                    weight(employeeDiscountWeight, "Employee discount weight"),
                    weight(adoptionAssistanceWeight, "Adoption assistance weight"),
                    weight(dentalInsuranceWeight, "Dental insurance weight"));
            finish();
        } catch (IllegalArgumentException error) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int weight(EditText field, String label) {
        String value = field.getText().toString().trim();
        int parsed;
        try {
            parsed = Integer.parseInt(value);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(label + " must be a whole number.");
        }
        if (parsed < 0 || parsed > 9) {
            throw new IllegalArgumentException(label + " must be between 0 and 9.");
        }
        return parsed;
    }
}
