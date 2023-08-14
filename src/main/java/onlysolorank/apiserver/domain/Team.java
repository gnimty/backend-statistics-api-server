package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private Integer tower;
    private Integer totalKills;


    @ToString
    @Getter
    private class Ban {
        private Integer championId;

        public Ban(Integer championId, Integer pickTurn) {
            this.championId = championId;
            this.pickTurn = pickTurn;
        }

        private Integer pickTurn;
    }
}
