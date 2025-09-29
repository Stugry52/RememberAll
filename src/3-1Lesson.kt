class Character{
    // Свойство класса задаются либо в (скобочках) либо внутри через переменные

    var name: String = "Безымнный"
    var health: Int = 100
    val maxHealth: Int = 100
    var attack: Int = 13
    var isAlive: Boolean = true
    var ulta: Int = 80
    var maxUlta: Int = 100

    // Методы класса - описывают поведение объкта класса
    // (damage: Int) - параметр метода

    fun attackDamage(damage: Int){
        health -= damage
        println("$name получает $damage урона!")

        // Ссылка на текущий объект
        if (this.health <= 0){
            isAlive = false
            println("$name, был  да сплыл")
        }
    }

    fun heal(amount: Int){
        if (isAlive){
            //Убедимся, что здоровье при хиле не привысит максимум
            health = minOf(health + amount, maxHealth)
            println("$name восстанавливает $amount здоровья. Теперь здоровье: $health")
        }else{
            println("$name не может быть исцелен")
        }
    }
    // Метод атаки другого персонажа
    fun attack(target: Character){
        if (!isAlive){
            println("$name не может атаковать, поскольку он всё")
            return // немедленый выход из метода
        }

        val damage = calculateDamage(attack)
        println("$name атакуем ${target.name}")
        target.attackDamage(damage)
        ulta()
    }
    fun ulta(){
        if (attack >= 20){
               ulta += 20
        }
        if (ulta == maxUlta){
            println("Ваша супер атака заряжана, сделай фаталити!")
        }
    }
}

fun main(){
    println("==== СОЗДАНИЕ ПЕРСОНАЖЕЙ ЧЕРЕЗ КЛАССЫ ====")

    // Создание объекты (Экземпляры класса Character)

    val player = Character()
    val monster = Character()

    player.name = "Артём"
    player.health = 100
    player.attack = 18

    monster.name = "Nikitos-Zombi"
    monster.health = 60
    monster.attack = 22

    // вызываем методы объектов
    println(">>> Начала боя <<<")
    player.attack(monster)
    monster.attack(player)

    println("--- Состояние после первого боя ---")
    println("Игрок: ${player.name} имеет ${player.health} HP, ЖИВ? ${player.isAlive}")
    println("Никитос: ${monster.name} имеет ${monster.health} HP, ЖИВ? ${monster.isAlive}")

    println("--- Игрок нашол зелье ---")
    player.heal(25)
}