package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.unitofwork.CurrentUnitOfWork;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentCreateCommand;
import org.dallaybatta.axondemo.command.AgentDeleteCommand;
import org.dallaybatta.axondemo.command.AgentUpdateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentCreatedEvent;
import org.dallaybatta.axondemo.event.AgentDeleteEvent;
import org.dallaybatta.axondemo.event.AgentUpdateEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.isLive;

import java.util.Optional;


// An Aggregate is a regular object, which contains state and methods to alter that state
//@Aggregate
public class AgentTask {	
    
	@AggregateIdentifier
	private String id;
	
	AgentTask(){
		
	}
	
	// Advertiser CRUD Commands
	@CommandHandler
	//public void createAgent(AgentCreateCommand command) {
	public AgentTask(AgentCreateCommand command) {
		System.out.println("Creating Agent by Name "+command.getName());
		AgentCreatedEvent event = new AgentCreatedEvent(command.getId(),command.getName());
		//event.setName(command.getName());
		//event.setId(command.getId());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentCreatedEvent event,@Autowired AgentRepository agencyRepository) {		
		if(!isLive()) {
			GenericCommandMessage msg = (GenericCommandMessage) CurrentUnitOfWork.get().getMessage();
			if(msg.getPayload() instanceof AgentUpdateCommand) {
				AgentUpdateCommand cmd = (AgentUpdateCommand) msg.getPayload();
				this.id = cmd.getId();
			}
			if(msg.getPayload() instanceof AgentDeleteCommand) {
				AgentDeleteCommand cmd = (AgentDeleteCommand) msg.getPayload();
				this.id = cmd.getId();
			}
		} else {
			System.out.println(event.getId()+" "+event.getName());
			Agent agent = new Agent();
			agent.setAdvertiserName(event.getName());
			//agent.setId(Long.valueOf(event.getId()));
			agent = agencyRepository.save(agent);
			System.out.println("Created Agent by Id "+agent.getId()+" isLive "+isLive());
			this.id = Utility.CREATE+agent.getId().toString();
		}
	}
	
	
	@CommandHandler
	public void update(AgentUpdateCommand command) {
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

	
	
	@CommandHandler
	public void delete(AgentDeleteCommand command) {
		System.out.println("Delete Agent by Id "+command.getId());
		AgentDeleteEvent event = new AgentDeleteEvent();
		event.setId(command.getId());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentDeleteEvent event,@Autowired AgentRepository agencyRepository) {
		agencyRepository.deleteById(Long.valueOf(event.getId()));
		System.out.println("Deleted Agent by Id "+event.getId());
		this.id = Utility.DELETE+event.getId();
	}	
}
