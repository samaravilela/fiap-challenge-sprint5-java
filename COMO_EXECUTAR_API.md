# 🚀 Como Executar a API REST

## ✅ Resposta à sua pergunta: **SIM, você pode testar a API no Postman!**

A API REST foi configurada e está pronta para ser testada no Postman. Siga os passos abaixo:

## 📋 Passo a Passo

### 1. Compilar o Projeto

```bash
mvn clean compile
```

### 2. Executar o Servidor

```bash
mvn exec:java -Dexec.mainClass="br.com.fiap.main.ApiServer"
```

**OU** se preferir compilar primeiro e depois executar:

```bash
# Compilar tudo
mvn clean package

# Executar
java -cp "target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout)" br.com.fiap.main.ApiServer
```

### 3. Verificar se o Servidor Está Rodando

Você verá uma mensagem como esta:

```
✓ Servidor iniciado com sucesso!
📍 URL Base: http://localhost:8080/api
```

### 4. Testar no Postman

#### Exemplo 1: Listar Pacientes

1. Abra o Postman
2. Crie uma nova requisição GET
3. URL: `http://localhost:8080/api/pacientes`
4. Clique em "Send"

#### Exemplo 2: Criar um Paciente

1. Método: `POST`
2. URL: `http://localhost:8080/api/pacientes`
3. Headers: Adicione `Content-Type: application/json`
4. Body: Selecione "raw" e "JSON", então cole:
```json
{
  "nomeCompleto": "João Silva",
  "dataNascimento": "1990-05-15",
  "genero": "M",
  "telefone": "(11) 99999-9999",
  "tipoSanguineo": "O+",
  "alergias": "Nenhuma"
}
```

## 📚 Endpoints Disponíveis

### Base URL: `http://localhost:8080/api`

- **Pacientes**: `/api/pacientes`
- **Médicos**: `/api/medicos`
- **Consultas**: `/api/consultas`
- **Especialidades**: `/api/especialidades`
- **Localizações**: `/api/localizacoes`

Veja o arquivo **GUIA_API_POSTMAN.md** para exemplos completos de todos os endpoints!

## 🔧 Troubleshooting

### Porta 8080 já está em uso?

Edite o arquivo `src/br/com/fiap/main/ApiServer.java` e altere a porta:

```java
private static final int PORT = 8081; // ou outra porta
```

### Erro de conexão com banco de dados?

Verifique as credenciais no arquivo `src/br/com/fiap/model/dao/ConexaoBD.java`

## 📖 Documentação Completa

- **GUIA_API_POSTMAN.md**: Guia completo com todos os endpoints e exemplos
- **README.md**: Documentação geral do projeto

---

**Pronto! Agora você pode testar sua API no Postman! 🎉**

