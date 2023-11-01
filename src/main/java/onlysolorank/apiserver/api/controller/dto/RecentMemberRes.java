package onlysolorank.apiserver.api.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class RecentMemberRes {

    List<RecentMemberDto> recentMembers;
    private Integer count;
}
