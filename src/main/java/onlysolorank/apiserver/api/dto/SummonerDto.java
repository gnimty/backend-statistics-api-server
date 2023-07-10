package onlysolorank.apiserver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : SummonerDto
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 */
@Data
@AllArgsConstructor
@Builder
public class SummonerDto {
    private String naem;
    private String internalName;
    private String summonerId;
    private String puuid;
    private Integer profileIconId;
    private Integer summonerLevel;
}
