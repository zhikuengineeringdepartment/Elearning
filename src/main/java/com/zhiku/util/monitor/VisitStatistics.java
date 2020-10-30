package com.zhiku.util.monitor;

import com.zhiku.entity.mysql.AccessRecord;
import com.zhiku.service.DataStatisticsService;
import com.zhiku.util.SmallTools;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class VisitStatistics {
    //储存数据
    private static final Map<String, Date> ipDates=new ConcurrentHashMap<>(  );
    private static final List<AccessRecord> acEnds=new CopyOnWriteArrayList<>(  );//记录无对应进入页面的离开页面请求
    private static final Map<String, AccessRecord> ipUriDatas =new ConcurrentHashMap<>();//累计停留时间记录；ip+uri:AccessRecord

    //提供服务对象
    private volatile static DataStatisticsService dataStatisticsService;
    private volatile static ScheduledExecutorService scheduledExecutorService;
    private volatile static ExecutorService executorServiceDo;
    private static final Runnable task;

    //配置参数
    private static String saveFile;
    private static final Charset charset=StandardCharsets.UTF_8;
    private static final int limit=60*60*1000;//无法判断用户何时下线，以60分钟不发请求为下线
    private static final int sleepTime=2*60*1000;//每2分钟写入一次文件，防止网站关闭数据丢失,不立即记录，防止性能过低
    private static final int MAX_changeNumber=100000;//最多在内存中储存数目，超过此数，sleepTime时间不到也转储数据库
    private static final int MAX_taskNumber=100;//添加写入数据库的最大任务大小，防止任务执行出现问题，一直添加任务占用过多内存，超过的任务数的任务暂时舍弃掉//TODO：待添加通知功能
    //计数变量
    private volatile static boolean isChing;
    private volatile static int changeNumber=0;
    private volatile static AtomicInteger taskNumber=new AtomicInteger(0);

    private volatile static List<AccessRecord> acEndsT;
    private volatile static Map<String, AccessRecord> ipUriDatasT;
    private volatile static int changeNumberT;
    static {
        task= ()-> {
            try {
                recordToDb( ipUriDatasT, acEndsT, changeNumberT );
            }finally {
                taskNumber.getAndAdd( -1 );
            }
        };
    }

    public static synchronized void add(String ip){
        ipDates.put( ip,new Date(  ) );
        isChing=true;
    }

    static void setSaveFile(String saveFile){
        VisitStatistics.saveFile=saveFile;
    }

    public static void run(){
        if(scheduledExecutorService!=null)
            return;
        scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                VisitStatistics::recordToDb,
                0,
                sleepTime,
                TimeUnit.MILLISECONDS);
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

    /*原统计方案-在文件中记录访问人数*/
    private synchronized static void recordToTxt() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ntime=new Date(  );
        Date nDay=sdf.parse( sdf.format( ntime ) );
        if(!isChing&&(ntime.getTime()-nDay.getTime())>=sleepTime*2){
            Thread.sleep(sleepTime);
            return;
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
            ltime= SmallTools.toDay( info[0] );
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
    }

    /* 新统计-在数据库中记录访问停留时间
     * Map<String, AccessRecord> ipUriDatas=<ip+uri,(ip,uri,day)>
     * 只保证ip+uri唯一，如果day不同，都算做前一天的
     */

    /*添加访问开始记录*/
    public synchronized static void addStart(String ip,String uri,Date time) throws ParseException {
        AccessRecord accessRecord=ipUriDatas.get( ip+uri );
        if(accessRecord==null){
            accessRecord=new AccessRecord();
            accessRecord.setIp( ip );
            accessRecord.setUri( uri );
            accessRecord.setDate( SmallTools.toDay( time ) );
            accessRecord.setNumber( 1 );
            accessRecord.setStayTime( 0 );
            accessRecord.setLatestTime( time );
            ipUriDatas.put(ip+uri, accessRecord );
        }else{
            accessRecord.setNumber( accessRecord.getNumber()+1 );
            accessRecord.setLatestTime( time );
        }
        if(++changeNumber>MAX_changeNumber){
            recordToDb();
        }
    }

    /*添加离开页面记录*/
    public synchronized static void addEnd(String ip,String uri,Date time) throws ParseException {
        AccessRecord accessRecord=ipUriDatas.get( ip+uri );
        if(accessRecord==null){
            accessRecord=new AccessRecord();
            accessRecord.setIp( ip );
            accessRecord.setUri( uri );
            accessRecord.setDate( SmallTools.toDay( time ) );
            accessRecord.setLatestTime( new Date(  ) );//记录加入时间
            acEnds.add( accessRecord );
        }else {
            if(accessRecord.getLatestTime()!=null){
                accessRecord.setStayTime( accessRecord.getStayTime() +
                        (int) ((time.getTime() - accessRecord.getLatestTime().getTime()) / 1000) );
                accessRecord.setLatestTime( null );
            }
        }
        if(++changeNumber>MAX_changeNumber){
            recordToDb();
        }
    }

    public synchronized static void setDataStatisticsService(DataStatisticsService dataStatisticsService){
        VisitStatistics.dataStatisticsService=dataStatisticsService;
    }

    /*存入数据库*/
    private synchronized static void recordToDb(){
        if(changeNumber<=0||dataStatisticsService==null||(ipUriDatas.isEmpty()&&acEnds.isEmpty()))
            return;
        if(executorServiceDo==null) executorServiceDo=Executors.newSingleThreadExecutor();
        ipUriDatasT=clone( ipUriDatas );
        acEndsT=clone( acEnds );
        changeNumberT=changeNumber;
        if( taskNumber.getAndAdd(1) <MAX_taskNumber ){
            //无法通过线程池ExecutorService获取当前排队的任务数，只能额外记录加入任务数taskNumber//TODO: try{}finally{}有线程死亡不执行的风险，待修改
            executorServiceDo.submit( task );
        }else{
            taskNumber.getAndAdd( -1 );
        }
        ipUriDatas.clear();
        acEnds.clear();
        changeNumber=0;
    }

    private static void recordToDb(Map<String, AccessRecord> ipUriDatas,List<AccessRecord> acEnds,int changeNumber){
        if(changeNumber<=0||dataStatisticsService==null||(ipUriDatas.isEmpty()&&acEnds.isEmpty()))
            return;

        //查询有关记录
        List<AccessRecord> forSelect = new LinkedList<>(ipUriDatas.values());
        forSelect.addAll( acEnds );
        List<AccessRecord> accessRecords=dataStatisticsService.selectByIpUriDateAll( forSelect );
        Map<String,AccessRecord> mapARs=new HashMap<>();
        for(AccessRecord accessRecord:accessRecords){
            mapARs.put( accessRecord.getIp()+accessRecord.getUri(),accessRecord );
        }
        //先记录离开页面
        for(AccessRecord accessRecord:acEnds){
            AccessRecord oldAR=mapARs.get(accessRecord.getIp()+accessRecord.getUri());
            if(oldAR==null||oldAR.getLatestTime()==null)
                continue;
            oldAR.setStayTime(  oldAR.getStayTime() +
                    (int) ((accessRecord.getLatestTime().getTime() - oldAR.getLatestTime().getTime()) / 1000) );
            oldAR.setLatestTime( null );
        }
        //记录其他访问
        for(AccessRecord accessRecord:ipUriDatas.values()){
            AccessRecord oldAR=mapARs.get(accessRecord.getIp()+accessRecord.getUri());
            if(oldAR==null){
                //没有，添加
                accessRecords.add( accessRecord );
            }else{
                //有,更新
                oldAR.setNumber( oldAR.getNumber()+accessRecord.getNumber() );
                oldAR.setLatestTime( accessRecord.getLatestTime() );
            }
        }
        //更新
        if(!accessRecords.isEmpty()){
            dataStatisticsService.replaceAll( accessRecords );
        }
    }

    private static Map<String, AccessRecord> clone(Map<String, AccessRecord> map){
        Map<String, AccessRecord> newMap=new HashMap<>();
        for (Map.Entry<String, AccessRecord> entry: map.entrySet()) {
            newMap.put( entry.getKey(),new AccessRecord(entry.getValue()) );
        }
        return newMap;
    }

    private static List<AccessRecord> clone(List<AccessRecord> list){
        List<AccessRecord> newList=new ArrayList<>();
        for (AccessRecord entry: list) {
            newList.add( new AccessRecord(entry) );
        }
        return newList;
    }

//    private static Object clone(Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        Class cla=object.getClass();
//        //调用Class类的getConstructor()获得一个带参构造器对象
//        Constructor constructor =  cla.getConstructor(object.getClass());
//        //调用Constructor的newInstance()方法获取一个实例
//        Object target = constructor.newInstance(object);
//        return target;
//    }

}
