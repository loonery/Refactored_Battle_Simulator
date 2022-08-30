'''
Ryan Looney
CS 5001
Cantrell
11/29/2021

'''

##########IMPORTS##########
from weapon import Ranged_Weapon, Weapon
import random 
import time

##########SUPPORTING DEFS##########
def attack_text(n):
    '''this function creates a dictionary of ascii images to be housed'''

    ##########ASCII Art Definitions##########

    missed_melee = '''

                                    _
                                      \'
                                        \'
                                          \  
                                            \ '                                          
                         //                  \'
                    O===[====================- 
                         \\
                                            '''

    
    hit_melee = '''
                                         |      
                         //              |   
                    O===[================|====- 
                         \\              |
                                         |
                                         '''

    missed_shot = '''
                
                 - - -   >>------>
                
                                        '''

    hit_shot =  '''
                                   |
                      - - -   >>---|--->
                                   |
                                        '''


    run = '''
                             
                                      _
                                    _( }
                             (\   _ <<\ 
                              `.\__/`/\\
                        -=      '--'\\  "
                            -=     //
                                   \)

                        '''

    begin_battle = '''
    
            O                                     O
    {o)xxx|===============-  *  -===============|xxx(o}
            O                                     O
                        
    '''

    bare_hands = '''

            .--.___.----.___.--._
          /|  |   |    |   |  | `--.
        /                          `.
        |       |        |           |
        |       |        |      |     |
        '|  `    | ` ` `  |  ` ` |  `  |
        ||`   `  |     `  |   `  |`   `|
        ||  `    |  `     | `    |  `  |
        ||  ___  |  ____  |  __  |  _  |
        | \_____/ \______/ \____/ \___/
        |     `----._
        |    /       \'
        |         .--.\'
        |        '    \'
        `.      |  _.-'
            `.|__.-'
            
    
    '''



    #Create and update the dictionary with art
    attack_text = {}
    attack_text.update({'missed_melee' : missed_melee})
    attack_text.update({'hit_melee' : hit_melee})
    attack_text.update({'missed_shot' : missed_shot})
    attack_text.update({'hit_shot' : hit_shot})
    attack_text.update({'run' : run})
    attack_text.update({'begin_battle' : begin_battle})
    attack_text.update({'bare_hands' : bare_hands})

    return attack_text[n]

