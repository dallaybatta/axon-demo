package org.dallaybatta.axondemo.task;

import org.axonframework.eventhandling.EventHandler;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentCreatedEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.stereotype.Component;

@Component
public class AgentQueryObjectCreator {

    private final AgentRepository agencyRepository;

    public AgentQueryObjectCreator(AgentRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @EventHandler
    public void on(AgentCreatedEvent event) {
		Agent agent = new Agent();
		agent.setAdvertiserName(event.getName());
		agent.setId(Long.valueOf(event.getId()));
    	agencyRepository.save(agent);
		System.out.println("Created Agent by Id "+agent.getId());
    }
}
