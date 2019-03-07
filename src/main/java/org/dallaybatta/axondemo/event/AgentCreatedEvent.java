package org.dallaybatta.axondemo.event;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
@Value
@Getter
@Setter
public class AgentCreatedEvent {

	private String id;
	private String name;

}
