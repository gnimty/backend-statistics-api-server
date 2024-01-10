package onlysolorank.apiserver.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import onlysolorank.apiserver.domain.Champion;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : ChampionDto
 * author         : solmin
 * date           : 11/6/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/6/23        solmin       최초 생성
 */


@SuperBuilder
@Getter
public class ChampionDto {

    @Schema(example = "266", description = "챔피언 ID")
    private Long championId;
    @Schema(example = "아트록스", description = "챔피언 ID")
    private String krName;
    @Schema(example = "266", description = "챔피언 ID")
    private String enName;

    public static ChampionDto from(Champion champion) {
        return ChampionDto.builder()
            .championId(champion.getChampionId())
            .krName(champion.getKrName())
            .enName(champion.getEnName())
            .build();
    }
}
