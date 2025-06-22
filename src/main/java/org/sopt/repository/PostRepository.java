package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);
    boolean existsByTitleContaining(String keyword);
    Page<Post> findAllByOrderByPostTimeDesc(Pageable pageable);
    Page<Post> findAllByUser_IdOrderByPostTimeDesc(Long id, Pageable pageable);
    Page<Post> findAllByTitleContainingOrderByPostTimeDesc(String keyword, Pageable pageable);
    List<Post> id(Long id);
}