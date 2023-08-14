package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.dto.QueueType;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : QueueDto
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 */

@Data
public class QueueDto {
    private Integer queueId;
    private String queueInfo;
    private String map;

    @JsonIgnore
    private QueueType queueType;

    @Builder
    public QueueDto(Integer queueId) {
        this.queueId = queueId;

        switch (queueId){
            case 420:
                this.queueType = QueueType.RANK_SOLO;
                break;
            case 430:
                this.queueType = QueueType.BLIND;
                break;
            case 440:
                this.queueType = QueueType.RANK_FLEX;
                break;
            case 450:
                this.queueType = QueueType.ARAM;
                break;
        }
        this.map = queueType.getMap();
        this.queueInfo = queueType.getValue();
    }
}
