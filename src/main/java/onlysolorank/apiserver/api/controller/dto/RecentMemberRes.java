package onlysolorank.apiserver.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import onlysolorank.apiserver.api.service.dto.RecentMemberDto;

import java.util.List;

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
    private Integer count;
    List<RecentMemberDto> recentMembers;
}
