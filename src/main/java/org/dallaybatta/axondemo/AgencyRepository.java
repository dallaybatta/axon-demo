package org.dallaybatta.axondemo;


import org.dallaybatta.axondemo.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agent, Long>{

}
