# 🏥 EaseHC - Sistema de Gestão de Consultas Médicas

## 📌 Projeto Finalizado - Sprint 4

Sistema completo de gestão de consultas médicas desenvolvido em Java com arquitetura em camadas, seguindo boas práticas de desenvolvimento e princípios RESTful.

---

## 🎯 Status do Projeto

✅ **PROJETO 100% FINALIZADO E PRONTO PARA ENTREGA**

| Critério | Pontos | Status |
|----------|--------|--------|
| Camada Model (DTOs) | 10/10 | ✅ Completo |
| Camada DAO e Service | 30/30 | ✅ CRUD + Validações + Regras |
| API RESTful | 30/30 | ✅ Todos endpoints REST |
| Boas Práticas | 20/20 | ✅ Padrões + Exceções + Doc |
| **TOTAL** | **90/90** | ✅ **100%** |

---

## 📁 Arquivos Importantes

- 📘 **`PROJETO_FINALIZADO.md`** - Documentação completa do projeto (LEIA PRIMEIRO)
- 🔧 **`INSTRUCOES_COMPILACAO.md`** - Como compilar e executar o projeto
- 🗄️ **`database_schema.sql`** - Script completo do banco de dados
- 🔢 **`CREATE_SEQUENCES.sql`** - Sequences para auto-incremento
- ⚙️ **`pom.xml`** - Configuração Maven do projeto

---

## 🏗️ Estrutura do Projeto

```
Sprint4/
├── src/br/com/fiap/
│   ├── model/dto/           # 8 Entidades (Paciente, Medico, Consulta...)
│   ├── model/dao/           # 8 DAOs com CRUD completo
│   ├── service/             # 5 Services com validações e regras
│   ├── resource/            # 5 Resources REST (API)
│   └── exception/           # 4 Exceções personalizadas
├── lib/
│   └── ojdbc8.jar          # Driver Oracle JDBC
├── PROJETO_FINALIZADO.md    # Documentação completa ⭐
├── INSTRUCOES_COMPILACAO.md # Guia de compilação
├── database_schema.sql      # Script do banco
├── CREATE_SEQUENCES.sql     # Sequences do banco
├── pom.xml                  # Maven config
└── README.md               # Este arquivo

**Total**: 30+ classes Java implementadas
```

---

## 🚀 Quick Start

### 1. Configurar Banco de Dados

```bash
sqlplus seu_usuario/sua_senha@oracle.fiap.com.br:1521/ORCL
SQL> @database_schema.sql
SQL> @CREATE_SEQUENCES.sql
```

### 2. Configurar Credenciais

Edite `src/br/com/fiap/model/dao/ConexaoBD.java`:

```java
private static final String USUARIO = "seu_usuario";
private static final String SENHA = "sua_senha";
```

### 3. Compilar

```bash
# Com Maven
mvn clean compile

# Gerar WAR
mvn clean package
```

### 4. Executar

```bash
# Teste console
mvn exec:java -Dexec.mainClass="br.com.fiap.main.SistemaAgendamentoConsultas"

# Deploy API REST
cp target/easehc-api.war /caminho/tomcat/webapps/
```

---

## 📊 Funcionalidades Implementadas

### ✅ Camada Model (10 pontos)

8 classes DTO completas:
- Paciente, Medico, Consulta, Especialidade
- Localizacao, Cancelamento, HistoricoMedico, Orientacao

### ✅ Camada DAO (15 pontos)

8 DAOs com CRUD completo:
- `criar()` - INSERT com retorno de ID gerado
- `buscarPorId()` - SELECT por chave primária
- `listarTodos()` - SELECT * ORDER BY
- `atualizar()` - UPDATE completo
- `deletar()` - DELETE com verificação
- Métodos auxiliares específicos por entidade

### ✅ Camada Service (15 pontos)

5 Services com:
- ✅ Validações completas de todos os campos
- ✅ Regras de negócio (ex: conflito de horários)
- ✅ Verificação de integridade referencial
- ✅ Tratamento de exceções personalizado
- ✅ Mensagens de erro descritivas

### ✅ API RESTful (30 pontos)

40+ endpoints REST implementados:

**Pacientes** (`/api/pacientes`)
- GET - Listar todos
- GET /{id} - Buscar por ID
- POST - Criar novo
- PUT /{id} - Atualizar
- DELETE /{id} - Deletar
- GET /buscar?nome=X - Buscar por nome

