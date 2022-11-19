package br.com.trabalhofinal.pedidos.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoPedidoDto {
    private Integer quantidade;
    private String descricao;
}
