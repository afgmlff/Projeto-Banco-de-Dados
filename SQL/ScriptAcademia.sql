CREATE DATABASE IF NOT EXISTS `Academia`;

USE `Academia`;

CREATE TABLE IF NOT EXISTS `Usuario` (
    `matricula` INT AUTO_INCREMENT NOT NULL UNIQUE,
    `nome` VARCHAR(255) NOT NULL,
    `endereco` VARCHAR(255) NOT NULL,
    `data_nascimento` DATE NOT NULL,
    `data_inicio` DATE NOT NULL,
    `foto` LONGBLOB NOT NULL,
    PRIMARY KEY (`matricula`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Usuario_Telefone` (
    `usuario` INT,
    `telefone` INT NOT NULL,
    PRIMARY KEY (`usuario` , `telefone`),
    CONSTRAINT FOREIGN KEY (`usuario`) REFERENCES `Usuario` (`matricula`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Funcionario` (
    `registro_funcionario` INT NOT NULL UNIQUE AUTO_INCREMENT,
    `nome` VARCHAR(255) NOT NULL,
    `endereco` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`registro_funcionario`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Funcionario_Telefone` (
    `codigo_funcionario` INT,
    `telefone` INT,
    PRIMARY KEY (`codigo_funcionario` , `telefone`),
    CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Manutencao` (
    `codigo_funcionario` INT,
    `area` VARCHAR(255),
    PRIMARY KEY (`codigo_funcionario`),
    CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Estagiario` (
    `codigo_funcionario` INT,
    `area` VARCHAR(255) NOT NULL,
    `instituicao_de_ensino` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`codigo_funcionario`),
    CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Professor` (
    `codigo_funcionario` INT,
    PRIMARY KEY (`codigo_funcionario`),
    CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Professor_Especialidade` (
    `codigo_professor` INT,
    `especialidade` VARCHAR(255),
    PRIMARY KEY (`codigo_professor` , `especialidade`),
    CONSTRAINT FOREIGN KEY (`codigo_professor`) REFERENCES `Professor` (`codigo_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Nutricionista` (
    `codigo_funcionario` INT REFERENCES `Funcionario` (`registro_funcionario`)
    ON DELETE CASCADE,
    `CFN` INT NOT NULL UNIQUE,
    PRIMARY KEY (`codigo_funcionario`),
	CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Fisioterapeuta` (
    `codigo_funcionario` INT,
    `CREFITO` INT NOT NULL UNIQUE,
    PRIMARY KEY (`codigo_funcionario`),
    CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Administracao` (
    `codigo_funcionario` INT,
    `area` VARCHAR(255),
    PRIMARY KEY (`codigo_funcionario`),
    CONSTRAINT FOREIGN KEY(`codigo_funcionario`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Equipamento` (
    `codigo_equipamento` INT NOT NULL UNIQUE AUTO_INCREMENT,
    `nome` VARCHAR(255) NOT NULL,
    `video_funcionamento` LONGBLOB,
    PRIMARY KEY (`codigo_equipamento`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Reparo` (
    `codigo_funcionario` INT,
    `codigo_equipamento` INT,
    `descricao_reparo` VARCHAR(255) NOT NULL,
    `data_reparo` DATETIME NOT NULL UNIQUE,
    PRIMARY KEY (`codigo_funcionario` , `codigo_equipamento` , `data_reparo`),
    CONSTRAINT FOREIGN KEY (`codigo_funcionario`) REFERENCES `Manutencao` (`codigo_funcionario`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`codigo_equipamento`) REFERENCES `Equipamento` (`codigo_equipamento`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Modalidade` (
    `codigo_modalidade` INT NOT NULL UNIQUE AUTO_INCREMENT,
    `nome` VARCHAR(255),
    `descricao` VARCHAR(255),
    `dias` VARCHAR(255),
    `horarios` VARCHAR(255),
    PRIMARY KEY (`codigo_modalidade`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Modalidades_Ministradas` (
    `codigo_professor` INT,
    `codigo_modalidade` INT,
    PRIMARY KEY (`codigo_professor` , `codigo_modalidade`),
    CONSTRAINT FOREIGN KEY (`codigo_professor`) REFERENCES `Professor` (`codigo_funcionario`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`codigo_modalidade`)  REFERENCES `Modalidade` (`codigo_modalidade`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Estagiario_Eh_Supervisionado` (
    `codigo_estagiario` INT,
    `codigo_supervisor` INT,
    PRIMARY KEY (`codigo_estagiario` , `codigo_supervisor`),
    CONSTRAINT FOREIGN KEY (`codigo_estagiario`) REFERENCES `Estagiario` (`codigo_funcionario`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`codigo_supervisor`) REFERENCES `Funcionario` (`registro_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Inscricao` (
    `matricula_usuario` INT,
    `codigo_modalidade` INT,
    PRIMARY KEY (`matricula_usuario` , `codigo_modalidade`),
    CONSTRAINT FOREIGN KEY (`matricula_usuario`) REFERENCES `Usuario` (`matricula`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`codigo_modalidade`) REFERENCES `Modalidade` (`codigo_modalidade`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Consulta_Nutri` (
    `matricula_usuario` INT,
    `cod_nutri` INT,
    `data_hora` DATETIME NOT NULL,
    PRIMARY KEY (`matricula_usuario` , `cod_nutri` , `data_hora`),
    CONSTRAINT FOREIGN KEY (`matricula_usuario`) REFERENCES `Usuario` (`matricula`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`cod_nutri`) REFERENCES `Nutricionista` (`codigo_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Consulta_Fisio` (
    `matricula_usuario` INT,
    `cod_fisio` INT,
    `data_hora` DATETIME NOT NULL,
    PRIMARY KEY (`matricula_usuario` , `cod_fisio` , `data_hora`),
    CONSTRAINT FOREIGN KEY (`matricula_usuario`) REFERENCES `Usuario` (`matricula`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`cod_fisio`) REFERENCES `Fisioterapeuta` (`codigo_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Elabora_Treino` (
    `matricula_usuario` INT,
    `cod_prof` INT,
    `data_hora` DATETIME NOT NULL,
    PRIMARY KEY (`matricula_usuario` , `cod_prof` , `data_hora`),
    CONSTRAINT FOREIGN KEY (`matricula_usuario`) REFERENCES `Usuario` (`matricula`) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`cod_prof`) REFERENCES `Professor` (`codigo_funcionario`) ON DELETE CASCADE
) ENGINE = InnoDB;