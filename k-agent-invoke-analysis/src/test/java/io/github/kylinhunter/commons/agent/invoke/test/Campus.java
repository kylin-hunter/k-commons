package io.github.kylinhunter.commons.agent.invoke.test;

import java.util.List;

import io.github.kylinhunter.commons.collections.ListUtils;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:50
 **/
@Data
public class Campus {
    public final String name;
    private List<Course> courses = ListUtils.newArrayList();

    public Course addClassRoom(Course course) {
        this.courses.add(course);
        return course;
    }

    public void d1oHomeWork1() {
        courses.forEach(course -> {
            course.getStudents().forEach(student -> {
                student.doHomeWork();
            });
        });
    }

}
