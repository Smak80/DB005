fun main() {
    try {
        with(DBHelper()){
            createDb()
            createDbStructure()
        }

    } catch (e: Exception){
        println("Ошибка: ${e.message}")
    }
}