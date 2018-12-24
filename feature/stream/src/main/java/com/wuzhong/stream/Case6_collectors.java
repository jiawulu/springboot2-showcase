package com.wuzhong.stream;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 1. Collectors.summarizingInt
 * 2. Collectors.partitioningBy
 * 3. Collectors.groupingBy
 * 4. Collectors.counting()
 */
public class Case6_collectors {


    /**
     * 统计
     * @param args
     */
    public static void main(String[] args) {

        List<Student> students = Lists.newArrayList(new Student("a", 10, true),
                new Student("b", 10, true), new Student("c", 10, true),
                new Student("d", 88, false), new Student("e", 2, true),
                new Student("g", 11, true), new Student("f", 10, false));


        IntSummaryStatistics collect = students.stream().collect(Collectors.summarizingInt(s -> s.age));
        System.out.println(collect);

        System.out.printf("students.stream().collect(Collectors.partitioningBy(Student::isMale))");
        Map<Boolean, List<Student>> collect1 = students.stream().collect(Collectors.partitioningBy(Student::isMale));
        System.out.println(JSON.toJSONString(collect1,true));

        System.out.printf("students.stream().collect(Collectors.groupingBy(Student::ageArea))");
        Map<String, List<Student>> collect2 = students.stream().collect(Collectors.groupingBy(Student::ageArea));
        System.out.println(JSON.toJSONString(collect2,true));

        Map<String, List<Student>> collect3 = students.stream().collect(Collectors.groupingBy( s -> {
            if (s.getAge() > 20){
                return "old";
            }else {
                return "yong";
            }
        }));
        System.out.println(JSON.toJSONString(collect3,true));

        System.out.println("students.stream().collect(Collectors.groupingBy(Student::ageArea, Collectors.counting()));");
        Map<String, Long> collect4 = students.stream().collect(Collectors.groupingBy(Student::ageArea, Collectors.counting()));
        System.out.println(JSON.toJSONString(collect4,true));

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Student {

        private String name;
        private int age;
        private boolean male;

        public String ageArea(){
            if (this.getAge() < 20){
                return "young";
            }else if (this.getAge() < 50){
                return "middle";
            }else {
                return "old";
            }
        }

    }
}
