package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.ContentDto;
import org.sopt.repository.PostRepository;
import org.sopt.validation.PostValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;
    //작성 시간을 받아놓고 있다가, 다음 게시물이 작성되면 시간 비교해서 컷해줌.
    private LocalDateTime latestPostTime = null;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long createPost(String title) {
        //도배 방지기능
        PostValidator.postTime(latestPostTime);
        latestPostTime = LocalDateTime.now();
        //중복제목 방지
        this.checkSameTitle(title);

        try{
            Post post = new Post(title);
            postRepository.save(post);
            return post.getId();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("사용자 정보를 읽어올 수 없습니다.");
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getIdPost(int id){
        return postRepository.findById(id);
    }

    public Boolean deleteIdPost(int id){
        return postRepository.deleteById(id);
    }

    public boolean updateTitleById(int id, String newTitle){
        try{
            this.checkSameTitle(newTitle);
            return postRepository.updateById(id, newTitle);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Post> searchByKeyword(String keyword){
        return postRepository.searchByKeyword(keyword);
    }

    public void checkSameTitle(String title){
        if(postRepository.existsByTitle(title)){
            throw new IllegalArgumentException("같은 제목의 게시물이 이미 존재합니다.");
        }
    }

}
