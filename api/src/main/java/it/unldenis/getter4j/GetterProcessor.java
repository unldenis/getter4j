package it.unldenis.getter4j;

import com.google.auto.service.AutoService;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.List;
import javax.annotation.processing.SupportedAnnotationTypes;

@SupportedAnnotationTypes("it.unldenis.getter4j.Getter")
@AutoService(Plugin.class)

public class GetterProcessor implements Plugin {

    private Trees trees;
    private TreeMaker factory;
    private Names symbolsTable;
    private JavacTrees javacTrees;


    @Override
    public String getName() {
        return "Getter";
    }

//    private void log(String text, Object... args) {
//        try {
//            Files.write(Path.of("test.txt" ), (text + "\n").formatted(args).getBytes(), StandardOpenOption.APPEND);
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    @Override
    public void init(final JavacTask task, String... args) {
        Context context = ((BasicJavacTask) task).getContext();;
        trees = Trees.instance(task);
        factory = TreeMaker.instance(context);
        symbolsTable = Names.instance(context);
        javacTrees = JavacTrees.instance(task);


        task.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
                TaskListener.super.started(e);
            }


            @Override
            public void finished(TaskEvent e) {

                if (e.getKind() != TaskEvent.Kind.PARSE) {
                    return;
                }
                e.getCompilationUnit().accept(new TreeScanner<>() {
                    @Override
                    public Object visitClass(ClassTree node, Object o) {
                        for (Tree member : node.getMembers()) {
                            if (member.getKind() == Tree.Kind.VARIABLE) {
                                VariableTree variableTree = (VariableTree) member;

                                if(variableTree.getModifiers().getAnnotations().stream()
                                        .anyMatch(a -> Getter.class.getSimpleName()
                                                .equals(a.getAnnotationType().toString()))) {

                                    JCTree.JCMethodDecl getterMethod = generateGetterMethod(variableTree);
                                    if (getterMethod != null) {
                                        JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) node;
                                        classDecl.defs = classDecl.defs.append(getterMethod);
                                    }
                                }
                            }
                        }
                        return super.visitClass(node, o);
                    }

                    @Override
                    public Object visitMethod(MethodTree node, Object o) {
                        return super.visitMethod(node, o);
                    }
                }, null);

            }
        });

    }

    private JCTree.JCMethodDecl generateGetterMethod(VariableTree variableTree) {
        String variableName = variableTree.getName().toString();
        String capitalizedVariableName = Character.toUpperCase(variableName.charAt(0)) + variableName.substring(1);

        Name methodName = symbolsTable.fromString("get" + capitalizedVariableName);

        if (isMethodAlreadyExists(methodName)) {
            return null;
        }

        JCTree.JCVariableDecl variableDecl = (JCTree.JCVariableDecl) variableTree;
        JCTree.JCExpression returnType = variableDecl.vartype;
        JCTree.JCStatement returnStatement = factory.Return(factory.Ident(variableDecl.name));
        JCTree.JCBlock methodBody = factory.Block(0, List.of(returnStatement));

        List<JCTree.JCAnnotation> methodAnnotations = List.nil();
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        List<JCTree.JCVariableDecl> methodParams = List.nil();
        List<JCTree.JCExpression> methodThrows = List.nil();
        JCTree.JCModifiers methodModifiers = factory.Modifiers(Flags.PUBLIC);

        return factory.MethodDef(methodModifiers, methodName, returnType, methodGenericParams, methodParams, methodThrows, methodBody, null);
    }


    private boolean isMethodAlreadyExists(Name methodName) {
        return false;
    }

}