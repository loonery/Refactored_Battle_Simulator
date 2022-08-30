'''
Ryan Looney
CS 5001
Cantrell
11/29/2021

'''


from character import Character
from weapon import Ranged_Weapon, Weapon
import unittest

class Weapon_test(unittest.TestCase):
    
    #Melee Weapon Constructor Test Case
    def test__init__(self):
        self.weapon1 = Weapon()

        self.assertEqual(self.weapon1.name, 'Generic Dagger')
        self.assertEqual(self.weapon1.melee_strength, 1)
        self.assertEqual(self.weapon1.durability, 1)
        self.assertEqual(self.weapon1.encumberence, 1)
        self.assertEqual(self.weapon1.type, 'melee')

    #Melee Weapon Print Test Case
    def test__str__(self):
        self.weapon1 = Weapon()
        
        self.assertEqual(self.weapon1.__str__(), '\t' + 'Melee Strength = ' + '1' + '\n' + 
                                                 '\t' + 'Durability = ' + '1' + '\n' + 
                                                 '\t' + "Weapon Encumberance = " + '1' + '\n')

    def test_melee_attack(self): 
        self.weapon1 = Weapon()
        
        #Testing no durability if the weapon prowess is 1
        self.weapon1.durability = 0
        self.assertEqual(self.weapon1.melee_attack(1.0), 0)
    
    def test_melee_attack2(self):
        self.weapon1 = Weapon()
       
        #Testing that if the weapon_prowess is 0, we will return -1 indicating a missed attack
        self.assertEqual(self.weapon1.melee_attack(0.0), -1)

if __name__ == '__main__':
    unittest.main()