import mysql.connector
from mysql.connector import errorcode

db_config = {
    'user': 'root',
    'password': 'root',
    'host': 'localhost',
}

DB_NAME = 'travel_db'

try:
    cnx = mysql.connector.connect(**db_config)
    cursor = cnx.cursor()
    cursor.execute(f"CREATE DATABASE {DB_NAME}")
    print(f"Database '{DB_NAME}' created successfully.")
except mysql.connector.Error as err:
    if err.errno == errorcode.ER_DB_CREATE_EXISTS:
        print(f"Database '{DB_NAME}' already exists.")
    else:
        print(err)
finally:
    if 'cursor' in locals() and cursor is not None:
        cursor.close()
    if 'cnx' in locals() and cnx.is_connected():
        cnx.close()
