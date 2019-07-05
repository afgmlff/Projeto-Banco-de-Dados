/* View criada para a análise do nome e telefone dos usuários que praticam karatê
*/

CREATE OR REPLACE VIEW vw_viewFoneUserKarate AS
    SELECT 
        usuario_telefone.telefone, usuario.nome
    FROM
        (usuario_telefone
        JOIN usuario ON (usuario.matricula = usuario_telefone.usuario)
        JOIN inscricao ON (usuario.matricula = inscricao.matricula_usuario)
        JOIN modalidade ON (inscricao.codigo_modalidade = modalidade.codigo_modalidade))
    WHERE
        inscricao.codigo_modalidade = (SELECT 
                modalidade.codigo_modalidade
            FROM
                modalidade
            WHERE
                modalidade.nome = 'karate');

SELECT 
    *
FROM
    vw_viewFoneUserKarate;
