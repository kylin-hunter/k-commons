package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.agent.invoke.test.Campus;
import io.github.kylinhunter.commons.agent.invoke.test.Course;
import io.github.kylinhunter.commons.agent.invoke.test.Student;
import io.github.kylinhunter.commons.agent.invoke.test.Teacher;

class InvokeAnalysisPluginTest {

  public static void main(String[] args) {

    Campus campus = new Campus("campus1");
    Course math = campus.addClassRoom(new Course("math"));

    Teacher teacher1 = math.addTeacher(new Teacher("teacher1"));
    math.addTeacher(new Teacher("teacher2"));

    final Student student1 = math.addStudent(new Student("student1"));

    math.addStudent(new Student("student2"));

    Course chinese = campus.addClassRoom(new Course("chinese"));
    chinese.addTeacher(teacher1);

    chinese.addStudent(new Student("student3"));
    chinese.addStudent(student1);

    campus.d1oHomeWork1();

    InvokeTraceManager.getInstance()
        .getTraces()
        .forEach(
            (k, v) -> {
              System.out.println("trace id=>" + k);
              v.forEach(
                  t -> {
                    System.out.println(t);
                  });
              System.out.println("===trace end with time ===");
            });
  }
}