##########CLASS DEFS##########
class Character:
    '''
    This is a character class for a simple battle game.
    Attributes: name, hitpoints, strength, walking_speed, shooting_accuracy_modifier, weapon_prowess, weapon_equipped, alive
    Methods: print, take_damage, attack, fight
    '''
    
    def __init__(self, name='Generic Fighter', strength=1, walking_speed=15, shooting_accuracy_modifier=.5, weapon_prowess=.5):
        '''This is the constructor for the Character class'''

        self.name = name

        #Character Physical Attributes
        self.hitpoints = 100
        self.strength = strength
        self.walking_speed = walking_speed
        self.shooting_accuracy_modifier = shooting_accuracy_modifier
        self.weapon_prowess = weapon_prowess

        #Character State Attributes - initialize weapon to empty
        self.weapon_equipped = Weapon()
        self.alive = True

    def __str__(self):
        ''''
        This method prints pertinent character attributes BUT NOT the name. It is formatted this way to format it well for the character selection menu.
        '''

        strength = '\t' + "Character Strength = " + str(self.strength) + '\n'
        speed = '\t' + "Speed = " + str(self.walking_speed) + '\n'
        accuracy = '\t' + "Shooting Accuracy = " + str(self.shooting_accuracy_modifier) + '\n'
        prowess = '\t' + 'Melee Prowess = ' + str(self.weapon_prowess) + '\n'
    
        result = (strength + speed + accuracy + prowess)
        return result

    ########## BATTLE METHOD DEFS ##########
    def take_damage(self, damage):
        '''This method takes an argument for damage and subtracts that argument from the hitpoints attribute of the invoked character object.'''
        
        self.hitpoints -= damage
        if self.hitpoints <= 0:
            self.hitpoints = 0
            self.alive = False

    ##########ATTACK METHOD DEF##########
    def attack(self, victim):
        'This method takes a character object as an argument, and has the invoked object attack the argued character object by applying damage to it via melee or ranged weapon.'
        
        ##########ATTACK FOR MELEE WEAPONS#########
        #If the weapon is of type 'melee'...
        if (self.weapon_equipped.type == "melee"):

            #The damage coming from the equipped weapon is calculated
            weapon_damage = self.weapon_equipped.melee_attack(self.weapon_prowess)
            
            #If melee weapon is broken, change self's weapon to 'Bare Hands'...
            if self.weapon_equipped.durability == 0:
                print(attack_text('bare_hands'))
                print(f"With no functional weapon, {self.name} resorted to using their bare hands!")
                print()
                self.weapon_equipped = Weapon("Bare Hands", self.strength, 1000)

            #If -1 is the value stored in weapon_damage, it means the strike with the weapon has missed, damage will be 0.
            if weapon_damage == -1:
                damage = 0
                victim.take_damage(damage)
                print(attack_text('missed_melee'))
                print(f"{self.name} struck at {victim.name} with their {self.weapon_equipped.name} and missed!")
                time.sleep(2.0)
                print()
            
            #Otherwise, the attack hit calculate total damageg and apply to victim...
            else:
                #Damage is equal to the character's strength + the weapon_damage
                damage = random.randint(1, self.strength) + weapon_damage
                victim.take_damage(damage)

                print(attack_text('hit_melee'))
                print(f"{self.name} lunged at {victim.name} with their {self.weapon_equipped.name} and HIT them for {damage} leaving them with {victim.hitpoints} hitpoints")
                time.sleep(2.0)
                print()

        ##########ATTACK FOR RANGED WEAPONS#########
        #If the weapon is of type 'ranged'...
        if (self.weapon_equipped.type == 'ranged'):

            #The damage cieling is set by the ranged_attack method
            damage_cieling = (self.weapon_equipped.ranged_attack(self.shooting_accuracy_modifier))
            
            #If the durability of the weapon is 0 (signified by return of 0), produce no from ranged attack, but call melee attack. 
            if damage_cieling == 0:
                damage = 0
                victim.take_damage(damage)
                
                #Set the weapon durability to be 150, because as a melee weapon, it is less likley to break
                self.weapon_equipped.type = 'melee'
                self.weapon_equipped.durability = 150
                
            #If the ranged_attack function returns -1, (value stored in damage_cieling) it means the shot missed. damage is 0 and the shot missed.
            elif damage_cieling == -1: 
                damage = 0

                print(attack_text('missed_shot'))
                print(f"{self.name} FIRED at {victim.name} with their {self.weapon_equipped.name} and missed!")
                
                #If the most recent shot drained the weapon of ammunition, print that information and change the weapon type to 'melee'
                if self.weapon_equipped.ammunition == 0:
                    print(f'{self.weapon_equipped.name} has run out of ammo and can no longer be fired! It will not be used as a melee weapon! ')
                    self.weapon_equipped.type = 'melee'
                
                time.sleep(2.0)
                print()


            #Hitting this else statement means the shot hit; Calculate the damage and apply it to the victim
            else:
                damage = random.randint(1, damage_cieling)
                victim.take_damage(damage)

                print(attack_text('hit_shot'))
                print(f'{self.name} SHOT at {victim.name} with their {self.weapon_equipped.name} and HIT them for {damage} leaving them with {victim.hitpoints} hitpoints')
                
                #If the most recent shot drained the weapon of ammunition, print that information and change the weapon type to 'melee'
                if self.weapon_equipped.ammunition == 0:
                    print(f'{self.weapon_equipped.name} has run out of ammo and can no longer be fired! It will not be used as a melee weapon! ')
                    self.weapon_equipped.type = 'melee'

                time.sleep(2.0)
                print()


    ##########FIGHT METHOD DEF##########
    def fight(self, fighter2):
        '''
        This method has two character objects engage in battle, including the invoked character object.
        
        '''

        #Start the fighters 80 units of distance away from one another
        distance_between = 80
        distance_closed = False

        #This is the individual battle loop - while both fighters are alive....
        while self.hitpoints > 0 and fighter2.hitpoints > 0:
            
            #If the fighters are in the same position
            if (distance_closed == True and distance_between <= 0):
                
                #roll a 50/50 die to see who attacks in a melee situation
                attack_die = random.randint(0, 1)
                if attack_die == 0: self.attack(fighter2)
                if attack_die == 1: fighter2.attack(self)
            
            #If the fighters are not in the same position...
            else:
                #Have self decide to move or not move towards fighter2 other based on weapon type.
                if self.weapon_equipped.type == "melee":
                    
                    #Self will run towards fighter2
                    distance_between -= self.walking_speed

                    #If the distance is closed to 0 on the above operation, express that the distance is closed, and have the self attack with a melee weapon
                    if distance_between <= 0 and distance_closed == False: 
                        distance_between = 0
                        distance_closed = True
                        print(attack_text('begin_battle'))
                        print(f"{self.name} met {fighter2.name} in the field of battle!")
                        time.sleep(2.0)
                        print()
                        self.attack(fighter2)
                    
                    #If the distance has not been closed on this iteration, make note that self is running
                    elif distance_between > 0 and distance_closed == False:
                        print(attack_text('run'))
                        print(f"{self.name} ran towards {fighter2.name}, closing range to {distance_between} units")
                        time.sleep(2.0)
                        print()
                
                #If the fighters are not in the same position and the weapon type is ranged, attack.
                else:
                    self.attack(fighter2)
                    time.sleep(2.0)
                    print()

                #If fighter 2 has a weapon type of melee, get closer to opponent, or attack, depending on distance between.
                if fighter2.weapon_equipped.type == 'melee':

                    distance_between -= fighter2.walking_speed
                    
                    #If the distance is closed on this iteration, make note that the distance is closed and initiate melee combat.
                    if distance_between <= 0 and distance_closed == False: 
                        distance_between = 0
                        distance_closed = True
                        print(attack_text('begin_battle'))
                        print(f"{fighter2.name} met {self.name} in the field of battle!")
                        time.sleep(2.0)
                        print()
                        fighter2.attack(self)
                    
                    #If the distance has not been closed on this iteration, make note that fighter2 is running
                    elif distance_between > 0 and distance_closed == False:
                        print(attack_text('run'))
                        print(f"{fighter2.name} ran towards {self.name}, closing range to {distance_between} units")
                        time.sleep(2.0)
                        print()
                
                #If the fighters are not in the same position and the weapon type is ranged, attack.
                else:
                    fighter2.attack(self)
                    time.sleep(2.0)
                    print()

            if not self.alive: break
            if not fighter2.alive: break
            
        if self.alive: 
            print(self.name, "Won...Who will come dethrone them?" + '\n')
        else: 
            print(fighter2.name, "Won...Who will come dethrone them?" + '\n')
