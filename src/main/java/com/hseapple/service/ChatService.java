package com.hseapple.service;

import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.ChatEntity;
import com.hseapple.dao.MessageEntity;
import com.hseapple.dao.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static com.hseapple.util.StringUtils.formatRequest;

@Service
public class ChatService {

    private final RestTemplate restTemplate;
    private final String url;

    public ChatService(RestTemplate restTemplate, @Value("${hseApple.storage.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public ChatEntity getGroupForCourse(Long groupID) {
        ResponseEntity<ChatEntity> forEntity = restTemplate.getForEntity(formatRequest(url, "/group/%s", groupID), ChatEntity.class);
        return forEntity.getBody();
    }

    public void deleteGroup(Long groupID) {
        restTemplate.delete(formatRequest(url, "/group/%s", groupID));
    }

    public List<ChatEntity> findAllGroups(Integer courseID) {
        ResponseEntity<ChatEntity[]> forEntity = restTemplate.getForEntity(formatRequest(url, "/course/%s/group", courseID), ChatEntity[].class);
        return List.of(forEntity.getBody());
    }

    public MessageEntity getMessageForChat(Long groupID, Long messageID) {
        ResponseEntity<MessageEntity> forEntity = restTemplate.getForEntity(formatRequest(url, "/group/%s/message/%s", groupID, messageID), MessageEntity.class);
        return forEntity.getBody();
    }

    public void deleteMessage(Long groupID, Long messageID) {
        restTemplate.delete(formatRequest(url, "/group/%s/message/%s", groupID, messageID));
    }

    public Iterable<MessageEntity> findMessages(Long groupID, Long start) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<MessageEntity[]> exchange = restTemplate.exchange(url + "/group/{groupID}/message?start={start}", HttpMethod.GET, entity, MessageEntity[].class, groupID, start);
        return List.of(exchange.getBody());
    }

    public MessageEntity createMessage(MessageEntity messageEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messageEntity.setCreatedAt(LocalDateTime.now());
        messageEntity.setUserID(user.getId());
        ResponseEntity<MessageEntity> messageEntityResponseEntity = restTemplate.postForEntity(formatRequest(url, "/group/message"), messageEntity, MessageEntity.class);
        return messageEntityResponseEntity.getBody();
    }

    public void deleteMember(Long groupID, Long userID) {
        restTemplate.delete(formatRequest(url, "/group/%s/member/%s", groupID, userID));
    }

    public Iterable<UserEntity> getMembers(Long groupID) {
        ResponseEntity<UserEntity[]> forEntity = restTemplate.getForEntity(formatRequest(url, "/group/%s/list", groupID), UserEntity[].class);
        return List.of(forEntity.getBody());
    }

    public UserEntity getMember(Long groupID, Long userID) {
        ResponseEntity<UserEntity> forEntity = restTemplate.getForEntity(formatRequest(url, "/group/%s/member/%s", groupID, userID), UserEntity.class);
        return forEntity.getBody();
    }
}