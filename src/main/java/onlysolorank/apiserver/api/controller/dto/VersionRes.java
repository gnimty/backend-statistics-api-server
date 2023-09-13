package onlysolorank.apiserver.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    private String patchUrl;

    @JsonIgnore
    private static final String baseUrl = "https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-%s-%s-notes/";

    public VersionRes(String version){
        this.version = version;

        String[] split = version.split("\\.");

        this.patchUrl = String.format(baseUrl, split[0], split[1]);
    }
}
