package onlysolorank.apiserver.api.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

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
    private Integer minute;
    private List<Integer> items;

    public ItemBundleDto(Integer minute, List<Integer> items) {
        this.minute = minute;
        this.items = items;
    }
}
