package saif.rest.springrest.DAO;

import java.io.Serializable;

public class Course implements Serializable {
    private String id;
    private String name;
    private String section;
    private int result;
    private double sum;
    private double avg;
    private double max;
    private double min;

    public Course() {}

    public Course(String id, String name, String section) {
        this.id = id;
        this.name = name;
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
