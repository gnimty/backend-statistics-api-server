package onlysolorank.apiserver.domain.dto;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

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

@Getter
@ToString
public class ItemBundleDto {

    private final Integer minute;
    private final List<Integer> items;

    public ItemBundleDto(Integer minute, List<Integer> items) {
        this.minute = minute;
        this.items = items;
    }
}
