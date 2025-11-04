# 🔧 Instruções de Compilação e Execução

## 📋 Pré-requisitos

Antes de compilar e executar o projeto, certifique-se de ter instalado:

- ☕ **Java JDK 11 ou superior**
  - Verificar instalação: `java -version`
  - Download: https://www.oracle.com/java/technologies/downloads/

- 🔧 **Maven 3.6+** (opcional, mas recomendado)
  - Verificar instalação: `mvn -version`
  - Download: https://maven.apache.org/download.cgi

- 🗄️ **Acesso ao Oracle Database**
  - Oracle 11g ou superior
  - Credenciais de acesso válidas

- 💻 **IDE Java** (opcional)
  - IntelliJ IDEA, Eclipse ou VS Code com extensão Java

---

## 🗄️ Configuração do Banco de Dados

### Passo 1: Criar as Tabelas

Execute o script SQL fornecido no arquivo `database_schema.sql`:

```bash
# Conectar ao Oracle via SQL*Plus
sqlplus seu_usuario/sua_senha@oracle.fiap.com.br:1521/ORCL

# Executar o script
SQL> @database_schema.sql
```

Ou copie e cole o conteúdo do arquivo no **SQL Developer** ou outra ferramenta de sua preferência.

### Passo 2: Criar as Sequences

Execute o script `CREATE_SEQUENCES.sql`:

```sql
SQL> @CREATE_SEQUENCES.sql
```

### Passo 3: Configurar Credenciais no Código

Abra o arquivo `src/br/com/fiap/model/dao/ConexaoBD.java` e ajuste as credenciais:

```java
private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
private static final String USUARIO = "seu_usuario";  // ← ALTERAR
private static final String SENHA = "sua_senha";      // ← ALTERAR
```

> ⚠️ **IMPORTANTE**: Nunca commite credenciais reais no Git!

---

## 🏗️ Compilação

### Opção 1: Compilação com Maven (Recomendado)

```bash
# Navegar até o diretório do projeto
cd /caminho/para/Sprint4

# Limpar compilações anteriores e compilar
mvn clean compile

# Gerar arquivo WAR para deploy
mvn clean package

# Resultado: target/easehc-api.war
```

### Opção 2: Compilação Manual (sem Maven)

```bash
# Navegar até o diretório do projeto
cd /caminho/para/Sprint4

# Criar diretório de saída
mkdir -p out/production/Sprint4

# Compilar todos os arquivos Java
javac -d out/production/Sprint4 \
      -cp "lib/ojdbc8.jar" \
      src/br/com/fiap/**/*.java

# Resultado: Arquivos .class gerados em out/production/Sprint4
```

### Possíveis Erros e Soluções

#### ❌ Erro: "javac: command not found"

**Solução**: Java JDK não está instalado ou não está no PATH.

```bash
# Adicionar ao PATH (Linux/Mac)
export PATH=$PATH:/caminho/para/jdk/bin

# Verificar
javac -version
```

#### ❌ Erro: "package oracle.jdbc does not exist"

**Solução**: Driver JDBC não encontrado.

```bash
# Certifique-se de que o arquivo ojdbc8.jar está em lib/
ls lib/ojdbc8.jar

# Use o parâmetro -cp correto na compilação
```

---

## ▶️ Execução

### Opção 1: Executar Aplicação Console (para testes)

```bash
# Com Maven
mvn exec:java -Dexec.mainClass="br.com.fiap.main.SistemaAgendamentoConsultas"

# Sem Maven (manual)
java -cp "out/production/Sprint4:lib/ojdbc8.jar" \
     br.com.fiap.main.SistemaAgendamentoConsultas
```

### Opção 2: Deploy da API REST (Servidor de Aplicação)

#### Usando Apache Tomcat

1. **Instalar Tomcat** (se ainda não tiver):
   - Download: https://tomcat.apache.org/download-90.cgi
   - Extrair em uma pasta

2. **Copiar o WAR gerado para o Tomcat**:

```bash
# Copiar WAR para pasta de deploy do Tomcat
cp target/easehc-api.war /caminho/para/tomcat/webapps/

# Iniciar Tomcat
cd /caminho/para/tomcat/bin
./startup.sh  # Linux/Mac
startup.bat   # Windows
```

3. **Acessar a API**:

```
http://localhost:8080/easehc-api/api/pacientes
```

#### Usando Glassfish

```bash
# Deploy
asadmin deploy target/easehc-api.war

# Acessar
http://localhost:8080/easehc-api/api/pacientes
```

#### Usando WildFly

```bash
# Copiar para pasta de deploy
cp target/easehc-api.war /caminho/para/wildfly/standalone/deployments/

# Iniciar WildFly
./bin/standalone.sh
```

---

## 🧪 Testando a API

### Teste de Conexão com Banco

