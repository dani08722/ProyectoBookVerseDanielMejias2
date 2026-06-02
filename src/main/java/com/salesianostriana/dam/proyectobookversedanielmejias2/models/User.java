package com.salesianostriana.dam.proyectobookversedanielmejias2.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	@NotBlank(message = "El nombre de usuario es obligatorio.")
	@Size(min = 4, max = 40, message = "El usuario debe tener entre 4 y 40 caracteres.")
	private String username;

	@Column(nullable = false)
	@Pattern(regexp = "^$|^.{6,}$", message = "La contraseña debe tener al menos 6 caracteres.")
	private String password;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;

	@OneToOne(mappedBy = "user")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Cliente cliente;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}
}
