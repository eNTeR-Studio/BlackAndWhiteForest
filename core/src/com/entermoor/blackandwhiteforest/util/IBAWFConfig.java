package com.entermoor.blackandwhiteforest.util;

public interface IBAWFConfig {

	public String get(String key, String defaultValue);

	public String get(String key);
	
	public void set(String key, String value);

	public void setProperty(String key, String value);
	
}
