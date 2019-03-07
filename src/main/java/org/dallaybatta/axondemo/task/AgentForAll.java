package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentCreateCommand;
import org.dallaybatta.axondemo.command.AgentDeleteCommand;
import org.dallaybatta.axondemo.command.AgentUpdateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentCreatedEvent;
import org.dallaybatta.axondemo.event.AgentDeleteEvent;
import org.dallaybatta.axondemo.event.AgentUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


// An Aggregate is a regular object, which contains state and methods to alter that state
@Aggregate
public class AgentForAll {	
    
	@AggregateIdentifier
	private String id;
	
	AgentForAll(){
		
	}
	
	@CommandHandler
	public AgentForAll(AgentCreateCommand command) {
		System.out.println("Creating Agent by Name "+command.getName()+" id "+command.getId());
		AgentCreatedEvent event = new AgentCreatedEvent(command.getId(),command.getName());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentCreatedEvent event) {
		this.id = Utility.CREATE+event.getId();
		System.out.println("Created Agent by Id(EventSourcingHandler) "+event.getId());
	}	
	
	
	@CommandHandler
	public void updateAgent(AgentUpdateCommand command) {
		System.out.println("Update Agent by Id "+command.getId());
		AgentUpdateEvent event = new AgentUpdateEvent();
		event.setId(command.getId());
		event.setName(command.getName());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentUpdateEvent event,@Autowired AgentRepository agencyRepository) {
		this.id = Utility.UPDATE+event.getId();
	}	
	
	@CommandHandler
	public void agentDelete(AgentDeleteCommand command) {
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

