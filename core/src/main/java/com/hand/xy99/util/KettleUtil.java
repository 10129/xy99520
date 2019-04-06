package com.hand.xy99.util;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.gui.AreaOwner;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.core.gui.SwingGC;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.JobPainter;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.TransPainter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author xuan
 * @since 1.0.0
 */
public class KettleUtil {

    public static void showJobImage(String in, String out) throws Exception {
        JobMeta jobMeta = new JobMeta(in, null);
        BufferedImage image = generateJobImage(jobMeta);
        saveImage(image, out);
    }

    public static void showTransformationImage(String in, String out) throws Exception {
        KettleEnvironment.init();
        Repository rep = null;
        TransMeta transMeta = new TransMeta(in, rep);
        BufferedImage image = generateTransformationImage(transMeta);
        saveImage(image, out);
    }

    private static BufferedImage generateJobImage(JobMeta jobMeta) throws Exception {
        float magnification = 2f;
        Point maximum = jobMeta.getMaximum();
        maximum.multiply(magnification);

        SwingGC gc = new SwingGC(null, maximum, 32, 0, 0);
        JobPainter jobPainter =
                new JobPainter(
                        gc, jobMeta, maximum, null, null, null, null, null, new ArrayList<AreaOwner>(),
                        new ArrayList<>(), 32, 2, 0, 0, true, "Arial", 10);
        jobPainter.setMagnification(magnification);
        jobPainter.drawJob();

        return (BufferedImage) gc.getImage();
    }

    private static BufferedImage generateTransformationImage(TransMeta transMeta) throws Exception {
        float magnification = 1.0f;
        Point maximum = transMeta.getMaximum();
        maximum.multiply(magnification);

        SwingGC gc = new SwingGC(null, maximum, 32, 0, 0);
        TransPainter transPainter =
                new TransPainter(
                        gc, transMeta, maximum, null, null, null, null, null, new ArrayList<AreaOwner>(),
                        new ArrayList<>(), 32, 1, 0, 0, true, "Arial", 10);
        transPainter.setMagnification(magnification);
        transPainter.buildTransformationImage();

        return (BufferedImage) gc.getImage();
    }

    private static void saveImage(BufferedImage image, String fName) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fName));
            ImageIO.write(image, "png", fos);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
    /**
     * java调用kettle方法
     * @param jobPath
     * @param para
     * @throws Exception
     */
    public static void runJob(String jobPath,Map<String, String> para) {
        try {
            KettleEnvironment.init();
            // jobname 是Job脚本的路径及名称
            JobMeta jobMeta = new JobMeta(jobPath, null);
            Job job = new Job(null, jobMeta);
            // 向Job 脚本传递参数，脚本中获取参数值：${参数名}
            for (Map.Entry<String, String> entry : para.entrySet()) {
                //设置命名参数
                job.setVariable(entry.getKey(), entry.getValue());
            }
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                throw new Exception(
                        "There are errors during job exception!(执行job发生异常)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java调用kettle方法
     * @param transFile
     * @param para
     * @throws Exception
     */
    public static void runTrans(String transFile, Map<String, String> para) throws Exception {
        KettleEnvironment.init();
        EnvUtil.environmentInit();
        TransMeta tm = new TransMeta(transFile);
        Trans trans = new Trans(tm);
        for (Map.Entry<String, String> entry : para.entrySet()) {
            //设置命名参数
            trans.setVariable(entry.getKey(), entry.getValue());
        }
        trans.execute(null);
        trans.waitUntilFinished();
        // 抛出异常
        if (trans.getErrors() > 0) {
            throw new Exception("There ("+transFile+") are errors during transformation exception!");
        }
    }


//    public  static String kettle(){
//        StepPluginType.getInstance().getPluginFolders().
//                add(new PluginFolder("C:\\ProgramFiles\\pdi-ce-8.0.0.0-28\\data-integration\\plugins\\pdi-xml-plugin", false, true));
//        KettleEnvironment.init(true);
//        EnvUtil.environmentInit();
//        String basePath= (String) this.getClass().getResource("/").getFile()
//                .replace("hap","oos")+"com/kettle/oos_md_erp_products.ktr";
//        TransMeta transMeta = new TransMeta(basePath);
////            List<DatabaseMeta>  dmlist=new ArrayList<>();
////            DatabaseMeta mysqlDataMeta=new DatabaseMeta("oos","mysql","jdbc","10.63.29.111","nos_dev","3306","nos_dev","Nos_dev01");
////            DatabaseMeta oracleDataMeta = new DatabaseMeta("erp","Oracle","Native","10.63.8.34","YUAT","1521","oos_jdbc","oos_jdbc1");
////            dmlist.add(mysqlDataMeta);
////            dmlist.add(oracleDataMeta);
////            transMeta.setDatabases(dmlist);
//        List<DatabaseMeta> dmlist=transMeta.getDatabases();
//        for(DatabaseMeta dm : dmlist)
//        {
//            String connection_name=dm.getName().trim();
//
//            if(connection_name.equals("erp"))
//            {
//                dm.setHostname("10.63.8.34"); //连接地址
//                dm.setDBName("YUAT"); //数据库名称
//                dm.setDatabaseType("oracle");
//                dm.setDBPort("1521"); //端口
//                dm.setUsername("oos_jdbc"); //用户
//                dm.setPassword("oos_jdbc1"); //密码
//
//            }
//            if(connection_name.equals("oos"))
//            {
//                dm.setHostname("10.63.29.111"); //连接地址
//                dm.setDBName("nos_dev"); //数据库名称
//                dm.setDatabaseType("mysql");
//                dm.setDBPort("3306"); //端口
//                dm.setUsername("nos_dev"); //用户
//                dm.setPassword("Nos_dev01"); //密码
//            }
//
//        }
//
//        Trans trans = new Trans(transMeta);//创建tran对象
//
//        //trans.setParameterValue("FILE_PATH", excelPath);
//        trans.prepareExecution(null);
//        trans.waitUntilFinished();
//    }

}
