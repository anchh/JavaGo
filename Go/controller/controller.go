package controller

import (
	"encoding/json"
	"net/http"

	"employee.db/myapp/model"
	"employee.db/myapp/operation"
)

// Select all employees
func AllEmployees(w http.ResponseWriter, r *http.Request) {

	var response model.Response
	params := r.URL.Query()
	db_name := params.Get("db")

	if db_name == "mongo" {
		// Connect to Mongo
	}
	if db_name == "sql" {
		// Connect to SQL
		response = operation.AllEmployees_SQL()
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}

// Insert Employee
func InsertEmployee(w http.ResponseWriter, r *http.Request) {
	var response model.Response

	var employee model.Employee
	err := json.NewDecoder(r.Body).Decode(&employee)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	params := r.URL.Query()
	db_name := params.Get("db")
	if db_name == "mongo" {
		// Connect to Mongo
	} else {
		// Connect to SQL
		response = operation.InsertEmployee_SQL(employee)
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}
