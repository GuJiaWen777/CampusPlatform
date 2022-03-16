package com.ocean.platformspringboot.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerate {
    public static void main(String[]  args){
        generate();
    }
    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/platform?serverTimezone=GMT%2B8", "root", "gjw523624")
                .globalConfig(builder -> {
                    builder.author("gjw") // 设置作者
//                           .enableSwagger() // 开启 swagger 模式
                            .outputDir("C:\\Users\\阿布\\PlatformProject\\platform-springboot\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ocean.platformspringboot") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\阿布\\PlatformProject\\platform-springboot\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("task_done") // 设置需要生成的表名
                            .addTablePrefix(); // 设置过滤表前缀
                })
       //         .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
