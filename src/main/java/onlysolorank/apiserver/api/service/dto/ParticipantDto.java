package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.doubleValueToHalfUp;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Participant;
import onlysolorank.apiserver.domain.dto.ItemBundle;
import onlysolorank.apiserver.domain.dto.Perk;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.dto.Tier;

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
    @Schema(description = "참여자 ID, 1~10")
    private Integer participantId;
    @Schema(description = "챔피언 ID")
    private Integer championId;
    @Schema(description = "챔피언 영문이름")
    private String championName;
    @Schema(example = "challenger", description = "소환사 티어 대분류 (플레이 시점)")
    private Tier tier;
    @Schema(example = "1", description = "소환사 티어 소분류, 1~4 (플레이 시점)")
    private Integer division;
    @Schema(example = "56", description = "랭크 포인트 (플레이 시점)")
    private Integer lp;
    private Boolean win;
    @Schema(example = "100", description = "팀 아이디 - 100:Blue, 200:200")
    private Integer teamId;
    @Schema(description = "플레이한 라인 정보")
    private Lane lane;
    private Integer kill;
    private Integer death;
    private Integer assist;
    @Schema(example = "0.75", description = "KDA, 소수점 둘째 자리까지 표기")
    private Double kda;
    @Schema(example = "0.75", description = "킬 관여율, 소수점 둘째 자리까지 표기")
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
    @Schema(description = "장신구 아이템 ID")
    private Integer accessory;
    @Schema(description = "시간별로 구매한 아이템")
    private List<ItemBundle> itemBuilds;
    private List<Integer> skillBuilds;
    private String summonerName;
    private String tagLine;
    private String internalTagName;

    @Builder
    public ParticipantDto(Participant participant, String summonerName, String tagLine, String internalTagName) {
        this.participantId = participant.getParticipantId();

        if (participant.getQueue() != null && participant.getTier() != null && participant.getLeaguePoints() != null) {
            this.tier = Tier.valueOf(participant.getQueue());
            this.division = Integer.parseInt(participant.getTier());
            this.lp = participant.getLeaguePoints();
        }

        this.championId = participant.getChampionId();
        this.championName = participant.getChampionName();
        this.win = participant.getWin();
        this.teamId = participant.getTeamId();
        this.kill = participant.getKills();
        this.death = participant.getDeaths();
        this.assist = participant.getAssists();
        this.kda = participant.getKda();
        this.killParticipation = doubleValueToHalfUp(participant.getKillParticipation(), 2);
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
            this.lane = Lane.valueOf(participant.getLane());
        } catch (IllegalArgumentException e) {
            this.lane = Lane.UNKNOWN;
        }

        this.summonerName = summonerName;
        this.internalTagName = internalTagName;
        this.tagLine = tagLine;
    }
}
