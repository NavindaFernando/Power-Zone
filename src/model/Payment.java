package model;

public class Payment {
    private String paymentName;
    private String validTime;
    private double price;

    public Payment() {

    }

    public Payment(String paymentName, String validTime, double price) {
        this.setPaymentName(paymentName);
        this.setValidTime(validTime);
        this.setPrice(price);
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
        return "Payment{" +
                "paymentName='" + paymentName + '\'' +
                ", validTime='" + validTime + '\'' +
                ", price=" + price +
                '}';
    }
}
