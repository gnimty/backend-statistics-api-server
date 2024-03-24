package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.dto.Lane;
import onlysolorank.apiserver.domain.statistics.stat.ChampionStatsRank;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : LaneSelectDto
 * author         : solmin
 * date           : 3/22/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/22/24        solmin       최초 생성
 */

@AllArgsConstructor
@Data
@Builder
public class LaneSelectDto {
    private Lane lane;
    private Double pickRate;

    public static LaneSelectDto from(ChampionStatsRank statsRank, Integer totalCnt){

        Optional<Double> pickRate = divideAndReturnDouble(statsRank.getPlays().intValue(),totalCnt,2);
        if(!pickRate.isPresent()){
            return null;
        }

        return LaneSelectDto.builder()
            .pickRate(pickRate.get())
            .lane(statsRank.getPosition())
            .build();
    }

}
