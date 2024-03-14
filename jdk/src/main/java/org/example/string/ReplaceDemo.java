package org.example.string;

public class ReplaceDemo {
    public static void main(String[] args) {
        String text = "/path/index/query";
        String regex = "/path/(?<page>/?.*)/(?<str>/?.*)";
        // regex 是匹配 text的正则表达式，如果匹配到了，则会将后面的replacement对应的字符串替换原始的字符串
        // 如果没有匹配，则会返回原始字符串
        // 同时 replacement中可以使用$占位符来引用regex中的数据
        System.out.println(text.replaceAll(regex, "/${page}/${str}"));
    }
}
