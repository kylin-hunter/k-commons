package com.kylinhunter.plat.commons.tools.select;

import java.util.function.Function;
import java.util.function.Predicate;

import lombok.ToString;

/**
 * @author BiJi'an
 * @description 分支结果选择器
 * @date 2022/01/01
 **/
@ToString(of = "selected")
public class BranchExecutor<P, T> {
    private boolean selected = false;
    private Function<P, T> factory;

    private final P param;/* 选择器参数，该参数会在进行条件判断和结果获取时会被当做条件传入*/

    public BranchExecutor(P param) {
        this.param = param;
    }

    /**
     * @param param
     * @return   com.kylinhunter.plat.commons.tools.select.BranchExecutor<P, T>
     * @throws
     * @title 使用指定的参数创建选择器
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:51 下午
     */
    public static <P, T> BranchExecutor<P, T> param(P param) {
        return new BranchExecutor<>(param);
    }

    /**
     * @param branch
     * @return   com.kylinhunter.plat.commons.tools.select.BranchExecutor<P, T>
     * @throws
     * @title 传入一个新的分支，判定这个分支满足条件
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:51 下午
     */
    public BranchExecutor<P, T> test(Branch<P, T> branch) {
        if (!selected) {
            boolean pass = branch.tester().test(param);
            if (pass) {
                selected = true;
                factory = branch.factory();
            }
        }
        return this;
    }

    /**
     * @param supplier
     * @return T
     * @throws
     * @title 获取结果，如果当前选择器没有击中任何条件分支，则从给定的提供者中获取结果；
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:52 下午
     */
    public T others(Function<P, T> supplier) {
        return selected ? this.factory.apply(param) : supplier.apply(param);
    }

    public BranchBuilder<P, T> predicate(Predicate<P> tester) {

        return factory -> Branch.of(tester, factory);
    }

}
