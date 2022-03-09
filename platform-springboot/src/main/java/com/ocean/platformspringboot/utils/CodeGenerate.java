package com.ocean.platformspringboot.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerate {
    public static void main(String[]  args){
        generate();
    }
    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/campus_platform?serverTimezone=GMT%2B8", "root", "gjw523624")
                .globalConfig(builder -> {
                    builder.author("gjw") // 设置作者
//                           .enableSwagger() // 开启 swagger 模式
                            .outputDir("C:\\Users\\阿布\\PlatformProject\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ocean.platformspringboot") // 设置父包名
                            .moduleName("platformspringboot") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\阿布\\PlatformProject\\platform-springboot\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("task") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
       //         .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
