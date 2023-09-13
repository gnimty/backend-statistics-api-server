package onlysolorank.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * packageName    : onlysolorank.apiserver.domain
 * fileName       : Version
 * author         : solmin
 * date           : 2023/09/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/09/13        solmin       최초 생성
 */
@Document(collection = "version")
@Getter
@AllArgsConstructor
public class Version {
    @Id
    private String id;
    private String version;
    private Integer order;
}
