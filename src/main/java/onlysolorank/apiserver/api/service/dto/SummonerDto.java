package onlysolorank.apiserver.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Summoner;
import onlysolorank.apiserver.domain.dto.QueueType;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : SummonerDto
 * author         : solmin
 * date           : 2023/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/10        solmin       최초 생성
 * 2023/07/24        solmin       필드 이름 수정 및 @Builder 추가
 * 2023/08/30        solmin       총 승리/패배 및 플레이수 필드 추가
 */
@Data
@Builder
public class SummonerDto {

    @Schema(example = "Hide on bush", description = "소환사명")
    private String summonerName;

    @Schema(example = "hideonbush", description = "소환사명의 모든 공백 제거 및 lowerCase로 적용한 문자열")
    private String internalName;

    @Schema(example = "KR1", description = "소환사 태그라인")
    private String tagLine;

    @Schema(example = "hideonbush#kr1", description = "internalName + '#' + 태그라인 lowerCase로 적용한 문자열")
    private String internalTagName;

    @Schema(
        example = "8jSvjaQOO931Iap8kPjFciD5ep55NuFX7eh1KHpe0rCPQyQmkHwN7T3jOVgtPLS73gmDpnuy8PwHRw",
        description = "소환사 puuid")
    private String puuid;

    @Schema(example = "2E9PHLIRTUzNE8JIotKPuxWcjCS8TwexrazzQ6w7vghMXA", description = "소환사 id")
    private String summonerId;

    @Schema(example = "6", description = "소환사 프로필 아이콘 id")
    private Integer profileIconId;

    @Schema(example = "260", description = "소환사 레벨")
    private Integer summonerLevel;

    @Schema(description = "솔로랭크 티어 정보")
    private SummonerTierDto soloTierInfo;

    @Schema(description = "자유랭크 티어 정보")
    private SummonerTierDto flexTierInfo;

    @Schema(description = "솔로랭크 순위정보로, 마스터 이상 유저에 한해서만 해당 필드가 노출됩니다.")
    @JsonInclude(Include.NON_NULL)
    private Integer rank;


    public static SummonerDto from(Summoner summoner) {

        SummonerTierDto soloTier = SummonerTierDto.from(summoner, QueueType.RANK_SOLO);
        SummonerTierDto flexTier = SummonerTierDto.from(summoner, QueueType.RANK_FLEX);

        return SummonerDto.builder()
            .summonerName(summoner.getName())
            .internalName(summoner.getInternalName())
            .internalTagName(summoner.getInternalTagName())
            .tagLine(summoner.getTagLine())
            .summonerId(summoner.getSummonerId())
            .puuid(summoner.getPuuid())
            .profileIconId(summoner.getProfileIconId())
            .summonerLevel(summoner.getSummonerLevel())
            .soloTierInfo(soloTier)
            .flexTierInfo(flexTier)
            .build();
    }

}
