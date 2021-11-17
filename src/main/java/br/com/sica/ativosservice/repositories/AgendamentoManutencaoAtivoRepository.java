package br.com.sica.ativosservice.repositories;

import br.com.sica.ativosservice.enums.StatusManutencao;
import br.com.sica.ativosservice.models.AgendamentoManutencaoAtivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoManutencaoAtivoRepository extends JpaRepository<AgendamentoManutencaoAtivo, Long> {

    AgendamentoManutencaoAtivo findTopByAtivoIdAndStatusOrderByDataRealizadaDesc(Long ativoId, StatusManutencao status);

    AgendamentoManutencaoAtivo findTopByAtivoIdAndStatusOrderByDataAgendadaAsc(Long ativoId, StatusManutencao status);

}
