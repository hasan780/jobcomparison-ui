package com.hasanshaikh.jobcomparisonui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Entry form for the current job or a job offer. */
public class JobDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_CURRENT_JOB = "currentJob";

    private boolean currentJob;

    private EditText title;
    private EditText company;
    private EditText location;
    private EditText livingCostIndex;
    private EditText salary;
    private EditText bonus;
    private EditText healthInsurance;
    private EditText employeeDiscount;
    private EditText adoptionAssistance;
    private EditText dentalInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        currentJob = getIntent().getBooleanExtra(EXTRA_CURRENT_JOB, false);
        TextView header = findViewById(R.id.jobDetailsHeaderID);
        header.setText(currentJob ? R.string.current_job_details : R.string.job_offer_details);

        title = findViewById(R.id.titleID);
        company = findViewById(R.id.companyID);
        location = findViewById(R.id.locationID);
        livingCostIndex = findViewById(R.id.livingCostIndexID);
        salary = findViewById(R.id.salaryID);
        bonus = findViewById(R.id.bonusID);
        healthInsurance = findViewById(R.id.healthInsuranceID);
        employeeDiscount = findViewById(R.id.employeeDiscountID);
        adoptionAssistance = findViewById(R.id.adoptionAssistanceID);
        dentalInsurance = findViewById(R.id.dentalInsuranceID);

        prefill();

        Button save = findViewById(R.id.saveJobButtonID);
        Button cancel = findViewById(R.id.cancelJobButtonID);
        save.setOnClickListener(v -> saveJob());
        cancel.setOnClickListener(v -> finish());
    }

    /** Reopening the current job shows what was saved before. */
    private void prefill() {
        if (!currentJob) { return; }
        Job saved = JobStore.get().getCurrentJob();
        if (saved == null) { return; }
        title.setText(saved.getTitle());
        company.setText(saved.getCompany());
        location.setText(saved.getLocation());
        livingCostIndex.setText(text(saved.getLivingCostIndex()));
        salary.setText(text(saved.getSalary()));
        bonus.setText(text(saved.getBonus()));
        healthInsurance.setText(text(saved.getHealthInsurance()));
        employeeDiscount.setText(text(saved.getEmployeeDiscount()));
        adoptionAssistance.setText(text(saved.getAdoptionAssistance()));
        dentalInsurance.setText(text(saved.getDentalInsurance()));
    }

    private void saveJob() {
        try {
            double index = number(livingCostIndex, "Living cost index");
            if (index <= 0) {
                throw new IllegalArgumentException("Living cost index must be greater than 0.");
            }
            Job job = new Job(
                    required(title, "Title"),
                    required(company, "Company"),
                    required(location, "Location"),
                    index,
                    number(salary, "Salary"),
                    number(bonus, "Bonus"),
                    number(healthInsurance, "Health insurance"),
                    number(employeeDiscount, "Employee discount"),
                    number(adoptionAssistance, "Adoption assistance"),
                    number(dentalInsurance, "Dental insurance"),
                    currentJob);
            JobStore.get().save(job);
            finish();
        } catch (IllegalArgumentException error) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String required(EditText field, String label) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException(label + " is required.");
        }
        return value;
    }

    private double number(EditText field, String label) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) { return 0; }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(label + " must be a number.");
        }
    }

    private String text(double value) {
        if (value == Math.rint(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
