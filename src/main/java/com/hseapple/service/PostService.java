package com.hseapple.service;

import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.PostEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.hseapple.util.StringUtils.formatRequest;

@Service
public class PostService {

    private final RestTemplate restTemplate;
    private final String url;

    public PostService(RestTemplate restTemplate, @Value("${hseApple.storage.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<PostEntity> findAllPosts(Integer courseID, Long start) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<PostEntity[]> exchange = restTemplate.exchange(url + "/course/{courseID}/post?start={start}", HttpMethod.GET, entity, PostEntity[].class, courseID, start);
        return List.of(exchange.getBody());
    }

    public PostEntity createPost(PostEntity postEntity) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postEntity.setCreatedBy(user.getId());
        ResponseEntity<PostEntity> messageEntityResponseEntity = restTemplate.postForEntity(formatRequest(url, "/course/post"), postEntity, PostEntity.class);
        return messageEntityResponseEntity.getBody();
    }

    public void updatePost(PostEntity newPost, Long postID) {
        UserAndRole user = (UserAndRole) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUpdatedBy(user.getId());
        restTemplate.put(formatRequest(url, "/course/post/%s", postID), newPost);
    }

    public void deletePost(Long postID) {
        restTemplate.delete(formatRequest(url, "course/post/%s", postID));
    }

}