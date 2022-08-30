'''
Ryan Looney
CS 5001
Cantrell
11/29/2021

'''

######### IMPORTS #########

from character import Character
from weapon import Ranged_Weapon, Weapon
import random as r 
import time

######### SUPPORTING FUNCTION DEFS #########

def fighters_info(character_list, bios):
        for i in enumerate(character_list):
            print(str(i[0]) + ' - ' + bios[i[1].name] + '\n')
            print(i[1])
            print('\n')

def weapons_info(weapons_list, bios):
    for i in enumerate(weapons_list):
        print(str(i[0]) + ' - ' + i[1].name + ' : ' + bios[i[1].name] + '\n')
        print(i[1])
        print('\n')

def weapon_selection(character, weapons_list, weapon_bios):

       #While the character has generic weapon (no assignment)... 
       while character.weapon_equipped.name == "Generic Dagger":

        #Prints weapons information based on what weapons are available.
        weapons_info(weapons_list, weapon_bios)

        try:
            #Find the chosen weapon and give it to fighter1
            weapon_choice = int(input(f"Enter the respective integer of the weapon you would like to give to {character.name}:\n"))

            #If the weapon choice within the indices 
            if weapon_choice < len(weapons_list):

                #Assign the weapon to the person
                weapon1 = weapons_list[weapon_choice]
                weapons_list.remove(weapon1)
                character.weapon_equipped = weapon1
                print("\033c")
            
            else:
                print("\033c")
                print("That is not an appropriate character choice. Please try again.\n")

        except ValueError:
            print("\033c")
            print('Character choice must be selected by an integer! Try again.\n')

        #After the first selection, clear the terminal.
        print("\033c")

def character_selection(fighters, characters, character_bios):
    '''
    This function takes a list and adds character objects to it based on user input, until there are 2 characters in the list.
    '''

    while (len(fighters) < 2):
        #print fighter text based on characters that are left in the list
        fighters_info(characters, character_bios)

        try:

            #Ask for input depending upon how many fighters are already chosen.
            if len(fighters) == 0:
                character_choice = int(input("which fighter would you like to choose?\nEnter the number next to the name of a character to select them:\n"))
            else:
                character_choice = int(input("Please select a challenger:\n"))
            
            #If the chosen number representing a character is within the indices of the characters list...
            if character_choice < len(characters):

                #Add  fighter to the fighters list and remove them from the characters list.
                fighter = characters[character_choice]
                characters.remove(fighter)
                fighters.append(fighter)
                print("\033c")
            
            else:
                print("\033c")
                print("That is not an appropriate character choice. Please try again.\n")

        except ValueError:
            print("\033c")
            print('Character choice must be selected by an integer! Try again.\n')
        
    return fighters

