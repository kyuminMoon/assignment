package com.kmmoon.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    @Schema(description="게시글 ID")
    private long postId;

    @Schema(description="제목")
    private String title;

    @Schema(description="내용")
    private String content;

    @Schema(description="닉네임")
    private String nickname;

    @Schema(description="계정 타입")
    private String accountType;

    @Schema(description="좋아요 수")
    private int likeCount;

    @Schema(description="좋아요 여부")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLike;

    @Schema(description="생성 시간")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul", locale = "ko")
    private LocalDateTime createdAt;

    @Schema(description="수정 시간")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul", locale = "ko")
    private LocalDateTime lastModifiedAt;

}

