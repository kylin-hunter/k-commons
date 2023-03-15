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
public class Campus {
    public final String name;
    private List<Course> courses = Lists.newArrayList();

    public Course addClassRoom(Course course) {
        this.courses.add(course);
        return course;
    }

    public void doHomeWork() {
        courses.forEach(course -> {
            course.getStudents().forEach(student -> {
                student.doHomeWork();
            });
        });
    }

}
