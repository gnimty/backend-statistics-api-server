package onlysolorank.apiserver.domain.statistics.analysis.component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * packageName    : onlysolorank.apiserver.domain.dto.component
 * fileName       : StatPerkComponentStat
 * author         : solmin
 * date           : 2023/10/02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/10/02        solmin       최초 생성
 */

@NoArgsConstructor
@Getter
public class StatPerkComponentStat extends BaseComponentStat {
    private List<List<Long>> statPerks;
}
