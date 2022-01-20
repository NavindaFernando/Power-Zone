package model;

public class Feedback {
    private String date;
    private String massage;

    public Feedback() {

    }

    public Feedback(String date, String massage) {
        this.setDate(date);
        this.setMassage(massage);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "date='" + date + '\'' +
                ", massage='" + massage + '\'' +
                '}';
    }
}
