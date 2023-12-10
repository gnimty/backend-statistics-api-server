package onlysolorank.apiserver.domain.summoner_play;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
public class BaseSummonerPlay {

    @Id
    private String id;
    private String season;
    private Long championId;
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

    private Double avgGold;
    private Double avgDamage;
    private Integer maxKill;
    private Integer maxDeath;
}
