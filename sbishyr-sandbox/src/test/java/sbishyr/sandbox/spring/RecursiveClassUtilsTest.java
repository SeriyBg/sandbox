package sbishyr.sandbox.spring;

import org.junit.Test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Serge Bishyr
 */
public class RecursiveClassUtilsTest {

    @Test
    public void shouldGetAllImplementedInterfaces() {
        Set<Class<?>> allInterfaces = RecursiveClassUtils.getAllInterfaces(OneInterface.class);
        HashSet<Object> expected = new HashSet<>();
        expected.add(Serializable.class);
        assertThat(allInterfaces).isEqualTo(expected);
    }

    @Test
    public void shouldReturnSecondLevelInterface() throws Exception {
        Set<Class<?>> allInterfaces = RecursiveClassUtils.getAllInterfaces(TwoLayerInterfaces.class);
        HashSet<Object> expected = new HashSet<>();
        expected.add(Serializable.class);
        expected.add(SubInterface.class);
        assertThat(allInterfaces).isEqualTo(expected);
    }

    private interface SubInterface extends Serializable{}
    private static class TwoLayerInterfaces implements SubInterface{}
    private static class OneInterface implements Serializable{}
}
