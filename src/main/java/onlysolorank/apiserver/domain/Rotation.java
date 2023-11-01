package onlysolorank.apiserver.domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Rotation
 * author         : solmin
 * date           : 10/9/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/9/23        solmin       최초 생성
 */

@Document(collection = "rotations")
@Getter
public class Rotation extends Champion {

    public Rotation(String id, Long championId, String krName, String enName) {
        super(id, championId, krName, enName);
    }
}
