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
	router = router.PathPrefix("/employee").Subrouter()
	router.HandleFunc("/all", controller.AllEmployees).Methods("GET")
	router.HandleFunc("/add", controller.InsertEmployee).Methods("POST")
	router.HandleFunc("/{id}", controller.GetEmployee).Methods("GET")
	router.HandleFunc("/{id}", controller.UpdateEmployee).Methods("PUT")
	router.HandleFunc("/remove/id/{id}", controller.DeleteEmployee).Methods("DELETE")
	router.HandleFunc("/remove/name/{name}", controller.DeleteEmployeeByName).Methods("DELETE")
	http.Handle("/", router)
	fmt.Println("Server running at port 8081")
	log.Fatal(http.ListenAndServe(":8081", router))
}
