package seguranca.projeto.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import seguranca.projeto.Repositories.OrganizacaoRepository;
import seguranca.projeto.dtos.organizacao.*;
import seguranca.projeto.entities.Organizacao;
import seguranca.projeto.mappers.OrganizacaoMapper;

@Service
@RequiredArgsConstructor
public class OrganizacaoServiceImpl implements OrganizacaoServices {

    private final OrganizacaoRepository repository;

    @Override
    public List<OrganizacaoResponseDto> findAll() {
        return OrganizacaoMapper.toDtoList(repository.findAll());
    }

    @Override
    public OrganizacaoResponseDto findById(@PathVariable Long id) {
        if(repository.existsById(id)){
            return OrganizacaoMapper.toDto(repository.findById(id).get());
        }else{
            throw new IllegalArgumentException("pedido id " + id + " inexistente");
        }
    }

    @Override
    public OrganizacaoResponseDto create(@RequestBody OrganizacaoRequestDto request) {
        Organizacao organizacao = OrganizacaoMapper.toEntity(request);
        organizacao = repository.save(organizacao);
        return OrganizacaoMapper.toDto(organizacao);
    }

    @Override
    public OrganizacaoResponseDto update(Long id, OrganizacaoRequestDto request) {
        Organizacao organizacao = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Organizacao com id " + id + " não encontrada"));

        organizacao.setName(request.getName());
        organizacao.setEmail(request.getEmail());

        return OrganizacaoMapper.toDto(repository.save(organizacao));
    }

    @Override
    public void delete(@PathVariable Long id) {
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("organização com id " + id +
             " não existe e não pode ser excluida");
        }
        repository.deleteById(id);
    }
    
}
