package onlysolorank.apiserver.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : ItemBundle
 * author         : solmin
 * date           : 2023/07/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/31        solmin       최초 생성
 */

@Data
@AllArgsConstructor
public class ItemBundle {

    private final Integer minute;
    private final List<Integer> items;
}
