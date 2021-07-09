package com.Quda.Backend.LoginApp.RegisterToken;

import com.Quda.Backend.TiendaApp.Entidad.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@Data
@Builder
@Entity
@NoArgsConstructor
@Table (name="confirmation_token")
public class TokenConfirmacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "token_id")
    private Long id;

    @Column (name = "token_name")
    private String token;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "expired")
    private LocalDateTime expired;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column (name = "fk_user_nickname")
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name="fk_user_nickname", updatable = false,insertable = false)
    private User users;




}
