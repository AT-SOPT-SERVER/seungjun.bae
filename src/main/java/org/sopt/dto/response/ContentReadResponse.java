package org.sopt.dto.response;

import org.sopt.dto.ContentListDto;

import java.util.List;

public record ContentReadResponse (List<ContentListDto> contentList) {}
