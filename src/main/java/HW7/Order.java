package HW7;

import javax.persistence.*;

import static HW7.Main.em;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private int orderSerialNumber;
    @Column
    private int clientID;
    @Column
    private int productId;
    @Column
    private int productQuantity;
    @Column
    private Double sumOfOrder;

    public Order(int orderSerialNumber, int clientID, int productId, int productQuantity) {
        this.orderSerialNumber = orderSerialNumber;
        this.clientID = clientID;
        this.productId = productId;
        this.productQuantity = productQuantity;
        Product p = em.find(Product.class, productId);
        this.sumOfOrder = p.getPrice() * productQuantity;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public int getOrderSerialNumber() {
        return orderSerialNumber;
    }

    public int getClientID() {
        return clientID;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
    public double getSumOfOrder() {
        return sumOfOrder;
    }
    public void setOrderSerialNumber(int orderSerialNumber) {
        this.orderSerialNumber = orderSerialNumber;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderSerialNumber=" + orderSerialNumber +
                ", clientID=" + clientID +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", sumOfOrder=" + sumOfOrder +
                '}';
    }
}
