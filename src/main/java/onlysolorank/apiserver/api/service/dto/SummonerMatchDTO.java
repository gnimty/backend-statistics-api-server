package onlysolorank.apiserver.api.service.dto;

import java.util.List;
import lombok.Data;
import onlysolorank.apiserver.domain.Match;


@Data
public class SummonerMatchDTO {

    private String puuid;
    private List<Match> matches;

    // 생성자, Getter, Setter (생략 가능)
}
