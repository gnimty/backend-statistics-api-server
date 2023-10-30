package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import onlysolorank.apiserver.api.controller.dto.PositionFilter;
import onlysolorank.apiserver.domain.dto.ItemBundleDto;
import onlysolorank.apiserver.domain.dto.Perk;
import onlysolorank.apiserver.domain.dto.SoloTierDto;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : ParticipantBriefDto
 * author         : solmin
 * date           : 10/11/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/11/23        solmin       최초 생성
 */

@Data
@Builder
@ToString
@AllArgsConstructor
public class ParticipantBriefDto {
    private String matchId;
    private String puuid;
    private Integer participantId;
    private Integer championId;
    private String championName;
    private Integer teamId;
    private String summonerName;
}
