package org.dallaybatta.axondemo.event;

import lombok.Getter;
import lombok.Setter;

public class AgentCreatedEvent {

	@Getter @Setter private String id;
	@Getter @Setter private String name;

}
