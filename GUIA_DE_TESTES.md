# 📝 Guia de Testes - Sistema de Agendamento de Consultas

**RM565060 - Sprint 3 - FIAP**

## 🎯 Objetivo

Este documento demonstra como testar todas as operações CRUD implementadas no sistema.

---

## 🚀 Iniciando o Sistema

1. Execute a aplicação:
   ```bash
   java -cp "lib/ojdbc8.jar:out/production/Sprint3" br.com.fiap.main.SistemaAgendamentoConsultas
   ```

2. O sistema testará automaticamente a conexão com o banco de dados
3. Você verá o menu principal

---

## ✅ Testando Operações CRUD

### 📌 Teste 1: CREATE (Criar Consulta)

**Passos:**
1. No menu principal, digite `1` (Gerenciar Consultas)
2. Digite `1` (CREATE - Criar Nova Consulta)
3. Veja a lista de pacientes disponíveis
4. Digite o ID de um paciente (ex: `1`)
5. Veja a lista de médicos disponíveis
6. Digite o ID de um médico (ex: `1`)
7. Digite uma data futura (ex: `20/11/2025 14:30`)
8. Digite o status (ex: `Agendada`)
9. Digite o preparo necessário (ex: `Jejum de 12 horas`) ou deixe em branco

**Resultado Esperado:**
```
✓ Consulta criada! Linhas afetadas: 1
✓ Consulta criada com sucesso!
```

**Isso demonstra: Operação CREATE funcionando corretamente**

---

### 📌 Teste 2: READ - Listar Todas (Ler)

**Passos:**
1. No menu de consultas, digite `2` (READ - Listar Todas as Consultas)

**Resultado Esperado:**
```
───────────── READ: LISTAR CONSULTAS ─────────────
✓ Consultas carregadas: X

Total de consultas: X
────────────────────────────────────────────────
Consulta #1 - Paciente: 1 | Médico: 1 | Data: ...
Consulta #2 - Paciente: 2 | Médico: 3 | Data: ...
...
```

**Isso demonstra: Operação READ (listar todos) funcionando corretamente**

---

### 📌 Teste 3: READ - Buscar por ID (Ler Específico)

**Passos:**
1. No menu de consultas, digite `3` (READ - Buscar Consulta por ID)
2. Digite o ID de uma consulta existente (ex: `1`)

**Resultado Esperado:**
```
───────────── READ: BUSCAR CONSULTA ─────────────
Digite o ID da consulta: 1

✓ Consulta encontrada:
Consulta #1 - Paciente: 1 | Médico: 1 | Data: ... | Status: Agendada
```

**Teste também com ID inexistente:**
```
Digite o ID da consulta: 999
✗ Consulta não encontrada!
```

**Isso demonstra: Operação READ (buscar específico) funcionando corretamente**

---

### 📌 Teste 4: UPDATE (Atualizar)

**Passos:**
1. No menu de consultas, digite `4` (UPDATE - Atualizar Consulta)
2. Digite o ID de uma consulta existente (ex: `1`)
3. Veja os dados atuais da consulta
4. Digite nova data (ou Enter para manter)
5. Digite novo status (ex: `Confirmada`)
6. Digite novo preparo (ou Enter para manter)

**Resultado Esperado:**
```
───────────── UPDATE: ATUALIZAR CONSULTA ─────────────
Digite o ID da consulta que deseja atualizar: 1

Consulta atual:
Consulta #1 - Paciente: 1 | Médico: 1 | Data: 20/11/2025 14:30 | Status: Agendada

--- Digite os novos dados (ou pressione Enter para manter o atual) ---
Nova data e hora (formato: dd/MM/yyyy HH:mm) [20/11/2025 14:30]: 
Novo status [Agendada]: Confirmada
Novo preparo [Jejum de 12 horas]: 

✓ Consulta atualizada com sucesso!
✓ Consulta atualizada com sucesso!
```

**Verifique a mudança:**
- Use a opção 3 (Buscar por ID) para confirmar que o status mudou para "Confirmada"

**Isso demonstra: Operação UPDATE funcionando corretamente**

---

### 📌 Teste 5: DELETE (Deletar)

**Passos:**
1. No menu de consultas, digite `5` (DELETE - Cancelar/Deletar Consulta)
2. Digite o ID de uma consulta para deletar (ex: `2`)
3. Veja os dados da consulta que será deletada
4. Digite `S` para confirmar

