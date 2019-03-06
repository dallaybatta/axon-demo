package org.dallaybatta.axondemo.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class AgentDeleteCommand {

	@TargetAggregateIdentifier
	private String id;  

}
