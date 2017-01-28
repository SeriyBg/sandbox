package sbishyr.sandbox.spring.factory.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Serge Bishyr
 */
@Component
public class MapAutowiredBeanPostProcessor implements BeanPostProcessor {

    private ApplicationContext context;

    @Autowired
    public MapAutowiredBeanPostProcessor(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(MapAutowired.class)) {
                Assert.isAssignable(Map.class, declaredField.getType(),
                        "@MapAutowired annotation should be set on field of type " + Map.class.getName());
                ParameterizedType genericType = (ParameterizedType)declaredField.getGenericType();
                Type[] actualTypeArguments = genericType.getActualTypeArguments();
                Assert.state(actualTypeArguments.length == 2,
                        "To inject @MapAutowired we need to have 2 generic types");

                Map<String, BeanCustomMapKeyProvider> beans = context.getBeansOfType(BeanCustomMapKeyProvider.class);
                List<BeanCustomMapKeyProvider> beansToInject = filterBeansToInject(beans);

                System.out.println(Arrays.toString(actualTypeArguments));
                System.out.println(genericType);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }

    private List<BeanCustomMapKeyProvider> filterBeansToInject(Map<String, BeanCustomMapKeyProvider> beans) {
        List<BeanCustomMapKeyProvider> result = new ArrayList<>();
        beans.forEach((k, v) -> {
            Class<?>[] interfaces = ClassUtils.getAllInterfaces(v);
            for (Class<?> anInterface : interfaces) {
                System.out.println(Arrays.toString(anInterface.getGenericInterfaces()));
            }
            System.out.println(Arrays.toString(interfaces));
        });
        return result;
    }
}
