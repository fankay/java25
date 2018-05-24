import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

    private String classPath; //D:/temp/src/main/java
    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadClassByName(name); //com.kaishengit.entity.Account
        return defineClass(name,bytes,0,bytes.length);
    }
    private byte[] loadClassByName(String name) {
        //D:/temp/src/main/java/com/kaishengit/entity/Account.class
        File file = new File(classPath,name.replace(".","/")+".class");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer,0,length);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
