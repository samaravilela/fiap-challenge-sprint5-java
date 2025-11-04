-- ####################################################
-- SOLUÇÃO RÁPIDA: Ajustar a sequence para o próximo ID disponível
-- ####################################################

PROMPT ============================================
PROMPT    AJUSTANDO SEQUENCES AUTOMATICAMENTE
PROMPT ============================================

-- Passo 1: Ver quantos pacientes já existem
SELECT 'Pacientes atuais na tabela:' AS INFO, COUNT(*) AS QTD 
FROM T_EASEHC_PACIENTE;

-- Passo 2: Ver o maior ID atual
SELECT 'Maior ID atual:' AS INFO, NVL(MAX(ID_PACIENTE), 0) AS VALOR 
FROM T_EASEHC_PACIENTE;

-- Passo 3: Deletar a sequence antiga se existir
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_EASEHC_PACIENTE';
   DBMS_OUTPUT.PUT_LINE('Sequence antiga deletada');
EXCEPTION
   WHEN OTHERS THEN 
   DBMS_OUTPUT.PUT_LINE('Sequence não existia');
END;
/

-- Passo 4: Criar sequence começando DEPOIS do último ID
DECLARE
   v_max_id NUMBER;
BEGIN
   -- Buscar o maior ID atual
   SELECT NVL(MAX(ID_PACIENTE), 0) INTO v_max_id 
   FROM T_EASEHC_PACIENTE;
   
   -- Criar sequence começando do próximo número
   EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_EASEHC_PACIENTE ' ||
                     'START WITH ' || (v_max_id + 1) || ' ' ||
                     'INCREMENT BY 1 NOCACHE NOCYCLE';
   
   DBMS_OUTPUT.PUT_LINE('Sequence criada começando em: ' || (v_max_id + 1));
END;
/

-- Passo 5: Verificar
SELECT 'Próximo valor da sequence:' AS INFO, SEQ_EASEHC_PACIENTE.NEXTVAL AS VALOR 
FROM DUAL;

-- Voltar um valor (porque consumimos um NEXTVAL acima)
ALTER SEQUENCE SEQ_EASEHC_PACIENTE INCREMENT BY -1;
SELECT SEQ_EASEHC_PACIENTE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_EASEHC_PACIENTE INCREMENT BY 1;

PROMPT 
PROMPT ============================================
PROMPT    PRONTO! Agora tente cadastrar novamente
PROMPT ============================================

-- ####################################################
-- FIM DO SCRIPT
-- ####################################################

