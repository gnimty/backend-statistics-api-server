package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Season
 * author         : solmin
 * date           : 12/10/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/10/23        solmin       최초 생성
 */
@Document(collection = "season")
@Getter
@AllArgsConstructor
public class Season {

    @Id
    private String id;
    private Long startAt;
    private String seasonName;
}