package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentUpdateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentUpdateEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.isLive;

import java.util.Optional;


// An Aggregate is a regular object, which contains state and methods to alter that state
@Aggregate
public class AgentUpdateTask {	
    
	@AggregateIdentifier
	private String id;
	
	//AgentUpdateTask(){
	//	
	//}
	
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
		System.out.println("@EventSourcingHandler :: AgentUpdateEvent"+Utility.UPDATE+event.getId()+""+isLive());
		this.id = Utility.UPDATE+event.getId();
	}	
}
