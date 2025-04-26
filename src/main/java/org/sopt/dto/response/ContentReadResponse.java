package org.sopt.dto.response;

import org.sopt.dto.ContentDto;
import java.util.List;

public record ContentReadResponse (List<ContentDto> contentList) {}
