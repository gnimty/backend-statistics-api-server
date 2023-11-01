package onlysolorank.apiserver.domain;

import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : SummonerMatch
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Document(collection = "summoner_matches")
@Getter
public class SummonerMatch {

    @Id
    private String id;

    private String puuid;

    @Field("summoner_match_ids")
    private List<String> summonerMatchIds;

}
