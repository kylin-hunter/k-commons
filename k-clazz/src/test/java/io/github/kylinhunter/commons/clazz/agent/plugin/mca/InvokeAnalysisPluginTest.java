package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Campus;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Course;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Student;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.test.Teacher;

class InvokeAnalysisPluginTest {

    public static void main(String[] args) {

        Campus campus = new Campus("campus1");
        Course math = campus.addClassRoom(new Course("math"));

        Teacher teacher1 = math.addTeacher(new Teacher("teacher1"));
        Teacher teacher2 = math.addTeacher(new Teacher("teacher2"));

        final Student student1 = math.addStudent(new Student("student1"));

        final Student student2 = math.addStudent(new Student("student2"));

        Course chinese = campus.addClassRoom(new Course("chinese"));
        chinese.addTeacher(teacher1);

        final Student student3 = chinese.addStudent(new Student("student3"));
        chinese.addStudent(student1);

        campus.doHomeWork();

    }
}