package it.unldenis.getter4j;

import com.google.auto.service.AutoService;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import it.unldenis.getter4j.Getter;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("it.unldenis.getter4j.Getter")
@AutoService(Plugin.class)

public class GetterProcessor implements Plugin {
    @Override
    public String getName() {
        return "Getter";
    }


    @Override
    public void init(final JavacTask task, String... args) {
        task.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
                TaskListener.super.started(e);
            }


            @Override
            public void finished(TaskEvent e) {

                if(e.getKind() != TaskEvent.Kind.ANALYZE) {
                    return;
                }
                if(true) {
                    throw new RuntimeException("Hello from analyzer");
                }

            }
        });

    }

}