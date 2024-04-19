package com.br.layconjohn.picpaydesafio.notification;

import org.springframework.stereotype.Service;

import com.br.layconjohn.picpaydesafio.transaction.Transaction;

@Service
public class NotificationService {
    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }
    public void notify(Transaction transaction) {
        notificationProducer.sendNotification(transaction);
    }
}
