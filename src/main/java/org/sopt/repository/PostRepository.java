package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);

    List<Post> findByTitleContaining(String title);
}