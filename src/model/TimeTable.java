package model;

public class TimeTable {
    private String day;
    private String branchName;
    private String amToPm;
    private String pmToPm;
    private String pmToAm;

    public TimeTable() {

    }

    public TimeTable(String day, String branchName, String amToPm, String pmToPm, String pmToAm) {
        this.setDay(day);
        this.setBranchName(branchName);
        this.setAmToPm(amToPm);
        this.setPmToPm(pmToPm);
        this.setPmToAm(pmToAm);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAmToPm() {
        return amToPm;
    }

    public void setAmToPm(String amToPm) {
        this.amToPm = amToPm;
    }

    public String getPmToPm() {
        return pmToPm;
    }

    public void setPmToPm(String pmToPm) {
        this.pmToPm = pmToPm;
    }

    public String getPmToAm() {
        return pmToAm;
    }

    public void setPmToAm(String pmToAm) {
        this.pmToAm = pmToAm;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "day='" + day + '\'' +
                ", branchName='" + branchName + '\'' +
                ", amToPm='" + amToPm + '\'' +
                ", pmToPm='" + pmToPm + '\'' +
                ", pmToAm='" + pmToAm + '\'' +
                '}';
    }
}
