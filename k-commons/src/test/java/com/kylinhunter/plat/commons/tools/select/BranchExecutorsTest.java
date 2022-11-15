package com.kylinhunter.plat.commons.tools.select;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

class BranchExecutorsTest {
    @Test
    void use() {

        List<String> keys = Lists.newArrayList("1", "2", "3", "4", "5");

        keys.forEach(key -> {

            BranchExecutor<String, Integer> branchExecutor = BranchExecutors.use(key);
            Integer result = branchExecutor.test(
                    branchExecutor.predicate(s -> s.equals("1")).then(f -> 11)

            ).test(
                    branchExecutor.predicate(s -> s.equals("2")).then(f -> 22)

            ).test(
                    branchExecutor.predicate(s -> s.equals("3")).then(f -> 33)

            ).test(
                    branchExecutor.predicate(s -> s.equals("4")).then(44)

            ).others(f -> 100);

            System.out.println(key + " ' result=" + result);
        });

    }
}