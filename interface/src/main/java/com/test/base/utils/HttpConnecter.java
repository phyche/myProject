package com.test.base.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Http protocol connector.
 * 
 */
public class HttpConnecter {
	/**
	 * 30s超时
	 */
	private static final int TIME = 30000;
	
	/**
	 * 日志记录工具
	 */
	protected static final Logger LogDebugger = Logger.getLogger(HttpConnecter.class);

	/**
	 * Get method.
	 * 
	 * @param uri
	 *            the uri of http request
	 * @return the string returned.
	 * @throws ClientProtocolException
	 *             ClientProtocolException
	 * @throws IOException
	 *             IOException
	 */
	public static String get(String uri) throws ClientProtocolException,
			IOException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时

		HttpGet httpGet = new HttpGet(uri);
		setHeader(httpGet);

		HttpResponse httpResponse = httpClient.execute(httpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();

		if (statusCode >= 200 && statusCode < 400) {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), "UTF-8"));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				stringBuilder.append(s);
			}
			reader.close();
			LogDebugger.info("HTTP GET:" + uri.toString());
			LogDebugger.info("Response:" + stringBuilder.toString());
			return stringBuilder.toString();
		}
		return null;
	}

	/**
	 * Post method.
	 * 
	 * @param uri
	 *            the uri of http request
	 * @param formparams
	 *            List<NameValuePair>
	 * @return the string returned.
	 * @throws ClientProtocolException
	 *             ClientProtocolException
	 * @throws IOException
	 *             IOException
	 */
	public static String post(String uri, List<NameValuePair> formparams)
			throws ClientProtocolException, IOException {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(uri);
		setHeader(httpPost);
		httpPost.setEntity(entity);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 400) {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), "UTF-8"));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				stringBuilder.append(s);
			}
			reader.close();
			LogDebugger.info("HTTP POST:" + uri.toString());
			LogDebugger.info("Response:" + stringBuilder.toString());
			return stringBuilder.toString();
		}
		return null;
	}
	
	public static String postMethod(String uri, List<BasicNameValuePair> formparams)
			throws ClientProtocolException, IOException {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(uri);
		setHeader(httpPost);
		httpPost.setEntity(entity);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 400) {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), "UTF-8"));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				stringBuilder.append(s);
			}
			reader.close();
			LogDebugger.info("HTTP POST:" + uri.toString());
			LogDebugger.info("Response:" + XMLUtils.toDeString(stringBuilder.toString()));
			return stringBuilder.toString();
		}
		return null;
	}
	/**
	 * 以post方式请求,参数类型为 json格式
	 * @param uri
	 * @param paramJson
	 * @return
	 */
	public static JSONObject postMethod(String uri, String paramJson){
		JSONObject res=null;
		HttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter(
				 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
		client.getParams().setIntParameter(
				 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时
		HttpPost post=new HttpPost(uri);
		try{
			StringEntity s=new StringEntity(paramJson);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse response=client.execute(post);
			String result= EntityUtils.toString(response.getEntity());
			res= JSONObject.parseObject(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String args[]){
		String pwd="";
		MD5 m=new MD5();
		try {
			pwd= m.getMD5ofStr("zla1ls9k4jfa_87");
			//MessageDigest md5= MessageDigest.getInstance("MD5");
			//md5.digest("zla1ls9k4jfa_87".getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String op="accountlogin";
		String appid="100020";
		Long ct=new Date().getTime(); 
		String key="BC1590DDE33749DA7F9B0CE1BBE3E2B2";
		String token="";
		String sign=m.getMD5ofStr(ct+op+appid+token+key);
		JSONObject param=new JSONObject();
		param.put("op", "accountlogin");
		param.put("appid", "100020");
		param.put("ct", new Date().getTime());
		param.put("token", token);
		param.put("sign", sign);
		param.put("account", "weconex2");
		param.put("password", pwd);
		JSONObject res=postMethod("http://114.215.208.62:11000/servermall/smodule/index.php", param.toJSONString());
		System.out.println(JSONObject.toJSONString(res));
	}
	
	public static String postMethod(String uri, List<BasicNameValuePair> formparams, String charset)
			throws ClientProtocolException, IOException {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				charset);
		HttpPost httpPost = new HttpPost(uri);
		setHeader(httpPost);
		httpPost.setEntity(entity);
		HttpResponse httpResponse = httpClient.execute(httpPost);

		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 400) {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), charset));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				stringBuilder.append(s);
			}
			reader.close();
			LogDebugger.info("HTTP POST:" + uri.toString());
			LogDebugger.info("Response:" + stringBuilder.toString());
			return stringBuilder.toString();
		}
		return null;
	}

	public static String post(String actionUrl, List<NameValuePair> formparams,
			List<File> attachments) throws ClientProtocolException, IOException {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		setHeader(conn);
		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		if (formparams != null) {
			for (NameValuePair entry : formparams) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getName() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET
						+ LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		if (attachments != null) {
			int i = 0;
			for (File file : attachments) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file"
						+ (i++) + "\"; filename=\"" + file.getName() + "\""
						+ LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		}

		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		// 得到响应码
		int statusCode = conn.getResponseCode();
		InputStream in = null;
		if (statusCode >= 200 && statusCode < 400) {
			in = conn.getInputStream();
			int ch;
			StringBuilder stringBuilder = new StringBuilder();
			while ((ch = in.read()) != -1) {
				stringBuilder.append((char) ch);
			}
			LogDebugger.info("HTTP POST:" + uri.toString());
			LogDebugger.info("Response:" + stringBuilder.toString());
			return stringBuilder.toString();
		}
		return in == null ? null : in.toString();
	}

	public static String post_bak(String uri, List<NameValuePair> formparams,
			List<File> attachments) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
		httpClient.getParams().setIntParameter(
				 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时
		@SuppressWarnings("deprecation")
        FileEntity entity = new FileEntity(attachments.get(0),
				"binary/octet-stream");
		HttpPost httpPost = new HttpPost(uri);
		setHeader(httpPost);
		httpPost.setEntity(entity);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 400) {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), "UTF-8"));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				stringBuilder.append(s);
			}
			reader.close();
			LogDebugger.info("HTTP POST:" + uri.toString());
			LogDebugger.info("Response:" + stringBuilder.toString());
			return stringBuilder.toString();
		}
		return null;
	}

	/**
	 * Download file from server.
	 * 
	 * @param file
	 *            File
	 * @param uri
	 *            the uri of http request
	 * @return the result. true if download success.
	 * @throws IOException
	 *             IOException
	 */
	public static boolean download(File file, String uri) throws IOException {
		if (file.exists() && file.isDirectory()) {
			return false;
		}
		boolean flag = false;
		file.getParentFile().mkdirs();
		File tmpFile = new File(file.getAbsolutePath() + ".tmp");
		InputStream is = null;
		BufferedOutputStream bos = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
					 HttpConnectionParams.SO_TIMEOUT, TIME); // 超时设置
			httpClient.getParams().setIntParameter(
					 HttpConnectionParams.CONNECTION_TIMEOUT, TIME);// 连接超时
			// ContentEncodingHttpClient httpClient = new
			// ContentEncodingHttpClient();
			HttpGet httpGet = new HttpGet(uri);
			setHeader(httpGet);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				tmpFile.createNewFile();
				is = httpResponse.getEntity().getContent();
				bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
				byte[] buffer = new byte[4096];
				int i = is.read(buffer);
				while (i != -1) {
					bos.write(buffer, 0, i);
					i = is.read(buffer);
				}
				tmpFile.renameTo(file);
				flag = true;
			}
		} catch (IOException ioe) {
			LogDebugger.error(ioe.getMessage());
			throw ioe;
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (is != null) {
				is.close();
			}
		}

		return flag;
	}

	private static void setHeader(HttpRequestBase httpGetOrPost) {

	}

	private static void setHeader(HttpURLConnection httpURLConnection) {

	}

}
