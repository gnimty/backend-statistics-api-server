package onlysolorank.apiserver.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.MatchBriefDto;
import onlysolorank.apiserver.api.service.dto.SummonerDto;
import onlysolorank.apiserver.api.service.dto.SummonerPlayDto;

import java.time.ZonedDateTime;
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
public class SummonerMatchRes {
    private SummonerDto summoner;
    private ZonedDateTime renewableAfter;
    private List<MatchBriefDto> matches;
    private List<SummonerPlayDto> mostPlayed;
}
