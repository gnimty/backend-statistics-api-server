package onlysolorank.apiserver.api.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.dto.ChampionPlaysBriefDto;
import onlysolorank.apiserver.api.dto.MatchDto;
import onlysolorank.apiserver.api.dto.SummonerDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.controller
 * fileName       : SummonerMatchResponseDto
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Data
@AllArgsConstructor
@Builder
public class SummonerMatchResponseDto {
    private SummonerDto summoner;
    private LocalDateTime renewableAt;
    private List<MatchDto> matches;
    private List<ChampionPlaysBriefDto> mostPlayed;
}
