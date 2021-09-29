package com.grp04.togosvc.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.grp04.togosvc.delivery.kafka.KafkaProcessor;

@Service
public class PolicyHandler {
    private DeliveryRepository deliveryRepository;

    @Autowired
    public PolicyHandler(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderPlaced_StartShipping(@Payload OrderPlaced orderPlaced){

        System.out.println("##### listener : ");

        if(!orderPlaced.validate()) return;

        System.out.println("\n\n##### listener StartShipping : " + orderPlaced.toJson() + "\n\n");

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderPlaced.getId());
        delivery.setProductId(orderPlaced.getProductId());
        delivery.setCustomerId(orderPlaced.getCustomerId());
        delivery.setDeliveryStatus("Delivery Started");
        deliveryRepository.save(delivery);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_CancelShipping(@Payload OrderCanceled orderCanceled){

        if(!orderCanceled.validate()) return;

        System.out.println("\n\n##### listener CancelShipping : " + orderCanceled.toJson() + "\n\n");

        Delivery cancelDelivery = deliveryRepository.findByOrderId(orderCanceled.getId());

        cancelDelivery.setDeliveryStatus("Delivery Cancelled.");
        deliveryRepository.save(cancelDelivery);
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReturnRequested_PickupProduct(@Payload ReturnRequested returnRequested){

        if(!returnRequested.validate()) return;

        System.out.println("\n\n##### listener PickupProduct : " + returnRequested.toJson() + "\n\n");

        Delivery picupProduct =  deliveryRepository.findByOrderId(returnRequested.getId());
        picupProduct.setDeliveryStatus("Product will be picked up.");

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}
}