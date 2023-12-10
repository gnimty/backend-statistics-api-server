package onlysolorank.apiserver.api.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.dto.ItemBundle;
import onlysolorank.apiserver.domain.dto.Perk;
import onlysolorank.apiserver.domain.dto.Position;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : ParticipantDto
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 * 2023/08/09        solmin       summoner entry 정보에 존재하지 않는 소환사 티어 정보 null 처리
 */

@Data
public class ParticipantDto {

    private Integer participantId;
    private SummonerTierDto soloTier;
    private Integer championId;
    private String championName;
    //    private Boolean isMe;
    private Boolean win;
    private Integer teamId;
    private Position position;
    private Integer kill;
    private Integer death;
    private Integer assist;
    private Double kda;
    private Double killParticipation;
    private Integer goldEarned;
    private Long totalDamageTaken;
    private Long totalDamageDealtToChampions;
    private Integer wardPlaced;
    private Integer wardKilled;
    private Integer visionWardBoughtInGame;
    private Integer totalChampionLevel;
    private Integer cs;
    private Integer pentaKills;
    private Integer quadraKills;
    private Integer tripleKills;
    private Integer doubleKills;
    private Integer spellDId;
    private Integer spellFId;
    private Perk perks;
    private List<Integer> items;
    private Integer accessory;
    private List<ItemBundle> itemBuilds;
    private List<Integer> skillBuilds;
    private String summonerName;
    private String tagLine;
    private String internalTagName;

    @Builder
    public ParticipantDto(Participant participant, String summonerName, String tagLine, String internalTagName) {
        this.participantId = participant.getParticipantId();
        this.soloTier = Participant.toSoloTierDto(participant);
        this.championId = participant.getChampionId();
        this.championName = participant.getChampionName();
        this.win = participant.getWin();
        this.teamId = participant.getTeamId();
        this.kill = participant.getKills();
        this.death = participant.getDeaths();
        this.assist = participant.getAssists();
        this.kda = participant.getKda();
        this.killParticipation = participant.getKillParticipation();
        this.goldEarned = participant.getGoldEarned();
        this.totalDamageTaken = participant.getTotalDamageTaken();
        this.totalDamageDealtToChampions = participant.getTotalDamageDealtToChampions();
        this.wardPlaced = participant.getWardsPlaced();
        this.wardKilled = participant.getWardsKilled();
        this.visionWardBoughtInGame = participant.getVisionWardsBoughtInGame();
        this.totalChampionLevel = participant.getChampionLevel();
        this.cs = participant.getCs();
        this.pentaKills = participant.getPentaKills();
        this.quadraKills = participant.getQuadraKills();
        this.tripleKills = participant.getTripleKills();
        this.doubleKills = participant.getDoubleKills();
        this.spellDId = participant.getSpellDId();
        this.spellFId = participant.getSpellFId();
        this.perks = participant.getPerks();
        //TODO 장신구 아이템 확인 필요
        this.items = List.of(
            participant.getItem0(),
            participant.getItem1(),
            participant.getItem2(),
            participant.getItem3(),
            participant.getItem4(),
            participant.getItem5());
        this.accessory = participant.getItem6();
        this.itemBuilds = participant.getItemBuild().entrySet()
            .stream().map(build -> new ItemBundle(build.getKey(), build.getValue())).toList();
        this.skillBuilds = participant.getSkillBuild();

        try {
            this.position = Position.valueOf(participant.getLane());
        } catch (IllegalArgumentException e) {
            this.position = Position.UNKNOWN;
        }

        this.summonerName = summonerName;
        this.internalTagName = internalTagName;
        this.tagLine = tagLine;
    }
}
