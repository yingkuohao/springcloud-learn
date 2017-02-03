package com.alicp.logapp.log.api;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午4:01
 * CopyRight: taobao
 * Descrption:
 */

public class LogCoreUtils {
    public static final String EMPTY_STRING = "";
   	public static final String NEWLINE = "\r\n";

   	public static final String[] EMPTY_STRING_ARRAY = new String[0];

   	private static final String LOCAL_IP_ADDRESS = getLocalInetAddress();
   	private static final int PID = doGetCurrrentPid();

   	public static boolean isBlank(String str) {
   		int strLen;
   		if (str == null || (strLen = str.length()) == 0) {
   			return true;
   		}
   		for (int i = 0; i < strLen; i++) {
   			if ((Character.isWhitespace(str.charAt(i)) == false)) {
   				return false;
   			}
   		}
   		return true;
   	}

   	public static String checkNotNullEmpty(String value, String name) throws IllegalArgumentException {
   		if (isBlank(value)) {
   			throw new IllegalArgumentException(name + " is null or empty");
   		}
   		return value;
   	}

   	public static <T> T checkNotNull(T value, String name) throws IllegalArgumentException {
   		if (value == null) {
   			throw new IllegalArgumentException(name + " is null");
   		}
   		return value;
   	}

   	public static <T> T defaultIfNull(T value, T defaultValue) {
   		return (value == null) ? defaultValue : value;
   	}

   	public static boolean isNotBlank(String str) {
   		return !isBlank(str);
   	}

   	public static String trim(String str) {
   		return str == null ? null : str.trim();
   	}

   	public static String[] split(String str, char separatorChar) {
   		return splitWorker(str, separatorChar, false);
   	}

