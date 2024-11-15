# Datos iniciales
clientes = [] 
productos = [
    {"id": 1, "nombre": "Producto A", "precio": 22},
    {"id": 2, "nombre": "Producto B", "precio": 45},
    {"id": 3, "nombre": "Producto C", "precio": 76}]
pedidos = [] 

# Función para registrar un cliente
def registrar_cliente():
    print(' Registro de Cliente ')
    nombre = input('Nombre: ')
    dni = input('DNI:')
    email = input("Correo electrónico: ")

    # Comprobara que el correo no esta
    for cliente in clientes:
        if cliente['email'] == email:
            print('Ya existe un cliente registrado con este correo')
            return

    cliente = {'nombre': nombre, 'DNI': dni, 'email': email}
    clientes.append(cliente)
    print('Registro perfecto')

# Ver los clientes
def mostrar_clientes():
    print(' Lista de Clientes ')
    if not clientes:
        print('No hay clientes registrados')
        return
    for cliente in clientes:
        print(f"{cliente['nombre']} {cliente['DNI']} {cliente['email']}")

# Función para buscar cliente 
def buscar_cliente():
    print(' Buscar Cliente ')
    email = input('Ingresa el correo electrónico del cliente: ')
    for cliente in clientes:
        if cliente['email'] == email:
            print(f"Cliente encontrado: {cliente['nombre']} {cliente['DNI']} {cliente['email']}")
            return
    print('Cliente no encontrado')

# Función para hacer unqa compra
def realizar_compra():
    print(' Realizar Compra ')
    email = input('Ingresa el correo electrónico del cliente: ')
    cliente = next((c for c in clientes if c['email'] == email), None)
    if not cliente:
        print('Cliente no encontrado')
        return

    print('Productos disponibles:')
    for producto in productos:
        print(f"{producto['id']}. {producto['nombre']} - {producto['precio']}")

    id_productos = input('Ingrese los IDs de los productos que desea comprar : ')
    id_productos = [int(id.strip()) for id in id_productos.split(",")]

    productos_comprados = [p for p in productos if p["id"] in id_productos]
    if not productos_comprados:
        print('Error: No se seleccionaron productos válidos.')
        return

    numero_pedido = len(pedidos) + 1
    pedido = {'numero': numero_pedido, 'cliente': cliente, 'productos': productos_comprados}
    pedidos.append(pedido)

    print(f'Compra realizada tu número de pedido es: {numero_pedido}')

# Función para ver el estado de la compra
def seguimiento_compra():
    print(' Seguimiento de Compra ')
    numero_pedido = int(input('Ingresa el número de pedido: '))
    pedido = next((i for i in pedidos if i['numero'] == numero_pedido), None)
    if not pedido:
        print('Pedido no encontrado')
        return

    print('Detalles del Pedido:')
    print(f"Cliente: {pedido['cliente']['nombre']} {pedido['cliente']['DNI']} - {pedido['cliente']['email']}")
    print('Productos:')
    for producto in pedido['productos']:
        print(f"- {producto['nombre']} ({producto['precio']})")
        
# funcion para ver el menu principal
def menu():
    while True:
        print(' Menú Principal ')
        print('1. Registrar cliente')
        print('2. Visualizar clientes')
        print('3. Buscar cliente')
        print('4. Realizar compra')
        print('5. Seguimiento de compra')
        print('6. Salir')

        seleccion = input('Seleccione una opción: ')
        if seleccion == '1':
            registrar_cliente()
        elif seleccion == '2':
            mostrar_clientes()
        elif seleccion == '3':
            buscar_cliente()
        elif seleccion == '4':
            realizar_compra()
        elif seleccion == '5':
            seguimiento_compra()
        elif seleccion == '6':
            print('Adios')
            break
        else:
            print('Opción no válida')

menu()
