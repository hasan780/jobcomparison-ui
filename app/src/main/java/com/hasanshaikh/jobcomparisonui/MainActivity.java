package com.hasanshaikh.jobcomparisonui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/** Main menu. Routes to the other four screens. */
public class MainActivity extends AppCompatActivity {

    private Button compareJobsButton;
    private Button viewJobRanksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enterCurrentJob = findViewById(R.id.enterCurrentJobButtonID);
        Button addJobOffer = findViewById(R.id.addJobOfferButtonID);
        Button editSettings = findViewById(R.id.editSettingsButtonID);
        Button exit = findViewById(R.id.exitButtonID);
        compareJobsButton = findViewById(R.id.compareJobsButtonID);
        viewJobRanksButton = findViewById(R.id.viewJobRanksButtonID);

        enterCurrentJob.setOnClickListener(v -> openJobDetails(true));
        addJobOffer.setOnClickListener(v -> openJobDetails(false));
        editSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        compareJobsButton.setOnClickListener(v -> startActivity(new Intent(this, CompareJobsActivity.class)));
        viewJobRanksButton.setOnClickListener(v -> startActivity(new Intent(this, JobRankingsActivity.class)));
        exit.setOnClickListener(v -> finishAffinity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean enabled = JobStore.get().canCompare();
        compareJobsButton.setEnabled(enabled);
        viewJobRanksButton.setEnabled(enabled);
    }

    private void openJobDetails(boolean currentJob) {
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra(JobDetailsActivity.EXTRA_CURRENT_JOB, currentJob);
        startActivity(intent);
    }
}
