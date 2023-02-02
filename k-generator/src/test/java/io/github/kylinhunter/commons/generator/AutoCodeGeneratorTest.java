package io.github.kylinhunter.commons.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AutoCodeGeneratorTest {

    @Test
    void execute() {
        AutoCodeGenerator autoCodeGenerator=new AutoCodeGenerator();
        autoCodeGenerator.execute();
    }
}