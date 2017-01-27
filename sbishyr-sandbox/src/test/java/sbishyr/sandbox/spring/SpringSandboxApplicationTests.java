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
	public void shoulHaveAutowiredMap() throws Exception {
		assertThat(map).containsKey(CustomKeyType.NONE);
		assertThat(map.get(CustomKeyType.NONE)).isEqualTo(new CustonMapBeanNone());
	}
}
