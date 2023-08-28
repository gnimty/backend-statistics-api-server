package onlysolorank.apiserver.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * packageName    : onlysolorank.apiserver.api.controller.dto
 * fileName       : HasPlayedRes
 * author         : solmin
 * date           : 2023/08/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/08/23        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class HasPlayedRes {
    private Boolean hasPlayed;
}