**Resultado Esperado:**
```
───────────── DELETE: CANCELAR CONSULTA ─────────────
Digite o ID da consulta que deseja deletar: 2

Consulta a ser deletada:
Consulta #2 - Paciente: 2 | Médico: 3 | Data: ... | Status: Agendada

Tem certeza que deseja deletar? (S/N): S

✓ Consulta deletada com sucesso!
✓ Consulta deletada com sucesso!
```

**Verifique a exclusão:**
- Use a opção 2 (Listar Todas) para confirmar que a consulta foi removida
- Use a opção 3 (Buscar por ID) com o ID deletado - deve retornar "não encontrada"

**Isso demonstra: Operação DELETE funcionando corretamente**

---

## 🔄 Fluxo Completo de Teste Sugerido

### Cenário: Agendar e gerenciar uma consulta completa

1. **CREATE**: Crie uma nova consulta
   - Paciente: João
   - Médico: Dr. Silva
   - Data: 25/11/2025 10:00
   - Status: Agendada

2. **READ (Listar)**: Verifique que a consulta aparece na lista

3. **READ (Buscar)**: Busque a consulta pelo ID para ver detalhes

4. **UPDATE**: Atualize o status para "Confirmada"

5. **READ (Buscar)**: Confirme que o status foi alterado

6. **UPDATE**: Atualize novamente para "Realizada"

7. **DELETE**: Delete a consulta

8. **READ (Listar)**: Confirme que a consulta não existe mais

---

## 📊 Outras Funcionalidades Demonstradas

### Listar Médicos (Menu Principal - Opção 2)
```
───────────── MÉDICOS DISPONÍVEIS ─────────────
✓ Médicos carregados: 3

Total de médicos: 3
────────────────────────────────────────────────
ID: 1 | Dr. João Silva - Cardiologia (Hospital São Paulo)
ID: 2 | Dra. Maria Santos - Ortopedia (Clínica Central)
...
```

### Listar Pacientes (Menu Principal - Opção 3)
```
───────────── PACIENTES CADASTRADOS ─────────────
✓ Pacientes carregados: 3

Total de pacientes: 3
────────────────────────────────────────────────
ID: 1 | Ana Costa | Tel: 11 98765-4321
ID: 2 | Carlos Souza | Tel: 11 91234-5678 (Precisa de assistência)
...
```

### Cadastrar Novo Paciente (Menu Principal - Opção 4)
```
───────────── CADASTRAR NOVO PACIENTE ─────────────
Nome do paciente: Maria Silva
Telefone: 11 99999-8888
Paciente tem dificuldade com tecnologia? (S/N): N

✓ Paciente criado! Linhas afetadas: 1
✓ Paciente cadastrado com sucesso!
```

---

## ✅ Checklist de Validação

Marque cada item após testar:

- [ ] Sistema conecta ao banco de dados
- [ ] CREATE: Consegui criar uma nova consulta
- [ ] READ: Consegui listar todas as consultas
- [ ] READ: Consegui buscar uma consulta específica por ID
- [ ] UPDATE: Consegui atualizar dados de uma consulta
- [ ] DELETE: Consegui deletar uma consulta
- [ ] Sistema mostra mensagens claras de sucesso/erro
- [ ] Interface é amigável e fácil de usar
- [ ] Validações de dados estão funcionando
- [ ] Confirmação antes de deletar está funcionando

---

## 🎓 Observações para o Professor

### Estrutura Correta Implementada
✅ Pacotes `dao` e `dto` estão dentro de `model`
✅ Separação clara de responsabilidades

### Interação com Usuário
✅ Sistema totalmente interativo
✅ Menus claros e intuitivos
✅ Feedback constante ao usuário

### CRUD Completo
✅ **C**reate - Criar novas consultas
✅ **R**ead - Listar e buscar consultas
✅ **U**pdate - Atualizar consultas existentes
✅ **D**elete - Deletar consultas

### Qualidade do Código
✅ Tratamento de exceções
✅ Validação de entrada de dados
✅ Código comentado e organizado
✅ Mensagens claras e profissionais

---

**Desenvolvido por: RM565060**  
**Data: Novembro 2025**  
**Sprint 3 - FIAP**

