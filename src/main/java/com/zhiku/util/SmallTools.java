package com.zhiku.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SmallTools {

    private static SimpleDateFormat sdf=null;

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String md5(byte[] source) {
        String s = null;
        char hexDigits[] = {       // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
            md.update( source );
            byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0;                                // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i];                 // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换
            }
            s = new String( str );                                 // 换后的结果转换为字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /*
     * 将父类所有的属性COPY到子类中。
     * 类定义中child一定要extends father；
     * 而且child和father一定为严格javabean写法，属性为deleteDate，方法为getDeleteDate
     */
    public static void fatherToChild (Object father,Object child){
        if(!(child.getClass().getSuperclass()==father.getClass())){
            System.err.println("child不是father的子类");
        }
        Class fatherClass= father.getClass();
        Field ff[]= fatherClass.getDeclaredFields();
        for(int i=0;i<ff.length;i++){
            Field f=ff[i];//取出每一个属性，如deleteDate
            Class type=f.getType();
            try {
                Method m = fatherClass.getMethod("get"+upperHeadChar(f.getName()));//方法getDeleteDate
                Object obj=m.invoke(father);//取出属性值
                f.set(child,obj);
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 首字母大写，in:deleteDate，out:DeleteDate
     */
    private static String upperHeadChar(String in){
        String head=in.substring(0,1);
        String out=head.toUpperCase()+in.substring(1,in.length());
        return out;
    }


    /*
    生成随机数
     */
    public static int nextInt(final int min, final int max) {
        Random rand= new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;

    }

    /*
    增加时间-小时
     */
    public static Date addDate(Date now,int h){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY,h);
        return calendar.getTime();   //邮箱激活到期时间为注册时间后1天
    }

    public static String word2Password(String word){
        return DigestUtils.md5Hex(word);
    }

    //注册密码生成
    public static void main(String[] args){
        System.out.println("输入密码：");
        Scanner in=new Scanner( System.in );
        String password=in.nextLine();
        System.out.println(DigestUtils.md5Hex(password));
    }

    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    /*
    获取客户端ip地址
     */
    public static String getIp(HttpServletRequest request) {
        System.out.println(request);
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCALHOST.equals(ipAddress)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


    public static Date toDay(Date date) throws ParseException {
        if(sdf==null)
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse( sdf.format( date ) );
    }

    public static Date toDay(String date) throws ParseException {
        if(sdf==null)
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(  date  );
    }

}
