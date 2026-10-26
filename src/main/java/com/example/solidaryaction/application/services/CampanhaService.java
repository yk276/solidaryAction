package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Campanha;
import com.example.solidaryaction.domain.repository.CampanhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.solidaryaction.domain.repository.OngRepository;

@Service
public class CampanhaService {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private OngRepository ongRepository;

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<CampanhaResponse> listarTodasCampanhasTable() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.authorities().contains(
                new org.springframework.security.core.authority.SimpleGrantedAuthority(
                        "ROLE_ADMIN"))) {

            return campanhaRepository.findAll()
                    .stream()
                    .map(CampanhaResponse::new)
                    .toList();
        }

        Long ongId = usuarioLogado.ong_id();

        if (ongId == null) {
            throw new AccessDeniedException("Usuário sem vínculo com uma ONG!");
        }

        return campanhaRepository.findByOng_Id(ongId)
                .stream()
                .map(CampanhaResponse::new)
                .toList();
    }

    public CampanhaResponse salvarCampanha(CampanhaRequest campanha, Long id) {

        if (id == 0L) {

            var usuarioLogado = getUsuarioLogado();

            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "É necessário estar vinculado a uma ONG para cadastrar campanhas!"
                );
            }

            var ong = ongRepository.findById(ongId).orElse(null);

            if (ong == null) {
                throw new IllegalArgumentException("ONG não encontrada!");
            }

            Campanha campanhaBanco = new Campanha(campanha);

            campanhaBanco.setOng(ong);

            campanhaRepository.save(campanhaBanco);

            return new CampanhaResponse(campanhaBanco);

        } else {

            var usuarioLogado = getUsuarioLogado();

            boolean administrador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            Campanha campanhaBanco;

            if (administrador) {
                campanhaBanco = campanhaRepository.findById(id).orElse(null);
            } else {
                Long ongId = usuarioLogado.ong_id();

                if (ongId == null) {
                    throw new AccessDeniedException(
                            "Usuário sem vínculo com uma ONG!"
                    );
                }

                campanhaBanco = campanhaRepository
                        .findByIdAndOng_Id(id, ongId)
                        .orElse(null);
            }

            if (campanhaBanco != null) {
                campanhaBanco.setTitulo(campanha.titulo());
                campanhaBanco.setDescricao(campanha.descricao());
                campanhaBanco.setMetaFinanceira(campanha.metaFinanceira());
                campanhaBanco.setDataInicio(campanha.dataInicio());
                campanhaBanco.setDataFim(campanha.dataFim());

                campanhaRepository.save(campanhaBanco);

                return new CampanhaResponse(campanhaBanco);
            }
        }
        return null;
    }

    public CampanhaResponse buscarPorId(Long id) {

        var usuarioLogado = getUsuarioLogado();

        Campanha campanha;

        if (usuarioLogado.authorities().contains(
                new org.springframework.security.core.authority.SimpleGrantedAuthority(
                        "ROLE_ADMIN"))) {

            campanha = campanhaRepository.findById(id).orElse(null);

        } else {

            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            campanha = campanhaRepository.findByIdAndOng_Id(id, ongId)
                    .orElse(null);
        }

        if (campanha == null) {
            return null;
        }

        return new CampanhaResponse(campanha);
    }

    public boolean atualizarStatus(EnumStatusCampanha status, Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Campanha campanhaBanco;

        if (administrador) {
            campanhaBanco = campanhaRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            campanhaBanco = campanhaRepository
                    .findByIdAndOng_Id(id, ongId)
                    .orElse(null);
        }

        if (campanhaBanco != null) {
            campanhaBanco.setStatus(status);
            campanhaRepository.save(campanhaBanco);
            return true;
        }

        return false;
    }
}
