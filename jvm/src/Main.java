import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

interface SuperClass {
    public static int a = 10;

}
interface SubClass extends SuperClass {
    public static int b = a;
}

class User{}

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {


        MyClassLoader myClassLoader = new MyClassLoader("D:/temp/src/main/java");
        Class clazz = myClassLoader.loadClass("com.kaishengit.entity.Account");
        System.out.println(clazz.getClassLoader());

        Object obj = clazz.newInstance();
        System.out.println(obj);
        Method method = clazz.getMethod("setUserName",String.class);
        method.invoke(obj,"Tom");

        Method getMethod = clazz.getMethod("getUserName");
        String name = (String) getMethod.invoke(obj);
        System.out.println(name);

        /*ClassLoader classLoader = User.class.getClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());*/


        //System.out.println(SubClass.b);
        //SuperClass[] superClass = new SuperClass[10];
        //System.out.println(SubClass.value);

        //-Xms20m -Xmx20M -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
        /*byte[] bytes = null;
        for (int i = 0;i < 10;i++) {
            bytes = new byte[1024*1024*1];
        }*/


        //-Xms5m -Xmx10M -XX:+PrintGCDetails -XX:+UseSerialGC
        /*Runtime runtime = Runtime.getRuntime();
        System.out.println("可用最大内存： " + runtime.maxMemory());
        System.out.println("空闲内存： " + runtime.freeMemory());
        System.out.println("当前可用内存： " + runtime.totalMemory());

        System.out.println("-------------------------------------------");
        byte[] bytes = new byte[1024*1024*4];
        System.out.println("可用最大内存： " + runtime.maxMemory());
        System.out.println("空闲内存： " + runtime.freeMemory());
        System.out.println("当前可用内存： " + runtime.totalMemory());
        System.out.println("-------------------------------------------");
        bytes = new byte[1024*1024*4];
        System.out.println("可用最大内存： " + runtime.maxMemory());
        System.out.println("空闲内存： " + runtime.freeMemory());
        System.out.println("当前可用内存： " + runtime.totalMemory());*/
    }
}
