package org.sopt.dto.response;

import java.util.List;

public record CommentReadResponse(List<CommentInfo> comments) {
}
