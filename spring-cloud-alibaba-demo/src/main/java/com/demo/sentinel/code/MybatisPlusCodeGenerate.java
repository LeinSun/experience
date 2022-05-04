package com.demo.sentinel.code;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author: sunlei
 * @date: 2022/2/25
 * @description:
 */
public class MybatisPlusCodeGenerate {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://10.103.106.188:3306/auto_csap?characterEncoding=utf8&useSSL=false", "xh_app_auto", "rFiQh4DYQisuOg7M")
                .globalConfig(builder -> {
                    builder.author("sunlei") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://temp//mybatisplus"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.iflytek.ocp.common.mappers") // 设置父包名
                            .moduleName("mappers") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://temp//mybatisplus//xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_provider_api") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
