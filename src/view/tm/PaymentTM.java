package view.tm;

public class PaymentTM {
    private String paymentName;
    private String validTime;
    private double price;

    public PaymentTM() {

    }

    public PaymentTM(String paymentName, String validTime, double price) {
        this.paymentName = paymentName;
        this.validTime = validTime;
        this.price = price;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "paymentName='" + paymentName + '\'' +
                ", validTime='" + validTime + '\'' +
                ", price=" + price +
                '}';
    }
}
