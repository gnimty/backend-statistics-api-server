package onlysolorank.apiserver.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Summoner
 * author         : solmin
 * date           : 2023/07/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/07        solmin       최초 생성
 */
@Document(collection = "summoners")
@Getter
public class Summoner {
    @Id
    private String id;
    private String puuid;
    private String profileIconId;
    private String queue;
    private LocalDateTime updatedAt;
    private Integer SummmonerLevel;
    private Integer tier;

    @Field("league_points")
    private Integer lp;

    private String name;

    @Field("internal_name")
    private String internalName;

    @Override
    public String toString() {
        return "Summoner{" +
            "puuid='" + puuid + '\'' +
            ", profileIconId='" + profileIconId + '\'' +
            ", queue='" + queue + '\'' +
            ", updatedAt=" + updatedAt +
            ", SummmonerLevel=" + SummmonerLevel +
            ", tier=" + tier +
            ", lp=" + lp +
            ", name='" + name + '\'' +
            ", internalName='" + internalName + '\'' +
            '}';
    }

}
