package com.Quda.Backend.TiendaApp.Entidad;

import com.Quda.Backend.LoginApp.Token.TokenConfirmacion;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Builder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class User  {



	@Id
	@Column(name="user_nick_name")
	@Size(min = 4, max = 30, message = "NickName debe tener entre 4 y 30 caracteres")
	private String userNickName;

	@Column(name="user_password")
	@Size(min = 8, message = "Contraseña debe contener mas de 8 Caracteres")
	//@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userPassword;

	@Positive
	@Column(name="fk_person_id")
	private Integer personId;

	@Positive
	@Column(name="fk_state_id")
	private Integer stateId;

	// Seguridad

	@Positive
	@Column(name="fk_roles_id")
	private Long UserRole;

	@Column(name = "enabled")
	private Boolean enabled = false;

	@Column(name = "lockeduser")
	private Boolean locked = false;


	// Relaciones
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name="fk_roles_id", updatable = false,insertable = false)
	private Role role;


	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="fk_person_id", updatable = false,insertable = false)
	//@JsonIgnore
	private Person person;

	//bi-directional many-to-one association to State
	//@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name="fk_state_id", updatable = false,insertable = false)
	private State state;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy="users")
	private List<TokenConfirmacion> tokens;

}