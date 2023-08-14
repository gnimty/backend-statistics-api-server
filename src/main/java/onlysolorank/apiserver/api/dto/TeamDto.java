package onlysolorank.apiserver.api.dto;

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

    private Boolean win;

    @Builder
    public TeamDto(Team team){
        this.win = team.getWin();
    }

}
