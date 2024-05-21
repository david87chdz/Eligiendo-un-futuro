const express = require('express');
const mysql = require('mysql2');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const axios = require('axios');
const cors = require('cors');
const { Resend } = require('resend');
const app = express();
const PORT = 3000;
const resend = new Resend('re_Gc3bkw7L_3UrJq7nywVf4SvHHCdpxpPKW');
const connection = mysql.createConnection({
  host: 'localhost',
  port: '6033',
  user: 'david',
  password: 'david',
  database: 'eligiendounfuturo'
});
const MI_EMAIL = 'david87chdz@gmail.com';


const nodemailer = require('nodemailer');


app.use(cors());
app.use(express.json());


/**
 * This method is used to login a user.
 * Firsts it checks if the user exists in the database.
 * Then it checks if the user is active.
 * Then it checks if the password is correct.
 * If all the checks pass, it returns the user data with a JWT token.
 * 
 * @param {string} email - The email of the user.
 * @param {string} password - The password of the user.
 * @returns {Promise} - A promise that resolves with the user data if the login is successful, or rejects with an error if the login fails.
 */
app.post('/login', async (req, res) => {
  const { email, password } = req.body;
  console.log('Email:', email);
  console.log('Contraseña recibida:', password);
  connection.query(
    `
  SELECT 
    u.id AS id,
    u.nombre AS nombre,
    u.apellidos AS apellidos,
    u.activo AS activo,
    u.email AS email,
    r.tipo AS rol,
    u.password AS password
  FROM 
    usuario u 
  LEFT OUTER JOIN 
    rol r 
  ON 
    u.rol_id = r.id 
  WHERE 
    u.email = ?
`,
    [email],
    async (error, results) => {
      if (error) {
        console.error('Error al buscar el usuario:', error);
        return res.status(500).json({ error: 'Error interno del servidor' });
      }

      if (results.length === 0) {
        return res.status(401).json({ error: 'Usuario no encontrado' });
      }

      let usuarioActivo = null;
      for (let usuario of results) {
        if (usuario.activo) {
          usuarioActivo = usuario;
          break;
        }
      }

      if (usuarioActivo === null) {
        return res.status(401).json({ error: 'Usuario no encontrado' });
      }

      const passwordMatch = await bcrypt.compare(password, usuarioActivo.password);

      if (!passwordMatch) {
        return res.status(401).json({ error: 'Contraseña incorrecta' });
      }
     
      const token = jwt.sign({ email: usuarioActivo.email, rol: usuarioActivo.rol, password:usuarioActivo.password, id: usuarioActivo.id, nombre: usuarioActivo.nombre}, 'secret', { expiresIn: '1h' });

      
      const userDataWithRole = {
        id: usuarioActivo.id,
        nombre: usuarioActivo.nombre,
        apellidos: usuarioActivo.apellidos,
        email: usuarioActivo.email,
        rol: usuarioActivo.rol,
        token: token
      };

      res.json(userDataWithRole);
    }
  );
});

/**
 * This method is used to recover the password of a user.
 * It sends an email to the user with root with token to change the password.
 * First it checks if the email exists in the database.
 * Then it sends an email to the user with a token to change the password.
 * 
 * param {string} email - The email of the user to recover the password from.
 * 
 */
/* app.post("/password", async (req, res) => {
  const { email } = req.body;
  if (!email) {
      return res.status(400).json({ error: "Se requiere un correo electrónico" });
  }
  connection.query('SELECT * FROM usuario WHERE email = ?', [email], async (error, results, fields) => {
      if (error) {
          return res.status(500).json({ error: "Error al buscar el correo electrónico en la base de datos" });
      }      
      if (results.length === 0) {
          return res.status(404).json({ error: "Correo electrónico no encontrado" });
      }      
      const plainPassword = results[0].plain_password;      
      const { data, error: emailError } = await resend.emails.send({
          from: "Acme <onboarding@resend.dev>",
          to: [email],
          subject: "Contraseña",
          html: `Tu contraseña es: ${plainPassword}`,
      });
      if (emailError) {
          return res.status(500).json({ error: "Error al enviar el correo electrónico" });
      }
      res.status(200).json({ message: "Correo electrónico enviado con éxito" });
  });
}); */


/**
 * this method is user to recover the password of a user.
 * It sends an email to the user with a token to change the password.
 * First it checks if the email exists in the database.
 * Then it sends an email to the user with a token to change the password.
 * 
 * @param {string} email - The email of the user to recover the password from.
 */
