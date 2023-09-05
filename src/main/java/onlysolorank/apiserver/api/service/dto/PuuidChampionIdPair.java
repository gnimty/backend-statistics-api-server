package onlysolorank.apiserver.api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : onlysolorank.apiserver.api.service.dto
 * fileName       : PuuidChampionIdPair
 * author         : solmin
 * date           : 2023/09/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/05        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class PuuidChampionIdPair {
    private String puuid;
    private Long championId;
}
