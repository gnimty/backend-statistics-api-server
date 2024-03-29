package onlysolorank.apiserver.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.MatchSummaryDto;
import onlysolorank.apiserver.api.service.dto.SummonerDto;
import onlysolorank.apiserver.api.service.dto.SummonerPlayDto;

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

    @JsonInclude(Include.NON_NULL)
    private SummonerDto summoner;
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime renewableAfter;
    private List<MatchBriefRes> matches;
    private MatchSummaryDto matchSummary;

}
