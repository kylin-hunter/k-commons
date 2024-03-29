package io.github.kylinhunter.commons.agent.invoke.test;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.List;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:50
 */
@Data
public class Teacher {
  public final String name;
  private List<Student> students = ListUtils.newArrayList();

  public Student addStudent(Student student) {
    this.students.add(student);

    return student;
  }
}
