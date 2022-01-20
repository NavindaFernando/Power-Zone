package view.tm;

public class FeedbackTM {
    private String date;
    private String massage;

    public FeedbackTM() {

    }

    public FeedbackTM(String date, String massage) {
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
        return "FeedbackTM{" +
                "date='" + date + '\'' +
                ", massage='" + massage + '\'' +
                '}';
    }
}
