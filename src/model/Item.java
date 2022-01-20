package model;

public class Item {
    private String itemCode;
    private String name;
    private String description;
    private int qtyOnHand;
    private String Size;
    private double unitPrice;

    public Item() {

    }

    public Item(String itemCode, String name, String description, int qtyOnHand, String size, double unitPrice) {
        this.setItemCode(itemCode);
        this.setName(name);
        this.setDescription(description);
        this.setQtyOnHand(qtyOnHand);
        setSize(size);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", Size='" + Size + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
