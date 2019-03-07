package org.dallaybatta.axondemo.task;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentUpdateEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.stereotype.Component;

@Component
public class AgentQueryObjectUpdator {

    private final AgentRepository agencyRepository;

    public AgentQueryObjectUpdator(AgentRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @EventHandler
    public void on(AgentUpdateEvent event) {
		Optional<Agent> a = agencyRepository.findById(Long.valueOf(event.getId()));
		Agent agent = a.get();
		agent.setAdvertiserName(event.getName());
		agencyRepository.save(agent);
		System.out.println("Updated Agent by Id "+event.getId());
	}
}
