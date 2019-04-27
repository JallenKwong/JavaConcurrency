package com.lun.action.c02;

public class Overflow {
    public static void main(String[] args) {

        int v1 = 1073741827;
        int v2 = 1431655768;

        System.out.println("V1="+v1);
        System.out.println("V2="+v2);

        int ave = (v1+v2)/2;
        System.out.println("ave="+ave);
        
        //利用乘法分配律进行修改
        System.out.println("ave2=" + (v1 / 2 + v2 / 2));

        //在排序算法中注意类似情况
    }
}

/*result:

V1=1073741827
V2=1431655768
ave=-894784850
ave2=1252698797
*/
