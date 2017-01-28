package sbishyr.sandbox.spring.factory.annotation.example;

import sbishyr.sandbox.spring.factory.annotation.BeanCustomMapKeyProvider;

import java.io.Serializable;

/**
 * Created by Serge Bishyr
 */
public interface CustomMapBean extends BeanCustomMapKeyProvider<CustomKeyType>, Serializable {
}
