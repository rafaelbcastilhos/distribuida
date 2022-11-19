package br.com.trabalhofinal.pedidos.amqp;

import br.com.trabalhofinal.pedidos.dto.PagamentoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {
    @RabbitListener(queues = "pagamento.concluido")
    public void recebeMensagem(@Payload PagamentoDto pagamento) throws Exception {
        String mensagem = "\nNúmero do pedido: " + pagamento.getPedidoId() + "\nValor R$: " + pagamento.getValor() + "\nStatus: " + pagamento.getStatus() +"\n";
        System.out.println("Recebi a mensagem " + mensagem);
    }
}
