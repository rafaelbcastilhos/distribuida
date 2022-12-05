# Trabalho final
Aplicação de pedidos e pagamentos genéricos utilizando SpringBoot utilizando Java 8 e RabbitMQ

Componentes da arquitetura:
![Arquitetura](https://raw.githubusercontent.com/rafaelbcastilhos/distribuida/main/arquitetura.png)

## Message-Broker (RabbitMQ): 
Atua como intermediário na gestão do envio e recebimento das mensagens, utilizando AMQP (Advanced Messaging Queue Protocol).

Pode ser executado com:
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
```
E acessado no endereço http://localhost:15672
Usuário: guest 
Senha: guest

## Server: 
Projeto que serve como service discovery, utilizando Eureka Server. Após a sua execução, o acesso é feito por meio do endereço http://localhost:8081
Pode ser construído com:
```
mvn clean package
```

E executado com:
```
mvn spring-boot:run
```

## Gateway: 
Projeto que atua como API Gateway, também utilizando ferramentas do Spring Cloud. Centraliza entrada dos diferentes microsserviços para o cliente (Transparência). Após a sua execução, o acesso dos microsserviços é dado por meio do endereço http://localhost:8082
Pode ser construído com:
```
mvn clean package
```

E executado com:
```
mvn spring-boot:run
```

## Pagamentos:
Microsserviço de pagamentos com armazenamento em memória. Após executar o Server, o Gateway e esse projeto, o acesso é feito por meio do endereço http://localhost:8082/pagamentos-ms/pagamentos.
Pode ser construído com:
```
mvn clean package
```

E executado com:
```
mvn spring-boot:run
```

O exemplo de corpo na requisição de http://localhost:8082/pagamentos-ms/pagamentos é:
```
{
    "valor": 450,
    "nome": "João",
    "numero": "123456",
    "expiracao": "10/2030",
    "codigo": "12345",
    "formaDePagamentoId": 1,
    "pedidoId": 1
}
```

## Pedidos: 
Microsserviço de pedidos com armazenamento em memória. Após executar o Server, o Gateway e esse projeto, o acesso é realizado pelo endereço http://localhost:8082/pedidos-ms/pedidos
Pode ser construído com:
```
mvn clean package
```

E executado com:
```
mvn spring-boot:run
```

O microsserviço de pedidos, pode possuir mais de uma instância, para isso é necessário executar em mais de um terminal (Escalabilidade/Balanceamento de carga). Para verificar em qual instância está sendo executado, é necessário requisitar o endereço http://localhost:8082/pedidos-ms/pedidos/porta

O exemplo de corpo na requisição de http://localhost:8082/pedidos-ms/pedidos é:
```
{
    "itens":[{
        "quantidade": 3,
        "descricao": "Mouse Logitech"
    },
    {
        "quantidade": 5,
        "descricao": "Fone JBL"
    }]
}
```