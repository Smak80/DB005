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
        val sql = "INSERT INTO `customers` (`phone`, `name`, `password`) " +
                "VALUES (?, ?, ?)"
        conn.prepareStatement(sql).apply {
            setLong(1, customer.phone)
            setString(2, customer.name)
            setString(3, customer.password)
            executeUpdate()
        }
    }

    fun clearCustomers() = conn.createStatement().executeUpdate("DELETE FROM `customers`")

    val allCustomers: List<Customer>
        get() {
            val sql = "SELECT * FROM `customers`"
            val cust = mutableListOf<Customer>()
            with(conn.createStatement().executeQuery(sql)){
                while (next()){
                    cust.add(Customer(
                        getLong("phone"),
                        getString("name"),
                        passwordHash = getString("password")
                    ))
                }
                close()
            }
            return cust
        }

    fun getCustomerByPhone(phone: Long): Customer? {
        val sql = "SELECT * FROM `customers` WHERE `phone`=?"
        return conn.prepareStatement(sql).run {
            setLong(1, phone)
            executeQuery()
        }.run{
            if (next()) Customer(
                getLong("phone"),
                getString("name"),
                passwordHash = getString("password")
            ) else null
        }
    }
}