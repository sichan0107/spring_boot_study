package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
/*
 *  @EntityScan : 애플리케이션이 실행될 때 basePackages 안의 클래스 중 @Entity가 설정된 클래스를 검색한다. 여기에 Jsr을 등록한다.
 *  Jsr310Converters : 날짜와 시간에 관련된 API -> Java 8의 날짜 시간 클래스를 그대로 사용하면 MySQL 버전에 따라 문제가 발생할 수 있음.
 */

@EntityScan(basePackageClasses = {Jsr310Converters.class}, basePackages = {"board"})
@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
