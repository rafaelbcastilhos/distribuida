package br.com.trabalhofinal.pagamentos.dto;

import br.com.trabalhofinal.pagamentos.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoDto {
    private Integer id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Integer formaDePagamentoId;
    private Integer pedidoId;


}