app.post('/send-password-email', async (req, res) => {
  const { email } = req.body;
  if (!email) {
    return res.status(400).json({ error: "Se requiere un correo electrónico" });
  }
  connection.query('SELECT * FROM usuario WHERE email = ?', [email], async (error, results, fields) => {
    if (error) {
      return res.status(500).json({ error: "Error al buscar el correo electrónico en la base de datos" });
    }
    if (results.length === 0) {
      return res.status(404).json({ error: "Correo electrónico no encontrado" });
    }   
    const user = results[0];
    const token = jwt.sign({ id: user.id, email: user.email }, 'your-secret-key', { expiresIn: '1h' });
    const { data, error: emailError } = await resend.emails.send({
      from: "Eligiendo un futuro <onboarding@resend.dev>",
      to: [email],
      subject: "Restablecimiento de contraseña",
      html: `Por favor, haz clic en el siguiente enlace, o pégalo en tu navegador para completar el proceso: http://localhost:4200/reset-password/${token}`,
    });
    if (emailError) {
      return res.status(500).json({ error: "Error al enviar el correo electrónico" });
    }
    res.status(200).json({ message: "Correo electrónico de restablecimiento de contraseña enviado", token: token });
  });
});

/**
 * This method is used to reset the password of a user.
 * First it checks if the token is valid.
 * Then it updates the password of the user.
 * 
 * @param {string} token - The token to reset the password.
 * @param {string} password - The new password.
 */
app.post('/reset-password', async (req, res) => {
  const { token, password } = req.body;
  console.log('body: ',req.body);
  console.log('Token:', token);
  console.log('Nueva contraseña: ', password);
  if (!token || !password) {
    return res.status(400).json({ error: "Se requiere un token y una nueva contraseña" });
  }
  let decoded;
  try {
    decoded = jwt.verify(token, 'your-secret-key');
  } catch (err) {
    return res.status(406).json({ error: "Token inválido" });
  }
  const { id, email } = decoded;
  connection.query('SELECT * FROM usuario WHERE id = ? AND email = ?', [id, email], async (error, results, fields) => {
    if (error) {
      return res.status(500).json({ error: "Error al buscar el usuario en la base de datos" });
    }
    if (results.length === 0) {
      return res.status(404).json({ error: "Usuario no encontrado" });
    }
    const updateQuery = 'UPDATE usuario SET password = ? WHERE id = ?';
    connection.query(updateQuery, [password, id], async (updateError) => {
      if (updateError) {
        return res.status(500).json({ error: "Error al actualizar la contraseña del usuario" });
      }

      res.status(200).json({ message: "Contraseña actualizada con éxito" });
    });
  });
});

/**
 * This method is to send an email when One user register how school.
 * 
 * @param {string} email - The email of the user.
 * @param {string} name - The name of the school.
 * @returns {Promise} - A promise that resolves when the email is sent successfully, or rejects with an error.
 */
app.post('/schoool-registry', async (req, res) => {
  const { email, name } = req.body;

  if (!email || !name) {
    return res.status(400).json({ error: "Se requiere un correo electrónico y un nombre" });
  }
  const { data, error: emailError } = await resend.emails.send({
    from: "Eligiendo un futuro <onboarding@resend.dev>",
    to: [MI_EMAIL],
    subject: "Nuevo colegio registrado",
    html: `Se ha registrado un nuevo colegio con el nombre: ${name} y el email: ${email}`,
  });

  if (emailError) {
    return res.status(500).json({ error: "Error al enviar el correo electrónico" });
  }

  res.status(200).json({ message: "Correo electrónico de aviso de creacion de colegio enviado" });
});


/**
   * Sends an email when the school email doesn´t exist in a JSON.
   *
   * @param {string} MI_EMAIL - The email address to send the reset email to.
   * @returns {Promise} A promise that resolves when the email is sent successfully, or rejects with an error.
   */
app.post('/school-no-exists', async (req, res) => {
  const { email } = req.body;

  if (!email ) {
    return res.status(400).json({ error: "Se requiere un correo electrónico" });
  }
  const { data, error: emailError } = await resend.emails.send({
    from: "Eligiendo un futuro <onboarding@resend.dev>",
    to: [MI_EMAIL],
    subject: "Restablecimiento de contraseña",
    html: `Los datos proporcionados no se encuentran en la lista de colegios registrados por la junta de castilla y leon por favor pongase en contacto con el admin de nuestra web para poder solucionarlo:
    eligiendounfuturo@gmail.com`,
  });

  if (emailError) {
    return res.status(500).json({ error: "Error al enviar el correo electrónico" });
  }

  res.status(200).json({ message: "Correo electrónico de aviso de creacion de colegio enviado" });
});

app.listen(PORT, () => {
  console.log(`Servidor escuchando en el puerto ${PORT}`);
});
