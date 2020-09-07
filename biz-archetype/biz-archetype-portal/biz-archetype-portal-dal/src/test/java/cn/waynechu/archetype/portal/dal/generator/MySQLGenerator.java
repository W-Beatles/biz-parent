
package cn.waynechu.archetype.portal.dal.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class MySQLGenerator {

    /**
     * 生成文件所在目录
     */
    private String outputDir;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 作者
     */
    private String author;

    /**
     * 数据库连接url
     */
    private String dbUrl;

    /**
     * 数据库用户名
     */
    private String dbUserName;
    /**
     * 数据库密码
     */
    private String dbPassword;
    /**
     * 数据库名称
     */
    private String databaseName;
    /**
     * 要生成代码的数据表
     */
    private String[] includeTables;

    public static void main(String[] args) {
        String outputDir = "D:/archetype-generator/project/3/biz-spring-cloud-service-utility/biz-spring-cloud-service-utility-dal";
        String packageName = "utility";
        String author = "waynechu";
        String dbUrl = "jdbc:mysql://localhost:3306/common?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&allowPublicKeyRetrieval=true";
        String dbUserName = "root";
        String dbPassword = "123456";
        String databaseName = "common";
        String[] includeTables = {"dictionary", "dictionary_type", "region"};
        MySQLGenerator mySQLGenerator = new MySQLGenerator()
                .setOutputDir(outputDir)
                .setPackageName(packageName)
                .setAuthor(author)
                .setDbUrl(dbUrl)
                .setDbUserName(dbUserName)
                .setDbPassword(dbPassword)
                .setDatabaseName(databaseName)
                .setIncludeTables(includeTables);
        mySQLGenerator.generate();
    }

    /**
     * 生成代码
     */
    public void generate() {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setDateType(DateType.TIME_PACK);
        gc.setBaseColumnList(true);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setEntityName("%sDO");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(dbUserName);
        dsc.setPassword(dbPassword);
        dsc.setUrl(dbUrl);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(includeTables);
        strategy.setEntityColumnConstant(true);
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.waynechu." + packageName);
        pc.setModuleName("dal");
        pc.setEntity("dataobject." + databaseName);
        pc.setMapper("mapper." + databaseName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // do nothing here.
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        String templatePath = "/templates/mapper.xml.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + "/src/main/resources/sqlmap/" + databaseName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        TemplateConfig tc = new TemplateConfig()
                .disable(TemplateType.CONTROLLER)
                .disable(TemplateType.SERVICE)
                .disable(TemplateType.XML);
        mpg.setTemplate(tc);

        // 生成代码
        mpg.execute();
    }
}
