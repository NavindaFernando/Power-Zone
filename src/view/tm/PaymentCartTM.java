package view.tm;

public class PaymentCartTM {
    private String paymentName;
    private double price;
    private String validTime;
    private int qty;
    private double total;

    public PaymentCartTM() {

    }

    public PaymentCartTM(String paymentName, double price, String validTime, int qty, double total) {
        this.setPaymentName(paymentName);
        this.setPrice(price);
        this.setValidTime(validTime);
        this.setQty(qty);
        this.setTotal(total);
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PaymentCartTM{" +
                "paymentName='" + paymentName + '\'' +
                ", price=" + price +
                ", validTime='" + validTime + '\'' +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
