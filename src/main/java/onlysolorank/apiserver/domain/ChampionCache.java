package onlysolorank.apiserver.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : ChampionCache
 * author         : solmin
 * date           : 11/1/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 11/1/23        solmin       최초 생성
 */

@Component
public class ChampionCache {
    private Map<Long, String> krMap = new HashMap<>();
    private Map<Long, String> enMap = new HashMap<>();

    public void initOnCrawl(List<Champion> champions){
        krMap = champions.stream().collect(Collectors.toMap(c->c.getChampionId(), c->c.getKrName()));
        enMap = champions.stream().collect(Collectors.toMap(c->c.getChampionId(), c->c.getEnName()));
    }

    public String resolve(Long championId){
        return enMap.getOrDefault(championId, "None");
    }

    public String resolve(Long championId, Boolean toEn){
        if(toEn){
            return enMap.getOrDefault(championId, "None");
        } else{
            return krMap.getOrDefault(championId, "None");
        }
    }
}
