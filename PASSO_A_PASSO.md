# 🔧 PASSO A PASSO: Como Inserir Dados no Banco

## ⚠️ Problema: "Não está inserindo paciente"

### 🎯 Solução em 3 passos:

---

## ✅ Passo 1: Criar as SEQUENCES

**Arquivo**: `1_CRIAR_SEQUENCES.sql`

```bash
# No SQL*Plus ou SQL Developer:
sqlplus rm565060/280201@oracle.fiap.com.br:1521/ORCL

SQL> @1_CRIAR_SEQUENCES.sql
```

**O que faz:**
- Cria as sequences necessárias para auto-incremento dos IDs
- Sem as sequences, o INSERT vai falhar com erro: `ORA-02289: sequence does not exist`

**Verificação:**
```sql
SELECT sequence_name FROM user_sequences WHERE sequence_name LIKE 'SEQ_EASEHC%';
```

Deve mostrar 8 sequences:
- SEQ_EASEHC_PACIENTE
- SEQ_EASEHC_MEDICO
- SEQ_EASEHC_ESPECIALIDADE
- SEQ_EASEHC_LOCALIZACAO
- SEQ_EASEHC_CONSULTA
- SEQ_EASEHC_CANREM
- SEQ_EASEHC_HISTORICO
- SEQ_EASEHC_ORIENTACAO

---

## ✅ Passo 2: Inserir Dados de Teste

**Arquivo**: `2_INSERIR_DADOS_TESTE.sql`

```bash
SQL> @2_INSERIR_DADOS_TESTE.sql
```

**O que faz:**
- Insere 5 pacientes
- Insere 3 médicos
- Insere 5 especialidades
- Insere 3 localizações
- Insere 3 consultas
- Insere 5 registros na tabela associativa

**Verificação:**
```sql
SELECT COUNT(*) FROM T_EASEHC_PACIENTE;
SELECT * FROM T_EASEHC_PACIENTE;
```

---

## ✅ Passo 3: Testar Sistema Java

Depois de inserir os dados, execute o sistema:

```bash
cd /Users/samaravilela/Documents/Sprint4

# Compilar
javac -d out/production/Sprint4 \
      -cp "lib/ojdbc8 (1).jar" \
      src/br/com/fiap/**/*.java

# Executar
java -cp "out/production/Sprint4:lib/ojdbc8 (1).jar" \
     br.com.fiap.main.SistemaAgendamentoConsultas
```

Escolha opção **3 - Listar Pacientes**

**Resultado esperado:**
```
───────────── PACIENTES CADASTRADOS ─────────────

Total de pacientes: 5
────────────────────────────────────────────────
ID: 1 | Ana Silva | Tel: 11988880001 | Tipo: O+
ID: 2 | Bruno Santos | Tel: 11977770002 | Tipo: A+
ID: 3 | Carla Mendes | Tel: 11333330003 | Tipo: B-
ID: 4 | Daniel Oliveira | Tel: 11222220004 | Tipo: AB+
ID: 5 | Eduarda Ramos | Tel: 11444440005 | Tipo: O-
```

---

## 🐛 Problemas Comuns

### Erro: "ORA-02289: sequence does not exist"

**Causa**: As sequences não foram criadas.

**Solução**: Execute o `1_CRIAR_SEQUENCES.sql`

---

### Erro: "ORA-00001: unique constraint violated"

**Causa**: Tentando inserir dados duplicados (nome de paciente único).

**Solução**: 
```sql
-- Limpar dados antigos
DELETE FROM T_EASEHC_CONSULTA;
DELETE FROM T_EASEHC_MED_ESP;
DELETE FROM T_EASEHC_PACIENTE;
DELETE FROM T_EASEHC_MEDICO;
DELETE FROM T_EASEHC_ESPECIALIDADE;
DELETE FROM T_EASEHC_LOCALIZACAO;
COMMIT;

-- Resetar sequences
DROP SEQUENCE SEQ_EASEHC_PACIENTE;
DROP SEQUENCE SEQ_EASEHC_MEDICO;
-- ... etc

-- Executar novamente o script 1 e 2
```

---

### Erro: "ORA-02291: integrity constraint violated"

**Causa**: Tentando inserir consulta antes de inserir paciente/médico.

**Solução**: Execute os INSERTs na ordem:
1. Pacientes
2. Médicos
3. Especialidades
4. Localizações
5. Consultas (por último, pois depende dos outros)

---

## 📊 Comandos Úteis

### Verificar quantidade de registros:
```sql
SELECT 'PACIENTES' AS TABELA, COUNT(*) AS QTD FROM T_EASEHC_PACIENTE
UNION ALL
SELECT 'MEDICOS', COUNT(*) FROM T_EASEHC_MEDICO
UNION ALL
SELECT 'ESPECIALIDADES', COUNT(*) FROM T_EASEHC_ESPECIALIDADE
UNION ALL
SELECT 'LOCALIZACOES', COUNT(*) FROM T_EASEHC_LOCALIZACAO
UNION ALL
SELECT 'CONSULTAS', COUNT(*) FROM T_EASEHC_CONSULTA;
```

### Limpar todos os dados:
```sql
DELETE FROM T_EASEHC_ORIENTACAO;
DELETE FROM T_EASEHC_HISTORICO;
DELETE FROM T_EASEHC_CANREM;
DELETE FROM T_EASEHC_CONSULTA;
DELETE FROM T_EASEHC_MED_ESP;
DELETE FROM T_EASEHC_LOCALIZACAO;
DELETE FROM T_EASEHC_ESPECIALIDADE;
DELETE FROM T_EASEHC_MEDICO;
DELETE FROM T_EASEHC_PACIENTE;
COMMIT;
```

### Ver último ID gerado:
```sql
SELECT SEQ_EASEHC_PACIENTE.CURRVAL FROM DUAL;
```

---

## 🎯 Resumo

1. ✅ Execute `1_CRIAR_SEQUENCES.sql` (UMA VEZ)
2. ✅ Execute `2_INSERIR_DADOS_TESTE.sql` (para popular)
3. ✅ Execute o sistema Java
4. ✅ Teste listar pacientes

**Pronto!** Agora o sistema deve funcionar perfeitamente! 🚀

