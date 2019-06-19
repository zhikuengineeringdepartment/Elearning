package com.zhiku.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.Date;
import java.util.regex.Pattern;

//TODO 修改Openoffice的服务开启方式，目前无法自动开启服务，整个逻辑建立在默认服务开启的情况下运行
public class Office2PDF {
    //可以转化的格式类型
    public static String MATCHTYPE = "docx|doc|xlsx|xls|pptx|ppt|txt|pdf";

    //不同系统下openoffice的安装路径
    public static String WINDOWS_PATH = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe";
    public static String LINUX_PATH = "/opt/openoffice4/program/soffice";
    public static String MAC_PATH = "";

    public static String LOCAL_IP = "127.0.0.1";
    public static String REMOTE_IP = "123.206.229.207";

    public static int DEFAULT_PAGE = 10;


    //openoffice的一个进程
    public static Process p = null;
    //openoffice的一个连接
//	public static OpenOfficeConnection connection = null;

    /**
     * 使Office2003-2007全 部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件<br>
     *
     * @param inputFilePath
     *            源文件路径，如："e:/test.docx"
     * @param outputFilePath
     *            目标文件路径，如："e:/test_docx.pdf"
     * @return
     * @throws ConnectException
     */
    public static boolean openOfficeToPDF(String inputFilePath, String outputFilePath) throws ConnectException {
        return office2pdf(inputFilePath, outputFilePath);
    }

    /**
     * 根据操作系统的名称，获取OpenOffice的安装目录<br>
     * 如我的OpenOffice安装在：C:/Program Files (x86)/OpenOffice 4/program<br>
     *
     * @return OpenOffice的安装目录
     */
    public static String getOfficeHome() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            return LINUX_PATH;
        } else if (Pattern.matches("Windows.*", osName)) {
            return WINDOWS_PATH;
        } else if (Pattern.matches("Mac.*", osName)) {
            return MAC_PATH;
        }
        return null;
    }


    /**
     * 启动openoffice服务，并使用8100端口
     * @return
     */
    public static boolean startOpenOffice() {
        boolean start = false;

        Runtime rt = Runtime.getRuntime();

        try
        {
            //执行的文件的位置
            //启动命令soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
            String[] instruction = new String[]{getOfficeHome(), "-headless", "-accept=\"socket,host=127.0.0.1,port=8100;urp;\"", "-nofirststartwizard"};
            p = rt.exec(instruction);
            start = true;
        }catch (Exception e) {
            start = false;
            e.printStackTrace();
        }

        return start;
    }

    /**
     * 判断是否是默认可以转pdf的文件后缀
     * @param inputFilePath 文件路径
     * @return 满足格式的进行转化
     */
    public static boolean isConvert(String inputFilePath){
        boolean can = false;
        String type = getPostfix(inputFilePath);
        if(type.matches(MATCHTYPE)){
            can = true;
        }
        return can;
    }


    /**
     * 获取输出文件
     *
     * @param inputFilePath
     * @return
     */
    public static String getOutputFilePath(String inputFilePath) {
        String file_name = inputFilePath.substring(inputFilePath.lastIndexOf(java.io.File.separator)+1,inputFilePath.lastIndexOf("."));
        String outputFilePath = "/var/zhiku/" + file_name + ".pdf";
        return outputFilePath;
    }


    /**
     * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"<br>
     *
     * @param inputFilePath
     * @return
     */
    public static String getPostfix(String inputFilePath) {
        return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
    }



    /**
     * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件<br>
     *
     * @param inputFilePath
     *            源文件路径，如："e:/test.docx"
     * @param outputFilePath
     *            目标文件路径，如："e:/test_docx.pdf"
     * @return
     * @throws ConnectException
     */
    public static boolean office2pdf(String inputFilePath, String outputFilePath) throws ConnectException {
        boolean flag = false;

        //检查输入文件是否存在
        File inputFile = new File(inputFilePath);
        if(!inputFile.exists()){
            return false;
        }

        //检查输出文件父目录以及文件本身是否存在
        File outputFile = new File(outputFilePath);
        if(!outputFile.getParentFile().exists()){
            outputFile.getParentFile().mkdirs();
        }else if (outputFile.exists()){
            return true;
        }



        // 连接OpenOffice
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(LOCAL_IP,8100);
        connection.connect();
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);

      long begin_time = new Date().getTime();
        converter.convert(inputFile, outputFile);
        flag = true;
      long end_time = new Date().getTime();
    	System.out.println("文件转换耗时：[" + (end_time - begin_time) + "]ms");
        connection.disconnect();
        return flag;
    }

    public static void writeOut(File file, OutputStream out,boolean admin) throws Exception{
        System.out.println(file.getAbsolutePath());
        PDDocument document = PDDocument.load(file, MemoryUsageSetting.setupTempFileOnly());
        document.getPages();
        int endPage = 0;
        if(!admin){
            endPage = document.getNumberOfPages()>DEFAULT_PAGE?DEFAULT_PAGE:document.getNumberOfPages();
        }else{
            endPage = document.getNumberOfPages();
        }

        PDDocument pd = new PDDocument();
        for(int i = 0;i<endPage;i++) {
            pd.addPage(document.getPage(i));
        }
        pd.save(out);
        pd.close();
        document.close();
    }


    public static void main(String[] args) throws ConnectException {
        String inputPath = "E:"+java.io.File.separator + "test.docx";
        if(isConvert(inputPath)){
            openOfficeToPDF(inputPath, getOutputFilePath(inputPath));
        }else{
            System.out.println("不支持预览");
        }
    }
}
