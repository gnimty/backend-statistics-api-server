package onlysolorank.apiserver.domain.statistics.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics
 * fileName       : DailyStatistics
 * author         : solmin
 * date           : 2023/09/30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/30        solmin       최초 생성
 */

@Document("day")
@AllArgsConstructor
@Getter
public class DailyChampionStat extends BaseChampionStat {
}
