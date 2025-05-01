package seguranca.projeto.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import seguranca.projeto.dtos.users.UsuarioRequestDto;
import seguranca.projeto.dtos.users.UsuarioResponseDto;
import seguranca.projeto.services.UsuarioServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    private final UsuarioServices service;

    @GetMapping
    public List<UsuarioResponseDto> findAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public UsuarioResponseDto findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDto create(@RequestBody UsuarioRequestDto request){
        return service.create(request);
    }

    @PutMapping("{id}")
    public UsuarioResponseDto update(@PathVariable Long id, @RequestBody UsuarioRequestDto request){
        return service.update(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
         service.delete(id);
    }
}