package org.sopt.repository;

import org.sopt.domain.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    private final String fileName = "postlist.txt";

    public void save(Post reqPost) {
        List<Post> postList = this.bringFile(fileName);
        postList.add(reqPost);
        saveToFile(postList, fileName);
    }

    public List<Post> findAll() {
        List<Post> postList = this.bringFile(fileName);
        return postList;
    }

    public Post findById(int id){
        List<Post> postList = this.bringFile(fileName);
        for(Post post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    public boolean existsByTitle(String title){
        List<Post> postList = this.bringFile(fileName);
        for(Post post : postList) {
            if (post.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public Boolean deleteById(int id){
        List<Post> postList = this.bringFile(fileName);
        for(Post post : postList){
            if (post.getId() == id){
                postList.remove(post);
                saveToFile(postList, fileName);
                return true;
            }
        }
        return false;
    }

    public boolean updateById(int id, String newTitle){
        List<Post> postList = this.bringFile(fileName);
        for(Post post : postList){
            if(post.getId() == id){
                post.setTitle(newTitle);
                saveToFile(postList, fileName);
                return true;
            }
        }
        return false;
    }

    //선택과제 4: 검색기능 추가
    public List<Post> searchByKeyword(String keyword){
        List<Post> postList = this.bringFile(fileName);
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
        List<Post> posts = this.bringFile(fileName);
        int maxId = -1;
        for (Post post : posts){
            if(post.getId()>maxId){
                maxId = post.getId();
            }
        }
        return maxId;
    }
}
