package model;

public class ItemDetail {
    private String itemCode;
    private double unitPrice;
    private int qtyForSell;

    public ItemDetail() {

    }

    public ItemDetail(String itemCode, double unitPrice, int qtyForSell) {
        this.setItemCode(itemCode);
        this.setUnitPrice(unitPrice);
        this.setQtyForSell(qtyForSell);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    @Override
    public String toString() {
        return "ItemDetail{" +
                "itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyForSell=" + qtyForSell +
                '}';
    }
}
