package onlysolorank.apiserver.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : SummonerPlay
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 */

@Document(collection = "summoner_plays")
@Getter
@ToString
@EqualsAndHashCode //TODO 나중에 지울예정
public class SummonerPlay {
    @Id
    private String id;

    private Integer championId;
    private String championName;
    private String puuid;
    private Integer totalPlays;
    private Double avgCs;
    private Double avgCsPerMinute;
    private Double avgKda;
    private Double avgKill;
    private Double avgDeath;
    private Double avgAssist;
    private Double winRate;
    private Integer totalWin;
    private Integer totalDefeat;
    private Integer totalKill;
    private Integer totalDeath;
    private Integer totalAssist;

}
