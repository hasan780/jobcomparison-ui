package com.hasanshaikh.jobcomparisonui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In memory store for the demo. Holds the jobs and the comparison
 * weights for as long as the app is running. Nothing is written to disk,
 * which keeps this project to the user interface it is meant to show.
 */
public final class JobStore {

    private static final JobStore INSTANCE = new JobStore();

    public static JobStore get() { return INSTANCE; }

    private final List<Job> jobs = new ArrayList<>();

    private int salaryWeight = 1;
    private int bonusWeight = 1;
    private int healthInsuranceWeight = 1;
    private int employeeDiscountWeight = 1;
    private int adoptionAssistanceWeight = 1;
    private int dentalInsuranceWeight = 1;

    private JobStore() { }

    public void save(Job job) {
        if (job.isCurrent()) {
            for (int i = 0; i < jobs.size(); i++) {
                if (jobs.get(i).isCurrent()) {
                    jobs.set(i, job);
                    return;
                }
            }
        }
        jobs.add(job);
    }

    public List<Job> getJobs() {
        return Collections.unmodifiableList(jobs);
    }

    public Job getCurrentJob() {
        for (Job job : jobs) {
            if (job.isCurrent()) { return job; }
        }
        return null;
    }

    /** Comparison needs two jobs to compare. */
    public boolean canCompare() {
        return jobs.size() >= 2;
    }

    public void setWeights(int salary, int bonus, int health,
                           int discount, int adoption, int dental) {
        salaryWeight = salary;
        bonusWeight = bonus;
        healthInsuranceWeight = health;
        employeeDiscountWeight = discount;
        adoptionAssistanceWeight = adoption;
        dentalInsuranceWeight = dental;
    }

    public int getSalaryWeight() { return salaryWeight; }
    public int getBonusWeight() { return bonusWeight; }
    public int getHealthInsuranceWeight() { return healthInsuranceWeight; }
    public int getEmployeeDiscountWeight() { return employeeDiscountWeight; }
    public int getAdoptionAssistanceWeight() { return adoptionAssistanceWeight; }
    public int getDentalInsuranceWeight() { return dentalInsuranceWeight; }

    /** Weighted average of the six values, with pay scaled for living cost. */
    public double score(Job job) {
        int total = salaryWeight + bonusWeight + healthInsuranceWeight
                + employeeDiscountWeight + adoptionAssistanceWeight + dentalInsuranceWeight;
        if (total == 0) { return 0; }
        double weighted = salaryWeight * job.getAdjustedSalary()
                + bonusWeight * job.getAdjustedBonus()
                + healthInsuranceWeight * job.getHealthInsurance()
                + employeeDiscountWeight * job.getEmployeeDiscount()
                + adoptionAssistanceWeight * job.getAdoptionAssistance()
                + dentalInsuranceWeight * job.getDentalInsurance();
        return weighted / total;
    }

    public List<Job> ranked() {
        List<Job> copy = new ArrayList<>(jobs);
        copy.sort((a, b) -> Double.compare(score(b), score(a)));
        return copy;
    }
}
