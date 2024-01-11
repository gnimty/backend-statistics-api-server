package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "420", description = "큐 ID")
    private Integer queueId;
    private String name;
    private String map;

    @JsonIgnore
    private QueueType queueType;

    @Builder
    public QueueDto(Integer queueId) {
        this.queueId = queueId;
        this.queueType = QueueType.getByQueueId(queueId);
        this.map = queueType.getMap();
        this.name = queueType.getValue();
    }
}