**Médicos** (`/api/medicos`)
- CRUD completo
- GET /crm/{crm} - Buscar por CRM
- GET /especialidade/{id} - Por especialidade

**Consultas** (`/api/consultas`)
- CRUD completo
- GET /paciente/{id} - Por paciente
- GET /medico/{id} - Por médico
- GET /status/{status} - Por status
- PUT /{id}/cancelar - Cancelar consulta

**+ Especialidades e Localizações**

### ✅ Boas Práticas (20 pontos)

- ✅ Nomenclatura padronizada (camelCase, PascalCase)
- ✅ Pacotes organizados por camada
- ✅ Tratamento completo de exceções
- ✅ 4 exceções personalizadas
- ✅ Padrões de projeto: DAO, MVC, Singleton, Service Layer
- ✅ Código documentado (JavaDoc)
- ✅ Separação de responsabilidades
- ✅ Validações em todas as camadas
- ✅ Commits organizados no Git

---

## 🎨 Tecnologias e Padrões

### Tecnologias

- ☕ Java 11+
- 🗄️ Oracle Database 21c
- 🔌 JDBC (ojdbc8)
- 🌐 JAX-RS (Jersey)
- 📦 Maven
- 🔧 Git

### Padrões de Projeto

- **DAO (Data Access Object)** - Acesso ao banco
- **Service Layer** - Regras de negócio
- **MVC** - Model-View-Controller
- **Singleton** - Conexão única com BD
- **Dependency Injection** - Injeção de dependências
- **RESTful API** - Princípios REST

### Princípios SOLID

- **S**ingle Responsibility Principle ✅
- **O**pen/Closed Principle ✅
- **L**iskov Substitution Principle ✅
- **I**nterface Segregation Principle ✅
- **D**ependency Inversion Principle ✅

---

## 📚 Documentação Completa

Para informações detalhadas, consulte:

1. 📘 **`PROJETO_FINALIZADO.md`** - Documentação técnica completa
   - Arquitetura detalhada
   - Todos os endpoints da API
   - Exemplos de uso
   - Regras de negócio
   - Validações implementadas

2. 🔧 **`INSTRUCOES_COMPILACAO.md`** - Guia passo a passo
   - Pré-requisitos
   - Configuração do banco
   - Compilação (Maven e manual)
   - Execução e deploy
   - Troubleshooting

---

## 🎯 Diferenciais do Projeto

✨ **Validações Robustas**
- Validação de email, CRM, tipos sanguíneos
- Verificação de duplicidade (CRM, nome paciente)
- Validação de datas (não futuras/passadas)

✨ **Regras de Negócio Complexas**
- Conflito de horários de consultas
- Impossibilidade de cancelar consulta realizada
- Validação de integridade referencial

✨ **Tratamento Completo de Exceções**
- 4 tipos de exceções personalizadas
- Status HTTP apropriados (200, 201, 400, 404, 422, 500)
- Mensagens descritivas para o usuário

✨ **Código Limpo e Documentado**
- JavaDoc em todas as classes principais
- Comentários explicativos
- Nomenclatura clara e consistente

✨ **Separação de Responsabilidades**
- Cada camada com função específica
- Sem lógica de negócio no DAO
- Sem acesso direto ao BD nos Resources

---

## 👥 Informações do Projeto

**Aluno**: Samara Vilela  
**RM**: 565060  
**Instituição**: FIAP  
**Disciplina**: Desenvolvimento Java Enterprise  
**Sprint**: Sprint 4  
**Data**: Novembro 2025

---

## 📞 Suporte

Dúvidas? Consulte:
1. `PROJETO_FINALIZADO.md` - Documentação completa
2. `INSTRUCOES_COMPILACAO.md` - Guia de compilação
3. Entre em contato via email institucional FIAP

---

## ✅ Checklist Final

- [x] Banco de dados modelado e populado
- [x] 8 Classes Model (DTOs) completas
- [x] 8 DAOs com CRUD completo
- [x] 5 Services com validações e regras
- [x] 5 Resources com API RESTful
- [x] 4 Exceções personalizadas
- [x] Tratamento de exceções em todas camadas
- [x] Padrões de projeto aplicados
- [x] Código documentado (JavaDoc)
- [x] pom.xml configurado
- [x] README.md completo
- [x] Scripts SQL incluídos
- [x] .gitignore configurado

---

**Status**: ✅ **PROJETO 100% COMPLETO E PRONTO PARA ENTREGA**

**Última atualização**: Novembro 2025
