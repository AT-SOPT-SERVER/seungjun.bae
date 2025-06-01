package org.sopt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(@NotBlank
                                   @Size(max = 300) String content) {
}
