package onlysolorank.apiserver.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.Tier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Match
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Document(collection = "matches")
@Getter
@ToString
public class Match {

    @Id
    private String id;

    @Field("avg_tier")
    private Tier averageTier;
    private String matchId;
    private String version;
    private LocalDateTime gameStartAt;
    private LocalDateTime gameEndAt;
    private Long gameDuration;
    private Integer queueId;
    private Boolean earlyEnded;

}
