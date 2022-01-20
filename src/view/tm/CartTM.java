package view.tm;

public class CartTM {
    private String code;
    private String name;
    private int qty;
    private double unitPrice;
    private double total;

    public CartTM() {

    }

    public CartTM(String code, String name, int qty, double unitPrice, double total) {
        this.setCode(code);
        this.setName(name);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
