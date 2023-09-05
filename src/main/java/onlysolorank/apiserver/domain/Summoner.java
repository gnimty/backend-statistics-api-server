package onlysolorank.apiserver.domain;

import lombok.Getter;
import lombok.ToString;
import onlysolorank.apiserver.domain.dto.History;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.ZonedDateTime;
import java.util.List;

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
 * 2023/08/15        solmin       HistoryList (소환사 티어 변동 정보) 가져오기, 약 2시간 텀
 * 2023/08/16        solmin       기존 league_entries 컬렉션 summoners 통합에 따라서 소환사 승패정보 추가
 *                                leaguePoints to lp로 필드명 변경
 * 2023/08/29        solmin       Mongodb utc datetime 이슈로 인해 LocalDateTime -> ZonedDateTime으로 변경
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
    private ZonedDateTime updatedAt;
    private Integer summonerLevel;
    private Integer tier;

    @Field("id")
    private String summonerId;

    @Field("leaguePoints")
    private Integer lp;
    private Integer mmr;
    private String name;
    @Field("wins")
    private Integer totalWin;
    @Field("losses")
    private Integer totalDefeat;


    @Field("internal_name")
    private String internalName;


}
