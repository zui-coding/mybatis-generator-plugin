
package com.zuicoding.framework.mybatis.generator.service;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.zuicoding.framework.mybatis.generator.util.StringTools;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/27.
 * <p>
 * <p>
 * </p>
 */
public class FileGenerator {

    private VelocityEngine engine;
    private ShellCallback callback;
    private List<String> warnings = new ArrayList<>();
    private boolean overwrite = true;
    private Template template ;
    private ProgressCallback progressCallback;
    private FileGenerator() throws Exception {
        engine = new VelocityEngine();
        engine.addProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.addProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        engine.addProperty("input.encoding", "UTF-8");
        engine.addProperty("output.encodng", "UTF-8");
        engine.init();
        template = engine.getTemplate("mybatis-generator-config.vm");
        callback = new DefaultShellCallback(overwrite);
        progressCallback = new VerboseProgressCallback();
    }

    private static class FileGeneratorHolder {
        private static final FileGenerator generator;

        static {
            try {
                generator = new FileGenerator();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final FileGenerator getInstance() {
        return FileGeneratorHolder.generator;
    }

    public void generateConfig(Map<String, Object> params, Writer writer) {

        VelocityContext context = new VelocityContext(params);

        template.merge(context, writer);
    }

    public String generateDBUrl(String database,
                                String host,
                                int port,
                                String urlTemplate) throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("database",database);
        params.put("host",host);
        params.put("port",port);
        return merge(params,urlTemplate);
    }

    private String merge(Map<String, Object> params,String template) throws Exception {
        if (StringTools.isBank(template)) {
            return null;
        }

        VelocityContext context = new VelocityContext(params);
        StringWriter writer = new StringWriter();
        engine.evaluate(context,writer,"MybatisGenerator",template);
        writer.flush();
        String result = writer.toString();
        writer.close();
        return writer.toString();
    }

    public void generate(File configFile) throws Exception {
        ConfigurationParser configParser = new ConfigurationParser(warnings);
        Configuration config = configParser.parseConfiguration(configFile);
        MyBatisGenerator generator = new MyBatisGenerator(config,callback,warnings);
        generator.generate(null);
    }

    public void generate(InputStream configStream) throws Exception {
        ConfigurationParser configParser = new ConfigurationParser(warnings);
        Configuration config = configParser.parseConfiguration(configStream);
        MyBatisGenerator generator = new MyBatisGenerator(config,callback,warnings);
        generator.generate(progressCallback);
    }
    public void generate(Reader configReader) throws Exception {
        ConfigurationParser configParser = new ConfigurationParser(warnings);
        Configuration config = configParser.parseConfiguration(configReader);
        MyBatisGenerator generator = new MyBatisGenerator(config,callback,warnings);
        generator.generate(progressCallback);
    }


}
