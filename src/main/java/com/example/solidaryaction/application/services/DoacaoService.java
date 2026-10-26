package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Doacao;
import com.example.solidaryaction.domain.entities.Campanha;
import com.example.solidaryaction.domain.repository.DoacaoRepository;
import com.example.solidaryaction.domain.repository.CampanhaRepository;
import com.example.solidaryaction.domain.repository.DoadorRepository;
import com.example.solidaryaction.domain.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private DoadorRepository doadorRepository;

    public UsuarioLogado getUsuarioLogado() {
        return (UsuarioLogado) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<DoacaoResponse> listarTodasDoacoesTable() {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        if (administrador) {
            return doacaoRepository.findAll()
                    .stream()
                    .map(DoacaoResponse::new)
                    .toList();
        }

        Long ongId = usuarioLogado.ong_id();

        if (ongId == null) {
            throw new AccessDeniedException(
                    "Usuário sem vínculo com uma ONG!"
            );
        }

        return doacaoRepository.findByCampanha_Ong_Id(ongId)
                .stream()
                .map(DoacaoResponse::new)
                .toList();
    }

    @Transactional
    public DoacaoResponse salvarDoacao(DoacaoRequest doacao, Long id) {

        if (id == 0L) {

            if (doacao.campanhaId() == null || doacao.doadorId() == null) {
                throw new IllegalArgumentException(
                        "Informe a campanha e o doador!"
                );
            }

            var usuarioLogado = getUsuarioLogado();

            boolean administrador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            Campanha campanha;

            if (administrador) {
                campanha = campanhaRepository
                        .findById(doacao.campanhaId())
                        .orElse(null);
            } else {
                Long ongId = usuarioLogado.ong_id();

                if (ongId == null) {
                    throw new AccessDeniedException(
                            "Usuário sem vínculo com uma ONG!"
                    );
                }

                campanha = campanhaRepository
                        .findByIdAndOng_Id(doacao.campanhaId(), ongId)
                        .orElse(null);
            }

            if (campanha == null) {
                return null;
            }

            var doador = doadorRepository
                    .findByIdAndOng_Id(
                            doacao.doadorId(),
                            campanha.getOng().getId()
                    )
                    .orElse(null);

            if (doador == null) {
                return null;
            }

            Doacao doacaoBanco = new Doacao(doacao);

            doacaoBanco.setCampanha(campanha);
            doacaoBanco.setDoador(doador);

            doacaoRepository.save(doacaoBanco);

            return new DoacaoResponse(doacaoBanco);

        } else {

            var usuarioLogado = getUsuarioLogado();

            boolean administrador = usuarioLogado.authorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            Doacao doacaoBanco;

            if (administrador) {
                doacaoBanco = doacaoRepository.findById(id).orElse(null);
            } else {
                Long ongId = usuarioLogado.ong_id();

                if (ongId == null) {
                    throw new AccessDeniedException(
                            "Usuário sem vínculo com uma ONG!"
                    );
                }

                doacaoBanco = doacaoRepository
                        .findByIdAndCampanha_Ong_Id(id, ongId)
                        .orElse(null);
            }

            if (doacaoBanco != null) {

                if (doacao.campanhaId() == null || doacao.doadorId() == null) {
                    throw new IllegalArgumentException(
                            "Informe a campanha e o doador!"
                    );
                }

                Long ongId = doacaoBanco.getCampanha().getOng().getId();

                var campanha = campanhaRepository
                        .findByIdAndOng_Id(doacao.campanhaId(), ongId)
                        .orElse(null);

                if (campanha == null) {
                    return null;
                }

                var doador = doadorRepository
                        .findByIdAndOng_Id(doacao.doadorId(), ongId)
                        .orElse(null);

                if (doador == null) {
                    return null;
                }

                // Retira o valor antigo antes de alterar a doação confirmada.
                if (doacaoBanco.getStatus() == EnumStatusDoacao.CONFIRMADO) {

                    var campanhaAnterior = doacaoBanco.getCampanha();
                    var ong = campanhaAnterior.getOng();
                    var valorAnterior = doacaoBanco.getValorDoado();

                    campanhaAnterior.setTotalArrecadado(
                            campanhaAnterior.getTotalArrecadado()
                                    .subtract(valorAnterior)
                    );

                    ong.setTotalArrecadado(
                            ong.getTotalArrecadado().subtract(valorAnterior)
                    );

                    campanhaRepository.save(campanhaAnterior);
                    ongRepository.save(ong);
                }

                doacaoBanco.setDataDoacao(doacao.dataDoacao());
                doacaoBanco.setValorDoado(doacao.valorDoado());
                doacaoBanco.setDescricao(doacao.descricao());
                doacaoBanco.setCampanha(campanha);
                doacaoBanco.setDoador(doador);

                // Acrescenta o novo valor se a doação continua confirmada.
                if (doacaoBanco.getStatus() == EnumStatusDoacao.CONFIRMADO) {

                    var ong = campanha.getOng();

                    campanha.setTotalArrecadado(
                            campanha.getTotalArrecadado()
                                    .add(doacaoBanco.getValorDoado())
                    );

                    ong.setTotalArrecadado(
                            ong.getTotalArrecadado()
                                    .add(doacaoBanco.getValorDoado())
                    );

                    campanhaRepository.save(campanha);
                    ongRepository.save(ong);
                }

                doacaoRepository.save(doacaoBanco);

                return new DoacaoResponse(doacaoBanco);
            }
        }

        return null;
    }

    public DoacaoResponse buscarPorId(Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Doacao doacao;

        if (administrador) {
            doacao = doacaoRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            doacao = doacaoRepository
                    .findByIdAndCampanha_Ong_Id(id, ongId)
                    .orElse(null);
        }

        if (doacao == null) {
            return null;
        }

        return new DoacaoResponse(doacao);
    }

    @Transactional
    public boolean atualizarStatus(EnumStatusDoacao status, Long id) {

        var usuarioLogado = getUsuarioLogado();

        boolean administrador = usuarioLogado.authorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Doacao doacaoBanco;

        if (administrador) {
            doacaoBanco = doacaoRepository.findById(id).orElse(null);
        } else {
            Long ongId = usuarioLogado.ong_id();

            if (ongId == null) {
                throw new AccessDeniedException(
                        "Usuário sem vínculo com uma ONG!"
                );
            }

            doacaoBanco = doacaoRepository
                    .findByIdAndCampanha_Ong_Id(id, ongId)
                    .orElse(null);
        }

        if (doacaoBanco != null) {

            if (status == null) {
                throw new IllegalArgumentException(
                        "Informe o status da doação!"
                );
            }

            if (doacaoBanco.getStatus() == status) {
                return true;
            }

            var campanha = doacaoBanco.getCampanha();
            var ong = campanha.getOng();
            var valor = doacaoBanco.getValorDoado();

            // Retira o valor quando a doação deixa de estar confirmada.
            if (doacaoBanco.getStatus() == EnumStatusDoacao.CONFIRMADO) {
                campanha.setTotalArrecadado(
                        campanha.getTotalArrecadado().subtract(valor)
                );

                ong.setTotalArrecadado(
                        ong.getTotalArrecadado().subtract(valor)
                );
            }

            // Acrescenta o valor quando a doação passa a estar confirmada.
            if (status == EnumStatusDoacao.CONFIRMADO) {
                campanha.setTotalArrecadado(
                        campanha.getTotalArrecadado().add(valor)
                );

                ong.setTotalArrecadado(
                        ong.getTotalArrecadado().add(valor)
                );
            }

            doacaoBanco.setStatus(status);

            campanhaRepository.save(campanha);
            ongRepository.save(ong);
            doacaoRepository.save(doacaoBanco);

            return true;
        }

        return false;
    }
}