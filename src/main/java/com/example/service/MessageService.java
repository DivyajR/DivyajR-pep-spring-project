package com.example.service;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<Message> createMessage(Message message)
    {
        
        if(accountRepository.findById(message.getPostedBy()).isPresent())
        {

            return Optional.of(messageRepository.save(message));

        }
        return Optional.empty();
        
    }

    public List<Message> getAllMessages()
    {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId)
    {
        if(messageRepository.findById(messageId).isPresent())
            return messageRepository.findById(messageId);
        
        return Optional.empty();
        
    }

    public int deleteMessage(Integer messageId)
    {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent() ) {
            messageRepository.delete(message.get());
            return 1;
        }
        return 0;
    }

    public int updateMessage(Integer messageId, String messageText)
    {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent() ) {
            
            message.get().setMessageText(messageText);
            messageRepository.save(message.get());
            return 1;
            
        }
        else
            return 0;
    
        
    }


    public List<Message> getAllMessagesById(Integer accountId)
    {
        List<Message> messages = messageRepository.findAllByPostedBy(accountId);

        return messages;


    }


}
