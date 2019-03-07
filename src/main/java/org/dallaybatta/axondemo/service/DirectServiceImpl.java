package org.dallaybatta.axondemo.service;

import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectServiceImpl implements DirectService {

	private AgentRepository agentRepository;
	
	@Autowired
	public DirectServiceImpl(AgentRepository agentRepository) {
		this.agentRepository = agentRepository;
	}
	
	@Override
	public String saveAgent(String id, String name) {
		Agent agent = new Agent();
		agent.setId(Long.valueOf(id));
		agent.setAdvertiserName(name);
		System.out.println("Saving Agent ");
		return agentRepository.save(agent).getId().toString();
	}

	@Override
	public void deleteAgent(String id) {
		System.out.println("Deleting Agent ");
		agentRepository.deleteById(Long.valueOf(id));
	}

	@Override
	public String updateAgent(String id, String name) {
		System.out.println("Updating Agent ");
		Agent agent = new Agent();
		agent.setId(Long.valueOf(id));
		agent.setAdvertiserName(name);
		return agentRepository.save(agent).getAdvertiserName();
	}

}
