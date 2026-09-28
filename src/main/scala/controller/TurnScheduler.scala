package cl.uchile.dcc
package controller

import scala.collection.mutable

/**
 * A class responsible for managing the battle turns.
 *
 * @constructor Creates an empty TurnScheduler ready to manage battle units.
 * @author Lucas  Farfán
 * @version 1.0.0
 * @since 1.0.0
 */
class TurnScheduler:
  /** Array storing the entities currently participating in the battle. */
  private var unitsInBattle: Array[Entity] = Array()

  /** A mapping of each entity to its current action bar progress. */
  private val actionBars: mutable.Map[Entity, Double] = mutable.Map()

  /**
   * Adds a new unit to the scheduler array and initializes its action bar.
   *
   * @param unit The entity to be added to the battle.
   */
  def addUnit(unit: Entity): Unit =
    // The :+ operator safely appends to the array returning a new one
    unitsInBattle = unitsInBattle :+ unit
    actionBars(unit) = 0.0

  /**
   * Calculates the maximum action bar capacity for a given unit.
   *
   * @param unit The entity whose maximum action bar is being calculated.
   * @return The maximum action bar value as a Double.
   */
  def getMaxActionBar(unit: Entity): Double =
    if unit.isInstanceOf[AbsCharacter] then
      // Paso extra: hay que forzar la transformación (casteo) manualmente
      val character = unit.asInstanceOf[AbsCharacter]

      val weaponWeight = if character.equippedWeapon.isDefined then
        character.equippedWeapon.get.weaponWeight
      else 0

      character.weight + (0.5 * weaponWeight)

    else if unit.isInstanceOf[Enemy] then
      val enemy = unit.asInstanceOf[Enemy]
      enemy.weight.toDouble

    else
      unit.weight.toDouble
  /**
   * Retrieves the current action bar progress of a specific unit.
   *
   * @param unit The entity whose current action bar is being queried.
   * @return The current action points of the unit.
   */
  def getCurrentActionBar(unit: Entity): Double =
    actionBars.getOrElse(unit, 0.0)

  /**
   * Increases the action bar of all units using a recursive function.
   *
   * @param k The arbitrary amount of action points to add.
   */
  def increaseActionBars(k: Double): Unit =
    def increaseRecursive(index: Int): Unit =
      if index < unitsInBattle.length then
        val currentUnit = unitsInBattle(index)
        actionBars(currentUnit) = getCurrentActionBar(currentUnit) + k
        increaseRecursive(index + 1) // Recursive call

    increaseRecursive(0)

  /**
   * Resets the action bar of a specific unit back to 0.0.
   *
   * @param unit The entity whose action bar will be reset.
   */
  def resetActionBar(unit: Entity): Unit =
    if actionBars.contains(unit) then
      actionBars(unit) = 0.0

  /**
   * Checks if a unit has completed its action bar.
   *
   * @param unit The entity to check.
   * @return True if the current action bar is greater than or equal to the maximum.
   */
  def isBarComplete(unit: Entity): Boolean =
    getCurrentActionBar(unit) >= getMaxActionBar(unit)

  /**
   * Calculates the surplus of a unit's action bar.
   *
   * @param unit The entity to evaluate.
   * @return The difference between current and maximum action bar.
   */
  private def calculateSurplus(unit: Entity): Double =
    getCurrentActionBar(unit) - getMaxActionBar(unit)

  /**
   * Retrieves all ready units, sorted by surplus, using pure recursion and arrays.
   *
   * @return An Array of ready entities sorted by highest surplus.
   */
  def getReadyUnits: Array[Entity] =
    // 1. Recursive filter to get only units with complete bars
    def filterReady(index: Int, acc: Array[Entity]): Array[Entity] =
      if index >= unitsInBattle.length then
        acc
      else if isBarComplete(unitsInBattle(index)) then
        filterReady(index + 1, acc :+ unitsInBattle(index))
      else
        filterReady(index + 1, acc)

    val readyArray = filterReady(0, Array())

    // 2. Recursive Bubble Sort to order by surplus (descending)
    def bubblePass(arr: Array[Entity], i: Int, end: Int): Unit =
      if i < end then
        if calculateSurplus(arr(i)) < calculateSurplus(arr(i + 1)) then
          // Swap positions manually
          val temp = arr(i)
          arr(i) = arr(i + 1)
          arr(i + 1) = temp
        bubblePass(arr, i + 1, end)

    def sortRecursive(arr: Array[Entity], n: Int): Array[Entity] =
      if n <= 1 then
        arr
      else
        bubblePass(arr, 0, n - 1)
        sortRecursive(arr, n - 1)

    sortRecursive(readyArray, readyArray.length)

  /**
   * Determines the next unit that should take a turn.
   *
   * @return An Option containing the entity with the highest action bar surplus.
   */
  def getNextUnit: Option[Entity] =
    val ready = getReadyUnits
    if ready.isEmpty then
      None
    else
      Some(ready(0)) // Accessing the first index of the sorted array