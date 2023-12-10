package onlysolorank.apiserver.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import onlysolorank.apiserver.domain.dto.QueueType;
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
    private List<String> soloMatchIds;

    @Field("summoner_match_ids_blind")
    private List<String> blindMatchIds;

    @Field("summoner_match_ids_flex")
    private List<String> flexMatchIds;

    @Field("summoner_match_ids_aram")
    private List<String> aramMatchIds;

    public List<String> getAllMatchIds(){
        List<String> total = new ArrayList<>(soloMatchIds);
        total.addAll(blindMatchIds);
        total.addAll(flexMatchIds);
        total.addAll(aramMatchIds);

        total.sort(Comparator.reverseOrder());

        return total;
    }

    public List<String> getByQueueType(QueueType queueType){
        switch (queueType){
            case RANK_SOLO -> {
                return getSoloMatchIds();
            }
            case BLIND -> {
                return getBlindMatchIds();
            }
            case RANK_FLEX -> {
                return getFlexMatchIds();
            }
            case ARAM -> {
                return getAramMatchIds();
            }
            case ALL -> {
                return getAllMatchIds();
            }
        }
        return null;
    }
}
