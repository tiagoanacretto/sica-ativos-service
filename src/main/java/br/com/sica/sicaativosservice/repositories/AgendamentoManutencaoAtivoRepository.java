package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.enums.StatusManutencao;
import br.com.sica.sicaativosservice.models.AgendamentoManutencaoAtivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoManutencaoAtivoRepository extends JpaRepository<AgendamentoManutencaoAtivo, Long> {

    AgendamentoManutencaoAtivo findTopByAtivoIdAndStatusOrderByDataRealizadaDesc(Long ativoId, StatusManutencao status);

    AgendamentoManutencaoAtivo findTopByAtivoIdAndStatusOrderByDataAgendadaAsc(Long ativoId, StatusManutencao status);

}
