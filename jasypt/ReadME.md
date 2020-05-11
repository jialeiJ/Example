# Jasypt

### 场景说明
Spring Boot的jasypt加密敏感配置信息：

### 引入依赖

	jasypt-spring-boot-starter

### 新建JasyptStringEncryptor实现StringEncryptor
JasyptStringEncryptor如下：

    @Slf4j
	public class JasyptStringEncryptor implements StringEncryptor {
		private final StandardPBEByteEncryptor byteEncryptor;
	    private final Base64 base64;
	    
	    public JasyptStringEncryptor() {
	        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
	        config.setPassword("EdLKOREFDMI/sddnc@A");
	        config.setAlgorithm("PBEWithMD5AndDES");
	        this.byteEncryptor = new StandardPBEByteEncryptor();
	        this.byteEncryptor.setConfig(config);
	        this.base64 = new Base64();
	    }
	    
	    public JasyptStringEncryptor(Environment environment) {
	        byteEncryptor = new StandardPBEByteEncryptor();
	        byteEncryptor.setConfig(getConfig(environment));
	        this.base64 = new Base64();
	    }
	    
	    public JasyptStringEncryptor(String password) {
	        SimplePBEConfig config = new SimplePBEConfig();
	        config.setAlgorithm("PBEWithMD5AndDES");
	        config.setPassword(password);
	        byteEncryptor = new StandardPBEByteEncryptor();
	        byteEncryptor.setConfig(config);
	        this.base64 = new Base64();
	    }
	    
	    public JasyptStringEncryptor(SimpleStringPBEConfig config) {
	        byteEncryptor = new StandardPBEByteEncryptor();
	        byteEncryptor.setConfig(config);
	        this.base64 = new Base64();
	    }
	    
	    private PBEConfig getConfig(Environment e){
	        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
	        config.setPassword(getRequiredProperty(e, "jasypt.encryptor.password"));
	        config.setAlgorithm(getProperty(e, "jasypt.encryptor.algorithm", "PBEWithMD5AndDES"));
	        config.setKeyObtentionIterations(getProperty(e, "jasypt.encryptor.keyObtentionIterations", "1000"));
	        config.setPoolSize(getProperty(e, "jasypt.encryptor.poolSize", "1"));
	        config.setProviderName(getProperty(e, "jasypt.encryptor.providerName", null));
	        config.setSaltGeneratorClassName(getProperty(e, "jasypt.encryptor.saltGeneratorClassname", 			"org.jasypt.salt.RandomSaltGenerator"));
	        config.setStringOutputType(getProperty(e, "jasypt.encryptor.stringOutputType", "base64"));
	        return config;
	    }
	    
	    private static String getProperty(Environment environment, String key, String defaultValue) {
	        if (!propertyExists(environment, key)) {
	            log.info("Encryptor config not found for property {}, using default value: {}", key, defaultValue);
	        }
	        return environment.getProperty(key, defaultValue);
	    }
	
	    private static boolean propertyExists(Environment environment, String key) {
	        return environment.getProperty(key) != null;
	    }
	
	    private static String getRequiredProperty(Environment environment, String key) {
	        if (!propertyExists(environment, key)) {
	            throw new IllegalStateException(String.format("Required Encryption configuration property missing: %s", key));
	        }
	        return environment.getProperty(key);
	    }
	
        @Override
        public String encrypt(String message) {
            byte[] encrypt = byteEncryptor.encrypt((message).getBytes());
            byte[] encode = base64.encode(encrypt);
            return new String(encode,StandardCharsets.UTF_8);
        }
	
        @Override
        public String decrypt(String encryptedMessage) {
            byte[] decode = base64.decode(encryptedMessage.getBytes());
            byte[] decrypt  = byteEncryptor.decrypt(decode);
            return new String(decrypt,StandardCharsets.UTF_8);
        }
	}

### 新建JasyptConfig
JasyptConfig如下：

	@Configuration
	public class JasyptConfig {
		
	    @Bean("jasyptStringEncryptor")
	    public StringEncryptor stringEncryptor(Environment environment){
	        return new JasyptStringEncryptor(environment);
	    }
	}
	
### 配置配置文件

	# 数据库基本配置
	spring:
	  datasource:
	    url: ENC(TIGOEB85ABSA+AT7M0tCbn15I7SSQaIvyYK18h0nu+By7THh8/ABdtEb6ZXMOSS4RQXkccDzJyGlTE1DDVtN3OOHlid8/Ej+zJFtIoOpZ3McapFugoiH44ru1IcKKSxk4WlIv5lxi6JkpCy1LDKNpp8TTIXVTPn1)
	    username: ENC(06xYRaQsjajt2WeUDkSS2g==)
	    password: ENC(06xYRaQsjajt2WeUDkSS2g==)
	    driver-class-name: com.mysql.jdbc.Driver
	    
### 测试Controller

	@RestController
	public class JasyptController {
		@Autowired
		private PropertiesConfig propertiesConfig;
		
		@GetMapping("/testJasypt")
		public PropertiesConfig testJasypt() {
			return propertiesConfig;
		}
	}

### 加密方法在单元测试中

    @Slf4j
	@SpringBootTest
    class JasyptApplicationTests {
    
        @Test
        void contextLoads() {
        }
    
        @Test
        public void jasyptEncrypt() {
            JasyptStringEncryptor jasyptStringEncryptor = new JasyptStringEncryptor("你的加密密码");
            String en = jasyptStringEncryptor.encrypt("jdbc:mysql://192.168.230.128:3306/jaray-database?useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=utf-8");
            String de = jasyptStringEncryptor.decrypt(en);
            log.info("加密后：{}", en);
            log.info("解密后：{}", de);
        }
    
    }
    
### 注意：

    jasypt.encryptor.password=你的加密密码

