package org.example.lessons.lesson04.homework

import kotlin.random.Random

class Inventory () {
    private val items =  mutableListOf<String>()
    operator fun plus(item: String){
         items.add(item)
    }
    operator fun get(i: Int): String {
        return items[i]
    }
    operator fun contains(item: String) : Boolean {
        return items.contains(item)
    }
}

class Toggle(val enabled: Boolean){
    operator fun not(): Toggle{
        return Toggle(!enabled)
    }
}

class Price (val amount: Int){
    operator fun times(items: Int): Price{
        return Price(amount*items)
    }
}

class Step(val number: Int) {

    operator fun rangeTo(other: Step): IntRange {
        return number..other.number
    }
}

operator fun IntRange.contains(step: Step): Boolean {
    return step.number in this
}

class Person(private val name: String) {

    private val phrases = mutableListOf<String>()

    fun print() {
        println(phrases.joinToString(" "))
    }

    private fun selectPhrase(first: String, second: String): String {
        val random = Random.nextInt(0, 2)
        return if (random == 0) first else second
    }

    infix fun says (phrase: String) : Person{
        phrases.add(phrase)
        return this
    }

    infix fun and (phrase: String) : Person{
        check(phrases.isNotEmpty()) { "Сначала используй says" }
        phrases.add(phrase)
        return this
    }

    infix fun or (phrase: String) : Person{
        check(phrases.isNotEmpty()) { "Сначала используй says" }
        phrases[phrases.lastIndex] = selectPhrase(phrases[phrases.lastIndex], phrase)
        return this
    }
}


fun main() {
    val inventory = Inventory()
    inventory + "стул"
    inventory + "стол"
    println(inventory[1])
    println("комод" in inventory)
    val toggle = Toggle(true)
    println(!toggle.enabled)
    val price = Price(10)
    val newPrice = price.times(10)
    println(newPrice.amount)
    val stepFrom = Step(4)
    val stepTo = Step(10)
    val stepBetween = Step(6)
    val range = stepFrom..stepTo
    println(range.joinToString())
    println(stepBetween in range)

    val andrew = Person("Andrew")
    andrew says "Hello" and "brothers." or "sisters." and "I believe" and "you" and "can do it" or "can't"
    andrew.print()
}