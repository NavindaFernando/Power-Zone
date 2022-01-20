package model;

public class PaymentDetail {
    String paymentName;
    String memberId;
    double price;

    public PaymentDetail() {

    }

    public PaymentDetail(String paymentName, String memberId, double price) {
        this.paymentName = paymentName;
        this.memberId = memberId;
        this.price = price;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "paymentName='" + paymentName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", price=" + price +
                '}';
    }
}
