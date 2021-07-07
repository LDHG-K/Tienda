package com.Quda.Backend.TiendaApp.Entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="roles_id")
	private Integer rolesId;

	@Column(name="roles_name")
	private String rolesName;

	//bi-directional many-to-one association to Person
	//@OneToMany(mappedBy="role")
	//private List<Person> persons;



	public Role() {
	}


}