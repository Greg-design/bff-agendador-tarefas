package com.javanauta.bffagendadortarefas.controller;

import com.javanauta.bffagendadortarefas.business.UsuarioService;
import com.javanauta.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.javanauta.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.javanauta.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.javanauta.bffagendadortarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários") // Aqui no controller documentamos a api com o swagger.
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    public ResponseEntity<UsuarioDTOResponse> salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login Usuários", description = "Login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO usuarioDTO){
       return ResponseEntity.ok(usuarioService.loginUsuario(usuarioDTO));
    }

    @GetMapping
    @Operation(summary = "Buscar dados de Usuários por Email", description = "Buscar dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<UsuarioDTOResponse> buscaUsuarioPorEmail(@RequestParam("email") String email,
                                                                  @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar Usuários por Id", description = "Deleta usuário")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email,
                                                      @RequestHeader(name = "Authorization", required = false) String token){
        usuarioService.deletarUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar dados de Usuários", description = "Atualizar dados de  usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<UsuarioDTOResponse> atualizaDadosUsuario(@RequestBody UsuarioDTORequest dto, @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualiza Endereço de Usuários", description = "Atualiza endereço de usuário")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<EnderecoDTOResponse> atualizaEndereco(@RequestBody EnderecoDTORequest dto, @RequestParam("id") Long id,
                                                                @RequestHeader(name = "Authorization", required = false) String token){
        return  ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto, token));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualiza Telefone de Usuários", description = "Atualiza Telefone de usuário")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<TelefoneDTOResponse> atualizaTelefone(@RequestBody TelefoneDTORequest dto, @RequestParam("id") Long id,
                                                                @RequestHeader(name = "Authorization", required = false) String token){
        return  ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto, token));
    }

    // post para enderecos de usuarios ja cadastrados
    @PostMapping("/endereco")
    @Operation(summary = "Salvar Endereço de Usuários", description = "Salva endereço de usuário")
    @ApiResponse(responseCode = "200", description = "Endereço salvo com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<EnderecoDTOResponse> cadastraEndereco(@RequestBody EnderecoDTORequest dto, @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token, dto));
    }

    // post para telefone de usuarios ja cadastrados
    @PostMapping("/telefone")
    @Operation(summary = "Salvar Telefone de Usuários", description = "Salvar telefone de usuário")
    @ApiResponse(responseCode = "200", description = "Telefone salvo com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de Servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<TelefoneDTOResponse> cadastraTelefone(@RequestBody TelefoneDTORequest dto, @RequestHeader(name = "Authorization", required = false) String token){
        return  ResponseEntity.ok(usuarioService.cadastroTelefone(token, dto));
    }

}
