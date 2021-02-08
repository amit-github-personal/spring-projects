package com.oodles.messaging.messagehandlers;

import com.oodles.messaging.QueueMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;

@RestController
public class MessageController {
    @Autowired
    QueueMatcher queueMatcher;

    /*
    * Whenever the messages are getting sent to specific application
    * destination. Those messages can be accessed and processed within
    * the controller. We just need to define the message mapping.
    *
    * When we've multiple broker destination. We will need to specify
    * the send to destination to broadcast the messages to specific
    * broker prefix endpoint. We can put any random destination we
    * want the messages to be sent.
    * */
    @MessageMapping("/application-destination")
    @SendTo("/queue/ticketAdmin")
    public String sendToUser(String message) {
        return "hello, " + message;
    }

    @MessageMapping("/user-messages")
    @SendTo("/queue/user-messages")
    public String getMessage(String message, Principal principal) {
        return "By : " + principal.getName();
    }

    @PostMapping
    public Object generateQueue(@RequestParam String queueName, @RequestParam Long toUser, @RequestParam Long fromUser) {
        return queueMatcher.generateRandomQueue(queueName, fromUser, toUser);
    }
}
