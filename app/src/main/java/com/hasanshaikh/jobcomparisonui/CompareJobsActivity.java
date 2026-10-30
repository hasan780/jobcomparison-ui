package com.hasanshaikh.jobcomparisonui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Side by side comparison of two saved jobs. */
public class CompareJobsActivity extends AppCompatActivity {

    private Spinner firstJob;
    private Spinner secondJob;
    private TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs);

        firstJob = findViewById(R.id.firstJobSpinnerID);
        secondJob = findViewById(R.id.secondJobSpinnerID);
        results = findViewById(R.id.comparisonResultsID);

        List<String> labels = new ArrayList<>();
        for (Job job : JobStore.get().getJobs()) {
            labels.add(job.label());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, labels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstJob.setAdapter(adapter);
        secondJob.setAdapter(adapter);
        if (labels.size() > 1) {
            secondJob.setSelection(1);
        }

        Button compare = findViewById(R.id.compareButtonID);
        Button back = findViewById(R.id.backToMainMenuButtonID);
        compare.setOnClickListener(v -> compare());
        back.setOnClickListener(v -> finish());
    }

    private void compare() {
        int first = firstJob.getSelectedItemPosition();
        int second = secondJob.getSelectedItemPosition();
        if (first == second) {
            Toast.makeText(this, "Pick two different jobs.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Job> jobs = JobStore.get().getJobs();
        results.setText(table(jobs.get(first), jobs.get(second)));
    }

    private String table(Job a, Job b) {
        StringBuilder out = new StringBuilder();
        out.append(row("", a.getTitle(), b.getTitle()));
        out.append(row("Company", a.getCompany(), b.getCompany()));
        out.append(row("Location", a.getLocation(), b.getLocation()));
        out.append(row("Adj. salary", money(a.getAdjustedSalary()), money(b.getAdjustedSalary())));
        out.append(row("Adj. bonus", money(a.getAdjustedBonus()), money(b.getAdjustedBonus())));
        out.append(row("Health ins.", money(a.getHealthInsurance()), money(b.getHealthInsurance())));
        out.append(row("Emp. discount", money(a.getEmployeeDiscount()), money(b.getEmployeeDiscount())));
        out.append(row("Adoption", money(a.getAdoptionAssistance()), money(b.getAdoptionAssistance())));
        out.append(row("Dental ins.", money(a.getDentalInsurance()), money(b.getDentalInsurance())));
        JobStore store = JobStore.get();
        out.append(row("Score", money(store.score(a)), money(store.score(b))));
        return out.toString();
    }

    private String row(String label, String left, String right) {
        return String.format(Locale.US, "%-14s %-14s %-14s%n", label, left, right);
    }

    private String money(double value) {
        return String.format(Locale.US, "%,.0f", value);
    }
}
