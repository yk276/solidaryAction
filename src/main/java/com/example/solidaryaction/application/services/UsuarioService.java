package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
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

    public LoginResponse validarUsuarioAutenticadoRetornaToken(
            LoginRequest request) {

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
            throw new AccessDeniedException(
                    "Usuário sem vínculo com uma ONG!"
            );
        }

        return usuarioRepository.findByOng_Id(usuarioLogado.ong_id())
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(
            CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)){
            return new CriarAdminResponse(0L, "Usuario salvo com sucesso!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(
                usuarioAdminSalvar.getId(),
                "Usuario salvo com sucesso!"
        );
    }

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    //Mantém o cadastro pela rota /ongs/{ongId}/coordenadores.
    public UsuarioResponse criarCoordenador(
            Long ongId,
            UsuarioRequest usuario) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        if (!administrador) {
            throw new AccessDeniedException(
                    "Somente o administrador pode cadastrar coordenadores!"
            );
        }

        if (ongId == null) {
            throw new IllegalArgumentException("Informe a ONG!");
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

    public UsuarioResponse salvarUsuario(
            UsuarioRequest usuario,
            Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        boolean coordenador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_COORDENADOR")
        );

        if (!administrador && !coordenador) {
            throw new AccessDeniedException(
                    "Você não tem permissão para salvar usuários!"
            );
        }

        if (id == 0L) {

            //O ADMIN cadastra coordenadores na ONG escolhida.
            if (administrador) {
                return criarCoordenador(usuario.ongId(), usuario);
            }

            //O coordenador cadastra somente usuários comuns.
            if (!"U".equals(usuario.perfil())) {
                throw new AccessDeniedException(
                        "O coordenador pode cadastrar somente usuários comuns!"
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

            //Ignora a ONG enviada no request pelo coordenador.
            usuarioBanco.setOng(ong);

            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);

        } else {

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

            if (usuarioBanco == null) {
                return null;
            }

            //O perfil ADMIN é preservado.
            if (!"ROLE_ADMIN".equals(usuarioBanco.getRole())) {

                if (!"C".equals(usuario.perfil())
                        && !"U".equals(usuario.perfil())) {

                    throw new IllegalArgumentException(
                            "Perfil inválido! Informe C ou U."
                    );
                }

                //Somente o ADMIN pode mudar o perfil.
                if (!administrador) {

                    if ("ROLE_USER".equals(usuarioBanco.getRole())
                            && !"U".equals(usuario.perfil())) {

                        throw new AccessDeniedException(
                                "Somente o administrador pode atribuir o perfil Coordenador!"
                        );
                    }

                    if ("ROLE_COORDENADOR".equals(usuarioBanco.getRole())
                            && !"C".equals(usuario.perfil())) {

                        throw new AccessDeniedException(
                                "Somente o administrador pode alterar o perfil de um coordenador!"
                        );
                    }
                }
            }

            usuarioBanco.setNome(usuario.nome());
            usuarioBanco.setCpf(new CPF(usuario.cpf()));
            usuarioBanco.setEmail(usuario.email());

            //Somente o ADMIN pode redefinir a senha pela edição.
            //A edição feita pelo coordenador preserva a senha existente.
            if (administrador
                    && usuario.senha() != null
                    && !usuario.senha().trim().isEmpty()) {
                usuarioBanco.setSenha(usuario.senha());
            }

            if (administrador
                    && !"ROLE_ADMIN".equals(usuarioBanco.getRole())) {

                if ("C".equals(usuario.perfil())) {
                    usuarioBanco.setRole("ROLE_COORDENADOR");
                } else {
                    usuarioBanco.setRole("ROLE_USER");
                }
            }

            //A edição preserva o vínculo atual com a ONG.
            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);
        }
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

    public UsuarioResponse buscarUsuarioLogado() {

        try {
            var usuarioLogado = getUsuarioLogado();
            return usuarioRepository.findById(usuarioLogado.id())
                    .stream()
                    .map(UsuarioResponse::new).findFirst().orElse(null);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuario logado!");
        }
    }
}