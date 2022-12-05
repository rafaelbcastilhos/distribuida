package br.com.trabalhofinal.pagamentos.controller;

import br.com.trabalhofinal.pagamentos.dto.PagamentoDto;
import br.com.trabalhofinal.pagamentos.service.PagamentoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping()
    public List<PagamentoDto> listarTodos() {
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Integer id) throws Exception {
        PagamentoDto dto = service.obterPorId(id);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        PagamentoDto pagamento = service.criarPagamento(dto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        rabbitTemplate.convertAndSend("pagamento.concluido", dto);

        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Integer id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto atualizado = service.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Integer id) {
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public void confirmarPagamento(@PathVariable @NotNull Integer id) throws Exception {
        service.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Integer id, Exception e) throws Exception {
        service.alteraStatus(id);
    }
}
