package com.zhiku.util.md2Database;

//import com.zhiku.util.md2Database.model.Table;
//import com.zhiku.util.md2Database.model.ZKList;
import java.io.*;
import java.util.ArrayList;

/**
 * 文件工具类
 *
 *
 * @version        jdk-11.0.2, 19/03/21
 * @author         ChenYanyu
 */
public class FileUtils {

//    /**
//     * 反序列化ZKList对象
//     *
//     *
//     * @param path
//     *
//     * @return
//     *
//     * @throws ClassNotFoundException
//     * @throws IOException
//     */
//    public ZKList deSerializeList(String path) throws IOException, ClassNotFoundException {
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
//        ZKList            li  = (ZKList) ois.readObject();
//
//        System.out.println("ZKList对象反序列化成功！");
//
//        return li;
//    }
//
//    /**
//     * 反序列化Table对象
//     *
//     *
//     * @param path
//     *
//     * @return
//     *
//     * @throws ClassNotFoundException
//     * @throws IOException
//     */
//    public Table deSerializeTable(String path) throws IOException, ClassNotFoundException {
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
//        Table             tab = (Table) ois.readObject();
//
//        System.out.println("Table对象反序列化成功！");
//
//        return tab;
//    }

    /**
     * 读入文件，转换成Array
     *
     *
     * @param path
     * @param encoding
     *
     * @return
     *
     * @throws IOException
     */
    public ArrayList<String> read2Array(String path, String encoding) throws IOException {

        // 编码格式传进来
        FileInputStream   fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        BufferedReader    br  = new BufferedReader(isr);

        // 输出结果
        ArrayList<String> arr  = new ArrayList<String>();
        String            line = "";

        // 不为空继续读
        while ((line = br.readLine()) != null) {
            arr.add(line);
        }

        br.close();
        isr.close();
        fis.close();
        return arr;
    }

//    /**
//     * 序列化
//     * @param obj
//     * @param path
//     * @throws IOException
//     */
//    public  void serialize(Object obj, String path) throws IOException {//多个线程可能要调用同一文件名，方法不能并非执行
//        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(path)));
//
//        oo.writeObject(obj);
//        System.out.println("序列化成功！");
//        oo.close();
//    }
//    //转字符串
//    public static String TabList2Str(Object obj, String path) throws IOException {
//        FileUtils fu = new FileUtils();
//        fu.serialize(obj, path);
//        String str=null;
//        File file=new File(path);
//        FileInputStream in=new FileInputStream(file);
//        // size 为字串的长度 ，这里一次性读完
//        int size=in.available();
//        byte[] buffer=new byte[size];
//        in.read(buffer);
//        in.close();
//        str=new String(buffer,"UTF-8");//读文件字符集，写文件也需要这种字符集
//        return str;
//    }
}


//~ Formatted by Jindent --- http://www.jindent.com
