package onlysolorank.apiserver.api.controller.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.CurrentGameParticipantDto;
import onlysolorank.apiserver.api.service.dto.QueueDto;

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
public class CurrentGameRes {

    private List<CurrentGameParticipantDto> participants;
    private QueueDto queueInfo;
    private LocalDateTime gameStartTime;
    private Long gameLength;


    @Builder
    public CurrentGameRes(Long startTime, Integer queueId,
        List<CurrentGameParticipantDto> participants) {
        // 현재시간 - startTime

        Long now = Instant.now().toEpochMilli();


        this.gameLength = (now - startTime)/1000;

        // epochMilliseconds to LocalDatetime
        Instant instant = Instant.ofEpochMilli(startTime);

        this.gameStartTime = instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        this.queueInfo = QueueDto.builder().queueId(queueId).build();
        this.participants = participants;
    }

}
