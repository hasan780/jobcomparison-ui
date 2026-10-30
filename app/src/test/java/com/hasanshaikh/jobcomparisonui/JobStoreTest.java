package com.hasanshaikh.jobcomparisonui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JobStoreTest {

    private Job job(String title, double livingCost, double salary, double bonus) {
        return new Job(title, "Acme", "Atlanta", livingCost, salary, bonus,
                0, 0, 0, 0, false);
    }

    @Test
    public void payIsScaledToALivingCostIndexOfOneHundred() {
        Job expensive = job("Expensive", 200, 100000, 10000);
        assertEquals(50000, expensive.getAdjustedSalary(), 0.01);
        assertEquals(5000, expensive.getAdjustedBonus(), 0.01);

        Job cheap = job("Cheap", 50, 100000, 10000);
        assertEquals(200000, cheap.getAdjustedSalary(), 0.01);
    }

    @Test
    public void scoreIsTheWeightedAverageOfTheSixValues() {
        JobStore store = JobStore.get();
        store.setWeights(1, 1, 1, 1, 1, 1);
        Job offer = new Job("Offer", "Acme", "Atlanta", 100,
                120000, 12000, 1000, 2000, 3000, 4000, false);
        // 120000 + 12000 + 1000 + 2000 + 3000 + 4000 = 142000 over 6 weights
        assertEquals(142000.0 / 6, store.score(offer), 0.01);
    }

    @Test
    public void weightsOfAllZeroDoNotBlowUp() {
        JobStore store = JobStore.get();
        store.setWeights(0, 0, 0, 0, 0, 0);
        assertEquals(0, store.score(job("Any", 100, 90000, 0)), 0.01);
        store.setWeights(1, 1, 1, 1, 1, 1);
    }

    @Test
    public void payWeightedHigherRanksThePayingJobFirst() {
        JobStore store = JobStore.get();
        store.setWeights(9, 1, 1, 1, 1, 1);
        Job bigSalary = new Job("Big salary", "Acme", "Atlanta", 100,
                200000, 0, 0, 0, 0, 0, false);
        Job bigBenefits = new Job("Big benefits", "Globex", "Austin", 100,
                80000, 0, 2000, 9000, 10000, 5000, false);
        assertTrue(store.score(bigSalary) > store.score(bigBenefits));
    }
}
