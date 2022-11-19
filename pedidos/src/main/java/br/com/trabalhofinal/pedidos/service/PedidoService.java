package br.com.trabalhofinal.pedidos.service;

import br.com.trabalhofinal.pedidos.dto.ItemDoPedidoDto;
import br.com.trabalhofinal.pedidos.dto.PedidoDto;
import br.com.trabalhofinal.pedidos.dto.StatusDto;
import br.com.trabalhofinal.pedidos.model.ItemDoPedido;
import br.com.trabalhofinal.pedidos.model.Pedido;
import br.com.trabalhofinal.pedidos.model.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {
    int counter = 1;
    HashMap<Integer, Pedido> pedidos = new HashMap<>();

    @Autowired
    private final ModelMapper modelMapper;

    public List<PedidoDto> obterTodos() {
        return pedidos.values().stream()
                .map(p -> modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Integer id) {
        Pedido pedido = pedidos.get(id);

        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);

        pedido.setId(counter);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);

        ArrayList<ItemDoPedido> itemsPedido = new ArrayList<>();
        for(ItemDoPedidoDto pedidoDto : dto.getItens()) {
            ItemDoPedido item = modelMapper.map(pedidoDto, ItemDoPedido.class);
            itemsPedido.add(item);
        }
        pedido.setItens(itemsPedido);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        pedidos.put(counter, pedido);
        counter += 1;

        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto atualizaStatus(Integer id, StatusDto dto) throws Exception {
        Pedido pedido = pedidos.get(id);

        if (pedido == null) {
            throw new Exception("Pedido não encontrado");
        }

        pedido.setStatus(dto.getStatus());
        pedidos.put(id, pedido);
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public void aprovaPagamentoPedido(Integer id) throws Exception {

        Pedido pedido = pedidos.get(id);

        if (pedido == null) {
            throw new Exception("Pedido não encontrado");
        }

        pedido.setStatus(Status.PAGO);
        pedidos.put(id, pedido);
    }
}
