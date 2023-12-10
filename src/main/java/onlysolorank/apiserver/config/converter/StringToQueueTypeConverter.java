package onlysolorank.apiserver.config.converter;

import onlysolorank.apiserver.domain.dto.QueueType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * packageName    : onlysolorank.apiserver.config.converter
 * fileName       : StringToQueueTypeConverter
 * author         : solmin
 * date           : 12/10/23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/10/23        solmin       최초 생성
 */
@Component
@ReadingConverter
public class StringToQueueTypeConverter implements Converter<String, QueueType> {
    @Override
    public QueueType convert(String source) {
        try {
            return QueueType.valueOf(source);
        } catch (IllegalArgumentException e) {
            return QueueType.ALL;
        }
    }
}
