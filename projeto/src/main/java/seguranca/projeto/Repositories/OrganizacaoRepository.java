package seguranca.projeto.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import seguranca.projeto.entities.Organizacao;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long>{
    
}
