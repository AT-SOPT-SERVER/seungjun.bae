package org.sopt.repository;

import org.sopt.domain.Comment;
import org.sopt.dto.response.CommentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_Id(Long postId);
}