################## MAIN ##################
def main():

    ################## CREATE OBJECTS, LISTS, AND DICTIONARIES ##################
    #Character object creation
    challenger1 = Character('Seger', strength=35, walking_speed=8, shooting_accuracy_modifier=.85, weapon_prowess=.85)
    challenger2 = Character('Eves', strength=25, walking_speed=10, shooting_accuracy_modifier=.7, weapon_prowess=.7)
    challenger3 = Character('Of Salamander Clan', strength=15, walking_speed=25, shooting_accuracy_modifier=.5, weapon_prowess=1)
    challenger4 = Character('Waltz', strength=55, walking_speed=5, shooting_accuracy_modifier=.1, weapon_prowess=.4)

    #Define Character bios
    character_bios = {
    'Seger' : "Seger is a skilled warrior. She is certain to best most opponents with her combat skill, but is not as mobile as most others. She is a good shooter.",
    'Eves' : "Eves is a jack of all trades - he is moderately strong and moderately quick. A tenacious all-around competitor. Average shooter and weapon handler.",
    'Of Salamander Clan' : "Of Salamander Clan is weak. She has learned that agility is her strong suit; there is no one faster. She is a mediocre shooter.",
    'Waltz' : "Waltz is a hulking beast. No one can stand his physical strength. He is, however, slower, cumsier, and is basically unable to use a firearm."}

    #Create melee weapon objects
    broadsword = Weapon("Broadsword", melee_strength=100, durability=100, encumberence=.6, type="melee")
    longsword = Weapon("Longsword of World's End", melee_strength=75, durability=80, encumberence=.8, type="melee")
    engraved_dagger = Weapon("Engraved Dagger", melee_strength=50, durability=80, encumberence=.95, type="melee")

    #Create ranged Weapon objects 
    bow = Ranged_Weapon("Chief Naera's Bow", melee_strength=5, durability=35, ranged_strength=20, accuracy= .8, ammunition=10, type='ranged', encumberence=1)
    musket = Ranged_Weapon("October's Musket", melee_strength=15, durability=35, ranged_strength=80, accuracy= .4, ammunition=8, type='ranged', encumberence=1)
    blunderbuss = Ranged_Weapon("Unwieldy Blunderbuss", melee_strength=10, durability=20, ranged_strength=120, accuracy=.15, ammunition=5, type='ranged', encumberence=1)

    #Define weapon descriptions
    weapon_bios = {
        "Armand's Hammer" : "Clunky but powerful.",
        "Longsword of World's End" : "Balanced and effective melee weapon.",
        "Engraved Dagger" : "Light dagger with low damage, but easy to use, and likely to hit.",
        "Chief Naera's Bow" : "Low damage but accurate.",
        "October's Musket" : "Heavy damage, but far more innacurate than a bow.",
        "Unwieldy Blunderbuss" : "Potentially deals Catastrophic damage, but very rareley hits."
    }

    #Append characters  to list
    characters = []
    characters.append(challenger1)
    characters.append(challenger2)
    characters.append(challenger3)
    characters.append(challenger4)

    #Append Melee Weapons to list
    weapons_list = []
    weapons_list.append(broadsword)
    weapons_list.append(longsword)
    weapons_list.append(engraved_dagger)
    weapons_list.append(bow)
    weapons_list.append(musket)
    weapons_list.append(blunderbuss)

    ################### WELCOME TEXT & CHARACTER SELECTION LOOP ###################
    
    print("\nWelcome to the Thunderdome! The rules are simple:")
    time.sleep(1.0)
    print("\nYou will choose two fighters and pit them against one another in battle.\nThey will start a distance away from one another. Fighters equipped with melee weapons will close range, \nwhile those equipped with range weapons will stay put. The fight will go on until one fighter remains standing.")
    time.sleep(12.0)
    
    #Clear the terminal
    print("\033c")
    print("HERE ARE THE FIGHTERS!" + '\n'*2)
    
    fighters = character_selection([], characters, character_bios)

    ########## WEAPON_ASSIGNMENTS ##########
    time.sleep(1.0)
    print("\033c")
    print("Each character must not enter the Thunderdome without armament! You must select how each fighter is outfitted before they enter.")
    print("\n"*3)

    #Fighter Weapon Choice Loop
    for i in range (2): weapon_selection(fighters[i], weapons_list, weapon_bios)

    ########## CHARACTERS FIGHTING LOOP ##########
    #While there are still characters who have not entered battle, 
    # or if both fighters selected for battle are still alive...
    while (len(characters) > 0) or (len(fighters) > 1):

        #Clear the terminal before beginning the fight
        print("\033c")

        #Run the fight method, which runs the battle and ends with one character object being defeated.
        fighters[0].fight(fighters[1])
            
        #Depending on who wins, remove that fighter from the fighters list.
        if fighters[0].alive == False :
            fighters.remove(fighters[0])
        
        elif fighters[1].alive == False : 
            fighters.remove(fighters[1])
        
        #If there are character's left standing Update the fighters list to have another competitor enter.
        if (len(characters) != 0):
            
            #Select fighters from characters remaining.
            character_selection(fighters, characters, character_bios)

            #Give each fighter in the list weapons.
            for i in range (2): weapon_selection(fighters[i], weapons_list, weapon_bios)

            #Heal the fighters in between battle. Their weapons do not recover.
            if fighters[0].hitpoints < 100: fighters[0].hitpoints = 100
            if fighters[1].hitpoints < 100: fighters[1].hitpoints = 100
        
        #If there are no fighters left to choose from, we have reached the last fighter standing!
        else:
            print('\n' + "It seems like no one will dethrone " + str(fighters[0].name) + "...they are the last fighter standing in the Thunderdome!")

main()