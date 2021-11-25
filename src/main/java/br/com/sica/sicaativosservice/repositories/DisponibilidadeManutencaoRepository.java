package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.models.DisponibilidadeManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadeManutencaoRepository extends JpaRepository<DisponibilidadeManutencao, Long> {
}
