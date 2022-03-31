package hello.jejulu.service.post;

import hello.jejulu.domain.util.Category;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.IOException;
import java.util.List;

public interface PostService {
    /**
     * 게시물 저장
     * @param postSaveDto Controller계층에서 넘어온 게시물 저장 DTO객체
     * @param loginHost Session 조회한 로그인 호스트 정보
     * @return 게시물 저장 후 게시물 정보 담은 DTO return
     * @throws IOException Firebase 연동 입출력 예외
     */
    PostDto.Info add(PostDto.Save postSaveDto, HostDto.Info loginHost) throws IOException;

    /**
     * 게시물 수정
     * @param postId Controller계층에서 넘어온 수정할 게시물 id
     * @param postUpdateDto Controller계층에서 넘어온 게시물 수정 내용 DTO객체
     * @throws IOException Firebase 연동 입출력 예외
     */
    void edit(Long postId, PostDto.Update postUpdateDto) throws IOException;

    /**
     * 상세 게시물 조회
     * @param postId 조회할 게시물 id
     * @return 게시물 상세 정보를 가지고있는 DetailDto 객체
     */
    PostDto.Detail getPostById(Long postId);

    /**
     * 게시물 수정 시 상세 게시물 정보 조회 (조회수 이슈 때문에 분리)
     * @param postId 조회할 게시물 id
     * @return 게시물 상세 정보를 가지고있는 DetailDto 객체
     */
    PostDto.Detail getUpdatePostById(Long postId);

    /**
     * 메인화면 게시물 목록 조회
     * @param category 카테고리별 게시물 목록 조회 인자
     * @return 조회된 결과 List로 반환
     */
    List<PostDto.Info> getHomePostsByCategory(Category category);

    /**
     * 카테고리별 게시물 목록 조회
     * @param category 조회할 카테고리
     * @param pageable Controller계층에서 넘어온 페이징 처리를 위한 PageRequest 객체
     * @return 페이징 처리된 Slice객체 조회 값
     */
    Slice<PostDto.Info> getPostsByCategory(Category category, Pageable pageable);

    /**
     * 호스트가 작성한 게시물 목록 조회
     * @param hostId 조회할 호스트 id
     * @param pageable Controller계층에서 넘어온 페이징 처리를 위한 PageRequest 객체
     * @return 페이징 처리된 Page객체 조회 값
     */
    Page<PostDto.Info> getPostsByHost(Long hostId, Pageable pageable);

    /**
     * 게시물 삭제
     * @param postId 삭제할 게시물 id
     */
    void delete(Long postId);

    /**
     * 게시물이 현제 로그인한 호스트가 작성한 게시물인지 판별
     * @param postId 조회할 게시물 id
     * @param loginHost 로그인한 호스트
     */
    boolean isPostByHost(Long postId, HostDto.Info loginHost);

    /**
     * 검색 결과 조회
     * @param keyword 게시물 검색할 키워드
     * @param type 게시물 검색 타입 : ex) 제목 or 작성자
     * @param pageable 페이징 처리를 위한 PageRequest 객체
     * @return 검색 결과를 페이징 처리 후 반환
     */
    Page<PostDto.Info> getSearchResult(String keyword,String type, Pageable pageable);

    /**
     * 카테고리별 검색 결과 조회
     * @param category 검색할 카테고리
     * @param keyword 게시물 검색할 키워드
     * @param type 게시물 검색 타입 : ex) 제목 or 작성자
     * @param pageable 페이징 처리를 위한 PageRequest객체
     * @return 검색 결과를 페이징 처리 후 반환
     */
    Page<PostDto.Info> getSeadrchResultByCategory(Category category, String keyword, String type, Pageable pageable);
}
