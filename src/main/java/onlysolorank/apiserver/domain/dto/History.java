package onlysolorank.apiserver.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : History
 * author         : solmin
 * date           : 2023/08/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/15        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class History {

    private String queue;
    private Integer tier;
    private Integer leaguePoints;
    private ZonedDateTime updatedAt;

}
