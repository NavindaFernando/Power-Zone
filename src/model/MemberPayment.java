package model;

import java.util.ArrayList;

public class MemberPayment {
    private String paymentId;
    private String memberId;
    private String paymentDate;
    private String paymentTime;
    private double cost;
    private ArrayList<PaymentDetail> payments;

    public MemberPayment() {

    }

    public MemberPayment(String paymentId, String memberId, String paymentDate, String paymentTime, double cost, ArrayList<PaymentDetail> payments) {
        this.setPaymentId(paymentId);
        this.setMemberId(memberId);
        this.setPaymentDate(paymentDate);
        this.setPaymentTime(paymentTime);
        this.setCost(cost);
        this.setPayments(payments);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<PaymentDetail> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<PaymentDetail> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "MemberPayment{" +
                "paymentId='" + paymentId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", cost=" + cost +
                ", payments=" + payments +
                '}';
    }
}
