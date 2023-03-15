package io.github.kylinhunter.commons.clazz.agent.plugin.mca.test;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:49
 **/
@Data
public class Course {
    public final String name;
    private List<Teacher> teachers = Lists.newArrayList();
    private List<Student> students = Lists.newArrayList();

    public Teacher addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        return teacher;
    }

    public Student addStudent(Student student) {
        this.students.add(student);
        this.teachers.forEach(t -> t.addStudent(student));
        return student;
    }

}
