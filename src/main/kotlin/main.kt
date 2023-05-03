fun main() {
    try {

        val customers = listOf<Customer>(
            Customer(9271110500L, "Иванов И. И.", "123456"),
            Customer(9171110890L, "Петров П. П.", "12345678"),
            Customer(9991110999L, "Курочкина К. К.", "qwerty"),
        )

        with(DBHelper()){
            createDb()
            createDbStructure()
            clearCustomers()
            customers.forEach { c -> addCustomer(c) }
            allCustomers.forEach {
                println(it)
            }
            println()
            println(getCustomerByPhone(9271110500L))
            println(getCustomerByPhone(9000000000L))
            println()
            println(getCustomerByPhone(9991110999L)?.checkPass("qwerty"))
            println(getCustomerByPhone(9991110999L)?.checkPass("123456"))
        }

    } catch (e: Exception){
        println("Ошибка: ${e.message}")
    }
}