import com.lambdaworks.crypto.SCryptUtil

class Customer(
    phone: Long,
    name: String,
    password: String? = null,
    passwordHash: String? = null,
){
    var phone: Long = phone
    var name: String = name
    var password: String
        get() = hash
        set(value) {
            hash = SCryptUtil.scrypt(value, 512, 16, 16)
        }

    var hash: String = ""

    init{
        password?.let { this.password = it } ?: passwordHash?.let { hash = it }
    }

    override fun toString() = "$phone: $name ($password)"
    fun checkPass(password: String) = SCryptUtil.check(password, this.password)
}