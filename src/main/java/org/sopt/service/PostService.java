package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.IdGenerator;
import org.sopt.validation.PostValidator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class PostService {
    private PostRepository postRepository = new PostRepository();
    //작성 시간을 받아놓고 있다가, 다음 게시물이 작성되면 시간 비교해서 컷해줌.
    private LocalDateTime latestPostTime = null;

    public void createPost(String title) {
        try{
            //도배 방지기능
            PostValidator.postTime(latestPostTime);
            //중복제목 방지
            this.checkSameTitle(title);
            //선택과제 5의 연장선..
            // 프로그램 실행 후 처음에 idgenerator.id가 null값이니까 그럴 때 txt파일에서 가장 큰 id 찾아서 그거 +1해서 신규 게시물 id설정해줌
            if(IdGenerator.getId()==null){
                IdGenerator.setId(postRepository.findLastId());
            }
            //프로그램 실행 후 2번째부터는 id가 차있으니까, 그냥 전에꺼 +1한 값으로 id 정해줌
            Post post = new Post(IdGenerator.newId(), title);
            postRepository.save(post);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
