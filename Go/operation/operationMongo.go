package operation

import (
	"context"
	"log"

	"employee.db/myapp/config"
	"employee.db/myapp/model"
	"github.com/google/uuid"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var ctx = context.TODO()

type OperationMongo struct {
}

// All Employees
func (o *OperationMongo) AllEmployees() model.Response {
	var employee model.Employee
	var response model.Response
	var arrEmployee []model.Employee

	collection := config.Connect_Mongo()

	cursor, err := collection.Find(ctx, bson.M{})
	if err != nil {
		log.Fatal(err)
	}
	for cursor.Next(nil) {
		cursor.Decode(&employee)
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
func (o *OperationMongo) InsertEmployee(employee model.Employee) model.Response {
	var response model.Response

	collection := config.Connect_Mongo()

	id := uuid.New().String()
	employee.Id = id
	_, err := collection.InsertOne(ctx, employee)

	if err == nil {
		response.Status = 200
		response.Message = "Success"
	} else {
		response.Status = 404
		response.Message = "Not Found"
	}
	return response
}

// Get Employee
func (o *OperationMongo) GetEmployee(id string) model.Response {
	var employee model.Employee
	var response model.Response

	collection := config.Connect_Mongo()
	filter := bson.M{"id": id}
	opts := options.FindOne()
	opts.SetProjection(bson.M{"_id": 0})
	ans := collection.FindOne(ctx, filter, opts).Decode(&employee)

	// employee = model.Employee{Id: employee.Id, Name: employee.Name, Email: employee.Email, Phone: employee.Phone, Address: employee.Address, Salary: employee.Salary}
	if ans == nil {
		response.Status = 200
		response.Message = "Success"
		response.Data = append(response.Data, employee)
	} else {
		response.Status = 404
		response.Message = "Not Found"
	}
	return response
}

// Update Employee
func (o *OperationMongo) UpdateEmployee(employee model.Employee) model.Response {
	var response model.Response

	collection := config.Connect_Mongo()
	id := employee.Id
	name := employee.Name
	email := employee.Email
	phone := employee.Phone
	address := employee.Address
	salary := employee.Salary

	filter := bson.M{"id": id}
	update := bson.M{"$set": bson.M{"name": name, "email": email, "phone": phone, "address": address, "salary": salary}}

	_, err := collection.UpdateOne(ctx, filter, update)
	if err == nil {
		response.Status = 200
		response.Message = "Success"
	} else {
		response.Status = 404
		response.Message = "Not Found"
	}
	return response
}

// Delete Employee
func (o *OperationMongo) DeleteEmployee(id string) model.Response {
	var response model.Response

	collection := config.Connect_Mongo()
	_, err := collection.DeleteOne(ctx, bson.M{"id": id})

	if err == nil {
		response.Status = 200
		response.Message = "Success"
	} else {
		response.Status = 404
		response.Message = "Not Found"
	}
	return response
}

// Delete Employee by name
func (o *OperationMongo) DeleteEmployeeByName(name string) model.Response {
	var response model.Response

	collection := config.Connect_Mongo()

	_, err := collection.DeleteOne(ctx, bson.M{"name": name})

	if err == nil {
		response.Status = 200
		response.Message = "Success"
	} else {
		response.Status = 404
		response.Message = "Not Found"
	}
	return response
}
