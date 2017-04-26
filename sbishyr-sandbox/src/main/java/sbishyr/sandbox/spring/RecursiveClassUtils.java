package sbishyr.sandbox.spring;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Serge Bishyr
 */
public class RecursiveClassUtils {
    public static Set<Class<?>> getAllInterfaces(Class<?> clazz) {
        Set<Class<?>> result = new HashSet<>();
        Class<?>[] allInterfacesForClassAsSet = clazz.getInterfaces();
        for (Class<?> iface : allInterfacesForClassAsSet) {
            result.add(iface);
            if (allInterfacesForClassAsSet.length != 0) {
                result.addAll(getAllInterfaces(iface));
            }
        }
        return result;
    }

}
