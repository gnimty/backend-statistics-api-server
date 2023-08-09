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
 * 2023/08/09        solmin       @Field 어노테이션으로 받아오도록 수정
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

    @Field("internal_name")
    private String internalName;

}
