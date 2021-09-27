package com.grp04.togosvc.delivery;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Delivery_table")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Long customerId;
    private String deliveryStatus;
    private Long productId;

    @PostPersist
    public void onPostPersist(){
        // try {
        //     Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }        
        DeliveryStarted deliveryStarted = new DeliveryStarted();
        BeanUtils.copyProperties(this, deliveryStarted);
        deliveryStarted.publishAfterCommit();

    }
    @PostUpdate
    public void onPostUpdate(){
        DeliveryCompleted deliveryCompleted = new DeliveryCompleted();
        BeanUtils.copyProperties(this, deliveryCompleted);
        deliveryCompleted.publishAfterCommit();

    }
    @PostRemove
    public void onPostRemove(){
        DeliveryCanceled deliveryCanceled = new DeliveryCanceled();
        BeanUtils.copyProperties(this, deliveryCanceled);
        deliveryCanceled.publishAfterCommit();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}