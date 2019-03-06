package org.dallaybatta.axondemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
public class Agent {
	
	@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
    private Long id;
	private String advertiserName;
}
