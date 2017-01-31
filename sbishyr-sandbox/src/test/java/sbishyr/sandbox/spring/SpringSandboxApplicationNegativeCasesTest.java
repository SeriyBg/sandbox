package sbishyr.sandbox.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import sbishyr.sandbox.spring.factory.annotation.BeanCustomMapKeyProvider;
import sbishyr.sandbox.spring.factory.annotation.MapAutowired;
import sbishyr.sandbox.spring.factory.annotation.example.CustomKeyType;
import sbishyr.sandbox.spring.factory.annotation.example.CustomMapBean;

import java.util.Comparator;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Serge Bishyr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSandboxApplicationNegativeCasesTest.TestConfiguration.class)
public class SpringSandboxApplicationNegativeCasesTest {

    @MapAutowired
    private Map<CustomKeyType, CustomMapBean> map;

    @Test
    public void shouldNotFailOnAutowiredMapWithOtherGenericInterfacesAdded() throws Exception {
        assertThat(map.get("String")).isNull();
    }

    @Configuration
    @ComponentScan
    public static class TestConfiguration {

        @Bean
        public BeanCustomMapKeyProvider<String> notNeededBean() {
            return new NotNeededBean();
        }
    }

    public static class NotNeededBean implements BeanCustomMapKeyProvider<String>, Comparator<CustomKeyType> {

        @Override
        public int compare(CustomKeyType o1, CustomKeyType o2) {
            return 0;
        }

        @Override
        public String getKey() {
            return "String";
        }
    }
}
