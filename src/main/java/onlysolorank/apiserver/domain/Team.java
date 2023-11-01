package onlysolorank.apiserver.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Team
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 * 2023/08/28        solmin       pickturn, 전령정보 추가
 */

@Document(collection = "teams")
@Getter
@AllArgsConstructor
@ToString
public class Team {

    @Id
    private String id;
    private String matchId;
    private Integer teamId;
    private Boolean win;
    private List<Ban> bans;
    private Integer baron;
    private Integer dragon;
    private Integer riftHerald;
    private Integer tower;

    @Field("totalKills")
    private Integer totalKill;


    @ToString
    @Getter
    public static class Ban {

        private final Integer championId;
        private final Integer pickTurn;

        public Ban(Integer championId, Integer pickTurn) {
            this.championId = championId;
            this.pickTurn = pickTurn;
        }
    }
}
