
use tienda

//despues del use

// --- BORRAR COLECCIONES SI EXISTEN ---
db.clientes.drop()
db.carritos.drop()
db.pedidos.drop()

db.clientes.createIndex(
    { email: 1 },         // Campo que será único
    { unique: true }      // Evita duplicados
)
db.clientes.insertOne({
    _id: ObjectId(),
    nombre: "Nombre Cliente 1",
    email: "cliente1@example.com",
    direccion: "Dirección Cliente 1"
})

db.clientes.insertOne({
    _id: ObjectId(),
    nombre: "Nombre Cliente 2",
    email: "cliente2@example.com",
    direccion: "Dirección Cliente 2"
})

//INSERTAR CARRITOS
db.carritos.insertOne({
    _id: ObjectId(),
    cliente_id: db.clientes.findOne({ email: "cliente1@example.com" })._id,
    productos: [
        { producto_id: 1, nombre: "Laptop HP Pavilion", cantidad: 2, precio_unitario: 799.99 },
        { producto_id: 3, nombre: "Tablet Lenovo Tab M10", cantidad: 1, precio_unitario: 199.99 }
    ],
    fecha_actualizacion: new Date()
})

db.carritos.insertOne({
    _id: ObjectId(),
    cliente_id: db.clientes.findOne({ email: "cliente2@example.com" })._id,
    productos: [
        { producto_id: 3, nombre: "Tablet Lenovo Tab M10", cantidad: 1, precio_unitario: 199.99 }
    ],
    fecha_actualizacion: new Date()
})

//INSERTAR PEDIDOS
db.pedidos.insertOne({
    _id: ObjectId(),
    cliente_id: db.clientes.findOne({ email: "cliente1@example.com" })._id,
    productos: [
        { producto_id: 1, nombre: "Laptop HP Pavilion", cantidad: 2, precio_unitario: 799.99 }
    ],
    total: 1599.98,
    fecha_pedido: new Date("2023-01-01T12:30:00Z")
})

db.pedidos.insertOne({
    _id: ObjectId(),
    cliente_id: db.clientes.findOne({ email: "cliente2@example.com" })._id,
    productos: [
        { producto_id: 3, nombre: "Tablet Lenovo Tab M10", cantidad: 1, precio_unitario: 199.99 }
    ],
    total: 199.99,
    fecha_pedido: new Date("2023-02-15T15:00:00Z")
})

// ÍNDICES PARA OPTIMIZAR CONSULTAS ---
db.carritos.createIndex({ cliente_id: 1 })
db.pedidos.createIndex({ cliente_id: 1 })