package config

import (
	"database/sql"
)

func Connect_SQL() *sql.DB {
	dbDriver := "mysql"
	dbUser := "root"
	dbPass := "anchal31"
	dbName := "employees"

	db, err := sql.Open(dbDriver, dbUser+":"+dbPass+"@/"+dbName)
	if err != nil {
		panic(err.Error())
	}
	return db
}