package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.DTOs.EnumStatusUsuario;
import com.example.solidaryaction.domain.entities.Usuario;
import com.example.solidaryaction.domain.repository.UsuarioRepository;
import com.example.solidaryaction.domain.valueobject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest request){

        if(usuarioRepository.existsUsuarioByEmailAndSenha(request.email(), request.senha())){
            //gerar o token

            var token = tokenService.gerarToken(request);
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable(){

        var usuarioLogado = getUsuarioLogado();

        if(usuarioLogado.empresa_id() ==null){
            return  usuarioRepository.findAll()
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
        }
        return  usuarioRepository.getUsuarioByEmpresa_Id(usuarioLogado.empresa_id())
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
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        if(id == 0L) {

            Usuario usuarioBanco = new Usuario(usuario);

            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);
        }else {
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if (usuarioBanco != null) {
                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setCpf(new CPF(usuario.cpf()));
                usuarioBanco.setEmail(usuario.email());
                usuarioBanco.setSenha(usuario.senha());
                usuarioRepository.save(usuarioBanco);
                return new UsuarioResponse(usuarioBanco);
            }
        }
        return null;
    }

    public UsuarioResponse buscarPorId(Long id){

        var usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null){
            return null;
        }

        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null){
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }
        return false;
    }
}
