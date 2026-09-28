package cl.uchile.dcc
package controller

/**
  * Controlador principal del juego.
  * En esta clase debes implementar la lógica que conecta las distintas
  * entidades de tu modelo, maneja los turnos, y ejecuta las acciones.
  */
class GameController {

}

trait Entity:
  def name: String
  def hp: Int
  def defense: Int
  def weight: Int

abstract class EntityAttributes(val name: String, var hp: Int, val defense: Int, val weight: Int) extends Entity

abstract class AbsCharacter(name: String, hp: Int, defense: Int, weight: Int) extends EntityAttributes(name, hp, defense, weight):
  var inventory: List[Potion] = List()
  var equippedWeapon: Option[Weapon] = None

abstract class AbsMagicianCharacter(name: String, hp: Int, defense: Int, weight: Int, var mana: Int) extends AbsCharacter(name, hp, defense, weight)

class Enemy(name: String, hp: Int, defense: Int, weight: Int, val attack: Int) extends EntityAttributes(name, hp, defense, weight)

class Knight(name: String, hp: Int, defense: Int, weight: Int) extends AbsCharacter(name, hp, defense, weight)
class Archer(name: String, hp: Int, defense: Int, weight: Int) extends AbsCharacter(name, hp, defense, weight)
class Thief(name: String, hp: Int, defense: Int, weight: Int) extends AbsCharacter(name, hp, defense, weight)

class BlackMagician(name: String, hp: Int, defense: Int, weight: Int, mana: Int) extends AbsMagicianCharacter(name, hp, defense, weight, mana)
class WhiteMagician(name: String, hp: Int, defense: Int, weight: Int, mana: Int) extends AbsMagicianCharacter(name, hp, defense, weight, mana)

class Player(var playerUnits: List[AbsCharacter] = List()):
  def isDefeated: Boolean =
    playerUnits.forall(character => character.hp <= 0)

trait Armament:
  def weaponName: String
  def attackPoints: Int
  def weaponWeight: Int
  def owner: AbsCharacter

abstract class Weapon(val weaponName: String, var attackPoints: Int, val weaponWeight: Int, var owner: AbsCharacter) extends Armament

abstract class MagicWeapon(weaponName: String, attackPoints: Int, weaponWeight: Int, owner: AbsCharacter, var magicPoints: Int) extends Weapon(weaponName, attackPoints, weaponWeight, owner)

class Sword(weaponName: String, attackPoints: Int, weaponWeight: Int, owner: AbsCharacter) extends Weapon(weaponName, attackPoints, weaponWeight, owner)
class Dagger(weaponName: String, attackPoints: Int, weaponWeight: Int, owner: AbsCharacter) extends Weapon(weaponName, attackPoints, weaponWeight, owner)
class Bow(weaponName: String, attackPoints: Int, weaponWeight: Int, owner: AbsCharacter) extends Weapon(weaponName, attackPoints, weaponWeight, owner)

class Wand(weaponName: String, attackPoints: Int, weaponWeight: Int, owner: AbsCharacter, magicPoints: Int) extends MagicWeapon(weaponName, attackPoints, weaponWeight, owner, magicPoints)
class MagicStaff(weaponName: String, attackPoints: Int, weaponWeight: Int, owner: AbsCharacter, magicPoints: Int) extends MagicWeapon(weaponName, attackPoints, weaponWeight, owner, magicPoints)

trait Effect:
  def name: String

abstract class Potion(val name: String) extends Effect

class HealthPotion(name: String) extends Potion(name)
class StrengthPotion(name: String) extends Potion(name)
class ManaPotion(name: String) extends Potion(name)
class MagicStrengthPotion(name: String) extends Potion(name)