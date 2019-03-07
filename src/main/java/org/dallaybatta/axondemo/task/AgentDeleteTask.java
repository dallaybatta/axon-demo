package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentDeleteCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentDeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;



// An Aggregate is a regular object, which contains state and methods to alter that state
@Aggregate
public class AgentDeleteTask {	
    
	@AggregateIdentifier
	private String id;
	
	//AgentDeleteTask(){
		
	//}
	
	@CommandHandler
	public AgentDeleteTask(AgentDeleteCommand command) {
		System.out.println("Delete Agent by Id "+command.getId());
		AgentDeleteEvent event = new AgentDeleteEvent();
		event.setId(command.getId());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentDeleteEvent event,@Autowired AgentRepository agencyRepository) {
		this.id = Utility.DELETE+event.getId();
	}	
}
