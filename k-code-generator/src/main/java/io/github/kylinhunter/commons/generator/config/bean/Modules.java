package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;
import lombok.Data;

@Data
public class Modules {
    protected Database database;
    protected List<Module> list;

}
