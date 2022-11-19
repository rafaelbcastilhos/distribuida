package br.com.trabalhofinal.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Integer id;
    private LocalDateTime dataHora;
    private Status status;
    private ArrayList<ItemDoPedido> itens = new ArrayList<>();
}
