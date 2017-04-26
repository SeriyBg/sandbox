package sbishyr.sandbox.spring.factory.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import sbishyr.sandbox.spring.RecursiveClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                List<BeanCustomMapKeyProvider> beansToInject = filterBeansToInject(beans, actualTypeArguments[0]);
                Map<Object, BeanCustomMapKeyProvider> autowiredMap =
                        beansToInject.stream().collect(Collectors.toMap(BeanCustomMapKeyProvider::getKey, Function.identity()));
                declaredField.setAccessible(true);
                ReflectionUtils.setField(declaredField, bean, autowiredMap);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }

    private List<BeanCustomMapKeyProvider> filterBeansToInject(Map<String, BeanCustomMapKeyProvider> beans,
                                                               Type mapValueType) {
        List<BeanCustomMapKeyProvider> result = new ArrayList<>();
        beans.forEach((beanName, bean) -> {
            Class<? extends BeanCustomMapKeyProvider> beanClass = bean.getClass();
            Set<Class<?>> allInterfaces = RecursiveClassUtils.getAllInterfaces(beanClass);
            Type[] beanClassGenericInterfaces = beanClass.getGenericInterfaces();
            getBeanFromGenericInterfaces(mapValueType, bean, beanClassGenericInterfaces).ifPresent(result::add);
            for (Class<?> anInterface : allInterfaces) {
                Type[] genericInterfaces = anInterface.getGenericInterfaces();
                getBeanFromGenericInterfaces(mapValueType, bean, genericInterfaces).ifPresent(result::add);
            }
        });
        return result;
    }

    private Optional<BeanCustomMapKeyProvider> getBeanFromGenericInterfaces(
                                Type mapValueType, BeanCustomMapKeyProvider bean, Type[] beanClassGenericInterfaces) {
        for (Type genericInterface : beanClassGenericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                if (ClassUtils.isAssignable((Class)parameterizedType.getRawType(), BeanCustomMapKeyProvider.class)){
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        if (actualTypeArgument.equals(mapValueType)) {
                            return Optional.of(bean);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
