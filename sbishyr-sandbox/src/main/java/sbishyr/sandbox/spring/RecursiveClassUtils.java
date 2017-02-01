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
            if (hasSuperInterfaces(allInterfacesForClassAsSet)) {
                result.addAll(getAllInterfaces(iface));
            }
        }
        return result;
    }

    private static boolean hasSuperInterfaces(Class<?>[] allInterfacesForClassAsSet) {
        return allInterfacesForClassAsSet.length != 0;
    }
}
