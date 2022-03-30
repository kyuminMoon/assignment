package com.kmmoon.assignment.controller;import com.kmmoon.assignment.dto.CursorResultDTO;import com.kmmoon.assignment.dto.PostDTO;import com.kmmoon.assignment.dto.PostResponseDTO;import com.kmmoon.assignment.entity.User;import com.kmmoon.assignment.service.PostService;import io.swagger.v3.oas.annotations.Operation;import io.swagger.v3.oas.annotations.tags.Tag;import lombok.RequiredArgsConstructor;import lombok.extern.slf4j.Slf4j;import org.springframework.beans.factory.annotation.Value;import org.springframework.data.domain.PageRequest;import org.springframework.security.core.annotation.AuthenticationPrincipal;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;@Slf4j@Tag(name = "게시글 관련 API")@RequiredArgsConstructor@RestController@RequestMapping("/posts")public class PostController {    @Value("${spring.data.web.pageable.default-page-size}")    private int defaultSize;    private final PostService postService;    /**     * path parameters postId 게시글 조회     */    @GetMapping("/{postId}")    @Operation(summary = "게시글 조회")    public PostResponseDTO findPostById(            @AuthenticationPrincipal User user,            @PathVariable Long postId    ) {        return postService.findPostById(user, postId);    }    /**     * query string으로 커서 기반 페이징, 게시글 리스트 조회     */    @GetMapping("/list")    @Operation(summary = "게시글 리스트 조회")    public CursorResultDTO<PostResponseDTO> findPostByPage(            @AuthenticationPrincipal User user,            Long cursorId,            Integer size    ) {        size = (size == null) ? defaultSize : size;        return postService.findPostByPage(user, cursorId, PageRequest.of(0, size));    }    /**     * 게시글 생성     */    @PostMapping    @Operation(summary = "게시글 생성")    public PostResponseDTO createPost(            @AuthenticationPrincipal User user,            @RequestBody @Valid PostDTO postDTO    ) {        return postService.createPost(user, postDTO);    }    /**     * path parameters postId 게시글의 좋아요     */    @Operation(summary = "게시글의 좋아요")    @PostMapping("/{postId}/like")    public void likePost(            @AuthenticationPrincipal User user,            @PathVariable Long postId    ) {        postService.likePost(user, postId);    }    /**     * path parameters postId 게시글 수정     */    @Operation(summary = "게시글 수정")    @PutMapping("/{postId}")    public PostResponseDTO modifyPost(            @AuthenticationPrincipal User user,            @PathVariable Long postId,            @RequestBody @Valid PostDTO postDTO    ) {        return postService.modifyPost(user, postId, postDTO);    }    /**     * path parameters postId 게시글 삭제 (Table is_active flag 값으로 삭제 처리)     */    @Operation(summary = "게시글 삭제")    @DeleteMapping("/{postId}")    public void deletePost(            @AuthenticationPrincipal User user,            @PathVariable Long postId    ) {        postService.deletePost(user, postId);    }}