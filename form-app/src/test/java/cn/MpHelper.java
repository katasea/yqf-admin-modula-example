package cn;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author katasea
 * 2020/4/16 9:44
 */


public class MpHelper {
	public static void main(String[] args) {

		String projectPath = System.getProperty("user.dir");
		String outputDir = projectPath + "/base-app/src/main/java"; //生成文件输出目录
		String author = "Generator"; //注释作者
		String entityPrefix = "ROUTE_"; //前缀
		//FIXME 根据实际情况修改要生成的表
		String[] tableName = {"ROUTE_PARAM_DIC_MAPPING"}; //表名多个以 , 分割

		//数据库
		String dbUrl = "jdbc:oracle:thin:@//xxx:1306/orcl";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.ORACLE)
				.setUrl(dbUrl)
				.setUsername("xx")
				.setPassword("xx")
				.setDriverName("oracle.jdbc.OracleDriver");


		GlobalConfig config = new GlobalConfig();
		config.setAuthor(author);
		config.setOutputDir(outputDir);
		//FIXME 文件覆盖可能导致已修改的代码被覆盖
		config.setFileOverride(false);
		config.setOpen(false); //文件生成完是否打开目录
		config.setEnableCache(false);
		config.setServiceName("%sService");
		config.setControllerName("%sHandler");
		config.setServiceImplName("%sServiceImpl");
		config.setMapperName("%sMapper");
		config.setXmlName("%sMapper");

		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig
				.setCapitalMode(true)
				.setEntityLombokModel(true) //设置是否使用 lombook
//                .setDbColumnUnderline(true)
				.setNaming(NamingStrategy.underline_to_camel)
				.setColumnNaming(NamingStrategy.underline_to_camel)
				.setRestControllerStyle(true)
				.setControllerMappingHyphenStyle(true)
//                .setSuperEntityClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
//                .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
				.setInclude(tableName)//修改替换成你需要的表名，多个表名传数组
				.setTablePrefix(entityPrefix)
		;

		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent("com.ylzinfo");
//        packageConfig.setModuleName("ProductCategory"); //在parent包下面添加的包名 eg: com.apple.ProductCategory
		packageConfig.setController("main.handler");
		packageConfig.setService("main.service.intf.db");
		packageConfig.setServiceImpl("main.service.impl.db");
		packageConfig.setEntity("bean.dto");
		packageConfig.setMapper("main.mapper");
		packageConfig.setXml("../resources/mapping");
		// 自定义需要填充的字段
		List<TableFill> tableFillList = new ArrayList<>();
		//如 每张表都有一个创建时间、修改时间
		//而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
		//修改时，修改时间会修改，
		//虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
		//使用公共字段填充功能，就可以实现，自动按场景更新了。
		//如下是配置
		//TableFill createField = new TableFill("gmt_create", FieldFill.INSERT);
		//TableFill modifiedField = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
		//tableFillList.add(createField);
		//tableFillList.add(modifiedField);

		// 自定义配置
//		InjectionConfig cfg = new InjectionConfig() {
//			@Override
//			public void initMap() {
//				// to do nothing
//			}
//		};
//		List<FileOutConfig> focList = new ArrayList<>();
//		focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//			@Override
//			public String outputFile(TableInfo tableInfo) {
//				// 自定义输入文件名称
//				return projectPath + "/src/main/resources/mapping/"
//						+ "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//			}
//		});
//		cfg.setFileOutConfigList(focList);

//        TemplateConfig templateConfig  = new TemplateConfig();
//        templateConfig.setEntity("/templates/entity2.java");
//        templateConfig.setXml(null);

		AutoGenerator mpg = new AutoGenerator();
//        mpg.setTemplate(templateConfig);
		mpg.setGlobalConfig(config);
		mpg.setDataSource(dataSourceConfig);
		mpg.setStrategy(strategyConfig);
//		mpg.setCfg(cfg);
		mpg.setPackageInfo(packageConfig);
		mpg.setTemplate(new TemplateConfig().setXml(null));
		mpg.execute();
	}
}
