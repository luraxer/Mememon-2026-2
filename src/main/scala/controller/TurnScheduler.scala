package cl.uchile.dcc
package controller

import scala.collection.mutable.ListBuffer

class TurnScheduler:
  // ListBuffer is a native Scala mutable list, perfect for adding/removing units dynamically.
  private val unitsInBattle: ListBuffer[Entity] = ListBuffer()

  def addUnit(unit: Entity): Unit =
    unitsInBattle += unit

  def getMaxActionBar(unit: Entity): Double =
    unit match
      case character: AbsCharacter =>
        // If the character has a weapon, we get its weight. If not, it defaults to 0.
        val weaponWeight = character.equippedWeapon.map(_.weaponWeight).getOrElse(0)
        character.weight + (0.5 * weaponWeight)

      case enemy: Enemy =>
        enemy.weight.toDouble

      case fallback: Entity =>
        fallback.weight.toDouble