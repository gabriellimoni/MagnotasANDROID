CREATE TABLE usuario(
	_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	u_nome VARCHAR(50) NOT NULL,
	u_apelido VARCHAR(12) NOT NULL,
	u_senha VARCHAR(250) NOT NULL,
	u_email VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE nota(
	_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	n_assunto VARCHAR(250) NOT NULL,
	n_texto VARCHAR(250) NOT NULL,
	n_usu INTEGER NOT NULL,
	n_categoria INTEGER NOT NULL,
	n_data_inserida TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	n_data_expira TIMESTAMP NOT NULL,
	FOREIGN KEY (n_usu) REFERENCES usuario(_id)
);