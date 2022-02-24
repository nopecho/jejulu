package hello.jejulu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //Spring JPA 공통 date 자동 할당 기능 활성화
@SpringBootApplication
public class JejuluApplication {

	//프로젝트 외부 application.yml 사용
	public static final String APPLICATION_PROPERTIES = "spring.config.location="
			+ "classpath:application.yml";
//			+ "/users/app/config/jejulu/application-prod.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(JejuluApplication.class)
				.properties(APPLICATION_PROPERTIES)
				.run(args);
	}
}
