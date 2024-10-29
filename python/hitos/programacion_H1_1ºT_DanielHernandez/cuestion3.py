#inicializador de variables
saldo = 0
contador_ingresos = 0
contador_retiradas = 0
saldo_actual = 0

#bucle para que pida el salario
while True:
    saldo = float(input('Dame el saldo de tu cuenta (tiene que ser mayor que 0): '))
    if saldo <= 0:
        print('Numero no valido ingreselo otra vez')
        saldo = float(input('Dame el saldo de tu cuenta (tiene que ser mayor que 0):'))
    else:
        saldo_actual = saldo + saldo_actual
        #bucle para que aparezaca el menu
        while True:
            menu = int(input('1- Ingresar dinero  2- Retirar dinero  3- Mostrar Saldo  4- Salir  5- Estadisticas Elige: '))    
            #pedir ingreso y que lo sume al salario actual 
            if menu == 1:
                ingreso = float(input('Que cantidad quieres ingresar: '))
                if ingreso <= 0:
                    print('Numero no valido ingreselo otra vez')
                    ingreso = float(input('Que cantidad quieres ingresar: '))
                else:
                    saldo_actual = saldo + ingreso
                    contador_ingresos += 1
                    print(f'Ahora tu saldo actual es: {saldo_actual}')
            #pedir retirada y que lo reste al salario actual
            elif menu == 2:
                retirada = float(input('Que cantidad quieres retirar: '))
                if retirada <= 0:
                    print('Numero no valido ingreselo otra vez')
                    retirada = float(input('Que cantidad quieres retirar: '))
                elif retirada > saldo_actual:
                    print('No puedes retirar esa cantidad es más de lo que tienes intentalo otra vez')
                    retirada = float(input('Que cantidad quieres retirar: '))
                else:
                    saldo_actual = saldo_actual - retirada
                    contador_retiradas += 1
                    print(f'Ahora tu sueldo actual es: {saldo_actual}')
            #Muestra del salario actual
            elif menu == 3:
                print(f'Este es tu saldo actual: {saldo_actual}')
            #salir otra vez al inicio
            elif menu == 4:
                print('Adios, gracias por usar nuestro banco')
                break
            #ver las veces que has ingresado y retirado
            elif menu == 5:
                print(f'Has ingresado {contador_ingresos} veces')
                print(f'Has retirado {contador_retiradas} veces')
            #si pone una opcion que no esta en el menu
            elif menu > 5:
                print('Tu seleccion no esta en nuestro menu')
                menu = int(input('1- Ingresar dinero  2- Retirar dinero  3- Mostrar Saldo  4- Salir  5- Estadisticas Elige: '))
