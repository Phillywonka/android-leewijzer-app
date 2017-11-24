package philip.com.leeswijzer_app

/**
 * @author Philip Wong
 * @since 21-11-17
 **/
data class Money(val amount: Int, val currency: String)

fun main(args: Array<String>) {
    val tickets = Money(100, "$")
    val popCorn = tickets.copy(amount = 500, currency = "€")

    if (tickets != popCorn) {
        println("They are different!")
    }


}
