package br.com.sica.ativosservice.repositories;

import br.com.sica.ativosservice.models.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoRepository  extends JpaRepository<Ativo, Long> {
}
