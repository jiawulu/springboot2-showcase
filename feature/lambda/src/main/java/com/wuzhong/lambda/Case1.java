package com.wuzhong.lambda;

/**
 * lambda必须要求接口里只有一个方法
 *
 * FunctionalInterface 尽量加一下
 */
@FunctionalInterface
interface Cal {
    int cal(int i);

    /**
     * 默认方法，可以方便向下兼容!!!
     * @param a
     * @param b
     * @return
     */
    default int add(int a , int b){
        return a + b;
    }
}

/**
 * 如果多个会报错
 */
//@FunctionalInterface
//interface Cal2 {
//    int a();
//    int b();
//}

public class Case1 {

    public static void main(String[] args) {
        Cal cal = i -> i * 2;
        Cal cal2 = i -> {
            return i * 2;
        };
        Cal cal3 = (int i) -> i * 2;
        Cal cal4 = (int i) -> {
            return i * 2;
        };

        System.out.println(cal.cal(1));
        System.out.println(cal2.cal(1));
        System.out.println(cal3.cal(1));
        System.out.println(cal4.cal(1));

        System.out.println(cal.add(1,2));
    }

}
