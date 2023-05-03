import java.sql.DriverManager

class DBHelper(
    host: String = "localhost",
    port: Int = 3306,
    user: String = "root",
    password: String = "",
) {
    private val conn = DriverManager.getConnection("jdbc:mysql://$host:$port", user, password)
    fun createDb(){
        val sql1 = "CREATE SCHEMA IF NOT EXISTS `db005`"
        val sql2 = "USE `db005`"
        conn.createStatement().apply{
            addBatch(sql1)
            addBatch(sql2)
            executeBatch()
        }
    }

    fun createDbStructure(){
        val sql = "CREATE TABLE IF NOT EXISTS `customers` " +
                "(phone BIGINT not null primary key," +
                "name VARCHAR(50) not null," +
                "password VARCHAR(512) not null)"
        conn.createStatement().execute(sql)
    }

    fun addCustomer(customer: Customer){

    }
}