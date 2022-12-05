package br.com.trabalhofinal.pagamentos.service;

import br.com.trabalhofinal.pagamentos.dto.PagamentoDto;
import br.com.trabalhofinal.pagamentos.http.PedidoClient;
import br.com.trabalhofinal.pagamentos.model.Pagamento;
import br.com.trabalhofinal.pagamentos.model.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {
    int counter = 1;
    HashMap<Integer, Pagamento> pagamentos = new HashMap<>();
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoClient pedido;

    public List<PagamentoDto> obterTodos() {
        return pagamentos.values().stream()
                .map(p -> modelMapper.map(p, PagamentoDto.class))
                .collect(Collectors.toList());
    }

    public PagamentoDto obterPorId(Integer id) throws Exception {
        Pagamento pagamento = pagamentos.get(id);

        if (pagamento == null) {
            throw new Exception("Pagamento não encontrado");
        }

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(counter);
        pagamento.setStatus(Status.CRIADO);
        pagamentos.put(counter, pagamento);
        counter += 1;

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Integer id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamentos.put(id, pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Integer id) {
        pagamentos.remove(id);
    }

    public void confirmarPagamento(Integer id) throws Exception {
        Pagamento pagamento = pagamentos.get(id);

        if (pagamento == null) {
            throw new Exception("Pagamento não encontrado");
        }

        pagamento.setStatus(Status.CONFIRMADO);
        pagamentos.put(id, pagamento);
    }


    public void alteraStatus(Integer id) throws Exception {
        Pagamento pagamento = pagamentos.get(id);

        if (pagamento == null) {
            throw new Exception("Pagamento não encontrado");
        }

        pagamento.setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentos.put(id, pagamento);

    }
}

