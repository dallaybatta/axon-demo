package org.dallaybatta.axondemo.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class AgentCreateCommand {

	@TargetAggregateIdentifier
	private String id;  
	// Basic Information 
	private String name;

}
