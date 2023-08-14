package onlysolorank.apiserver.api.dto;

import lombok.Data;
import onlysolorank.apiserver.domain.Match;

import java.util.List;


@Data
public class SummonerMatchDTO {
    private String puuid;
    private List<Match> matches;

    // 생성자, Getter, Setter (생략 가능)
}
