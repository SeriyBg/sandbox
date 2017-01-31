package sbishyr.sandbox.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sbishyr.sandbox.spring.factory.annotation.MapAutowired;
import sbishyr.sandbox.spring.factory.annotation.example.CustomKeyType;
import sbishyr.sandbox.spring.factory.annotation.example.CustomMapBean;
import sbishyr.sandbox.spring.factory.annotation.example.CustonMapBeanNone;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSandboxApplicationTests {

	@MapAutowired
	private Map<CustomKeyType, CustomMapBean> map;

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldHaveAutowiredMap() throws Exception {
		assertThat(map).as("Map should be Autowired").isNotNull();
		assertThat(map).as("Autowired map should contain %s", CustomKeyType.NONE).containsKey(CustomKeyType.NONE);
		assertThat(map.get(CustomKeyType.NONE))
				.as("Value type for %s should be %s", CustomKeyType.NONE, CustonMapBeanNone.class)
				.isEqualTo(new CustonMapBeanNone());
	}

	@Test
	public void shoulHaveFirstElementInMap() throws Exception {
		assertThat(map).containsKeys(CustomKeyType.FIRST);
	}
}
