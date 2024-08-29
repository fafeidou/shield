package com.shield.string;

public class StringExer {
    String str = new String("good");
    char[] ch = {'t', 'e', 's', 't'};

    public void change(String str, char ch[]) {
        System.out.println(str);//good
        System.out.println(ch);//test
        str = str + " test ok";
        ch[0] = 'b';
        System.out.println(str);//good test ok
        System.out.println(ch);//best
    }


    public static void main(String[] args) {
        StringExer ex = new StringExer();
        ex.change(ex.str, ex.ch);
        System.out.println(ex.str);//good
        System.out.println(ex.ch);//best

    }
}
