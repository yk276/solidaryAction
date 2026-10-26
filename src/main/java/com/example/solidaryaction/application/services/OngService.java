package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Ong;
import com.example.solidaryaction.domain.repository.OngRepository;
import com.example.solidaryaction.domain.valueobject.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
public class OngService {

    @Autowired
    OngRepository ongRepository;

    @Value("${spring.secret}")
    private String secret;

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<OngResponse> listarTodasOngsTable() {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        if (administrador) {
            return ongRepository.findAll()
                    .stream()
                    .map(OngResponse::new)
                    .toList();
        }

        Long ongId = usuarioLogado.ong_id();

        if (ongId == null) {
            throw new AccessDeniedException(
                    "Usuário sem vínculo com uma ONG!"
            );
        }

        var ong = ongRepository.findById(ongId).orElse(null);

        if (ong == null) {
            return List.of();
        }

        return List.of(new OngResponse(ong));
    }

    public CriarOngAdminResponse criarOngAdmin(CriarOngAdminRequest criarOngAdminRequest) {

        if(!criarOngAdminRequest.secretKey().equals(secret)){
            return new CriarOngAdminResponse(0L, "Ong salva com sucesso!");
        }

        Ong ongAdminSalvar = new Ong(criarOngAdminRequest);

        ongRepository.save(ongAdminSalvar);

        return new CriarOngAdminResponse(ongAdminSalvar.getId(), "Ong salva com sucesso!");
    }

    public OngResponse salvarOng(OngRequest ong, Long id) {

        if(id == 0L) {

            Ong ongBanco = new Ong(ong);

            ongRepository.save(ongBanco);

            return new OngResponse(ongBanco);

        } else {

            var usuarioLogado = getUsuarioLogado();

            boolean administrador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            if (!administrador) {

                Long ongId = usuarioLogado.ong_id();

                if (ongId == null) {
                    throw new AccessDeniedException(
                            "Usuário sem vínculo com uma ONG!"
                    );
                }

                if (!ongId.equals(id)) {
                    return null;
                }
            }

            Ong ongBanco = ongRepository.findById(id).orElse(null);

            if (ongBanco != null) {
                ongBanco.setNomeFantasia(ong.nomeFantasia());
                ongBanco.setRazaoSocial(ong.razaoSocial());
                ongBanco.setCnpj(new CNPJ(ong.cnpj()));
                ongBanco.setEmail(ong.email());
                ongBanco.setEndereco(ong.endereco());

                ongRepository.save(ongBanco);

                return new OngResponse(ongBanco);
            }
        }

        return null;
    }

    public OngResponse buscarPorId(Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        if (!administrador) {

            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            if (!ongId.equals(id)) {
                return null;
            }
        }

        var ong = ongRepository.findById(id).orElse(null);

        if (ong == null) {
            return null;
        }

        return new OngResponse(ong);
    }

    public boolean atualizarStatus(EnumStatusOng status, Long id) {

        Ong ongBanco = ongRepository.findById(id).orElse(null);

        if(ongBanco != null){
            ongBanco.setStatus(status);
            ongRepository.save(ongBanco);
            return true;
        }

        return false;
    }
}