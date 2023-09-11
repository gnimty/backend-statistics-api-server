package onlysolorank.apiserver.api.service.dto;

import lombok.Builder;
import lombok.Data;

import static onlysolorank.apiserver.utils.CustomFunctions.divideAndReturnDouble;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : RecentMemberDto
 * author         : solmin
 * date           : 2023/09/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/11        solmin       최초 생성
 */

@Data
public class RecentMemberDto {
    private String puuid;
    private Integer totalPlay;
    private Integer totalWin;
    private Integer totalDefeat;
    private Double winRate;

    @Builder
    public RecentMemberDto(String puuid, Integer totalWin, Integer totalDefeat){
        this.puuid = puuid;
        this.totalWin = totalWin;
        this.totalDefeat = totalDefeat;
        this.totalPlay = totalWin + totalDefeat;
        this.winRate = divideAndReturnDouble( totalWin, totalPlay, 3)
            .orElseGet(null);
    }
}
