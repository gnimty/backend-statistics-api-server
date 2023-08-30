package onlysolorank.apiserver.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.IngameParticipantDto;
import onlysolorank.apiserver.api.service.dto.QueueDto;
import onlysolorank.apiserver.api.service.dto.SpectatorV4GetCurrentGameInfo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : IngameInfoRes
 * author         : solmin
 * date           : 2023/08/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/30        solmin       최초 생성
 */

@Data
public class IngameInfoRes {
    private List<IngameParticipantDto> participants;
    private QueueDto queueInfo;
    private LocalDateTime gameStartTime;
    private Long gameLength;


    @Builder
    public IngameInfoRes(Long gameLength, Long startTime, Integer queueId, List<IngameParticipantDto> participants){
        this.gameLength = gameLength;

        // epochMilliseconds to LocalDatetime
        Instant instant = Instant.ofEpochMilli(startTime);
        this.gameStartTime = instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        this.queueInfo = QueueDto.builder().queueId(queueId).build();
        this.participants = participants;
    }

}
