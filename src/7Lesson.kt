import kotlin.random.Random

open class Character(val name: String, var health: Int, val attack: Int){
    val isAlive: Boolean get() = health > 0

    fun takeDamage(damage: Int){
        health -= damage
        println("$name получает $damage")
        if(health <= 0) println("$name пал в бою!")
    }

    open fun attack(target: Character){
        if (!isAlive || !target.isAlive) return
        val damage = Random.nextInt(attack - 3, attack + 4) // Случайный урон в диапозоне
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }
}

class Player(name: String, health: Int, attack: Int, var shield: Boolean = false): Character(name, health, attack){
    var potions = 3 // Количество зелий здоровья (индивидуальный аттрибут(параметр) дочернего класса)

    fun usePotions(){
        if (potions > 0){
            val healAmount = 30
            health += healAmount
            potions--
            println("$name использует зелье, +${healAmount} теперь у него $health HP")
        }else{
            println("У $name нет зелий БЕГИ ФОРЕСТ БЕГИ!!!")
        }
    }

    fun printStatus(){
        println("=== $name ===")
        println("HP: $health")
        println("ATK: $attack")
        println("Зелья: $potions")
        println("==============")
    }

    fun useShield(){
        println("$name использовал щит, урон понижен в двое")
        shield = true
    }

    override fun attack(target: Character) {
        if (shield == true){
            
        }
        super.attack(target)
    }
}

class GameInput{
    // Функция получения числа от пользователя
    fun getNumberInput(prompt: String, min: Int = 1, max: Int = 10): Int{
        while (true){ // Бесконечный цикл - пока мы его не прервем (не получим от пользователя верный ввод)
            print(prompt)

            try {
                // readln() - читает ввод пользователя (ждет пока пользователь нажмет Enter)
                val input = readln()
                // .toInt() - пытается перевести ввод в число
                val number = input.toInt()

                //Проверка попадает ли число в допустимый диапозон min max
                if (number in min..max){
                    return number // Возвращает корректное число
                }else{
                    println("Ввод не коректный, попробуйте снова")
                }
            }catch (e: NumberFormatException){
                // try - catch механизм обработки ошибок и исключений в коде
                // Если toInt не смог преобразовать строку в число (например введены буквы) то срабтает это исключение
                // NumberFormatException - неверный формат числа
                println("Ошибка! Ниче тот факт, что нужно ввести число")
            }
        }
    }

    fun getYesNoInput(promt: String): Boolean{
        while (true){
            print("$promt (д/н):")
            val input = readln().lowercase() // Приводит строку к нижнему регистру

            when(input){
                "д", "да","y", "yes" -> return true
                "н","нет","n","no" -> return false
                else -> print("Пожалуства введите да или нет или катись отсюда")
            }
        }
    }
}

fun main(){
    println("=== Система Ввода даных === ")

    val gameInput = GameInput()
    val oleg = Player("Олежа", 100, 15)
    val boris = Player("boris", 100, 20)

    println("Создание вашего персонажа: ")
    println("Введите имя игрока: ")
    val playerName = readln()
    // Пересоздаем игрока с введенным именем
    val customPlayer = Player(if (playerName.isBlank()) "Безымянный" else playerName, 100 ,15)
    val playerLevel = gameInput.getNumberInput("Введите уровень сложности (1-легкоб 5-сложно): ", 1, 5)
    println("выбран уровень сложности: $playerLevel, нет пути обработки! >:)")

    var gameRunning = true

    while (gameRunning){
        println("=== Главное меню ===")
        println("1. Посмотреть статус")
        println("2. Использовать зелье")
        println("3. Использовать щит")
        println("4. Сразится")
        println("5. Выйти из игры")

        val choise = gameInput.getNumberInput("Введите: 1, 2, 3, 4, 5")
        when(choise){
            1 -> customPlayer.printStatus()
            2 -> customPlayer.usePotions()
            3 -> customPlayer.useShield()
            4 -> boris.attack(customPlayer)
            5 -> {
                gameRunning = false
                println("Выход из игры... бб")
            }
        }
    }

    println("Спасибо за невероятную игру, ты же вернешься?")

}