package onlysolorank.apiserver.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.MatchDto;
import onlysolorank.apiserver.api.service.dto.ParticipantBriefDto;
import onlysolorank.apiserver.api.service.dto.ParticipantDto;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : MatchBriefDto
 * author         : solmin
 * date           : 10/11/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/11/23        solmin       최초 생성
 */

@Data
@AllArgsConstructor
@Builder
public class MatchBriefRes {

    private MatchDto matchInfo;
    private ParticipantDto participant;
    private List<ParticipantBriefDto> allParticipants;
}
