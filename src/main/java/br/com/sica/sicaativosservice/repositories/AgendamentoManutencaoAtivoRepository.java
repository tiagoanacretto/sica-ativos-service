package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.enums.StatusAgendamento;
import br.com.sica.sicaativosservice.models.AgendamentoManutencaoAtivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoManutencaoAtivoRepository extends JpaRepository<AgendamentoManutencaoAtivo, Long> {

    AgendamentoManutencaoAtivo findTopByAtivoIdAndStatusOrderByDataAgendadaAsc(Long ativoId, StatusAgendamento status);

}
