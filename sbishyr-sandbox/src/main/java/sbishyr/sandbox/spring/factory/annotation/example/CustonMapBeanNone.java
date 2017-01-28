package sbishyr.sandbox.spring.factory.annotation.example;

import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * Created by Serge Bishyr
 */
@Component
@EqualsAndHashCode
public class CustonMapBeanNone implements CustomMapBean {

    @Override
    public CustomKeyType getKey() {
        return CustomKeyType.NONE;
    }
}
