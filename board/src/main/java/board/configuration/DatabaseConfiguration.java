package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//application.properties를 사용할 수 있도록 위치 지정
//@PropertySource 어노테이션을 추가해서 다른 설정 파일도 사용 가능
@PropertySource("classpath:/application.properties") 
public class DatabaseConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	//properties에 설정된 데이터베이스 정보를 사용하도록 함
	//히카리 커넥션 풀의 설정 파일을 만듬
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	//앞서 만든 히카리 CP의 설정 파일을 이용해서 데이터베이스와 연결하는 데이터 소스를 생성
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	//mapper : 애플리케이션에서 사용할 SQL을 담고 있는 XML 파일
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); //스프링-마이바티스에서 SqlSessionFactory를 생성하기 위해선 SqlSessionFactoryBean 사용
		sqlSessionFactoryBean.setDataSource(dataSource); //앞서 만든 데이터 소스 설정
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml")); //마이바티스 매퍼 파일의 위치 설정
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
