package com.tekken.template;

import com.tekken.Option;
import com.tekken.exception.BackendInvalidException;
import com.tekken.site.Controller;
import com.tekken.site.Request;
import com.tekken.site.Response;
import com.tekken.site.Website;
import com.tekken.support.Logs;
import org.apache.commons.io.FilenameUtils;
import sun.rmi.runtime.Log;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

import static com.tekken.Option.TEKKEN_FOLDER;

public class TemplateEngine {

    private final TemplateUpdater templateUpdater;
    private final TemplateRouter templateRouter = new TemplateRouter();
    private final TemplateCache templateCache = new TemplateCache(templateRouter);
    private final Controller controller;

    public TemplateEngine(Controller controller) {
        this.controller = controller;
        getBackend();
        this.templateUpdater = new TemplateUpdater(Option.TEKKEN_FOLDER+Option.TEMPLATE_WEBROOT, this);
    }


    public Response classLoader(TemplatePage templatePage, Request request) throws BackendInvalidException {
        Response response = new Response(templatePage.getHtmlCode());
        for (Website back : templatePage.getBackends()) {
            try {
                response = back.handler(controller, request, response);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BackendInvalidException(back.getClass().getName());
            }
        }
        return response;

    }

    private void getBackend() {
        File dir;
        if((dir = new File(TEKKEN_FOLDER+"backends/")) != null){
            if(dir.isDirectory()){
                for (File file : dir.listFiles()) {
                    if (FilenameUtils.getExtension(file.getName()).equals("java")){
                        String classNameFile = file.getPath().replace(".java", ".class");
                        File classFile;
                        if((classFile = new File(classNameFile)).exists())
                            classFile.delete();
                        compileClass(file.getPath());
                    }
                }

            }
        }


    }

    public void compileClass(String javaFile) {
        Logs.debug("Compiling java "+javaFile+" ");
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        String javacOpts[] = {javaFile};
        if (javac.run(null, null, null,  javacOpts) != 0) {
            throw new RuntimeException("compilation of " + javaFile + " Failed");
        }
    }

    public TemplateCache getTemplateCache() {
        return templateCache;
    }

    public TemplateUpdater getTemplateUpdater() {
        return templateUpdater;
    }

    public Controller getController() {
        return controller;
    }

    public TemplateRouter getTemplateRouter() {
        return templateRouter;
    }
}
