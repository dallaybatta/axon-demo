package org.dallaybatta.axondemo.spring.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.dallaybatta.axondemo.command.AgentCreateCommand;
import org.dallaybatta.axondemo.command.AgentDeleteCommand;
import org.dallaybatta.axondemo.command.AgentUpdateCommand;
import org.dallaybatta.axondemo.dao.AgentRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AxonTestAPI {

	private final CommandGateway commandGateway;
	private final AgentRepository agentRepository;

	public AxonTestAPI(CommandGateway commandGateway, AgentRepository agentRepository) {
        this.commandGateway = commandGateway;
        this.agentRepository = agentRepository;
        //this.reviveAdServerURI = adServerConfig.getAdmanagerurl();
    }
	
	@RequestMapping(value = "agency", method = RequestMethod.POST)
    public CompletableFuture<String> createAdvertiser(@RequestBody Map<String, String> request) {
		String id = request.get("id");
		String agentName = request.get("name");
        return commandGateway.send(new AgentCreateCommand(id,agentName));
	}
	
	@RequestMapping(value = "agency", method = RequestMethod.DELETE)
    public CompletableFuture<String> removeAgency(HttpServletRequest request) throws InterruptedException, ExecutionException {
		String id = request.getParameter("id");
        return commandGateway.send(new AgentDeleteCommand(id));
	}
	
	@RequestMapping(value = "agency", method = RequestMethod.PUT)
    public CompletableFuture<String> updateAgency(@RequestBody Map<String, String> request) throws InterruptedException, ExecutionException {
		String id = request.get("id");
		String agentName = request.get("name");
		return commandGateway.send(new AgentUpdateCommand(id,agentName));
	}
	
}
