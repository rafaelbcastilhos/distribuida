import requests
import json 

fazer_novo_pedido = 1
consultar_pedidos =  2
fazer_novo_pagamento =  3
consultar_pagamentos =  4
SAIR = 5

header={'Content-type':'application/json', 'Accept':'application/json'}


def func(option: int) -> dict:
    match option:
        case 1:
            descricao = input(' Informe o nome do produto desejado: ')
            quantidade = input('\n Informe a quantidade desejada: ')
            novo_pedido = '{"itens":[{"quantidade": ' + quantidade + ',"descricao": "' + descricao + '"}]}'
            
            req = requests.post("http://localhost:8082/pedidos-ms/pedidos", data=novo_pedido, headers=header)

            if (req.status_code == 201 ):
                print("\n Pedido realizado com sucesso!")
            else:
                print("\n Não foi possível realizar o pedido, tente novamente mais tarde.")

        case 2:
            pedidos = requests.get("http://localhost:8082/pedidos-ms/pedidos")
            pedidos = pedidos.json()
            json_formatted_str = json.dumps(pedidos, indent=2)
            print ("\n", json_formatted_str)
        case 3:
            
            nome = input(' Informe seu nome: ')
            numero_pedido = input('\n Informe um numero de pedido (ex: 12345): ')
            valor = input('\n Informe o valor do pagamento (ex: 450): ')
            novo_pagamento = '{"valor": '+valor+',"nome": "'+nome+'","numero": "'+numero_pedido+'","expiracao": "10/2030","codigo": "12345","formaDePagamentoId": 1,"pedidoId": 1}'
            
            req = requests.post("http://localhost:8082/pagamentos-ms/pagamentos", data=novo_pagamento, headers=header)
            
            if (req.status_code == 201 ):
                print("\n Pagamento realizado com sucesso!")
            else:
                print("\n Não foi possível realizar o pagamento, tente novamente mais tarde.")

        case 4:
            pagamentos = requests.get("http://localhost:8082/pagamentos-ms/pagamentos")
            pagamentos = pagamentos.json()
            json_formatted_str = json.dumps(pagamentos, indent=2)
            print ("\n", json_formatted_str)

def menu():
    print('''
            MENU:

            [1] - Fazer novo pedido
            [2] - Consultar pedidos
            [3] - Fazer pagamento
            [4] - Consultar pagamentos
            [5] - Sair
        ''')
    return int(input('Escolha uma opção: '))

if __name__ == "__main__":
    while (option := menu()) != SAIR:
        func(option)