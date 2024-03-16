package org.example.methodhandle;

import javax.xml.crypto.dsig.TransformService;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class MethodHandleDemo {

    public static void main(String[] args) throws Throwable {

        Class<?> clazz = TestInfo.class;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 定义方法的签名
        MethodType methodType = MethodType.methodType(String.class);
        TestInfo testInfo = new TestInfo(1,"12");
        // 以下方法，只有在jdk9以上，才可以使用
        MethodHandles.Lookup jdk9Lookup = MethodHandles.privateLookupIn(TestInfo.class, lookup);
        MethodHandle jdk9plus = jdk9Lookup.findGetter(clazz, "id", int.class);
        System.out.println("jdk9Plus:"+ jdk9plus.bindTo(testInfo).invoke());

        // 对于私有字段，在jdk8以下，不能使用 lookup.findGetter(clazz, "id", int.class);
        // 有以下两方案：
        // 1. 使用反射获取字段，并设置访问权限
        // 2. 主动增加getXxx的getter 方法
        // 3. 在对应的类中配置Lookup对象
        // 需要先通过反射获取字段，并设置访问权限
        Field field = TestInfo.class.getDeclaredField("id");
        field.setAccessible(true);
        MethodHandle idMethodHandle = lookup.unreflectGetter(field);
        System.out.println("1."+ idMethodHandle.bindTo(testInfo).invoke());
        // 获取对应的方法
        MethodHandle getDisplay = lookup.findVirtual(clazz, "getName", methodType);
        // 通过调用MethodHandle的bindTo方法和具体实例进行绑定
        System.out.println("2."+ getDisplay.bindTo(testInfo).invoke());

        MethodHandle id = TestInfo.getLookup().findGetter(clazz, "id", int.class);

        System.out.println("3. "+ id.bindTo(testInfo).invoke());

        String toBeTrimmed = " text with spaces ";
        Method reflectionMethod = String.class.getMethod("trim");
        MethodHandle handle = lookup.unreflect(reflectionMethod);
        CallSite callSite = LambdaMetafactory.metafactory(
                // methodHandle lookup
                lookup,
                // 定义的方法名称为get
                "get",
                // 返回的方法签名
                MethodType.methodType(Supplier.class, String.class),
                // 方法返回值的类型擦除，统一返回 object
                MethodType.methodType(Object.class),
                // 需要进行转换的原始方法
                handle,
                // 真正的方法签名
                MethodType.methodType(String.class));
        Supplier<String> lambda = (Supplier<String>) callSite.getTarget().bindTo(toBeTrimmed).invoke();

        System.out.println(lambda.get());

    }
}
