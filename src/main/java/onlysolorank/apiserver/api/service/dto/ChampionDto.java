package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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


@Data
@Builder
@AllArgsConstructor
public class ChampionDto {
    private Long championId;
    private String krName;
    private String enName;

    public static ChampionDto from(Champion champion){
        return ChampionDto.builder()
                .championId(champion.getChampionId())
                .krName(champion.getKrName())
                .enName(champion.getEnName())
                .build();
    }
}
