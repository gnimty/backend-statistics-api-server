package onlysolorank.apiserver.domain.summoner_play;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : SummonerPlay
 * author         : solmin
 * date           : 2023/09/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/04        solmin       최초 생성
 */

@Document(collection = "summoner_plays_total")
@Getter
@ToString
public class SummonerPlayTotal extends BaseSummonerPlay{

}
