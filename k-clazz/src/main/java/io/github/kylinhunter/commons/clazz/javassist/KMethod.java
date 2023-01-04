package io.github.kylinhunter.commons.clazz.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 00:36
 **/

@Data
public class KMethod {
    private final String method;
    private final CtClass[] params;

    public KMethod(String method) {
        this.method = method;
        this.params = null;
    }

    public KMethod(String method, CtClass... params) {
        this.method = method;
        this.params = params;
    }

    public KMethod(String method, Class... params) {
        this.method = method;
        ClassPool classPool = ClassPool.getDefault();
        if (params != null && params.length > 0) {
            this.params = new CtClass[params.length];
            for (int i = 0; i < params.length; i++) {
                try {
                    CtClass ctClass = classPool.getCtClass(params[i].getName());
                    this.params[i] = ctClass;
                } catch (NotFoundException e) {
                    throw new IllegalArgumentException("init error", e);
                }
            }

        } else {
            this.params = null;
        }

    }
}
