package org.sopt.repository;

import org.sopt.domain.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    public void save(Post reqPost) {
        //선택과제 1 - 중복 게시물 불가
        // 로직이기는 하지만, db에 있는 데이터의 유효성을 검증한 후 db에 추가하니까 repository에 있는게 낫지 않을까..?
        //에러 반환하더라도 main을 건들지 않는 이상 게시글이 성공적으로 저장됐다고 뜨긴함... main을 건드려야되나?
        List<Post> postList = this.bringFile("postlist.txt");
        try {
            for(Post post : postList){
                if(reqPost.getTitle().equals(post.getTitle())){
                    throw new IllegalArgumentException("같은 제목의 게시물이 이미 존재합니다.");
                }
            }
            postList.add(reqPost);
            saveToFile(postList, "postlist.txt");
        } catch (IllegalArgumentException e){
            System.out.println("예외 발생: "+e.getMessage());
        }

    }

    public List<Post> findAll() {
        List<Post> postList = this.bringFile("postlist.txt");
        return postList;
    }

    public Post findById(int id){
        List<Post> postList = this.bringFile("postlist.txt");
        for(Post post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    public Boolean deleteById(int id){
        List<Post> postList = this.bringFile("postlist.txt");
        for(Post post : postList){
            if (post.getId() == id){
                postList.remove(post);
                saveToFile(postList, "postlist.txt");
                return true;
            }
        }
        return false;
    }

    public boolean updateById(int id, String newTitle){
        List<Post> postList = this.bringFile("postlist.txt");
        for(Post post : postList){
            if(post.getId() == id){
                post.setTitle(newTitle);
                saveToFile(postList, "postlist.txt");
                return true;
            }
        }
        return false;
    }

    //선택과제 4: 검색기능 추가
    public List<Post> searchByKeyword(String keyword){
        List<Post> postList = this.bringFile("postlist.txt");
        List<Post> searchedPosts = new ArrayList<>();
        for (Post post : postList){
            if(post.getTitle().contains(keyword)){
                searchedPosts.add(post);
            }
        }
        return searchedPosts;
    }

    //선택과제 5: 파일로 저장,불러오기
    public void saveToFile(List<Post> posts, String filename){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(posts);
            System.out.println("저장 완료");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Post> bringFile(String filename){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            return (List<Post>) ois.readObject();
        } catch (FileNotFoundException e) {
            // 파일이 없으면 새로운 빈 리스트 반환
            return new ArrayList<>();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int findLastId(){
        List<Post> posts = this.bringFile("postlist.txt");
        int maxId = -1;
        for (Post post : posts){
            if(post.getId()>maxId){
                maxId = post.getId();
            }
        }
        return maxId;
    }
}
