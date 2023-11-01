package onlysolorank.apiserver.domain;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.History;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : SummonerHistory
 * author         : solmin
 * date           : 2023/09/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/05        solmin       최초 생성
 */

@Document(collection = "summoner_history")
@Getter
@ToString
public class SummonerHistory {

    @Id
    private String id;
    private String puuid;

    private List<History> history;
}
