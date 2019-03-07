package org.dallaybatta.axondemo.service;

public interface DirectService {
	
	String saveAgent(String id, String name);
	void deleteAgent(String id);
	String updateAgent(String id, String name);
}
