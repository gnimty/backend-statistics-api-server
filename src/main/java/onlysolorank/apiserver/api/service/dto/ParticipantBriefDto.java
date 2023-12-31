package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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

    @JsonIgnore
    private String matchId;
    private String puuid;
    private Integer participantId;
    private Long championId;
    private String championName;
    private Integer teamId;
    private String summonerName;
    private String tagLine;
    private String internalTagName;
}
