package io.github.kylinhunter.commons.clazz.javassist;

import java.io.File;

import io.github.kylinhunter.commons.clazz.exception.ClazzException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 00:33
 **/
public class TimeCostHelper {
    private static final File USER_DIR = new File(System.getProperty("user.dir"));

    public static Class<?> watch(String clazz, KMethod... methods) throws ClazzException {

        ClassPool classPool = ClassPool.getDefault();
        try {
            CtClass ctClass = classPool.getCtClass(clazz);
            for (KMethod method : methods) {
                CtClass[] params = method.getParams();
                watch(ctClass, method.getMethod(), params);

            }
            ctClass.writeFile(new File(USER_DIR, "tmp").getAbsolutePath());
            Class<?> toClass = ctClass.toClass();
            ctClass.detach();
            return toClass;
        } catch (Exception e) {
            throw new ClazzException("init", e);
        }

    }

    private static void watch(CtClass ctClass, String method, CtClass[] params) throws ClazzException {
        try {
            CtMethod ctMethod;
            if (params != null && params.length > 0) {
                ctMethod = ctClass.getDeclaredMethod(method, params);
            } else {
                ctMethod = ctClass.getDeclaredMethod(method);
            }
            final CtClass longCtClass = ClassPool.getDefault().getCtClass("java.lang.Long");
            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            long cost = end;

            ctMethod.addLocalVariable("start", CtClass.longType);
            ctMethod.addLocalVariable("cost", CtClass.longType);

            ctMethod.insertBefore(" start = System.currentTimeMillis();");

            ctMethod.insertAfter(" cost =System.currentTimeMillis()-start;");

                        ctMethod.insertAfter(" System.out.println(\"time cost:\"+cost);");

            CtClass etype = ClassPool.getDefault().get("java.lang.Exception");
            ctMethod.addCatch("{ System.out.println($e); throw $e; }", etype);
        } catch (Exception e) {
            throw new ClazzException("init", e);
        }
    }
}
