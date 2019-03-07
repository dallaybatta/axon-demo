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
import org.dallaybatta.axondemo.service.DirectService;
import org.dallaybatta.axondemo.spring.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;


@RestController
public class AxonTestAPI {

	private final CommandGateway commandGateway;
	private final AgentRepository agentRepository;
	@Autowired //don't forget the setter
	@Getter @Setter private DirectService directService;
    @Autowired @Getter @Setter private Config config;

	
	public AxonTestAPI(CommandGateway commandGateway, AgentRepository agentRepository) {
        this.commandGateway = commandGateway;
        this.agentRepository = agentRepository;
        //this.reviveAdServerURI = adServerConfig.getAdmanagerurl();
    }

	
	@RequestMapping(value = "agency", method = RequestMethod.POST)
    public CompletableFuture<String> createAgency(@RequestBody Map<String, String> request) {
		String id = request.get("id");
		String agentName = request.get("name");
        return commandGateway.send(new AgentCreateCommand(id,agentName));
	}
	
	@RequestMapping(value = "agency", method = RequestMethod.DELETE)
    public CompletableFuture<String> removeAgency(@RequestBody Map<String, String> request) throws InterruptedException, ExecutionException {
		String id = request.get("id");
        return commandGateway.send(new AgentDeleteCommand(id));
	}
	
	@RequestMapping(value = "agency", method = RequestMethod.PUT)
    public CompletableFuture<String> updateAgency(@RequestBody Map<String, String> request) throws InterruptedException, ExecutionException {
		String id = request.get("id");
		String agentName = request.get("name");
		return commandGateway.send(new AgentUpdateCommand(id,agentName));
	}
	
	@RequestMapping(value = "agency1", method = RequestMethod.POST)
    public String createAgency1(@RequestBody Map<String, String> request) {
		String id = request.get("id");
		String agentName = request.get("name");
        return directService.saveAgent(id, agentName);
	}
	
	@RequestMapping(value = "agency1", method = RequestMethod.DELETE)
    public String removeAgency1(@RequestBody Map<String, String> request) throws InterruptedException, ExecutionException {
		String id = request.get("id");
		directService.deleteAgent(id);
        return id;
	}
	
	@RequestMapping(value = "agency1", method = RequestMethod.PUT)
    public String updateAgency1(@RequestBody Map<String, String> request) throws InterruptedException, ExecutionException {
		String id = request.get("id");
		String agentName = request.get("name");
        return directService.updateAgent(id, agentName);
	}
	
}
