/*SET COLLATION PORTUGUESE STRENGTH PRIMARY;*/
/*SET FOREIGN_KEY_CHECKS=0;*/

CREATE TABLE IF NOT EXISTS Funcionario (
id_funcionario int PRIMARY KEY,
nome varchar(120),
cpf varchar(24),
endereco varchar(120),
telefone varchar(12),
email varchar(120),
login varchar(32) UNIQUE,
senha varchar(120),
especialidade varchar(120),
registro varchar(64),
permissao int,
criado_em datetime DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS Paciente (
id_paciente int PRIMARY KEY,
nome varchar(120),
cpf varchar(24),
endereco varchar(120),
telefone varchar(12),
email varchar(120),
sexo varchar(10),
cartao varchar(64),
criado_em datetime DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS FichaMedica (
id_ficha int PRIMARY KEY,
qp clob,
hda clob,
hf clob,
hp clob,
isda clob,
prescricao clob,
id_paciente int,
criado_em datetime DEFAULT CURRENT_TIMESTAMP(),
FOREIGN KEY(id_paciente) REFERENCES Paciente (id_paciente),
);

CREATE TABLE IF NOT EXISTS Exame (
id_exame int PRIMARY KEY,
nome varchar(120),
data_exame datetime,
data_resultado datetime,
resultado clob,
id_ficha int,
criado_em datetime DEFAULT CURRENT_TIMESTAMP(),
FOREIGN KEY(id_ficha) REFERENCES FichaMedica (id_ficha),
);

CREATE TABLE IF NOT EXISTS Imagem (
id_imagem int PRIMARY KEY,
arquivo varchar(256),
id_exame int,
criado_em datetime DEFAULT CURRENT_TIMESTAMP(),
FOREIGN KEY(id_exame) REFERENCES Exame (id_exame),
);

/*SET FOREIGN_KEY_CHECKS=1;*/
