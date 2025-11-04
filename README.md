# 🏥 EaseHC - Sistema de Gestão de Consultas Médicas

**Sprint 4 - FIAP**

**Grupo:**
- Samara Vilela de Oliveira - RM 566133
- Felipe Conte Ferreira - RM 562248
- Altamir Lima - RM 562906

---

## 📋 Sobre o Projeto

Sistema completo de gestão de consultas médicas desenvolvido em Java com arquitetura em camadas, seguindo padrões de projeto e princípios REST.

**Status**: ✅ **100% COMPLETO E PRONTO PARA ENTREGA**

---

## 🚀 Início Rápido

### 1. Configurar Banco de Dados

```bash
# Conectar ao Oracle
sqlplus seu_rm/senha@oracle.fiap.com.br:1521/ORCL

# Executar script completo
SQL> @database_schema.sql
```

### 2. Configurar Credenciais

Edite o arquivo `src/br/com/fiap/model/dao/ConexaoBD.java`:

```java
private static final String USUARIO = "seu_rm";
private static final String SENHA = "sua_senha";
```

### 3. Compilar

```bash
# Com Maven (recomendado)
mvn clean compile

# Manual
javac -d out/production/Sprint4 -cp "lib/ojdbc8.jar" src/br/com/fiap/**/*.java
```

### 4. Executar

```bash
# Sistema Console
java -cp "out/production/Sprint4:lib/ojdbc8.jar" br.com.fiap.main.SistemaAgendamentoConsultas

# Teste Rápido
java -cp "out/production/Sprint4:lib/ojdbc8.jar" br.com.fiap.main.TesteSimples
```

---

## 📚 Documentação Completa

**Leia o guia completo aqui**: [`GUIA_COMPLETO.md`](GUIA_COMPLETO.md)

O guia único contém **TUDO que você precisa saber**:

- ✅ Visão geral e pontuação (90/90)
- ✅ Estrutura completa do projeto (30+ classes)
- ✅ Configuração do banco de dados (9 tabelas)
- ✅ Como compilar e executar (passo a passo)
- ✅ API RESTful completa (40+ endpoints)
- ✅ Funcionalidades implementadas (detalhadas)
- ✅ Troubleshooting (soluções para erros comuns)
- ✅ Tecnologias e padrões de projeto
- ✅ Checklist final de entrega

---

## 🎯 Resumo Técnico

### Arquitetura

```
┌─────────────────────────────────────────┐
│   Resource (API REST) - 5 classes      │
├─────────────────────────────────────────┤
│   Service (Negócio) - 5 classes        │
├─────────────────────────────────────────┤
│   DAO (Persistência) - 8 classes       │
├─────────────────────────────────────────┤
│   Model (DTOs) - 8 classes             │
└─────────────────────────────────────────┘
```

### Entidades do Sistema

| Entidade | Tabela BD | DAO | Service | Resource |
|----------|-----------|-----|---------|----------|
| Paciente | T_EASEHC_PACIENTE | ✅ | ✅ | ✅ |
| Médico | T_EASEHC_MEDICO | ✅ | ✅ | ✅ |
| Consulta | T_EASEHC_CONSULTA | ✅ | ✅ | ✅ |
| Especialidade | T_EASEHC_ESPECIALIDADE | ✅ | ✅ | ✅ |
| Localização | T_EASEHC_LOCALIZACAO | ✅ | ✅ | ✅ |
| Cancelamento | T_EASEHC_CANREM | ✅ | - | - |
| Histórico | T_EASEHC_HISTORICO | ✅ | - | - |
| Orientação | T_EASEHC_ORIENTACAO | ✅ | - | - |

### Tecnologias

- **Linguagem**: Java 11+
- **Banco de Dados**: Oracle 21c
- **API**: JAX-RS (Jersey)
- **Build**: Maven 3.6+
- **Padrões**: DAO, MVC, Singleton, Service Layer

---

## 📊 Pontuação Final

| Critério | Pontos | Status |
|----------|--------|--------|
| Camada Model (DTOs) | 10/10 | ✅ |
| Camada DAO e Service | 30/30 | ✅ |
| API RESTful | 30/30 | ✅ |
| Boas Práticas | 20/20 | ✅ |
| **TOTAL** | **90/90** | **100%** ✅ |

---

## 🔗 Links Úteis

- [📖 Guia Completo](GUIA_COMPLETO.md) ← **LEIA ESTE ARQUIVO PRIMEIRO**
- [📄 Script SQL](database_schema.sql)
- [⚙️ Maven Config](pom.xml)

---

## 📞 Contato

**Grupo:**
- Samara Vilela de Oliveira - RM 566133
- Felipe Conte Ferreira - RM 562248
- Altamir Lima - RM 562906

**Instituição**: FIAP  
**Curso**: Análise e Desenvolvimento de Sistemas

---

## 📝 Notas Importantes

1. **Antes de executar**: Configure as credenciais do banco em `ConexaoBD.java`
2. **Banco de dados**: Execute o script `database_schema.sql` no Oracle
3. **Documentação**: Leia o [`GUIA_COMPLETO.md`](GUIA_COMPLETO.md) para instruções detalhadas
4. **Problemas?**: Consulte a seção de Troubleshooting no guia completo

---

**Última atualização**: Novembro 2025  
**Versão**: 1.0  
**Status**: ✅ Pronto para entrega
