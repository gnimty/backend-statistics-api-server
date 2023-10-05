package onlysolorank.apiserver.domain.statistics.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics
 * fileName       : WeeklyStatisticsEntity
 * author         : solmin
 * date           : 2023/09/27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/27        solmin       최초 생성
 */

@Document("week")
@AllArgsConstructor
@Getter
public class WeeklyChampionStat extends BaseChampionStat {
}
