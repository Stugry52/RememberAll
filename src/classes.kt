class Player{
    var name: String = "Беззымяный герой" // Свойствоб, атрибуты, параметры - которые определяют функцию
    var health: Int = 100
    var damage: Int = 1
    var isAlive: Boolean = true

    // Методы = Это функции принадлежащие классу
    // Они описывают поведение объекта

    // damage - параметр метода takeDamage
    fun takeDamage(damage: Int){
        health -= damage
        println("$name получает $damage урона! Осталось здоровья: $health")

        // this - ссылка на текущий объект в методе
        if (this.health <= 0){
            isAlive = false
            println("ПОТЕРЕНО")
        }
    }

    fun heal(amount: Int){
        if (isAlive){
            health += amount
            println("Здоровье $name, востановилось на $amount. Теперь у него $health HP")
        }else {
            println("Исцеление не возможно")
        }
    }
}

fun main(){
    // Создание объекта (экземпляр класса) Player
    val warrior = Player()
    warrior.name = "Варио"
    println("Игрок ${warrior.name} появился в мире, здоровье: ${warrior.health}")

    // Точка это вывод объекта
    warrior.takeDamage(30) // Герой
    warrior.heal(10)
}