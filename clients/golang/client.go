package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

func main() {

	fmt.Println(string("Todos os pedidos no sistema:\n"))

	api := "http://localhost:8082/pedidos-ms/pedidos"

	response, _ := http.Get(api)

	responseData, _ := ioutil.ReadAll(response.Body)

	fmt.Println(string(responseData))

	fmt.Println(string("\nTodos os pagamentos no sistema:\n"))

	api2 := "http://localhost:8082/pagamentos-ms/pagamentos"

	response2, _ := http.Get(api2)

	responseData2, _ := ioutil.ReadAll(response2.Body)

	fmt.Println(string(responseData2))

}
