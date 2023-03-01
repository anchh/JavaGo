package operation

import (
	"context"
	"log"

	"employee.db/myapp/config"
	"employee.db/myapp/model"
	"github.com/google/uuid"
)

func AllEmployees_SQL() model.Response {
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
func InsertEmployee_SQL(employee model.Employee) model.Response {
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
