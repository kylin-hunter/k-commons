package io.github.kylinhunter.commons.clazz.agent.plugin.mca.test;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:50
 **/
@Data
public class Teacher  {
    public final String name;
    private List<Student> students = Lists.newArrayList();

    public Student addStudent(Student student) {
        this.students.add(student);

        return student;
    }
}
