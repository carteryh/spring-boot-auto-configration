# spring-boot-auto-configration
spring boot 自动装配


spring boot自定义starter，开发过程中，经常遇到一些通用功能，为了防止重复造轮子，我们只需要利用Spring SPI扩展，SpringBoot为我们完成自动装配。本文是基于gradle管理依赖，会对gradle做一个简单介绍。

一.gradle
Gradle是一个基于Apache Ant和Apache Maven概念的项目自动化构建开源工具。它使用一种基于Groovy的特定领域语言(DSL)来声明项目设置，目前也增加了基于Kotlin语言的kotlin-based DSL，抛弃了基于XML的各种繁琐配置。

1.仓库地址配置

	repositories {
	    mavenLocal() // 使用本地仓库
	    mavenCentral()
	}

2.gradle打包上传本地仓库配置
	
	// 指定上传的路径
	def localMavenRepo = 'file://' + new 		File(System.getProperty('user.home'), '.m2/').absolutePath
	
	uploadArchives {
	    repositories.mavenDeployer {
	        repository(url: localMavenRepo)
	    }
	}

2.全局依赖排除

	configurations {
	    //依赖排除
	    all*.exclude group:'com.alibaba', module: 'fastjson'
	}

二、自动装配
1.@Import注解
 通过@Import注解把相应的Bean注册到Spring IOC容器中，例如:
 @Import(FormatAutoConfiguration.class)
该注解就是把FormatAutoConfiguration中定义的Bean导入IOC容器


	@Configuration
	public class FormatAutoConfiguration {
	
	    /**
	     *
	     * @return 
	     * ConditionalOnMissingClass表示类不存在
	     */
	    //metadata-auto....
	    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
	    @Bean
	    @Primary
	    public FormatProcessor stringFormat(){
	        return new StringFormatProcessor();
	    }
	
	    /**
	     *
	     * @return 
	     * ConditionalOnClass表示类存在
	     */
	    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
	    @Bean
	    public FormatProcessor jsonFormat(){
	        return new JsonFormatProcessor();
	    }
	    
	}

2.@EnableConfigurationProperties 和@ConfigurationProperties注解
@ConfigurationProperties注解，通常是用来将properties和yml配置文件属性转化为bean对象使用。
@EnableConfigurationProperties 导入启用的属性类

	@Data
	@ConfigurationProperties(prefix= DemoProperties.FORMAT_PREFIX)
	public class DemoProperties {
	
	    public static final String FORMAT_PREFIX="format";
	
	    private String style;
	
	    private String code;
	
	    private String name;
	
	}
	
	@Import(FormatAutoConfiguration.class)
	@EnableConfigurationProperties(DemoProperties.class) 
	@Configuration
	public class DemoAutoConfiguration {
	
	    @Bean
	    public FormatTemplate formatTemplate(DemoProperties demoProperties, FormatProcessor formatProcessor){
	        return new FormatTemplate(demoProperties,formatProcessor);
	    }
	
	}
	
3.Spring SPI EnableAutoConfiguration
EnableAutoConfiguration是spring提供SPI扩展的场景，是一个key-value的形式，只需要在resources建一个文件，完整路径resources/META-INF/spring.factories

	org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
	  com.starter.autoconfiguration.DemoAutoConfiguration
Spring就会找到对应的类com.starter.autoconfiguration.DemoAutoConfiguration注册到IOC容器中。

