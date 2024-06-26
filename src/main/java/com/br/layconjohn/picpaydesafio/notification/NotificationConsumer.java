package com.br.layconjohn.picpaydesafio.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.br.layconjohn.picpaydesafio.authorization.AuthorizerService;
import com.br.layconjohn.picpaydesafio.transaction.Transaction;

@Service
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizerService.class);


    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
            .baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
            .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio")
    public void receiveNotification(Transaction transaction) {
        LOGGER.info("Notifying transaction: {}", transaction);

        var response = this.restClient.get()
        .retrieve()
        .toEntity(Notification.class);

        if (response.getStatusCode().isError() || !response.getBody().message()) {
            throw new NotificationException("Error to send notification");
        }

        LOGGER.info("Notification has been send: {}", transaction);
    }
}
