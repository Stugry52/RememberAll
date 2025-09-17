
fun main(){
    val PlayerHealth = 100
    val PlayerName = "Olega"

    println("$PlayerName начинает путишествие по брейнротопии")
    println("Здоровье: $PlayerHealth")

    val updateHealth = attackMonster(PlayerHealth)
    println("После боя здоровье ваше: $updateHealth")

    val healingPosion = drinkHealingVodka(updateHealth)
    println("Вы восстановили здоровье до: $healingPosion")
}


fun attackMonster(health: Int): Int{
    println(">>>>>>> Начинается битва! <<<<<<<")
    // Имитация получения урона в бое с монстром
    val damage = 25
    val newHealth = health -damage
    println("Получено урона: $damage")

    //Возвращаем результат функции
    return newHealth // Возвращаем обновленое здоровье
}

fun drinkHealingVodka(health: Int): Int{
    println(">>>>>>>> Вы выпили святую водку <<<<<<<")
    val healing = 30
    val newHealth = health + healing
    println("Восстановили здоровье: $healing")
    return newHealth
}