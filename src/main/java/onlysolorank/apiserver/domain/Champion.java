package onlysolorank.apiserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Champion
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */

@Document(collection = "champion_info")
@Getter
@AllArgsConstructor
public class Champion {
    @Id
    @JsonIgnore
    private String id;

    private Long championId;

    @Field("kr")
    private String krName;

    @Field("en")
    private String enName;

}
