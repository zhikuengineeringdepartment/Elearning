package com.zhiku.util.monitor;

import java.awt.dnd.DropTarget;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VisitStatistics {
    private static Map<String, Date> ipDates=new HashMap<>(  );
    private static Thread thread;
    private static String saveFile;
    private static Charset charset=StandardCharsets.UTF_8;
    private static int limit=30*60*1000;//无法判断用户何时下线，以30分钟不发请求为下线
    private static boolean isChing;

    private static int errorNum=0;

    public static synchronized void add(String ip){
        ipDates.put( ip,new Date(  ) );
        isChing=true;
    }

    static void setSaveFile(String saveFile){
        VisitStatistics.saveFile=saveFile;
    }

    public static void run(){
        if(thread!=null&&thread.isAlive())
            return;
        thread=new Thread(new Runnable() {
            public void run() {
                while(true){
                    try {//确保这个线程不会关闭
                        int sleepTime=2*60*1000;//每2分钟写入一次文件，防止网站关闭数据丢失,不立即记录，防止性能过低
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date ntime=new Date(  );
                        Date nDay=sdf.parse( sdf.format( ntime ) );
                        //不改变不记录//确保每天运行一次
                        if(!isChing&&(ntime.getTime()-nDay.getTime())>=sleepTime*2){
                            Thread.sleep(sleepTime);
                            continue;
                        }
                        Date ltime;
                        int lsum=0;
                        int lmax=0;
                        //读取原记录
                        File file=new File(saveFile);
                        File fileParent = file.getParentFile();
                        if(!fileParent.exists()) {
                            fileParent.mkdirs();
                        }
                        if (!file.exists()) {
                            file.createNewFile();// 创建文件
                        }
                        String vs=readLastLine(file,charset);
                        if(vs==null)
                            throw new Exception(  );
                        String[] info=vs.trim().split( "\t+" );
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if(vs.equals( "" )||info.length<3){
                            appendFile(file,"#日期\t\t\tIP访问量\t最高同时在线量",charset);
                            ltime=new Date(  );
                        }else {
                            ltime=sdf.parse( info[0] );
                            lsum=Integer.parseInt( info[1] );
                            lmax=Integer.parseInt( info[2] );

                        }
                        //判断在线人数
                        int nmax=0;
                        int nsum=0;
                        for (Map.Entry<String, Date> entry : ipDates.entrySet()) {
                            nsum++;
                            if((ntime.getTime()-entry.getValue().getTime())<limit)
                                nmax++;
                        }
                        if(nmax<lmax)
                            nmax=lmax;
                        if(nsum<lsum)
                            nsum=lsum;
                        String lline;
                        if(nDay.getTime()>ltime.getTime()){//新一天
                            lline="\n"+sdf.format( ltime )+"\t\t"+nsum+"\t"+nmax
                                    +"\n"+sdf2.format( ntime )+"\t0\t0";
                            ipDates.clear();
                        }else{
                            lline="\n"+sdf2.format( ntime )+"\t"+nsum+"\t"+nmax;
                        }
                        if(!vs.equals( "" )&&info.length>2){
                            deleteLastLine(file);
                        }
                        appendFile(file,lline,charset);
                        isChing=false;
                        Thread.sleep(sleepTime);
                    }catch (Exception e) {
                        e.printStackTrace();
                        try {//防止出错后快速循环
                            Thread.sleep(2*60*1000);
                        }catch (Exception e0){
                            e0.printStackTrace();
                            errorNum++;
                            if(errorNum>100){//如果时停也一直失败，为保障性能，放弃记录
                                errorNum=0;
                                try {
                                    throw e0;
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public static String readLastLine(File file, Charset charset) throws IOException {
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                if (charset == null) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset);
                }
            }
        } catch (FileNotFoundException e) {
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e2) {
                }
            }
        }
        return null;
    }

    /**
     * 删除本文文件最后一行。
     * @param file
     */
    public static void deleteLastLine(File file){
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rwd");
            long lastLinePos = 0L;
            // 获取文件占用字节数
            long len = raf.length();
            if (len > 0L){
                // 向前走一个字节
                long pos = len - 1;
                while (pos > 0)
                {
                    pos--;//先减1可能是防止空行，最后一个字节是\n
                    // 移动指针
                    raf.seek(pos);
                    // 判断这个字节是不是回车符
                    if (raf.readByte() == '\n') {
                        lastLinePos=pos;
                        break;
                    }
                }
            }
            // 删除最后一行
            raf.setLength(lastLinePos);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void appendFile(File file, String content, Charset charset) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter
                    (new FileOutputStream (file,true), charset ));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
