import kotlin.random.Random
// Random дает возможность подставлять функции для генерации случайных чисел

fun main() {
    val PlayerDamage: Int = 50
    val MonsterHp: Int = 500

    println("На вас напал брейнрот монст")

    // Копирование переменной со здоровьем монстра для изменения

    var currentMonsterHp = MonsterHp

    while (currentMonsterHp > 0){ // Random.nextInt() Функция возвращающая случайное int число
        val damageVariation = Random.nextInt(80, 121) // Число от 80 до 120
        val actualDamage = (PlayerDamage * damageVariation) / 100 // Вычисляем фактический урон


        currentMonsterHp -= actualDamage
        println("Вы наносите монстру $actualDamage урона!")
        if (currentMonsterHp <=0){
            println("Вы победили Брейнротопия не останется в долгу")
        } else{
            println("Монстр все ещё на ногах с $currentMonsterHp здоровья")
        }
    }
}