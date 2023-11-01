package onlysolorank.apiserver.api.controller.dto;

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
public class VersionRes {

    private String version;
    private String releaseNoteUrl;
    private String releaseNoteImgUrl;

    public VersionRes(Version version) {
        this.version = version.getVersion();
        this.releaseNoteUrl = version.getReleaseNoteUrl();
        this.releaseNoteImgUrl = version.getReleaseNoteImgUrl();
    }
}
