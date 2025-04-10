package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.IdGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class PostService {
    private PostRepository postRepository = new PostRepository();
    //작성 시간을 받아놓고 있다가, 다음 게시물이 작성되면 시간 비교해서 컷해줌.
    private LocalDateTime latestPostTime = null;

    public void createPost(String title) {
        //필수과제 2,3: 게시물 생성시
        try {
            if (title.isEmpty()) {
                throw new IllegalArgumentException("제목을 입력해주세요");
            }
            if (title.length()>30){
                throw new IllegalArgumentException("제목은 최대 30자까지 작성할 수 있어요");
            }
            //선택과제 3 - 도배 방지
            if(latestPostTime==null || Duration.between(latestPostTime , LocalDateTime.now()).toMinutes()>=3){
                latestPostTime = LocalDateTime.now();
                //선택과제 5의 연장선..
                // 프로그램 실행 후 처음에 idgenerator.id가 null값이니까 그럴 때 txt파일에서 가장 큰 id 찾아서 그거 +1해서 신규 게시물 id설정해줌
                if(IdGenerator.getId()==null){
                    IdGenerator.setId(postRepository.findLastId());
                }
                //프로그램 실행 후 2번째부터는 id가 차있으니까, 그냥 전에꺼 +1한 값으로 id 정해줌
                Post post = new Post(IdGenerator.newId(), title);
                postRepository.save(post);
            } else {
                System.out.println((180-(int)Duration.between(latestPostTime , LocalDateTime.now()).getSeconds()) + "초 뒤에 게시물을 다시 작성할 수 있어요.");
            }
        } catch (IllegalArgumentException e){
            System.out.println("에러 발생: "+e.getMessage());
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
        //필수과제 2,3 : 게시글 수정 시
        // 에러문 반환하더라도, catch에서 false를 반환하기 때문에 해당ID의 게시글이 존재하지 않습니다가 뜨는 문제 발생.
        try {
            if (newTitle.isEmpty()) {
                throw new IllegalArgumentException("제목을 입력해주세요");
            }
            if (newTitle.length()>30){
                throw new IllegalArgumentException("제목은 최대 30자까지 작성할 수 있어요");
            }
            return postRepository.updateById(id, newTitle);
        } catch (IllegalArgumentException e){
            System.out.println("에러 발생: "+e.getMessage());
            return false;
        }
    }

    public List<Post> searchByKeyword(String keyword){
        return postRepository.searchByKeyword(keyword);
    }


}
