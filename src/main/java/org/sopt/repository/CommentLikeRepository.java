package org.sopt.repository;

import org.sopt.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUser_IdAndComment_Id(Long userId, Long commentId);
}
