package br.com.sica.sicaativosservice.repositories;

import br.com.sica.sicaativosservice.models.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoRepository  extends JpaRepository<Ativo, Long> {
}
