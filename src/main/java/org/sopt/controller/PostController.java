package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    public void createPost(String title){
        postService.createPost(title);
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
        return postService.updateTitleById(id, newTitle);
    }

    public List<Post> searchPostsByKeyword(String keyword){
        return postService.searchByKeyword(keyword);
    }
}
