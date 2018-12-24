package com.wuzhong.lambda;

import java.text.DecimalFormat;
import java.util.function.Function;

interface Format {
    String format(int i);
}

class Money {
    private int money;

    public Money(int money) {
        this.money = money;
    }

    public void print(Format format) {
        System.out.println(format.format(money));
    }

    public void print2(Function<Integer, String> function) {
        System.out.println(function.apply(money));
    }
}


/**
 * @see java.util.function.Supplier       只有一个输出的函数
 * @see java.util.function.Consumer       只有一个输入的函数
 * @see java.util.function.Predicate      只有一个输入，返回boolean的函数
 * @see Function                          输入T输出R的函数
 * @see java.util.function.UnaryOperator  一元函数，输入输出相同
 * @see java.util.function.BiFunction     2个输入的函数
 * @see java.util.function.BinaryOperator 二元函数，输入输出相同
 */
public class Case2 {

    public static void main(String[] args) {
        Money money = new Money(Integer.MAX_VALUE);

        money.print(i -> {
            return new DecimalFormat("#,###").format(i);
        });

        money.print(i -> new DecimalFormat("#,###").format(i));

        //不用定义过多无用的接口，通用 ； 函数接口
        Function<Integer, String> integerStringFunction = integer -> {
            return new DecimalFormat("#,###").format(integer);
        };
        //链式操作， 根据灵活
        money.print2(integerStringFunction.andThen(s -> "RMB:" + s));
    }

}


