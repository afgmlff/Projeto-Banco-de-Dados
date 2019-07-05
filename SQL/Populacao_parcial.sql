USE `academia`;

INSERT INTO `Usuario`
VALUES (DEFAULT, 'flavio', 'octogonal', '1997-09-17', '2018-09-17', 'blobbbb');

INSERT INTO `Usuario`
VALUES (DEFAULT, 'lucas', 'cruzeiro', '1996-09-17', '2018-11-02', 'blobbbbaa');

INSERT INTO `Modalidade`
VALUES (DEFAULT, 'crossfit', 'balancar corda e pular', 'ter-qui', '14h-15h');

INSERT INTO `Modalidade`
VALUES (DEFAULT, 'karate', 'luta de contato', 'seg-qua', '08h-09h');

INSERT INTO `Inscricao`
VALUES (1, 1);

INSERT INTO `Inscricao`
VALUES (1, 2);

INSERT INTO `Inscricao`
VALUES (2, 1);

INSERT INTO `Inscricao`
VALUES (2, 2);

INSERT INTO `Usuario_Telefone`
VALUES (1, 99997777);

INSERT INTO `Usuario_Telefone`
VALUES (1, 99998888);

INSERT INTO `Usuario_Telefone`
VALUES (2, 88885555);

INSERT INTO `Funcionario`
VALUES (DEFAULT, 'Vinicius', 'Cruzeiro');

INSERT INTO `Funcionario`
VALUES (DEFAULT, 'Augusto', 'Cruzeiro');

INSERT INTO `Funcionario`
VALUES (DEFAULT, 'Marcos', 'Aguas Claras');

INSERT INTO `Funcionario`
VALUES (DEFAULT, 'Joao', 'Sudoeste');

INSERT INTO `Funcionario_Telefone`
VALUES (1, 22234112);

INSERT INTO `Funcionario_Telefone`
VALUES (1, 32234112);

INSERT INTO `Funcionario_Telefone`
VALUES (2, 12234112);

INSERT INTO `Funcionario_Telefone`
VALUES (2, 52234112);

INSERT INTO `Manutencao`
VALUES (1, 'eletrica');

INSERT INTO `Manutencao`
VALUES (2, 'eletrica');

INSERT INTO `Equipamento`
VALUES (DEFAULT, 'multimetro', 'blob');

INSERT INTO `Equipamento`
VALUES (DEFAULT, 'amperimetro', 'blob');

INSERT INTO `Reparo`
VALUES (1, 1, 'eletrica', '2019-07-14');

INSERT INTO `Reparo`
VALUES (1, 2, 'eletrica', '2019-07-14');

INSERT INTO `Reparo`
VALUES (2, 1, 'eletrica', '2019-07-14');

INSERT INTO `estagiario`
VALUES (4, 'nutricao', 'UnB');

INSERT INTO `nutricionista`
VALUES (3, 1234);

INSERT INTO `estagiario_eh_supervisionado`
VALUES (4, 3);

INSERT INTO consulta_nutri
VALUES (1, 3, '2019-08-08');

INSERT INTO consulta_nutri
VALUES (2, 3, '2019-08-08');
