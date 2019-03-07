package org.dallaybatta.axondemo.task;

import org.axonframework.eventhandling.EventHandler;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentDeleteEvent;
import org.springframework.stereotype.Component;

@Component
public class AgentQueryObjectDeletor {

    private final AgentRepository agencyRepository;

    public AgentQueryObjectDeletor(AgentRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @EventHandler
    public void on(AgentDeleteEvent event) {
		agencyRepository.deleteById(Long.valueOf(event.getId()));
		System.out.println("Deleted Agent by Id "+event.getId());
    }
}
