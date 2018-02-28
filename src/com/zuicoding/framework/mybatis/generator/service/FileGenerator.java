
package com.zuicoding.framework.mybatis.generator.service;

import java.io.File;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/27.
 * <p>
 * <p>
 * </p>
 */
public class FileGenerator {

    private VelocityEngine engine;

    private FileGenerator() {
        engine = new VelocityEngine();
        engine.addProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.addProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        engine.addProperty("input.encoding", "UTF-8");
        engine.addProperty("output.encodng", "UTF-8");
        engine.init();
    }

    private static class FileGeneratorHolder {
        private static final FileGenerator generator = new FileGenerator();
    }

    public static final FileGenerator getInstance() {
        return FileGeneratorHolder.generator;
    }

    public void generateConfig(Map<String, Object> params, Writer writer) {
        Template template = engine.getTemplate("mybatis-generator-config.vm");
        VelocityContext context = new VelocityContext();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                context.put(entry.getKey(),entry.getValue());
            }
        }

        template.merge(context, writer);

    }

    public void generate(File configFile) throws Exception {
        URL url = this.getClass().getClassLoader().getResource("execute.sh");
        File shellFile = new File(url.toURI());
        ProcessBuilder builder = new ProcessBuilder("/bin/chmod","755",shellFile.getAbsolutePath());
        Process process = builder.start();
        process.waitFor();
        process = Runtime.getRuntime().exec(shellFile.getAbsolutePath());
        process.waitFor();

    }


}
