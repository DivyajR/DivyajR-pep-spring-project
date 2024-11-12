package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;


import com.example.service.AccountService;
import com.example.entity.Account;

import com.example.service.MessageService;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;


    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Account>  registerAccount(@RequestBody Account account) {

        if (account.getUsername() == null || account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        Optional<Account> registeredAccount = accountService.registerAccount(account);

        if (registeredAccount.isEmpty()) {
            return ResponseEntity.status(409).build(); 
        }

        return ResponseEntity.status(200).body(registeredAccount.get());
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Account> login(@RequestBody Account account)
    {
        Optional<Account> returnAccount = accountService.login(account.getUsername(), account.getPassword());

        if (returnAccount.isEmpty()) {
            return ResponseEntity.status(401).build(); 
        }
        else{
            return ResponseEntity.status(200).body(returnAccount.get());

        }
    }

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Message> createMessage(@RequestBody Message message){

        if(message.getMessageText().length() < 255 && !message.getMessageText().isEmpty())
        {
            Optional<Message> returnMessage = messageService.createMessage(message);
            if(returnMessage.isPresent())
                return ResponseEntity.status(200).body(returnMessage.get());
            else
                return ResponseEntity.status(400).build();

        }
        else
            return ResponseEntity.status(400).build();



    }

    @GetMapping("/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> getAllMessages(){

        List<Message> messages = messageService.getAllMessages();

        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{message_id}")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer messageId){

        Optional<Message> message = messageService.getMessageById(messageId);
        if(message.isPresent())
            return ResponseEntity.status(200).body(message.get());
        else
            return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/messages/{message_id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteMessage(@PathVariable("message_id") Integer messageId)
    {
        int rows = messageService.deleteMessage(messageId);

        if(rows == 1)
            return ResponseEntity.status(200).body(1);
        else
            return ResponseEntity.status(200).build();
    }
    
    @PatchMapping("/messages/{message_id}")
    @ResponseBody
    public ResponseEntity<Integer> updateMessage(@PathVariable("message_id") Integer messageId, 
        @RequestBody Map<String, String> message)
    {
        String messageText = message.get("messageText");

    if (messageText == null || messageText.isEmpty() || messageText.length() > 255) {
        return ResponseEntity.status(400).build(); 
    }

    int rows = messageService.updateMessage(messageId, messageText);

    if (rows == 1)
        return ResponseEntity.status(200).body(rows); // Successful update
    else
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> getAllMessageById(@PathVariable("account_id") Integer accountId)
    {
        List<Message> messages = messageService.getAllMessagesById(accountId);
    
        return ResponseEntity.status(200).body(messages);
    }
}
