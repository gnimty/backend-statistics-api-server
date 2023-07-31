package onlysolorank.apiserver.domain;

import lombok.Getter;
import lombok.ToString;
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
 * 2023/07/24        solmin       잘못 참조된 필드 수정 및 summonerId 추가, @toString 삭제
 * 2023/07/31        solmin       필드명 mongo structure로 통일, internalName getter 추가
 */
@Document(collection = "summoners")
@Getter
@ToString
public class Summoner {
    @Id
    private String id;
    private String puuid;
    private Integer profileIconId;
    private String queue;
    private LocalDateTime updatedAt;
    private Integer summonerLevel;
    private Integer tier;

    @Field("id")
    private String summonerId;
    private Integer leaguePoints;

    private String name;
    private String internal_name;

    public String getInternalName(){
        return this.internal_name;
    }
}
