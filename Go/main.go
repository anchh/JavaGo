package main

import (
	"fmt"
	"log"
	"net/http"

	"employee.db/myapp/controller"
	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/mux"
)

func main() {
	router := mux.NewRouter()
	router.HandleFunc("/employee/all", controller.AllEmployees).Methods("GET")
	router.HandleFunc("/employee/add", controller.InsertEmployee).Methods("POST")
	router.HandleFunc("/employee/{id}", controller.GetEmployee).Methods("GET")
	router.HandleFunc("/employee/{id}", controller.UpdateEmployee).Methods("PUT")
	router.HandleFunc("/employee/remove/id/{id}", controller.DeleteEmployee).Methods("DELETE")
	router.HandleFunc("/employee/remove/name/{name}", controller.DeleteEmployeeByName).Methods("DELETE")
	http.Handle("/", router)
	fmt.Println("Server running at port 8080")
	log.Fatal(http.ListenAndServe(":8080", router))
}
