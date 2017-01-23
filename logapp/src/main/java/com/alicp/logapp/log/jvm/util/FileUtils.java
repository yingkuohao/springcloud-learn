package com.alicp.logapp.log.jvm.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:29
 * CopyRight: taobao
 * Descrption:
 */

public class FileUtils {
    private final static Log LOG                   = LogFactory.getLog(FileUtils.class);

       private final static String jmonitorConfigLogHome = System.getProperty("user.home") + "/logs/jmonitor.log";

       static {
           // 初始化并清空日志
           FileUtils.saveSelfLog("start:" + new Date(), false);
       }

       public static void appendToLog(String log) {
           FileUtils.saveSelfLog(new Date() + ":" + log, true);
       }

       public static void saveSelfLog(String log, boolean append) {
           try {
               writeStringToFile(new File(jmonitorConfigLogHome), log + JmonitorConstants.newLine,
                                 Charset.forName("UTF-8"), append);
           } catch (Exception e) {
               LOG.error(e.getMessage(), e);
           }
       }

       public static void writeStringToFile(File file, String data, Charset encoding, boolean append) throws IOException {
           OutputStream out = null;
           try {
               out = openOutputStream(file, append);
               write(data, out, encoding);
               out.close(); // don't swallow close Exception if copy completes normally
           } finally {
               closeQuietly(out);
           }
       }

       public static void closeQuietly(OutputStream output) {
           closeQuietly((Closeable) output);
       }

       public static void closeQuietly(Closeable closeable) {
           try {
               if (closeable != null) {
                   closeable.close();
               }
           } catch (IOException ioe) {
               // ignore
           }
       }

       public static void write(String data, OutputStream output, Charset encoding) throws IOException {
           if (data != null) {
               output.write(data.getBytes(encoding));
           }
       }

       /**
        * Opens a {@link FileOutputStream} for the specified file, checking and creating the parent directory if it does
        * not exist.
        * <p>
        * At the end of the method either the stream will be successfully opened, or an exception will have been thrown.
        * <p>
        * The parent directory will be created if it does not exist. The file will be created if it does not exist. An
        * exception is thrown if the file object exists but is a directory. An exception is thrown if the file exists but
        * cannot be written to. An exception is thrown if the parent directory cannot be created.
        *
        * @param file the file to open for output, must not be {@code null}
        * @param append if {@code true}, then bytes will be added to the end of the file rather than overwriting
        * @return a new {@link FileOutputStream} for the specified file
        * @throws IOException if the file object is a directory
        * @throws IOException if the file cannot be written to
        * @throws IOException if a parent directory needs creating but that fails
        * @since 2.1
        */
       public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
           if (file.exists()) {
               if (file.isDirectory()) {
                   throw new IOException("File '" + file + "' exists but is a directory");
               }
               if (file.canWrite() == false) {
                   throw new IOException("File '" + file + "' cannot be written to");
               }
           } else {
               File parent = file.getParentFile();
               if (parent != null) {
                   if (!parent.mkdirs() && !parent.isDirectory()) {
                       throw new IOException("Directory '" + parent + "' could not be created");
                   }
               }
           }
           return new FileOutputStream(file, append);
       }

   }

