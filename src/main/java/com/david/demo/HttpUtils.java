package com.david.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * Created by albertc on 2016/1/7.
 */
public class HttpUtils {

	private final static Logger logger = Logger.getLogger(HttpUtils.class);

	private volatile static DefaultHttpClient httpClient = null;

	private static Object syncObj = new Object();

	public static String httpGet(String url) {

		return httpGet(url, new HashMap<String, String>());
	}

	public static String httpGet(String url, Map<String, String> queryParams) {

		String queryStr = buildQueryString(queryParams, "utf-8");

		if (queryStr != null) {
			url = url + "?" + queryStr;
		}

		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;

		try {
			response = getHttpClient().execute(httpget);
			return getResponseStr(response);
		} catch (Exception ex) {
			throw new RuntimeException("请求地址" + url + "出错:" + ex.getMessage(), ex);
		} finally {
			httpget.releaseConnection();
		}
	}

	public static String httpPostNoBody(String url, Map<String, String> queryParams) {
		return _httpPost(url, queryParams, null, null);
	}

	public static String httpPost(String url, Map<String, String> formParams) {
		return _httpPost(url, null, formParams, null);
	}

	public static String httpPost(String url, Map<String, String> queryParams, String body) {
		return _httpPost(url, queryParams, null, body);
	}

	public static String httpPost(String url, Map<String, String> queryParams, String body, String charSet) {
		return _httpPost(url, queryParams, null, body, charSet);
	}

	public static String httpPost(String url, String body) {
		return _httpPost(url, null, null, body);
	}

	public static String httpPost(String url, String body, String charSet) {
		return _httpPost(url, null, null, body, charSet);
	}

	private static String _httpPost(String url, Map<String, String> queryParams, Map<String, String> formParams,
			String body) {
		return _httpPost(url, queryParams, formParams, body, "utf-8");
	}

	/**
	 * post请求
	 *
	 * @param url
	 *            请求的地址,not null
	 * @param queryParams
	 *            查询参数，位于url的“?”之后的参数
	 * @param formParams
	 *            form表单数据
	 * @param body
	 *            body数据,formParams与body不可同时有值
	 * @param charset
	 *            编码格式,传入空时按照utf-8解析
	 * @return
	 */
	private static String _httpPost(String url, Map<String, String> queryParams, Map<String, String> formParams,
			String body, String charset) {

		if (charset == null) {
			charset = "utf-8";
		}

		String queryStr = buildQueryString(queryParams, charset);
		if (queryStr != null) {
			url = url + "?" + queryStr;
		}

		HttpPost post = new HttpPost(url);
		List<NameValuePair> list = toNameValuePairs(formParams);

		if (list != null && list.isEmpty() == false) {
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setEntity(new UrlEncodedFormEntity(list, Charset.forName(charset)));
		} else if (body != null) {
			post.setEntity(new StringEntity(body, Charset.forName(charset)));
		}

		HttpResponse responseBody = null;

		try {
			responseBody = getHttpClient().execute(post);
			if (isSuccess(responseBody) == false) {
				throw new RuntimeException("请求地址" + url + "出错:" + responseBody.getStatusLine().getReasonPhrase());
			}

			return getResponseStr(responseBody, charset);
		} catch (Exception ex) {
			throw new RuntimeException("请求地址" + url + "出错:" + ex.getMessage(), ex);
		} finally {
			post.releaseConnection();
		}
	}

	private static boolean isSuccess(HttpResponse responseBody) {

		return responseBody.getStatusLine().getStatusCode() < 400;
	}

	private static String getResponseStr(HttpResponse response) throws IOException {

		return getResponseStr(response, "utf-8");
	}

	private static String getResponseStr(HttpResponse responseBody, String charSet) throws IOException {

		HttpEntity entity = responseBody.getEntity();
		if (entity == null)
			return null;
		String result = IOUtils.toString(entity.getContent(), charSet);
		EntityUtils.consume(entity);
		return result;
	}

	private static String buildQueryString(Map<String, String> params, String charSet) {

		List<NameValuePair> list = toNameValuePairs(params);
		if (list == null || list.isEmpty())
			return null;
		return URLEncodedUtils.format(list, charSet);
	}

	private static List<NameValuePair> toNameValuePairs(Map<String, String> params) {

		if (params == null || params.isEmpty())
			return null;
		List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String value = entry.getValue();
			if (value != null) {
				pairs.add(new BasicNameValuePair(entry.getKey(), value));
			}
		}

		return pairs;
	}

	private static HttpClient getHttpClient() {

		if (httpClient == null) {
			synchronized (syncObj) {
				if (httpClient == null) {
					PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
					cm.setMaxTotal(200);
					cm.setDefaultMaxPerRoute(20);
					BasicHttpParams params = new BasicHttpParams();
					params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
					params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 64 * 1024);
					httpClient = new DefaultHttpClient(cm, params);
				}
			}
		}

		return httpClient;
	}
}
