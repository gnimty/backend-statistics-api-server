package onlysolorank.apiserver.api.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.dto.MatchDto;
import onlysolorank.apiserver.api.dto.SummonerDto;
import onlysolorank.apiserver.api.dto.SummonerMatchDTO;
import onlysolorank.apiserver.domain.Summoner;

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
public class SummonerMatchResponseDto {
    private SummonerDto summoner;
    private LocalDateTime renewableAt;
    private List<MatchDto> matches;

    // private ChampionPlaysBriefDto mostPlayed;
}
