package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentCreateCommand;
import org.dallaybatta.axondemo.command.AgentDeleteCommand;
import org.dallaybatta.axondemo.command.AgentUpdateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentCreatedEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.stereotype.Component;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.isLive;


// An Aggregate is a regular object, which contains state and methods to alter that state
@Aggregate
public class AgentCreateTask {	
    
	@AggregateIdentifier
	private String id;
	
	//AgentCreateTask(){
		
	//}
	
	@CommandHandler
	//public void createAgent(AgentCreateCommand command) {
	public AgentCreateTask(AgentCreateCommand command) {
		System.out.println("Creating Agent by Name "+command.getName()+" id "+command.getId());
		AgentCreatedEvent event = new AgentCreatedEvent(command.getId(),command.getName());
		//event.setName(command.getId());
		//event.setName(command.getName());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentCreatedEvent event) {
		if(!isLive()) {
			System.out.println("Aggregat .....");

			GenericCommandMessage msg = (GenericCommandMessage) CurrentUnitOfWork.get().getMessage();
			if(msg.getPayload() instanceof AgentUpdateCommand) {
				AgentUpdateCommand cmd = (AgentUpdateCommand) msg.getPayload();
				this.id = cmd.getId();
			}
			if(msg.getPayload() instanceof AgentDeleteCommand) {
				AgentDeleteCommand cmd = (AgentDeleteCommand) msg.getPayload();
				this.id = cmd.getId();
			}
		} 
		else {
			this.id = Utility.CREATE+event.getId();
			System.out.println("Created Agent by Id(EventSourcingHandler) "+event.getId());
		}
	}	
}

