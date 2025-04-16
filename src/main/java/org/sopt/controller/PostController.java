package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.service.PostService;
import org.sopt.validation.PostValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    @PostMapping("/post")
    public void createPost(@RequestBody final PostRequest postRequest) {
        postService.createPost(postRequest.getTitle());
    }

    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    public Post getPostById(int postId){
        return postService.getIdPost(postId);
    }

    public Boolean deletePostById(int postId){
        return postService.deleteIdPost(postId);
    }

    public boolean updatePostTitle(int id, String newTitle){
        //제목 글자수 검사
        try {
            PostValidator.titleLength(newTitle);
            return postService.updateTitleById(id, newTitle);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Post> searchPostsByKeyword(String keyword){
        return postService.searchByKeyword(keyword);
    }
}
