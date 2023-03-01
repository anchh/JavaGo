package controller

import (
	"encoding/json"
	"net/http"

	"employee.db/myapp/model"
	"employee.db/myapp/operation"
	"github.com/gorilla/mux"
)

// Select all employees
func AllEmployees(w http.ResponseWriter, r *http.Request) {

	var response model.Response
	params := r.URL.Query()
	db_name := params.Get("db")

	if db_name == "mongo" {
		// Connect to Mongo
		response = operation.AllEmployees_Mongo()
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
		response = operation.InsertEmployee_Mongo(employee)
	} else {
		// Connect to SQL
		response = operation.InsertEmployee_SQL(employee)
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}

// Delete Employee
func DeleteEmployee(w http.ResponseWriter, r *http.Request) {
	q_field := r.URL.Query()
	db_name := q_field.Get("db")
	var response model.Response
	params := mux.Vars(r)
	id := params["id"]
	if db_name == "mongo" {
		// Connect to Mongo
		response = operation.DeleteEmployee_Mongo(id)
	} else {
		// Connect to SQL
		response = operation.DeleteEmployee_SQL(id)
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}

// Update Employee
func UpdateEmployee(w http.ResponseWriter, r *http.Request) {
	var response model.Response

	var employee model.Employee
	err := json.NewDecoder(r.Body).Decode(&employee)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	params := mux.Vars(r)
	id := params["id"]
	employee.Id = id
	q_field := r.URL.Query()
	db_name := q_field.Get("db")
	if db_name == "mongo" {
		// Connect to Mongo
		response = operation.UpdateEmployee_Mongo(employee)
	} else {
		// Connect to SQL
		response = operation.UpdateEmployee_SQL(employee)
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}

// Select employee by id
func GetEmployee(w http.ResponseWriter, r *http.Request) {
	var response model.Response

	q_field := r.URL.Query()
	db_name := q_field.Get("db")

	params := mux.Vars(r)
	id := params["id"]
	if db_name == "mongo" {
		// Connect to Mongo
		response = operation.GetEmployee_Mongo(id)
	} else {
		// Connect to SQL
		response = operation.GetEmployee_SQL(id)
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}

// Delete employee by name
func DeleteEmployeeByName(w http.ResponseWriter, r *http.Request) {
	var response model.Response

	q_field := r.URL.Query()
	db_name := q_field.Get("db")

	params := mux.Vars(r)
	name := params["name"]
	if db_name == "mongo" {
		// Connect to Mongo
		response = operation.DeleteEmployeeByName_Mongo(name)
	} else {
		// Connect to SQL
		response = operation.DeleteEmployeeByName_SQL(name)
	}

	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	json.NewEncoder(w).Encode(response)

}
