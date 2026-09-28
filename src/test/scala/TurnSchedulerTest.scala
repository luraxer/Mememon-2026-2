package cl.uchile.dcc
package controller

import munit.FunSuite

class TurnSchedulerTest extends FunSuite:

  var scheduler: TurnScheduler = _
  var knight: Knight = _
  var enemy: Enemy = _
  var sword: Sword = _

  override def beforeEach(context: BeforeEach): Unit =
    scheduler = new TurnScheduler()

    // Constructor: name, hp, defense, weight
    knight = new Knight("Arthur", 100, 20, 10)

    // Constructor: name, hp, defense, weight, attack
    enemy = new Enemy("Goblin", 50, 5, 15, 10)

    // Constructor: weaponName, attackPoints, weaponWeight, owner
    sword = new Sword("Excalibur", 25, 4, knight)

    // Equipping the weapon using Option as defined in AbsCharacter
    knight.equippedWeapon = Some(sword)

  test("A TurnScheduler should be able to add units and correctly calculate their max action bar"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)

    // Knight weight (10) + 0.5 * Sword weight (4) = 12.0
    assertEquals(scheduler.getMaxActionBar(knight), 12.0)

    // Enemy weight (15) = 15.0
    assertEquals(scheduler.getMaxActionBar(enemy), 15.0)