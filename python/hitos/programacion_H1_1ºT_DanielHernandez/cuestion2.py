import random
#Inicializavariables
contador_persona = 0
persona = 0
contador_maquina = 0
maquina = 0

#Bucle con el que la maquina y la persona eligen numeros 
while True:
        persona = int(input('Vamos a jugar a 1-piedra 2-papel y 3-tijera elige una de estas tres y el que llegue a tres gana: '))
        maquina = random.randint(1,3)
        #bucle por si pone algun numero que no es los que les decimos nostros
        while persona not in (1,2,3):
            print('Pon solo 1 , 2 o 3')
            persona = int(input('Vamos a jugar a 1-piedra 2-papel y 3-tijera elige una de estas tres y el que llegue a tres gana: '))
        #resultados para las distintas opciones que se dan y sumar al contador dependiendo de quien gane 
        if persona == maquina:
            print('Habeis empatado')
        elif (persona == 1 and maquina == 2) or (persona == 2 and maquina == 3) or (persona == 3 and maquina == 1):
            contador_maquina += 1
            print('Perdiste')
            print(f'Tu: {contador_persona} y Maquina: {contador_maquina}')
        elif (persona == 2 and maquina == 1) or (persona == 3 and maquina == 2) or (persona == 1 and maquina == 3):
            contador_persona += 1
            print('Ganaste')
            print(f'Tu: {contador_persona} y Maquina: {contador_maquina}')
        #If para que cuando el contador de alguno de los dos llegue a 3 deje de hacer e bucle 
        if contador_maquina == 3:
            print(f'Ha ganado Maquina con: {contador_maquina}')
            break
        elif contador_persona == 3:
            print(f'Has ganado tu con: {contador_persona}')
            break
