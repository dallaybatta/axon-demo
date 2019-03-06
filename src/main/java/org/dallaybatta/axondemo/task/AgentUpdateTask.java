package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentUpdateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentUpdateEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.Optional;


// An Aggregate is a regular object, which contains state and methods to alter that state
//@Aggregate
public class AgentUpdateTask {	
    
	@AggregateIdentifier
	private String id;
	
	AgentUpdateTask(){
		
	}
	
	@CommandHandler
	public AgentUpdateTask(AgentUpdateCommand command) {
		System.out.println("Update Agent by Id "+command.getId());
		AgentUpdateEvent event = new AgentUpdateEvent();
		event.setId(command.getId());
		event.setName(command.getName());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentUpdateEvent event,@Autowired AgentRepository agencyRepository) {
		Optional<Agent> a = agencyRepository.findById(Long.valueOf(event.getId()));
		Agent agent = a.get();
		agent.setAdvertiserName(event.getName());
		agencyRepository.save(agent);
		System.out.println("Updated Agent by Id "+event.getId());
		this.id = Utility.UPDATE+event.getId();
	}	

}
