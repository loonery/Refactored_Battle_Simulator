'''
Ryan Looney
CS 5001
Cantrell
11/29/2021

'''

from character import Character
from weapon import Weapon
import unittest

class CharacterTest(unittest.TestCase):
    def set_up(self):
        self.c1 = Character()
        self.c2 = Character()

    #Test method for Character constructor
    def test_init(self):
        self.c1 = Character()

        #Character Physical Attributes
        self.assertEqual(self.c1.name, 'Generic Fighter')
        self.assertEqual(self.c1.strength, 1)
        self.assertEqual(self.c1.walking_speed, 15)
        self.assertEqual(self.c1.shooting_accuracy_modifier, .5)
        self.assertEqual(self.c1.weapon_prowess, .5)
        
        #Character State Attributes
        self.assertNotEqual(self.c1.weapon_equipped, None)
        self.assertEqual(self.c1.alive, True)
        self.assertEqual(self.c1.weapon_prowess, .5)
    
    #Character Print Test Case
    def test__str__(self):
        self.c1 = Character()

        self.assertEqual(self.c1.__str__(), '\t' + "Character Strength = " + str(self.c1.strength) + '\n' + 
                                            '\t' + "Speed = " + str(self.c1.walking_speed) + '\n' + 
                                            '\t' + "Shooting Accuracy = " + str(self.c1.shooting_accuracy_modifier) + '\n' +
                                            '\t' + 'Melee Prowess = ' + str(self.c1.weapon_prowess) + '\n')

    #Greater than 0
    def test_take_damage1(self):
        self.c1 = Character()

        self.c1.take_damage(10)
        self.assertEqual(self.c1.hitpoints, 90)

    #0 Test Case
    def test_take_damage2(self):
        self.c1 = Character()

        self.c1.take_damage(100)
        self.assertEqual(self.c1.hitpoints, 0)

    #Greater than 0
    def test_take_damage3(self):
        self.c1 = Character()

        self.c1.take_damage(200)
        self.assertEqual(self.c1.hitpoints, 0)

    def test_attack(self):
        self.c1 = Character()
        self.c2 = Character()

        self.c1.attack(self.c2)

        #Testing for less than or equal to 100 because I included the possibility for attacks to miss
        self.assert_(self.c2.hitpoints <= 100)
        self.assert_(self.c1.hitpoints == 100)

    def test_fight(self):
        self.c1 = Character()
        self.c2 = Character()

        #Raise damage and durability of weapons to make test go faster
        self.c1.weapon_equipped.melee_strength = 50
        self.c2.weapon_equipped.melee_strength = 50
        self.c1.weapon_equipped.durability = 1000
        self.c2.weapon_equipped.durability = 1000


        self.c1.fight(self.c2)
        self.assert_(self.c1.hitpoints != 0 and self.c2.hitpoints == 0) or (self.c1.hitpoints == 0 and self.c2.hitpoints != 0)


if __name__ == '__main__':
    unittest.main()