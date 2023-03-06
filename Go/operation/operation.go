package operation

import (
	"employee.db/myapp/model"
)

type Operation interface {
	AllEmployees() model.Response
	InsertEmployee(employee model.Employee) model.Response
	UpdateEmployee(employee model.Employee) model.Response
	DeleteEmployee(id string) model.Response
	GetEmployee(id string) model.Response
	DeleteEmployeeByName(name string) model.Response
}
