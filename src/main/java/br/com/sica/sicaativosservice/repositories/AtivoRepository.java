package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.enums.CondicaoManutencao;
import br.com.sica.sicaativosservice.models.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtivoRepository  extends JpaRepository<Ativo, Long> {

    List<Ativo> findByStatusManutencao(CondicaoManutencao condicaoManutencao);
}
