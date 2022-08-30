'''
Ryan Looney
CS 5001
Cantrell
11/29/2021

'''

######### IMPORTS #########
import random as r 
import time

######### WEAPON CLASS DEFS #########
class Weapon:
    '''
    This is a weapon class for a simple battle game. The superclass defines melee weapons.
    Attributes: name, type, melee_strength, durability, encumberence
    Methods: print, melee_attack
    '''

    def __init__(self, name='Generic Dagger', melee_strength=1, durability=1, type="melee", encumberence=1):
        
        self.name = name
        self.melee_strength = melee_strength
        self.durability = durability
        self.type = type
        self.encumberence = encumberence

    def __str__(self):
        '''
        This print method for the weapon class is formatted with tabs to format it well within the weapon selection menu.
        '''

        melee_strength = '\t' + 'Melee Strength = ' + str(self.melee_strength) + '\n'
        durability = '\t' + 'Durability = ' + str(self.durability) + '\n'
        encumberance = '\t' + "Weapon Encumberance = " + str(self.encumberence) + '\n'
        return (melee_strength + durability + encumberance)

    def melee_attack(self, weapon_prowess):
        '''
        This melee attack method determines the value of a melee attack for a given weapon based upon the strength of the weapon and the ability of the character using it, passed in as weapon_prowess.
        '''
        #If the durability is 0, have the weapon break and return no damage.
        if self.durability <= 0:
            self.durability = 0
            weapon_break = '''


               |                         
              \|/                          
            `--+--'                        
              /|\                          
             ' | ' 
    
            '''

            print(weapon_break)
            print(f'{self.name} has broken and can no longer be used!')
            return 0

        #Calculate hit chance based upon weapon encumberance and user's prowess
        hit_chance = self.encumberence * weapon_prowess
        hit_instance = r.random()

        #If the attack hit...
        if hit_instance <= hit_chance:

            #Have melee weapon randomly lose durability for attacks that hit
            self.durability -= r.randint(1, 20)

            #Instance of attack is random number between 1 and the weapon's strength
            attack_instance = r.randint(1, self.melee_strength)
            return attack_instance
       
        #This else statement means the attack missed, and -1 will be returned signifying this.
        else:
            return -1


##########EXTENSION CLASS DEFS##########
class Ranged_Weapon(Weapon):
    '''
    This is a weapon class for a simple battle game. The subclass defines ranged weapons.
    Attributes: name, type, melee_strength, durability, encumberence, ranged_strength, accuracy, ammunition
    Methods: print, ranged_attack
    '''

    def __init__(self, name, melee_strength, durability, ranged_strength, accuracy, ammunition, type, encumberence):
        
        super().__init__(name, melee_strength, durability, type, encumberence)
        
        self.ranged_strength = ranged_strength
        self.encumberence = encumberence
        self.accuracy = accuracy
        self.ammunition = ammunition

    def __str__(self):
        
        ranged_strength = '\t' + 'Ranged Strength = ' + str(self.ranged_strength) + '\n'
        accuracy = '\t' + 'Accuracy = ' + str(self.accuracy) + '\n'
        ammunition = '\t' + 'Ammunition = ' + str(self.ammunition) + '\n'
        
        return (super().__str__() + ranged_strength + accuracy + ammunition)

    def ranged_attack(self, accuracy_modifier): 
        '''
        This ranged attack method determeines the value of a ranged attack for a given weapon based upon the accuracy of the weapon and the ability of the character using it, passed in as the accuracy_modifier.
        '''

        #If the durability of the weapon is 0, return 0.
        if self.durability <= 0:
            self.durability = 0

            weapon_break = '''


               |                         
              \|/                          
            `--+--'                        
              /|\                          
             ' | ' 
    
            '''

            print(weapon_break)
            print(f'{self.name} has broken and can no longer be fired!')
            time.sleep(2.0)
            return 0

        else:
            #Reduce ammunition and durability for any shot whether it hits or misses
            self.ammunition -= 1
            self.durability -= r.randint(1, 5)

            #Before calculating damage, check if the shot will hit
            hit_chance = self.accuracy * accuracy_modifier 
            hit_instance = r.random()

            #If the attack hits...
            if hit_instance <= hit_chance:
                #Instance of attack is random number between 1 and the weapon's strength
                attack_instance = r.randint(1, self.ranged_strength)
                return attack_instance
            
            #This else statement means the attack missed, and -1 wil be returned signifying this.
            else:
                return -1
