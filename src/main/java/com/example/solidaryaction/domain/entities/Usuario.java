package com.example.solidaryaction.domain.entities;

//Entidade JPA `Usuario`. Representa a estrutura persistida no banco e usa Lombok para gerar
//automaticamente métodos e construtores, reduzindo código repetitivo.

import com.example.solidaryaction.application.DTOs.CriarAdminRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusUsuario;
import com.example.solidaryaction.application.DTOs.UsuarioLogado;
import com.example.solidaryaction.application.DTOs.UsuarioRequest;
import com.example.solidaryaction.domain.valueobject.CPF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//As anotações JPA transformam esta classe em uma entidade persistida no banco. O Lombok reduz código repetitivo,
//como getters, setters e construtores.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    //Este campo é a chave primária da entidade; @GeneratedValue deixa a geração do identificador a cargo do
    //banco/JPA.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    private CPF cpf;

    private String senha;

    private String email;

    private String role = "ROLE_USER";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ong_id", referencedColumnName = "id")
    private Ong ong;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    public void setEmail(String email){
        if(!email.isEmpty() && !email.contains("@")){
            throw new IllegalArgumentException("Email inválido!");
        }
        this.email = email;
    }

    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(new CPF(criarAdminRequest.cpf()));
        this.setNome(criarAdminRequest.nome());
        this.setSenha(criarAdminRequest.senha());
        this.setEmail(criarAdminRequest.email());
        this.setRole("ROLE_ADMIN");
    }

    public Usuario(UsuarioRequest usuario) {

        if (usuario.perfil() == null) {
            throw new IllegalArgumentException(
                    "Informe o perfil do usuário!"
            );
        }

        if (!usuario.perfil().equals("C")
                && !usuario.perfil().equals("U")) {
            throw new IllegalArgumentException(
                    "Perfil inválido! Informe C ou U."
            );
        }

        this.setCpf(new CPF(usuario.cpf()));
        this.setNome(usuario.nome());
        this.setSenha(usuario.senha());
        this.setEmail(usuario.email());

        if (usuario.perfil().equals("C")) {
            this.setRole("ROLE_COORDENADOR");
        } else {
            this.setRole("ROLE_USER");
        }
    }

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
