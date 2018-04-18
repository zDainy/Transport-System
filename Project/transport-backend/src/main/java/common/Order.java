package common;

import java.util.Date;

public class Order {
    private int id;
    private Date dateCreated;
    private int customerId;
    private int serviceId;

    public Order(int id, Date dateCreated, int customerId, int serviceId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.customerId = customerId;
        this.serviceId = serviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
