package cn.business.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DBConfig {
	//DataSource配置
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		List<Filter> filterList = new ArrayList<>();
		filterList.add(wallFilter());
		druidDataSource.setProxyFilters(filterList);
		return druidDataSource;
//      return new org.apache.tomcat.jdbc.pool.DataSource();
	}


	@Bean
	public WallFilter wallFilter() {
		WallFilter wallFilter = new WallFilter();
		wallFilter.setConfig(wallConfig());
		return wallFilter;
	}

	@Bean
	public WallConfig wallConfig() {
		WallConfig config = new WallConfig();
		config.setMultiStatementAllow(true);//允许一次执行多条语句
		config.setNoneBaseStatementAllow(true);//允许非基本语句的其他语句
		return config;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}


}
