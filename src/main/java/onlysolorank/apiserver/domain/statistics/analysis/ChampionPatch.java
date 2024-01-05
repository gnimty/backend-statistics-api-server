package onlysolorank.apiserver.domain.statistics.analysis;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.Champion;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain.statistics.analysis
 * fileName       : Patch
 * author         : solmin
 * date           : 1/5/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 1/5/24        solmin       최초 생성
 */

@Document("patch")
@Getter
@ToString
public class ChampionPatch extends Champion {
    private String version;
    private String target;
    private String targetImgUrl;
    private List<String> changes;
    public ChampionPatch(String id, Long championId, String krName, String enName, String version, String target,
        String targetImgUrl, List<String> changes) {
        super(id, championId, krName, enName);
        this.version = version;
        this.target = target;
        this.targetImgUrl = targetImgUrl;
        this.changes = changes;
    }

}
