#inicializar variables 
area = 0
base = 0
altura = 0
perimetro = 0
#bucle para pedir el area y dar el perimetro pidiendo la base y la altura
while True:
    respuesta = int(input('Dime que figura que quieres que te muestre(1-cuadrado, 2-rectangulo  3- salir):'))
    while True:
        #realizacion de el cuadrado calculo
        if respuesta == 1:   
            base = int(input('Dame el lado del cuadrado:'))
            area = base * base
            perimetro = base * 4
            print(f'El área del cuadrado es {area}')
            print(f'El perímetro del cuadrado es {perimetro}')
            
            #dibujo del cuadrado
            for dibujo in range(0,base):
                for i in range(0, base):
                    print('* ', end='')
                print()
            break
        #realizacion de el calculo del rectangulo
        elif respuesta == 2:
            base = int(input('Dame la base: '))
            altura =int(input('Dame la altura:'))
            perimetro = (base * 2) + (altura * 2)
            area = (base * altura)
            print(f'El área del rectángunlo es {area}')
            print(f'El perímetro del rectángulo es {perimetro}')
            #dibujo de el rectngulo
            for dibujo in range(0,base):
                for i in range(0,altura):
                    print('* ', end='')
                print()
            break
        #salir al menu otra vez 
        elif respuesta == 3:
            print('Adios')
            break
        else:
            print('Opción no valida introduce numero otra vez')