Execute a classe de teste:

```bash
java -cp "out/production/Sprint4:lib/ojdbc8.jar" \
     br.com.fiap.main.TesteConexaoBanco
```

**Saída esperada:**
```
Conectando ao banco de dados...
✓ Conexão com o banco estabelecida com sucesso!
✓ Teste de conexão: SUCESSO!
```

### Testar Endpoints da API

Use ferramentas como **Postman**, **Insomnia** ou **cURL**.

#### Exemplo: Listar todos os pacientes

```bash
curl -X GET http://localhost:8080/easehc-api/api/pacientes
```

**Resposta esperada (200 OK):**
```json
[
  {
    "idPaciente": 1,
    "nomeCompleto": "Ana Silva",
    "dataNascimento": "1985-03-12",
    "genero": "F",
    "telefone": "11988880001",
    "tipoSanguineo": "O+",
    "alergias": "Nenhuma"
  },
  ...
]
```

#### Exemplo: Criar novo paciente

```bash
curl -X POST http://localhost:8080/easehc-api/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nomeCompleto": "João Santos",
    "dataNascimento": "1990-05-15",
    "genero": "M",
    "telefone": "11987654321",
    "tipoSanguineo": "A+",
    "alergias": "Nenhuma"
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "idPaciente": 11,
  "nomeCompleto": "João Santos",
  ...
}
```

---

## 🐛 Resolução de Problemas

### Problema: "ClassNotFoundException: oracle.jdbc.driver.OracleDriver"

**Causa**: Driver JDBC não está no classpath.

**Solução**:
1. Verificar se `lib/ojdbc8.jar` existe
2. Adicionar ao classpath: `-cp "out/production/Sprint4:lib/ojdbc8.jar"`

---

### Problema: "SQLException: ORA-01017: invalid username/password"

**Causa**: Credenciais incorretas em `ConexaoBD.java`.

**Solução**:
1. Verificar usuário e senha no arquivo
2. Testar conexão via SQL*Plus manualmente

---

### Problema: "SQLException: ORA-00942: table or view does not exist"

**Causa**: Tabelas não foram criadas no banco.

**Solução**:
1. Executar o script `database_schema.sql`
2. Verificar se está conectando ao schema correto

---

### Problema: Porta 8080 já está em uso

**Solução**:
```bash
# Linux/Mac - Liberar porta
lsof -ti:8080 | xargs kill -9

# Ou alterar porta no servidor de aplicação
# Tomcat: conf/server.xml
# Glassfish: domain.xml
```

---

## 📚 Estrutura de Compilação

### Arquivos Gerados

Após compilação com Maven:

```
target/
├── classes/                      # .class compilados
│   └── br/com/fiap/...
├── easehc-api.war               # Arquivo para deploy
└── easehc-api/                  # Conteúdo explodido do WAR
    ├── WEB-INF/
    │   ├── classes/
    │   └── lib/
    └── META-INF/
```

Após compilação manual:

```
out/
└── production/
    └── Sprint4/
        └── br/
            └── com/
                └── fiap/
                    ├── model/
                    ├── service/
                    ├── resource/
                    └── exception/
```

---

## 📦 Dependências do Projeto

### Maven (pom.xml)

```xml
<!-- Driver Oracle JDBC -->
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>21.5.0.0</version>
</dependency>

<!-- Jersey (JAX-RS) -->
<dependency>
    <groupId>org.glassfish.jersey.containers</groupId>
    <artifactId>jersey-container-servlet</artifactId>
    <version>2.35</version>
</dependency>

<!-- Jackson (JSON) -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.13.0</version>
</dependency>
```

### Manual (JARs necessários)

- `ojdbc8.jar` (Oracle JDBC Driver) - **OBRIGATÓRIO**
- Jersey JARs (para API REST) - Opcional para compilação simples
- Jackson JARs (para JSON) - Opcional para compilação simples

---

## 🎯 Checklist de Compilação

Antes de entregar o projeto, verifique:

- [ ] ✅ Banco de dados criado e populado
- [ ] ✅ Credenciais corretas em `ConexaoBD.java`
- [ ] ✅ Projeto compila sem erros
- [ ] ✅ Teste de conexão com banco funcionando
- [ ] ✅ WAR gerado com sucesso (se usar Maven)
- [ ] ✅ API REST responde corretamente (se deployado)
- [ ] ✅ Código documentado e limpo
- [ ] ✅ README.md atualizado

---

## 📞 Suporte

Em caso de dúvidas:

1. Consulte o arquivo `PROJETO_FINALIZADO.md` para documentação completa
2. Revise o arquivo `README.md` para visão geral
3. Entre em contato com o professor ou monitoria

---

**Última atualização**: Novembro 2025

**Autor**: RM565060 - Samara Vilela

