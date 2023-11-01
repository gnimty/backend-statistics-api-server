package onlysolorank.apiserver.domain.statistics.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : ChampionStatistics
 * author         : solmin
 * date           : 2023/09/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/06        solmin       최초 생성
 * 2023/09/06        solmin       TODO: SummonerPlay와 내용이 너무 동일해서 Boilerplate한 코드를 없애야 함
 * 2023/09/06        solmin       TODO: Dto 따로 만들 예정 (현재 임시)
 */

@Document(collection = "champion_statistics")
@Getter
@AllArgsConstructor
@ToString
public class ChampionStatistics {

    @Id
    private String id;
    private Long championId;
    private String championName;
    private Integer totalPlays;
    private Integer bans;
    private Double banRate;
    private Double avgCs;
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
