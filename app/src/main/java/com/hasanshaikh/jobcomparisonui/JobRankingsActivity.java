package com.hasanshaikh.jobcomparisonui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/** Jobs listed best to worst by score. The current job is marked. */
public class JobRankingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_rankings);

        ListView rankingsList = findViewById(R.id.jobRankingsListID);
        JobStore store = JobStore.get();

        List<String> rows = new ArrayList<>();
        int position = 1;
        for (Job job : store.ranked()) {
            String row = position + ". " + job.label();
            if (job.isCurrent()) {
                row = row + "  (current job)";
            }
            rows.add(row);
            position++;
        }

        rankingsList.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, rows));

        Button back = findViewById(R.id.backToMainMenuButtonID);
        back.setOnClickListener(v -> finish());
    }
}
