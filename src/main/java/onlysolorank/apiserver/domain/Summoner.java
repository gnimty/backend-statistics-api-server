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
 * 2023/07/24        solmin       잘못 참조된 필드 수정 및 summonerId 추가, @toString 삭제
 */
@Document(collection = "summoners")
@Getter
public class Summoner {
    @Id
    private String id;
    private String puuid;
    private Integer profileIconId;
    private String queue;
    private LocalDateTime updatedAt;
    private Integer summonerLevel;

    @Field("tier")
    private Integer division;

    @Field("id")
    private String summonerId;

    @Field("leaguePoints")
    private Integer lp;

    private String name;

    @Field("internal_name")
    private String internalName;
}
