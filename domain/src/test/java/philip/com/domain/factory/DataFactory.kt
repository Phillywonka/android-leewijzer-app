package philip.com.domain.factory

import java.util.concurrent.ThreadLocalRandom

/**
 * Factory class for data instances
 */
class DataFactory {

    companion object Factory {

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomLong(): Long {
            return randomInt().toLong()
        }

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomBoolean(): Boolean {
            return Math.random() < 0.5
        }
    }

}