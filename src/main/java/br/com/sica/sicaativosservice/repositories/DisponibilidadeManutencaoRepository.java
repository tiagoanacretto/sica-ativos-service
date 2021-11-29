package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.enums.CategoriaAtivo;
import br.com.sica.sicaativosservice.models.DisponibilidadeManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadeManutencaoRepository extends JpaRepository<DisponibilidadeManutencao, Long> {

    List<DisponibilidadeManutencao> findByCategoria(final CategoriaAtivo categoria);
}
