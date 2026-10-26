package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.DTOs.EnumStatusUsuario;
import com.example.solidaryaction.domain.entities.Usuario;
import com.example.solidaryaction.domain.repository.UsuarioRepository;
import com.example.solidaryaction.domain.repository.OngRepository;
import com.example.solidaryaction.domain.valueobject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Value("${spring.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OngRepository ongRepository;

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request) {

        var usuario = usuarioRepository
                .findByEmailAndSenha(request.email(), request.senha())
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        var token = tokenService.gerarToken(request);

        Long ongId = null;

        if (usuario.getOng() != null) {
            ongId = usuario.getOng().getId();
        }

        return new LoginResponse(token, usuario.getRole(), ongId);
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (administrador) {
            return usuarioRepository.findAll()
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
        }

        if (usuarioLogado.ong_id() == null) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Usuário sem vínculo com uma ONG!"
            );
        }

        return usuarioRepository.findByOng_Id(usuarioLogado.ong_id())
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)){
            return new CriarAdminResponse(0L, "Usuario salvo com sucesso!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(), "Usuario salvo com sucesso!");
    }

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public UsuarioResponse criarCoordenador(Long ongId, UsuarioRequest usuario) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        if (!administrador) {
            throw new AccessDeniedException(
                    "Somente o administrador pode realizar este cadastro!"
            );
        }

        var ong = ongRepository.findById(ongId).orElse(null);

        if (ong == null) {
            return null;
        }

        if (!"C".equals(usuario.perfil())) {
            throw new IllegalArgumentException(
                    "Informe o perfil C para cadastrar um coordenador!"
            );
        }

        Usuario coordenador = new Usuario(usuario);

        coordenador.setOng(ong);

        usuarioRepository.save(coordenador);

        return new UsuarioResponse(coordenador);
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        if (id == 0L) {

            var usuarioLogado = getUsuarioLogado();

            boolean coordenador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_COORDENADOR")
            );

            if (!coordenador) {
                throw new AccessDeniedException(
                        "O cadastro comum deve ser realizado por um coordenador!"
                );
            }

            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            var ong = ongRepository.findById(ongId).orElse(null);

            if (ong == null) {
                throw new IllegalArgumentException("ONG não encontrada!");
            }

            Usuario usuarioBanco = new Usuario(usuario);

            usuarioBanco.setOng(ong);

            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);

        } else {

            var usuarioLogado = getUsuarioLogado();

            boolean administrador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            Usuario usuarioBanco;

            if (administrador) {
                usuarioBanco = usuarioRepository.findById(id).orElse(null);
            } else {
                Long ongId = usuarioLogado.ong_id();

                if (ongId == null) {
                    throw new AccessDeniedException(
                            "Usuário sem vínculo com uma ONG!"
                    );
                }

                usuarioBanco = usuarioRepository
                        .findByIdAndOng_Id(id, ongId)
                        .orElse(null);
            }

            if (usuarioBanco != null) {

                // O perfil ADMIN é preservado.
                // Os demais usuários só podem receber C ou U.
                if (!"ROLE_ADMIN".equals(usuarioBanco.getRole())) {

                    if (!"C".equals(usuario.perfil())
                            && !"U".equals(usuario.perfil())) {
                        throw new IllegalArgumentException(
                                "Perfil inválido! Informe C ou U."
                        );
                    }
                }

                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setCpf(new CPF(usuario.cpf()));
                usuarioBanco.setEmail(usuario.email());
                usuarioBanco.setSenha(usuario.senha());

                if (!"ROLE_ADMIN".equals(usuarioBanco.getRole())) {

                    if ("C".equals(usuario.perfil())) {
                        usuarioBanco.setRole("ROLE_COORDENADOR");
                    } else {
                        usuarioBanco.setRole("ROLE_USER");
                    }
                }

                usuarioRepository.save(usuarioBanco);

                return new UsuarioResponse(usuarioBanco);
            }
        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Usuario usuario;

        if (administrador) {
            usuario = usuarioRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            usuario = usuarioRepository
                    .findByIdAndOng_Id(id, ongId)
                    .orElse(null);
        }

        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Usuario usuarioBanco;

        if (administrador) {
            usuarioBanco = usuarioRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            usuarioBanco = usuarioRepository
                    .findByIdAndOng_Id(id, ongId)
                    .orElse(null);
        }

        if (usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }

        return false;
    }
}