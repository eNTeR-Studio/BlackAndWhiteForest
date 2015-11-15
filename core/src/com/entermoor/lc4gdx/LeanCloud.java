package com.entermoor.lc4gdx;

import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
import com.badlogic.gdx.utils.Pools;

public class LeanCloud {
	
	public static String X_LC_Id;
	public static String X_LC_Key;
	public static HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
	
	private static void isAvaliable() {
		if(X_LC_Id==null||X_LC_Id.equals("")||X_LC_Key==null||X_LC_Key.equals(""))
			throw new LCRuntimeException();
	}
	
	public static void feedback(Properties props){
		isAvaliable();
		writeJson(httpRequestBuilder.newRequest().method(HttpMethods.POST)
				.url("https://api.leancloud.cn/1.1/feedback")
				.header("X-LC-Id", "6v9rp1ndzdl5zbv9uiqjlzeex4v7gv2kh7hawtw02kft5ccd")
				.header("X-LC-Key", "jlgcq1xbr6op5f5yuyj304x7iu6ee4b70tfei0dtzoghjxgv"), props);
		final HttpRequest httpRequest = httpRequestBuilder.build();
		System.out.println(httpRequest.getContent());

		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				System.out.println(httpResponse.getResultAsString());
				Pools.free(httpRequest);
			}

			@Override
			public void failed(Throwable t) {
				Pools.free(httpRequest);
			}

			@Override
			public void cancelled() {
				Pools.free(httpRequest);
			}
		});
	}

	public static HttpRequestBuilder writeJson(HttpRequestBuilder requestBuilder, Properties map) {
		requestBuilder.header(HttpRequestHeader.ContentType, "application/json");
		StringBuilder content = new StringBuilder(200);
		content.append("{");
		for (Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<Object, Object> current = iterator.next();
			content.append(current.getKey());
			content.append(":");
			content.append(current.getValue());
			if (iterator.hasNext())
				content.append(",");
			else
				content.append("}");
		}
		requestBuilder.content(content.toString());
		return requestBuilder;
	}
	
}
