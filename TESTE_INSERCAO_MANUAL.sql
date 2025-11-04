-- ####################################################
-- TESTE: Inserir um paciente manualmente para testar
-- ####################################################

-- Ver situação atual
PROMPT Estado atual:
SELECT COUNT(*) AS TOTAL_PACIENTES FROM T_EASEHC_PACIENTE;
SELECT NVL(MAX(ID_PACIENTE), 0) AS MAIOR_ID FROM T_EASEHC_PACIENTE;

-- Verificar se a sequence existe
SELECT sequence_name, last_number 
FROM user_sequences 
WHERE sequence_name = 'SEQ_EASEHC_PACIENTE';

-- Tentar inserir um paciente de teste
PROMPT 
PROMPT Inserindo paciente de teste...

INSERT INTO T_EASEHC_PACIENTE (
    ID_PACIENTE, 
    NM_COMPLETO, 
    DT_NASC, 
    GENERO, 
    TELEFONE, 
    TP_SANGUINEO, 
    ALERGIAS
) VALUES (
    SEQ_EASEHC_PACIENTE.NEXTVAL,
    'Paciente Teste ' || TO_CHAR(SYSDATE, 'HH24:MI:SS'),
    TO_DATE('1990-01-01', 'YYYY-MM-DD'),
    'M',
    '11999999999',
    'O+',
    'Nenhuma'
);

COMMIT;

-- Ver resultado
PROMPT 
PROMPT Resultado:
SELECT ID_PACIENTE, NM_COMPLETO, TELEFONE 
FROM T_EASEHC_PACIENTE 
ORDER BY ID_PACIENTE DESC 
FETCH FIRST 5 ROWS ONLY;

PROMPT 
PROMPT ✅ Se apareceu o paciente acima, está funcionando!

-- ####################################################
-- FIM DO SCRIPT
-- ####################################################

