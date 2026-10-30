package com.hasanshaikh.jobcomparisonui;

/** A job offer or the user's current job. Plain data holder. */
public class Job {

    private String title;
    private String company;
    private String location;
    private double livingCostIndex;
    private double salary;
    private double bonus;
    private double healthInsurance;
    private double employeeDiscount;
    private double adoptionAssistance;
    private double dentalInsurance;
    private boolean current;

    public Job(String title, String company, String location, double livingCostIndex,
               double salary, double bonus, double healthInsurance, double employeeDiscount,
               double adoptionAssistance, double dentalInsurance, boolean current) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.livingCostIndex = livingCostIndex;
        this.salary = salary;
        this.bonus = bonus;
        this.healthInsurance = healthInsurance;
        this.employeeDiscount = employeeDiscount;
        this.adoptionAssistance = adoptionAssistance;
        this.dentalInsurance = dentalInsurance;
        this.current = current;
    }

    /** Salary scaled to a living cost index of 100, so cities compare fairly. */
    public double getAdjustedSalary() {
        return salary * 100 / livingCostIndex;
    }

    public double getAdjustedBonus() {
        return bonus * 100 / livingCostIndex;
    }

    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public double getLivingCostIndex() { return livingCostIndex; }
    public double getSalary() { return salary; }
    public double getBonus() { return bonus; }
    public double getHealthInsurance() { return healthInsurance; }
    public double getEmployeeDiscount() { return employeeDiscount; }
    public double getAdoptionAssistance() { return adoptionAssistance; }
    public double getDentalInsurance() { return dentalInsurance; }
    public boolean isCurrent() { return current; }

    public String label() {
        return title + " at " + company;
    }
}
