package cl.uchile.dcc
package controller

import munit.FunSuite
import scala.compiletime.uninitialized

/**
 * Test suite for the TurnScheduler class
 *
 * Verifies that the scheduler correctly works
 *
 * @author Lucas Farfán
 * @version 1.0.0
 * @since 1.0.0
 */

class TurnSchedulerTest extends FunSuite:

  var scheduler: TurnScheduler = uninitialized
  var knight: Knight = uninitialized
  var enemy: Enemy = uninitialized
  var sword: Sword = uninitialized

  override def beforeEach(context: BeforeEach): Unit =
    scheduler = new TurnScheduler()

    // Constructor: name, hp, defense, weight
    knight = new Knight("Vendetta", 100, 20, 10)

    // Constructor: name, hp, defense, weight, attack
    enemy = new Enemy("Doomfist", 50, 5, 15, 10)

    // Constructor: weaponName, attackPoints, weaponWeight, owner
    sword = new Sword("Palatine Fang", 25, 4, knight)

    // Equipping the weapon using Option as defined in AbsCharacter
    knight.equippedWeapon = Some(sword)

  test("A TurnScheduler should be able to add units and correctly calculate their max action bar"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)

    // Knight weight (10) + 0.5 * Sword weight (4) = 12.0
    assertEquals(scheduler.getMaxActionBar(knight), 12.0)

    // Enemy weight (15) = 15.0
    assertEquals(scheduler.getMaxActionBar(enemy), 15.0)

  test("A TurnScheduler should track, increase, and reset action bars"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)

    // Bars should start at 0 
    assertEquals(scheduler.getCurrentActionBar(knight), 0.0)
    assertEquals(scheduler.getCurrentActionBar(enemy), 0.0)

    // Increase simultaneously by an arbitrary amount k
    scheduler.increaseActionBars(5.5)

    assertEquals(scheduler.getCurrentActionBar(knight), 5.5)
    assertEquals(scheduler.getCurrentActionBar(enemy), 5.5)

    // Reset a specific unit action bar
    scheduler.resetActionBar(knight)
    assertEquals(scheduler.getCurrentActionBar(knight), 0.0)

    // The enemy's bar should remain unaffected
    assertEquals(scheduler.getCurrentActionBar(enemy), 5.5)

  test("A TurnScheduler should identify ready units, sort them by surplus, and return the next unit"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)

    // Max for Knight is 12.0 and Enemy is 15.0
    // Increase bars arbitrarily by 16.0 so both exceed their max
    scheduler.increaseActionBars(16.0)

    // Verify if the scheduler detects the completed bars
    assert(scheduler.isBarComplete(knight))
    assert(scheduler.isBarComplete(enemy))

    // Knight surplus = 16.0 - 12.0 = 4.0
    // Enemy surplus = 16.0 - 15.0 = 1.0
    // We convert the resulting Array to a List just for the assertion to work seamlessly in MUnit
    val readyUnits = scheduler.getReadyUnits
    assertEquals(readyUnits.toList, List(knight, enemy))

    // The only character that gets the turn is the knight
    assertEquals(scheduler.getNextUnit, Some(knight))