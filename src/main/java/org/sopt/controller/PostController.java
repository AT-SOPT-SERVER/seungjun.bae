package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;
import org.sopt.validation.PostValidator;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    public void createPost(String title){
        //제목 글자수 검사
        try {
            PostValidator.titleLength(title);
            postService.createPost(title);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
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
