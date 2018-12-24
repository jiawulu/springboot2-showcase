package com.wuzhong.lambda;

import java.util.function.*;

/**
 * @see java.util.function.Supplier       只有一个输出的函数
 * @see java.util.function.Consumer       只有一个输入的函数
 * @see java.util.function.Predicate      只有一个输入，返回boolean的函数
 * @see java.util.function.Function       输入T输出R的函数
 * @see java.util.function.UnaryOperator  一元函数，输入输出相同
 * @see java.util.function.BiFunction     2个输入的函数
 * @see java.util.function.BinaryOperator 二元函数，输入输出相同
 */
public class Case3_Function {

    public static void main(String[] args) {

        Case3_Function case3 = new Case3_Function();

        Predicate<Integer> pre = integer -> integer > 100;
        Function<Integer, String> function = integer -> String.valueOf(integer);
        Consumer<String> consumer = s -> System.out.println(s);
        Supplier<String> supplier = () -> String.valueOf(System.currentTimeMillis());
        UnaryOperator<Integer> unaryOperator = integer -> integer * 2;
        BiFunction<Integer, Integer, String> biFunction = (a, b) -> String.valueOf(a + b);
        BinaryOperator<Integer> binaryOperator = (a, b) -> a + b;

        //方法引用1， 静态方法的方法引用
        BinaryOperator<Integer> binaryOperator2 = Case3_Function::add2;
        //方法引用2， 使用实例引用的方法
        BinaryOperator<Integer> binaryOperator3 = case3::add;
        //方法引用3， 构造器引用
        Function<String,Dog> dogFunction = Dog::new;

        /**
         * 优先使用特定类型的
         */
        LongPredicate longPredicate = l -> l > 100L;

        pre.test(123);
        function.apply(123);

        System.out.println(binaryOperator.apply(1,2));
        System.out.println(binaryOperator2.apply(1,2));
        System.out.println(binaryOperator3.apply(1,2));
        System.out.println(dogFunction.apply("xtq"));

    }

    int add(int a, int b) {
        return a + b;
    }

    static int add2(int a, int b) {
        return 2 * (a + b);
    }

    static class Dog{

        String name;

        public Dog(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