   	private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
   		if (str == null) {
   			return null;
   		}
   		int len = str.length();
   		if (len == 0) {
   			return EMPTY_STRING_ARRAY;
   		}
   		List<String> list = new ArrayList<String>();
   		int i = 0, start = 0;
   		boolean match = false;
   		boolean lastMatch = false;
   		while (i < len) {
   			if (str.charAt(i) == separatorChar) {
   				if (match || preserveAllTokens) {
   					list.add(str.substring(start, i));
   					match = false;
   					lastMatch = true;
   				}
   				start = ++i;
   				continue;
   			}
   			lastMatch = false;
   			match = true;
   			i++;
   		}
   		if (match || (preserveAllTokens && lastMatch)) {
   			list.add(str.substring(start, i));
   		}
   		return (String[]) list.toArray(new String[list.size()]);
   	}

   	public static StringBuilder appendWithBlankCheck(String str, String defaultValue, StringBuilder appender) {
   		if (isNotBlank(str)) {
   			appender.append(str);
   		} else {
   			appender.append(defaultValue);
   		}
   		return appender;
   	}

   	public static StringBuilder appendWithNullCheck(Object obj, String defaultValue, StringBuilder appender) {
   		if (obj != null) {
   			appender.append(obj.toString());
   		} else {
   			appender.append(defaultValue);
   		}
   		return appender;
   	}

   	/**
   	 * 追加日志，同时过滤字符串中的换行为空格，避免导致日志格式解析错误
   	 */
   	public static StringBuilder appendLog(String str, StringBuilder appender, char delimiter) {
   		if (str != null) {
   			int len = str.length();
   			appender.ensureCapacity(appender.length() + len);
   			for (int i = 0; i < len; i++) {
   				char c = str.charAt(i);
   				if (c == '\n' || c == '\r' || c == delimiter) {
   					// 用 appender.append(str, start, len) 批量 append 实质也是一个字符一个字符拷贝
   					// 因此此处还是用土办法
   					c = ' ';
   				}
   				appender.append(c);
   			}
   		}
   		return appender;
   	}

   	/**
   	 * 过滤字符串中的换行为空格，避免导致日志格式解析错误
   	 * @param str
   	 * @return
   	 */
   	public static String filterInvalidCharacters(String str) {
   		StringBuilder appender = new StringBuilder(str.length());
   		return appendLog(str, appender, '|').toString();
   	}

   	/**
   	 * 对字符串生成摘要，目前使用 CRC32 算法
   	 * @param str
   	 * @return 摘要后的字符串
   	 */
   	public static String digest(String str) {
   		CRC32 crc = new CRC32();
   		crc.update(str.getBytes());
   		return Long.toHexString(crc.getValue());
   	}

       // 自身日志的时间标签格式化器
   	private static final ThreadLocal<FastDateFormat> dateFmt = new ThreadLocal<FastDateFormat>() {
   		@Override
   		protected FastDateFormat initialValue() {
   			return new FastDateFormat();
   		}
   	};

   	/**
   	 * 时间格式化成 yyyy-MM-dd HH:mm:ss.SSS
   	 * @param timestamp
   	 * @return
   	 */
   	public static String formatTime(long timestamp) {
   		return dateFmt.get().format(timestamp);
   	}

   	/**
   	 * 从 URL 解析出 URI，如果解析不到 URI，就用域名，例如：
   	 * <pre>
   	 * "http://shop66155774.taobao.com/shop/view_shop.htm" => "/shop/view_shop.htm"
   	 * "http://shop66155774.taobao.com/shop/" => "/shop"
   	 * "http://www.taobao.com" => "www.taobao.com"
   	 * "http://www.taobao.com/" => "www.taobao.com"
   	 * "https://www.alipay.com/" => "www.alipay.com"
   	 * "not_url_entrance" => "not_url_entrance"
   	 * </pre>
   	 * @param url
   	 * @return
   	 */
   	public static String getUriFromUrl(String url) {
   		int start;
   		final int len = url.length();
   		if (len <= 7) {
   			return url;
   		}
   		if (url.startsWith("http://")) {
   			start = 7;
   		} else if ((start = url.indexOf("://")) != -1) {
   			start += 3;
   		} else {
   			start = 0;
   		}

   		// 去掉末尾的 ‘/’
   		final int end = (url.charAt(len - 1) == '/') ? (len - 1) : len;
   		final int istart = url.indexOf('/', start);
   		if (istart >= 0 && istart < end) {
   			return url.substring(istart, end);
   		}
   		return url.substring(start, end);
   	}

       private static String getLocalInetAddress() {
   		String localIp = LogCoreUtils.getSystemProperty("EAGLEEYE.LOCAL.IP");
   		if (LogCoreUtils.isNotBlank(localIp) && localIp.length() >= 7
   				&& Character.isDigit(localIp.charAt(0))
   				&& Character.isDigit(localIp.charAt(localIp.length() - 1))) {
   			return localIp;
   		}
           try {
               Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
               InetAddress address = null;
               while (interfaces.hasMoreElements()) {
                   NetworkInterface ni = interfaces.nextElement();
                   Enumeration<InetAddress> addresses = ni.getInetAddresses();
                   while (addresses.hasMoreElements()) {
                       address = addresses.nextElement();
                       if (!address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
                           return address.getHostAddress();
                       }
                   }
               }
           } catch (Throwable t) {
           }
           return "127.0.0.1";
       }

       public static String getLocalAddress() {
       	return LOCAL_IP_ADDRESS;
       }


   	/**
   	 * get current pid,max pid 32 bit systems 32768, for 64 bit 4194304
   	 * http://unix.stackexchange.com/questions/16883/what-is-the-maximum-value-of-the-pid-of-a-process
   	 *
   	 * http://stackoverflow.com/questions/35842/how-can-a-java-program-get-its-own-process-id
   	 * @return
   	 */
   	private static int doGetCurrrentPid() {
   		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
   		String name = runtime.getName();
   		int pid;
   		try {
   			pid = Integer.parseInt(name.substring(0, name.indexOf('@')));
   		} catch (Exception e) {
   			pid = 0;
   		}
   		return pid;
   	}

   	public static int getCurrrentPid() {
   		return PID;
   	}

   	public static String getSystemProperty(String key) {
   		try {
   			return System.getProperty(key);
   		} catch (Exception e) {
   			return null;
   		}
   	}

   	public static long getSystemPropertyForLong(String key, long defaultValue) {
   		try {
   			return Long.parseLong(System.getProperty(key));
   		} catch (Exception e) {
   			return defaultValue;
   		}
   	}
}
