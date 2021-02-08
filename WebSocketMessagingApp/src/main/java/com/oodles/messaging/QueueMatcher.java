package com.oodles.messaging;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class QueueMatcher {

    public MessageQueueRepresentation generateRandomQueue(String queue, Long currentUser, Long toUser) {
      String queueName = queue + UUID.randomUUID().toString().substring(0, 24);
      List<Long> connectedUserList = List.of(toUser, currentUser);
        MessageQueueRepresentation messageQueueRepresentation = new MessageQueueRepresentation();
        messageQueueRepresentation.setConnectedUsers(connectedUserList);
        messageQueueRepresentation.setDestinationUrl("/queue/" + queueName);
        messageQueueRepresentation.setSubscriptionUrl("/queue/" + queueName);
        return messageQueueRepresentation;
    }
}

class MessageQueueRepresentation {
    private String destinationUrl;
    private String subscriptionUrl;
    private List<Long> connectedUsers;

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public String getSubscriptionUrl() {
        return subscriptionUrl;
    }

    public void setSubscriptionUrl(String subscriptionUrl) {
        this.subscriptionUrl = subscriptionUrl;
    }

    public List<Long> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<Long> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }
}
