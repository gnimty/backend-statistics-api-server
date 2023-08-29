package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.Perk;
import onlysolorank.apiserver.domain.dto.Position;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Participant
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Document(collection = "participants")
@Getter
@ToString
@AllArgsConstructor
public class Participant {
    @Id
    private String id;
    private String matchId;
    private Integer teamId;
    private String puuid;
    private Integer participantId;
    private Long totalDamageTaken;
    private Long totalDamageDealtToChampions;
    private Integer wardsPlaced;
    private Integer wardsKilled;
    private Integer visionWardsBoughtInGame;

    /* null로 받아 왔을 때 예외 처리 후 enum 변환할 것 */
    private String queue;
    private String tier;
    private String leaguePoints;
    private String lane;
    /* -------------------------------------- */

    private Integer championLevel;
    private Integer championId;
    private String championName;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer cs;
    private Double killParticipation;
    private Integer goldEarned;
    private Double kda;
    private Integer pentaKills;
    private Integer quadraKills;
    private Integer tripleKills;
    private Integer doubleKills;
    private Perk perks;
    private Integer item0;
    private Integer item1;
    private Integer item2;
    private Integer item3;
    private Integer item4;
    private Integer item5;
    private Integer item6;
    private Integer spellDId;
    private Integer spellFId;
    private Boolean win;
    private Integer gameDuration;
    private Map<Integer, List<Integer>> itemBuild;
    private List<Integer> skillBuild;


}
