package run.smt.karafrunner.util

sealed class Filter<T> {
    companion object {
        fun <T> all(): Filter<T> = All()
        fun <T> none(): Filter<T> = None()
        fun <T> some(indexes: Iterable<Int>) = SomeIndexes<T>(indexes)
        fun <T> allExcept(indexes: Iterable<Int>) = ExcludeIndexes<T>(indexes)
        fun <T> first() = SomeIndexes<T>(listOf(0))
    }

    abstract fun applyTo(someList: Iterable<T>): Iterable<T>

    class All<T>: Filter<T>() {
        override fun applyTo(someList: Iterable<T>): Iterable<T> {
            return someList
        }
    }

    class None<T>: Filter<T>() {
        override fun applyTo(someList: Iterable<T>): Iterable<T> {
            return emptyList()
        }
    }

    class SomeIndexes<T>(private val some: Iterable<Int>): Filter<T>() {
        override fun applyTo(someList: Iterable<T>): Iterable<T> {
            return someList.filterIndexed { index, value -> index in some }
        }
    }

    class ExcludeIndexes<T>(private val exclusion: Iterable<Int>): Filter<T>() {
        override fun applyTo(someList: Iterable<T>): Iterable<T> {
            return someList.filterIndexed { index, value -> index !in exclusion }
        }
    }
}