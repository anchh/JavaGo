package operation

import (
	"context"
	"log"

	"employee.db/myapp/config"
	"employee.db/myapp/model"
	"github.com/google/uuid"
)

type OperationSQL struct {
}

func (os *OperationSQL) AllEmployees() model.Response {
	var employee model.Employee
	var response model.Response
	var arrEmployee []model.Employee

	db := config.Connect_SQL()
	defer db.Close()
	rows, err := db.Query("SELECT * FROM employee")

	if err != nil {
		log.Print(err.Error())
	}

	for rows.Next() {
		err = rows.Scan(&employee.Id, &employee.Name, &employee.Email, &employee.Phone, &employee.Address, &employee.Salary)
		if err != nil {
			log.Print(err.Error())
			continue
		}
		arrEmployee = append(arrEmployee, employee)
	}

	if err == nil {
		response.Status = 200
		response.Message = "Success"
		response.Data = arrEmployee
	} else {
		response.Status = 404
		response.Message = "Not Found"
	}
	return response
}

// Insert Employee
func (os *OperationSQL) InsertEmployee(employee model.Employee) model.Response {
	var response model.Response

	db := config.Connect_SQL()
	defer db.Close()

	id := uuid.New().String()
	name := employee.Name
	email := employee.Email
	phone := employee.Phone
	address := employee.Address
	salary := employee.Salary

	add_query := "INSERT into employee(id, name, email, phone, address, salary) VALUES(?,?,?,?,?,?)"
	_, err := db.ExecContext(context.Background(), add_query, id, name, email, phone, address, salary)

	if err != nil {
		log.Print(err.Error())
		response.Status = 500
		response.Message = "Failed to Insert"
		response.Data = nil
	} else {
		response.Status = 200
		response.Message = "Successfully Inserted"
	}
	return response
}

// Delete Employee
func (os *OperationSQL) DeleteEmployee(id string) model.Response {
	var response model.Response

	db := config.Connect_SQL()
	defer db.Close()

	delete_query := "DELETE FROM employee WHERE id = ?"
	_, err := db.ExecContext(context.Background(), delete_query, id)

	if err != nil {
		log.Print(err.Error())
		response.Status = 500
		response.Message = "Failed to Delete"
		response.Data = nil
	} else {
		response.Status = 200
		response.Message = "Successfully Deleted"
	}
	return response
}

// Update Employee
func (os *OperationSQL) UpdateEmployee(employee model.Employee) model.Response {
	var response model.Response

	db := config.Connect_SQL()
	defer db.Close()

	id := employee.Id
	name := employee.Name
	email := employee.Email
	phone := employee.Phone
	address := employee.Address
	salary := employee.Salary

	update_query := "UPDATE employee SET name = ?, email = ?, phone = ?, address = ?, salary = ? WHERE id = ?"
	_, err := db.ExecContext(context.Background(), update_query, name, email, phone, address, salary, id)

	if err != nil {
		log.Print(err.Error())
		response.Status = 500
		response.Message = "Failed to Update"
		response.Data = nil
	} else {
		response.Status = 200
		response.Message = "Successfully Updated"
	}
	return response
}

// Get Employee by ID
func (os *OperationSQL) GetEmployee(id string) model.Response {
	var employee model.Employee
	var response model.Response

	db := config.Connect_SQL()
	defer db.Close()

	get_query := "SELECT * FROM employee WHERE id = ?"
	err := db.QueryRowContext(context.Background(), get_query, id).Scan(&employee.Id, &employee.Name, &employee.Email, &employee.Phone, &employee.Address, &employee.Salary)

	if err != nil {
		log.Print(err.Error())
		response.Status = 404
		response.Message = "Not Found"
		response.Data = nil
	} else {
		response.Status = 200
		response.Message = "Success"
		response.Data = append(response.Data, employee)
	}
	return response
}

// Delete Employee by name
func (os *OperationSQL) DeleteEmployeeByName(name string) model.Response {
	var response model.Response

	db := config.Connect_SQL()
	defer db.Close()

	delete_query := "DELETE FROM employee WHERE name = ?"
	_, err := db.ExecContext(context.Background(), delete_query, name)

	if err != nil {
		log.Print(err.Error())
		response.Status = 500
		response.Message = "Failed to Delete"
		response.Data = nil
	} else {
		response.Status = 200
		response.Message = "Successfully Deleted"
	}
	return response
}
