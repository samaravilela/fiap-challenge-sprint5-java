-- ####################################################
-- SCRIPT 3: TESTAR INSERÇÃO MANUAL
-- Use este script para testar se a inserção está funcionando
-- ####################################################

-- Teste 1: Verificar se as sequences existem
SELECT 'Verificando sequences...' AS STATUS FROM DUAL;
SELECT sequence_name FROM user_sequences WHERE sequence_name LIKE 'SEQ_EASEHC%';

-- Se não aparecer nenhuma sequence, execute o script 1_CRIAR_SEQUENCES.sql primeiro!

-- Teste 2: Inserir um paciente de teste
INSERT INTO T_EASEHC_PACIENTE (ID_PACIENTE, NM_COMPLETO, DT_NASC, GENERO, TELEFONE, TP_SANGUINEO, ALERGIAS)
VALUES (SEQ_EASEHC_PACIENTE.NEXTVAL, 'Teste Silva', TO_DATE('1990-01-01','YYYY-MM-DD'), 'M', '11999999999', 'O+', 'Nenhuma');

-- Teste 3: Verificar se foi inserido
SELECT * FROM T_EASEHC_PACIENTE WHERE NM_COMPLETO = 'Teste Silva';

-- Teste 4: Se funcionou, faça COMMIT, senão ROLLBACK
-- COMMIT;
-- ou
-- ROLLBACK;

-- ####################################################
-- FIM DO SCRIPT
-- ####################################################

