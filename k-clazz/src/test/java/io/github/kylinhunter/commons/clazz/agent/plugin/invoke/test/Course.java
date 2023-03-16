package io.github.kylinhunter.commons.clazz.agent.plugin.invoke.test;

import java.util.List;

import io.github.kylinhunter.commons.collections.ListUtils;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:49
 **/
@Data
public class Course {
    public final String name;
    private List<Teacher> teachers = ListUtils.newArrayList();
    private List<Student> students = ListUtils.newArrayList();

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
