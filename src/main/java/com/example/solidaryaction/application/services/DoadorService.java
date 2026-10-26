package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Doador;
import com.example.solidaryaction.domain.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;
import com.example.solidaryaction.domain.repository.OngRepository;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private OngRepository ongRepository;

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<DoadorResponse> listarTodosDoadoresTable() {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        if (administrador) {
            return doadorRepository.findAll()
                    .stream()
                    .map(DoadorResponse::new)
                    .toList();
        }

        Long ongId = usuarioLogado.ong_id();

        if (ongId == null) {
            throw new AccessDeniedException(
                    "Usuário sem vínculo com uma ONG!"
            );
        }

        return doadorRepository.findByOng_Id(ongId)
                .stream()
                .map(DoadorResponse::new)
                .toList();
    }

    public DoadorResponse salvarDoador(DoadorRequest doador, Long id) {

        if (id == 0L) {

            var usuarioLogado = getUsuarioLogado();

            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "É necessário estar vinculado a uma ONG para cadastrar doadores!"
                );
            }

            var ong = ongRepository.findById(ongId).orElse(null);

            if (ong == null) {
                throw new IllegalArgumentException("ONG não encontrada!");
            }

            Doador doadorBanco = new Doador(doador);

            doadorBanco.setOng(ong);

            doadorRepository.save(doadorBanco);

            return new DoadorResponse(doadorBanco);

        } else {

            var usuarioLogado = getUsuarioLogado();

            boolean administrador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            Doador doadorBanco;

            if (administrador) {
                doadorBanco = doadorRepository.findById(id).orElse(null);
            } else {
                Long ongId = usuarioLogado.ong_id();

                if (ongId == null) {
                    throw new AccessDeniedException(
                            "Usuário sem vínculo com uma ONG!"
                    );
                }

                doadorBanco = doadorRepository
                        .findByIdAndOng_Id(id, ongId)
                        .orElse(null);
            }

            if (doadorBanco != null) {
                doadorBanco.setNome(doador.nome());
                doadorBanco.setCpf(doador.cpf());
                doadorBanco.setEmail(doador.email());
                doadorBanco.setProfissao(doador.profissao());
                doadorBanco.setEndereco(doador.endereco());

                doadorRepository.save(doadorBanco);

                return new DoadorResponse(doadorBanco);
            }
        }
        return null;
    }

    public DoadorResponse buscarPorId(Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Doador doador;

        if (administrador) {
            doador = doadorRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            doador = doadorRepository
                    .findByIdAndOng_Id(id, ongId)
                    .orElse(null);
        }

        if (doador == null) {
            return null;
        }

        return new DoadorResponse(doador);
    }

    public boolean atualizarStatus(EnumStatusDoador status, Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Doador doadorBanco;

        if (administrador) {
            doadorBanco = doadorRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            doadorBanco = doadorRepository
                    .findByIdAndOng_Id(id, ongId)
                    .orElse(null);
        }

        if (doadorBanco != null) {
            doadorBanco.setStatus(status);
            doadorRepository.save(doadorBanco);
            return true;
        }

        return false;
    }
}
