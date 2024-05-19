const mysql = require('mysql2');

const connection = mysql.createConnection({
  host: 'localhost', 
  port: '6033', 
  user: 'david',
  password: 'david',
  database: 'eligiendounfuturo' 
});

connection.connect((err) => {
  if (err) {
    console.error('Error al conectar a la base de datos:', err);
    return;
  }
  console.log('Conexión exitosa a la base de datos MySQL');
});
