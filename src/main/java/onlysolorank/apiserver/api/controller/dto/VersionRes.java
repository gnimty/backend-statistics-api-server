package onlysolorank.apiserver.api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Version;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : VersionRes
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */
@Data
@Builder
public class VersionRes {

    @Schema(example = "13.12.1", description = "클라언트 버전 정보, XX.XX.XX 형식")
    private String version;
    @Schema(example = "https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-13-23-notes/", description = "클라언트 패치노트 링크")
    private String releaseNoteUrl;
    @Schema(example = "https://images.contentstack.io/v3/assets/blt731acb42bb3d1659/blt366e461a3acc1e4a/65544864cf92374c841b9e2d/112023_Patch_13_23_Notes_Banner.jpg", description = "패치노트 커버 이미지 링크")
    private String releaseNoteImgUrl;

    public static VersionRes from(Version version) {
        return VersionRes.builder().version(version.getVersion()).releaseNoteImgUrl(version.getReleaseNoteImgUrl())
            .releaseNoteUrl(version.getReleaseNoteUrl()).build();
    }
}
