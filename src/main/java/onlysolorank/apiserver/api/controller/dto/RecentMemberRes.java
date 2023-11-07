package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.RecentMemberDto;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : RecentMemberRes
 * author         : solmin
 * date           : 2023/09/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/11        solmin       최초 생성
 */

@Data
public class RecentMemberRes {

    List<RecentMemberDto> recentMembers;
    private Integer count;

    @Builder
    public RecentMemberRes(List<RecentMemberDto> recentMembers){
        this.recentMembers = recentMembers;
        this.count = recentMembers.size();
    }

}
