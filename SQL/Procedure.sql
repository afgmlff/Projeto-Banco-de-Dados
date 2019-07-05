/*De acordo com uma entrada de 0 a 5, a função lista o nome de todos os funcionários listados na profissão a partir de:
0 -> nutricionistas
1 -> manutenção 
2 -> estagiarios 
3 -> professores
4 -> administração
5 -> fisioterapeutas
*/

delimiter $$

CREATE PROCEDURE Funcionarios3(IN op INTEGER)

BEGIN

    CASE  op
    
	WHEN 0 THEN SELECT funcionario.nome FROM nutricionista JOIN funcionario ON
			(nutricionista.codigo_funcionario = funcionario.registro_funcionario);
            
	WHEN 1 THEN SELECT funcionario.nome FROM manutencao JOIN funcionario ON
			(manutencao.codigo_funcionario = funcionario.registro_funcionario);
            
        WHEN 2 THEN SELECT funcionario.nome FROM estagiario JOIN funcionario ON
			(estagiario.codigo_funcionario = funcionario.registro_funcionario);
            
        WHEN 3 THEN SELECT funcionario.nome FROM professor JOIN funcionario ON
			(professor.codigo_funcionario = funcionario.registro_funcionario);
            
        WHEN 4 THEN SELECT funcionario.nome FROM administracao JOIN funcionario ON
			(administracao.codigo_funcionario = funcionario.registro_funcionario);
            
        WHEN 5 THEN SELECT funcionario.nome FROM fisioterapeuta JOIN funcionario ON
			(fisioterapeuta.codigo_funcionario = funcionario.registro_funcionario);
	
    END CASE;

  END $$

delimiter ;

CALL Funcionarios3(0); /* Exemplo para a análise do nome dos funcionários nutricionistas*/

/* Para chamar a procedure:
   CALL Funcionarios3(op);,
   sendo 'op' um inteiro de 0 a 5.   
*/
