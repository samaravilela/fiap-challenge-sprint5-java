-- ####################################################
-- SCRIPT 1: CRIAR SEQUENCES
-- Execute este script PRIMEIRO, antes de inserir dados
-- ####################################################

-- Sequence para Pacientes
CREATE SEQUENCE SEQ_EASEHC_PACIENTE
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Médicos
CREATE SEQUENCE SEQ_EASEHC_MEDICO
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Especialidades
CREATE SEQUENCE SEQ_EASEHC_ESPECIALIDADE
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Localizações
CREATE SEQUENCE SEQ_EASEHC_LOCALIZACAO
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Consultas
CREATE SEQUENCE SEQ_EASEHC_CONSULTA
START WITH 1001
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Cancelamentos
CREATE SEQUENCE SEQ_EASEHC_CANREM
START WITH 5001
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Histórico Médico
CREATE SEQUENCE SEQ_EASEHC_HISTORICO
START WITH 9001
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Sequence para Orientações
CREATE SEQUENCE SEQ_EASEHC_ORIENTACAO
START WITH 8001
INCREMENT BY 1
NOCACHE
NOCYCLE;

COMMIT;

-- Verificar sequences criadas
SELECT sequence_name, min_value, max_value, increment_by, last_number
FROM user_sequences
WHERE sequence_name LIKE 'SEQ_EASEHC%'
ORDER BY sequence_name;

-- ####################################################
-- FIM DO SCRIPT
-- ####################################################

