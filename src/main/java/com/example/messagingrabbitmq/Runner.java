package com.example.messagingrabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String message;
        do {
            message = sc.nextLine();
            if (message.equals("\\q")) break;
            rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.exchangeName, "", message);
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        } while (true);
    }

}
