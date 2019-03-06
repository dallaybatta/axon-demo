package org.dallaybatta.axondemo.task;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.dallaybatta.axondemo.Utility;
import org.dallaybatta.axondemo.command.AgentCreateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.dallaybatta.axondemo.event.AgentCreatedEvent;
import org.dallaybatta.axondemo.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


// An Aggregate is a regular object, which contains state and methods to alter that state
//@Aggregate
public class AgentCreateTask {	
    
	@AggregateIdentifier
	private String id;
	
	AgentCreateTask(){
		
	}
	
	// Advertiser CRUD Commands
	@CommandHandler
	//public void createAgent(AgentCreateCommand command) {
	public AgentCreateTask(AgentCreateCommand command) {
		System.out.println("Creating Agent by Name "+command.getName());
		AgentCreatedEvent event = new AgentCreatedEvent();
		event.setName(command.getName());
		apply(event);
	}
	
	@EventSourcingHandler
	void on(AgentCreatedEvent event,@Autowired AgentRepository agencyRepository) {
		Agent agent = new Agent();
		agent.setAdvertiserName(event.getName());
		agent = agencyRepository.save(agent);
		System.out.println("Created Agent by Id "+agent.getId());
		this.id = Utility.CREATE+agent.getId().toString();
	}	
}
