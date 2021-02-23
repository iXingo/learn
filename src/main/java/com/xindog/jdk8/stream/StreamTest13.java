package com.xindog.jdk8.stream;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest13 {

    public static void main(String[] args) {
        UniversityStudent universityStudent1 = new UniversityStudent("zhangsan", 100, 20);
        UniversityStudent universityStudent2 = new UniversityStudent("lisi", 90, 20);
        UniversityStudent universityStudent3 = new UniversityStudent("wangwu", 90, 30);
        UniversityStudent universityStudent4 = new UniversityStudent("zhangsan", 80, 40);

        List<UniversityStudent> universityStudents = Arrays.asList(universityStudent1, universityStudent2, universityStudent3, universityStudent4);

//        Map<String, List<Student>> map = students.stream().
//                collect(Collectors.groupingBy(Student::getName));
//        Map<Integer, List<Student>> map = students.stream().
//                collect(Collectors.groupingBy(Student::getScore));

//        Map<String, Long> map = students.stream().
//                collect(Collectors.groupingBy(Student::getName, Collectors.counting()));

//        Map<String, Double> map = students.stream().
//                collect(Collectors.groupingBy(Student::getName, Collectors.averagingDouble(Student::getScore)));

        Map<Boolean, List<UniversityStudent>> map = universityStudents.stream().
                collect(Collectors.partitioningBy(universityStudent -> universityStudent.getScore() >= 90));


        map.forEach((k, v) -> System.out.println(k + " " + v));


    }
}
