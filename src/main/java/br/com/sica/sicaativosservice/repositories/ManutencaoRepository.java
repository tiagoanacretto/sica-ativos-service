package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.models.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    Manutencao findTopByAtivoIdOrderByDataRealizadaDesc(Long ativoId);
}
