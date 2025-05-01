package seguranca.projeto.services;

import java.util.List;

import seguranca.projeto.dtos.organizacao.*;


public interface OrganizacaoServices {

    public List<OrganizacaoResponseDto> findAll();

    public OrganizacaoResponseDto findById(Long id);

    public OrganizacaoResponseDto create(OrganizacaoRequestDto request);

    public OrganizacaoResponseDto update(Long id, OrganizacaoRequestDto request);

    public void delete(Long id);
}
