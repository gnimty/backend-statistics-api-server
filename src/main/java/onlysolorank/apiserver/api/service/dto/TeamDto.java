package onlysolorank.apiserver.api.service.dto;

import static onlysolorank.apiserver.domain.Team.Ban;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.domain.Team;

/**
 * packageName    : onlysolorank.apiserver.api.dto
 * fileName       : TeamDto
 * author         : solmin
 * date           : 2023/07/28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/28        solmin       최초 생성
 */

@Data
public class TeamDto {

    private Integer teamId;
    private Boolean win;
    private List<Ban> banList;
    private Integer baron;
    private Integer dragon;
    private Integer riftHerald;
    private Integer tower;
    private Integer totalKill;
    private Integer totalGold;


    @Builder
    public TeamDto(Team team, Integer totalGold) {
        this.teamId = team.getTeamId();
        this.win = team.getWin();
        this.banList = team.getBans();
        this.baron = team.getBaron();
        this.dragon = team.getDragon();
        this.riftHerald = team.getRiftHerald();
        this.tower = team.getTower();
        this.totalKill = team.getTotalKill();
        this.totalGold = totalGold;
    }
}
