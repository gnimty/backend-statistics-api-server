package onlysolorank.apiserver.api.service.dto;

import java.util.List;
import lombok.Data;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : mostChampionsBySummonerDto
 * author         : solmin
 * date           : 2023/08/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/16        solmin       최초 생성
 */
@Data
public class MostChampionsDto {

    private String puuid;
    private List<Integer> championIds;

    public MostChampionsDto(String puuid, List<Integer> championIds) {
        this.puuid = puuid;
        this.championIds = championIds;
    }
}
