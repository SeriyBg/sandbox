package sbishyr.sandbox.spring.factory.annotation.example;

import org.springframework.stereotype.Component;

/**
 * Created by Serge Bishyr
 */
@Component
public class CustomMapBeanFirst implements CustomMapBean {
    @Override
    public CustomKeyType getKey() {
        return CustomKeyType.FIRST;
    }
}
