# 📊 DIAGRAMA DE CLASSES - SISTEMA EASEHC

**Projeto**: Sistema de Gestão de Consultas Médicas  
**Grupo**: 
- Samara Vilela de Oliveira - RM 566133
- Felipe Conte Ferreira - RM 562248
- Altamir Lima - RM 562906

---

## 🏗️ ARQUITETURA DO SISTEMA

```
┌─────────────────────────────────────────────────────────────┐
│                    CAMADA RESOURCE (API REST)               │
│  PacienteResource | MedicoResource | ConsultaResource      │
│  EspecialidadeResource | LocalizacaoResource               │
└────────────────────────┬────────────────────────────────────┘
                         │ usa
┌────────────────────────▼────────────────────────────────────┐
│                    CAMADA SERVICE (NEGÓCIO)                 │
│  PacienteService | MedicoService | ConsultaService          │
│  EspecialidadeService | LocalizacaoService                  │
└────────────────────────┬────────────────────────────────────┘
                         │ usa
┌────────────────────────▼────────────────────────────────────┐
│                 CAMADA DAO (PERSISTÊNCIA)                   │
│  PacienteDAO | MedicoDAO | ConsultaDAO | EspecialidadeDAO  │
│  LocalizacaoDAO | CancelamentoDAO | HistoricoMedicoDAO     │
│  OrientacaoDAO | ConexaoBD (Singleton)                      │
└────────────────────────┬────────────────────────────────────┘
                         │ manipula
┌────────────────────────▼────────────────────────────────────┐
│                    CAMADA MODEL (DTOs)                      │
│  Paciente | Medico | Consulta | Especialidade              │
│  Localizacao | Cancelamento | HistoricoMedico | Orientacao │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 1. CAMADA MODEL (DTOs)

### 1.1 Paciente
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Paciente                      │
├─────────────────────────────────────────┤
│ - idPaciente: Long                      │
│ - nomeCompleto: String                  │
│ - dataNascimento: LocalDate             │
│ - genero: String                        │
│ - telefone: String                      │
│ - tipoSanguineo: String                 │
│ - alergias: String                      │
├─────────────────────────────────────────┤
│ + Paciente()                            │
│ + Paciente(Long, String, LocalDate,     │
│            String, String, String,      │
│            String)                      │
│ + getIdPaciente(): Long                 │
│ + setIdPaciente(Long): void             │
│ + getNomeCompleto(): String             │
│ + setNomeCompleto(String): void         │
│ + getDataNascimento(): LocalDate        │
│ + setDataNascimento(LocalDate): void    │
│ + getGenero(): String                   │
│ + setGenero(String): void               │
│ + getTelefone(): String                 │
│ + setTelefone(String): void             │
│ + getTipoSanguineo(): String            │
│ + setTipoSanguineo(String): void        │
│ + getAlergias(): String                 │
│ + setAlergias(String): void             │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.2 Medico
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Medico                        │
├─────────────────────────────────────────┤
│ - idMedico: Long                        │
│ - nomeCompleto: String                  │
│ - crm: String                           │
│ - telefone: String                      │
│ - email: String                         │
├─────────────────────────────────────────┤
│ + Medico()                              │
│ + Medico(Long, String, String,          │
│          String, String)                │
│ + getIdMedico(): Long                   │
│ + setIdMedico(Long): void               │
│ + getNomeCompleto(): String             │
│ + setNomeCompleto(String): void         │
│ + getCrm(): String                      │
│ + setCrm(String): void                  │
│ + getTelefone(): String                 │
│ + setTelefone(String): void             │
│ + getEmail(): String                    │
│ + setEmail(String): void                │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.3 Consulta
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Consulta                      │
├─────────────────────────────────────────┤
│ - idConsulta: Long                      │
│ - idPaciente: Long                      │
│ - idMedico: Long                        │
│ - idLocal: Long                         │
│ - idEsp: Long                           │
│ - dataHora: LocalDateTime               │
│ - duracaoMinutos: Integer               │
│ - status: String                        │
│ - observacoes: String                   │
│ - prioridade: String                    │
├─────────────────────────────────────────┤
│ + Consulta()                            │
│ + Consulta(Long, Long, Long, Long,      │
│           Long, LocalDateTime, Integer, │
│           String, String, String)       │
│ + getIdConsulta(): Long                 │
│ + setIdConsulta(Long): void             │
│ + getIdPaciente(): Long                 │
│ + setIdPaciente(Long): void             │
│ + getIdMedico(): Long                   │
│ + setIdMedico(Long): void               │
│ + getIdLocal(): Long                    │
│ + setIdLocal(Long): void                │
│ + getIdEsp(): Long                      │
│ + setIdEsp(Long): void                  │
│ + getDataHora(): LocalDateTime          │
│ + setDataHora(LocalDateTime): void      │
│ + getDuracaoMinutos(): Integer          │
│ + setDuracaoMinutos(Integer): void      │
│ + getStatus(): String                   │
│ + setStatus(String): void               │
│ + getObservacoes(): String              │
│ + setObservacoes(String): void          │
│ + getPrioridade(): String               │
│ + setPrioridade(String): void           │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.4 Especialidade
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Especialidade                 │
├─────────────────────────────────────────┤
│ - idEsp: Long                           │
│ - nomeEsp: String                       │
│ - areaMed: String                       │
│ - tpMedCons: Integer                    │
├─────────────────────────────────────────┤
│ + Especialidade()                       │
│ + Especialidade(Long, String, String,   │
│                 Integer)                │
│ + getIdEsp(): Long                      │
│ + setIdEsp(Long): void                  │
│ + getNomeEsp(): String                  │
│ + setNomeEsp(String): void              │
│ + getAreaMed(): String                  │
│ + setAreaMed(String): void              │
│ + getTpMedCons(): Integer               │
│ + setTpMedCons(Integer): void           │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.5 Localizacao
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Localizacao                   │
├─────────────────────────────────────────┤
│ - idLocal: Long                         │
│ - nomeUnidade: String                   │
│ - endereco: String                      │
│ - estado: String                        │
│ - cidade: String                        │
│ - pais: String                          │
│ - hrFunc: String                        │
│ - telefone: String                      │
├─────────────────────────────────────────┤
│ + Localizacao()                         │
│ + Localizacao(Long, String, String,     │
│              String, String, String,    │
│              String, String)            │
│ + getIdLocal(): Long                    │
│ + setIdLocal(Long): void                │
│ + getNomeUnidade(): String              │
│ + setNomeUnidade(String): void          │
│ + getEndereco(): String                 │
│ + setEndereco(String): void             │
│ + getEstado(): String                   │
│ + setEstado(String): void               │
│ + getCidade(): String                   │
│ + setCidade(String): void               │
│ + getPais(): String                     │
│ + setPais(String): void                 │
│ + getHrFunc(): String                   │
│ + setHrFunc(String): void               │
│ + getTelefone(): String                 │
│ + setTelefone(String): void             │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.6 Cancelamento
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Cancelamento                  │
├─────────────────────────────────────────┤
│ - idCanRem: Long                        │
│ - idConsulta: Long                      │
│ - tipoAjuste: String                    │
│ - motivo: String                        │
│ - novaDataHora: LocalDateTime           │
├─────────────────────────────────────────┤
│ + Cancelamento()                        │
│ + Cancelamento(Long, Long, String,      │
│                String, LocalDateTime)   │
│ + getIdCanRem(): Long                   │
│ + setIdCanRem(Long): void               │
│ + getIdConsulta(): Long                 │
│ + setIdConsulta(Long): void             │
│ + getTipoAjuste(): String               │
│ + setTipoAjuste(String): void           │
│ + getMotivo(): String                   │
│ + setMotivo(String): void               │
│ + getNovaDataHora(): LocalDateTime      │
│ + setNovaDataHora(LocalDateTime): void  │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.7 HistoricoMedico
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           HistoricoMedico               │
├─────────────────────────────────────────┤
│ - idHistorico: Long                     │
│ - idPaciente: Long                      │
│ - diagnostico: String                   │
│ - tratamento: String                    │
│ - medicacao: String                     │
│ - observacoes: String                   │
│ - dataAcesso: LocalDate                 │
├─────────────────────────────────────────┤
│ + HistoricoMedico()                     │
│ + HistoricoMedico(Long, Long, String,   │
│                   String, String,       │
│                   String, LocalDate)    │
│ + getIdHistorico(): Long                │
│ + setIdHistorico(Long): void            │
│ + getIdPaciente(): Long                 │
│ + setIdPaciente(Long): void             │
│ + getDiagnostico(): String              │
│ + setDiagnostico(String): void          │
│ + getTratamento(): String               │
│ + setTratamento(String): void           │
│ + getMedicacao(): String                │
│ + setMedicacao(String): void            │
│ + getObservacoes(): String              │
│ + setObservacoes(String): void          │
│ + getDataAcesso(): LocalDate            │
│ + setDataAcesso(LocalDate): void        │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

### 1.8 Orientacao
```
┌─────────────────────────────────────────┐
│           <<DTO>>                       │
│           Orientacao                    │
├─────────────────────────────────────────┤
│ - idOrientacao: Long                    │
│ - idConsulta: Long                      │
│ - tipoExame: String                     │
│ - instrucoes: String                    │
│ - recomendacoes: String                 │
├─────────────────────────────────────────┤
│ + Orientacao()                          │
│ + Orientacao(Long, Long, String,        │
│              String, String)            │
│ + getIdOrientacao(): Long               │
│ + setIdOrientacao(Long): void           │
│ + getIdConsulta(): Long                 │
│ + setIdConsulta(Long): void             │
│ + getTipoExame(): String                │
│ + setTipoExame(String): void            │
│ + getInstrucoes(): String               │
│ + setInstrucoes(String): void           │
│ + getRecomendacoes(): String            │
│ + setRecomendacoes(String): void        │
│ + toString(): String                    │
└─────────────────────────────────────────┘
```

---

## 🗄️ 2. CAMADA DAO (DATA ACCESS OBJECT)

### 2.1 ConexaoBD (Singleton)
```
┌─────────────────────────────────────────┐
│         <<Singleton>>                   │
│           ConexaoBD                     │
├─────────────────────────────────────────┤
│ - URL: String {static, final}           │
│ - USUARIO: String {static, final}       │
│ - SENHA: String {static, final}         │
│ - conexao: Connection {static}          │
├─────────────────────────────────────────┤
│ - ConexaoBD()                           │
│ + getConexao(): Connection {static}     │
│ + fecharConexao(): void {static}        │
│ + commit(): void {static}               │
│ + rollback(): void {static}             │
│ + testarConexao(): boolean {static}     │
└─────────────────────────────────────────┘
```

### 2.2 PacienteDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           PacienteDAO                   │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Paciente): Paciente             │
│ + buscarPorId(Long): Paciente           │
│ + listarTodos(): List<Paciente>         │
│ + atualizar(Paciente): boolean          │
│ + deletar(Long): boolean                │
│ + buscarPorNome(String): List<Paciente> │
│ - extrairPacienteDoResultSet(           │
│     ResultSet): Paciente                │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.3 MedicoDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           MedicoDAO                     │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Medico): Medico                 │
│ + buscarPorId(Long): Medico             │
│ + listarTodos(): List<Medico>           │
│ + atualizar(Medico): boolean            │
│ + deletar(Long): boolean                │
│ + buscarPorCrm(String): Medico          │
│ + listarPorEspecialidade(Long):         │
│     List<Medico>                        │
│ - extrairMedicoDoResultSet(             │
│     ResultSet): Medico                  │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.4 ConsultaDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           ConsultaDAO                   │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Consulta): Consulta             │
│ + buscarPorId(Long): Consulta           │
│ + listarTodos(): List<Consulta>         │
│ + atualizar(Consulta): boolean          │
│ + deletar(Long): boolean                │
│ + listarPorPaciente(Long):              │
│     List<Consulta>                      │
│ + listarPorMedico(Long): List<Consulta> │
│ + listarPorStatus(String):              │
│     List<Consulta>                      │
│ - extrairConsultaDoResultSet(           │
│     ResultSet): Consulta                │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.5 EspecialidadeDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           EspecialidadeDAO              │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Especialidade): Especialidade   │
│ + buscarPorId(Long): Especialidade      │
│ + listarTodos(): List<Especialidade>    │
│ + atualizar(Especialidade): boolean     │
│ + deletar(Long): boolean                │
│ - extrairEspecialidadeDoResultSet(      │
│     ResultSet): Especialidade           │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.6 LocalizacaoDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           LocalizacaoDAO                │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Localizacao): Localizacao       │
│ + buscarPorId(Long): Localizacao        │
│ + listarTodos(): List<Localizacao>      │
│ + atualizar(Localizacao): boolean       │
│ + deletar(Long): boolean                │
│ + listarPorCidade(String):              │
│     List<Localizacao>                   │
│ - extrairLocalizacaoDoResultSet(        │
│     ResultSet): Localizacao             │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.7 CancelamentoDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           CancelamentoDAO               │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Cancelamento): Cancelamento     │
│ + buscarPorId(Long): Cancelamento       │
│ + listarTodos(): List<Cancelamento>     │
│ + atualizar(Cancelamento): boolean      │
│ + deletar(Long): boolean                │
│ + listarPorConsulta(Long):              │
│     List<Cancelamento>                  │
│ - extrairCancelamentoDoResultSet(       │
│     ResultSet): Cancelamento            │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.8 HistoricoMedicoDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           HistoricoMedicoDAO            │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(HistoricoMedico):               │
│     HistoricoMedico                     │
│ + buscarPorId(Long): HistoricoMedico    │
│ + listarTodos(): List<HistoricoMedico>  │
│ + atualizar(HistoricoMedico): boolean   │
│ + deletar(Long): boolean                │
│ + listarPorPaciente(Long):              │
│     List<HistoricoMedico>               │
│ - extrairHistoricoMedicoDoResultSet(    │
│     ResultSet): HistoricoMedico         │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

### 2.9 OrientacaoDAO
```
┌─────────────────────────────────────────┐
│           <<DAO>>                       │
│           OrientacaoDAO                 │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + criar(Orientacao): Orientacao         │
│ + buscarPorId(Long): Orientacao         │
│ + listarTodos(): List<Orientacao>       │
│ + atualizar(Orientacao): boolean        │
│ + deletar(Long): boolean                │
│ + listarPorConsulta(Long):              │
│     List<Orientacao>                    │
│ - extrairOrientacaoDoResultSet(         │
│     ResultSet): Orientacao              │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌─────────────┐
     │  ConexaoBD  │
     └─────────────┘
```

---

## 🎯 3. CAMADA SERVICE (REGRAS DE NEGÓCIO)

### 3.1 PacienteService
```
┌─────────────────────────────────────────┐
│         <<Service>>                     │
│         PacienteService                 │
├─────────────────────────────────────────┤
│ - pacienteDAO: PacienteDAO              │
├─────────────────────────────────────────┤
│ + PacienteService()                     │
│ + criar(Paciente): Paciente             │
│ + buscarPorId(Long): Paciente           │
│ + listarTodos(): List<Paciente>         │
│ + atualizar(Paciente): boolean          │
│ + deletar(Long): boolean                │
│ + buscarPorNome(String): List<Paciente> │
│ - validarPaciente(Paciente): void       │
│ - validarId(Long): void                 │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌───────────────┐
     │ PacienteDAO   │
     └───────────────┘
```

**Validações**:
- Nome completo obrigatório (máx. 100 caracteres)
- Data de nascimento obrigatória e não futura
- Gênero: F, M ou O
- Tipo sanguíneo: A+, A-, AB+, AB-, B+, B-, O+, O-
- Telefone máximo 15 caracteres

### 3.2 MedicoService
```
┌─────────────────────────────────────────┐
│         <<Service>>                     │
│         MedicoService                   │
├─────────────────────────────────────────┤
│ - medicoDAO: MedicoDAO                  │
├─────────────────────────────────────────┤
│ + MedicoService()                       │
│ + criar(Medico): Medico                 │
│ + buscarPorId(Long): Medico             │
│ + listarTodos(): List<Medico>           │
│ + atualizar(Medico): boolean            │
│ + deletar(Long): boolean                │
│ + buscarPorCrm(String): Medico          │
│ + listarPorEspecialidade(Long):         │
│     List<Medico>                        │
│ - validarMedico(Medico): void           │
│ - validarId(Long): void                 │
│ - validarEmail(String): void            │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌───────────────┐
     │  MedicoDAO    │
     └───────────────┘
```

**Validações**:
- Nome completo obrigatório
- CRM obrigatório e único
- Email válido (regex)
- Verificação de duplicidade de CRM
- Telefone máximo 15 caracteres

### 3.3 ConsultaService
```
┌─────────────────────────────────────────┐
│         <<Service>>                     │
│         ConsultaService                 │
├─────────────────────────────────────────┤
│ - consultaDAO: ConsultaDAO              │
│ - pacienteDAO: PacienteDAO              │
│ - medicoDAO: MedicoDAO                  │
├─────────────────────────────────────────┤
│ + ConsultaService()                     │
│ + criar(Consulta): Consulta             │
│ + buscarPorId(Long): Consulta           │
│ + listarTodos(): List<Consulta>         │
│ + atualizar(Consulta): boolean          │
│ + deletar(Long): boolean                │
│ + cancelarConsulta(Long, String):       │
│     boolean                             │
│ + listarPorPaciente(Long):              │
│     List<Consulta>                      │
│ + listarPorMedico(Long): List<Consulta> │
│ + listarPorStatus(String):              │
│     List<Consulta>                      │
│ - validarConsulta(Consulta): void       │
│ - validarId(Long): void                 │
│ - verificarConflito(Consulta): void     │
│ - verificarEntidadesExistentes(         │
│     Consulta): void                     │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌───────────────┐
     │ ConsultaDAO   │
     │ PacienteDAO   │
     │ MedicoDAO     │
     └───────────────┘
```

**Validações e Regras de Negócio**:
- Todos os IDs relacionados devem existir
- Data e hora não podem ser no passado
- Duração deve ser > 0
- Status: Agendada, Cancelada, Realizada
- Prioridade: Alta, Baixa, Normal
- **Regra**: Médico não pode ter consultas sobrepostas
- **Regra**: Não pode cancelar consulta já realizada

### 3.4 EspecialidadeService
```
┌─────────────────────────────────────────┐
│         <<Service>>                     │
│         EspecialidadeService            │
├─────────────────────────────────────────┤
│ - especialidadeDAO: EspecialidadeDAO    │
├─────────────────────────────────────────┤
│ + EspecialidadeService()                │
│ + criar(Especialidade): Especialidade   │
│ + buscarPorId(Long): Especialidade      │
│ + listarTodos(): List<Especialidade>    │
│ + atualizar(Especialidade): boolean     │
│ + deletar(Long): boolean                │
│ - validarEspecialidade(                 │
│     Especialidade): void                │
│ - validarId(Long): void                 │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌───────────────────┐
     │ EspecialidadeDAO  │
     └───────────────────┘
```

**Validações**:
- Nome obrigatório (máx. 100 caracteres)
- Tempo médio de consulta > 0

### 3.5 LocalizacaoService
```
┌─────────────────────────────────────────┐
│         <<Service>>                     │
│         LocalizacaoService              │
├─────────────────────────────────────────┤
│ - localizacaoDAO: LocalizacaoDAO        │
├─────────────────────────────────────────┤
│ + LocalizacaoService()                  │
│ + criar(Localizacao): Localizacao       │
│ + buscarPorId(Long): Localizacao        │
│ + listarTodos(): List<Localizacao>      │
│ + atualizar(Localizacao): boolean       │
│ + deletar(Long): boolean                │
│ + listarPorCidade(String):              │
│     List<Localizacao>                   │
│ - validarLocalizacao(Localizacao): void │
│ - validarId(Long): void                 │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌──────────────────┐
     │ LocalizacaoDAO   │
     └──────────────────┘
```

**Validações**:
- Nome da unidade obrigatório (máx. 100 caracteres)
- Validações de tamanhos máximos
- Estado máximo 2 caracteres

---

## 🌐 4. CAMADA RESOURCE (API REST)

### 4.1 ResponseEntity (Helper)
```
┌─────────────────────────────────────────┐
│         <<Helper>>                      │
│         ResponseEntity                  │
├─────────────────────────────────────────┤
│ - status: int                           │
│ - message: String                       │
│ - data: Object                          │
├─────────────────────────────────────────┤
│ + ResponseEntity()                      │
│ + ResponseEntity(int, String, Object)   │
│ + ok(Object): ResponseEntity            │
│ + created(Object): ResponseEntity       │
│ + noContent(): ResponseEntity           │
│ + badRequest(String): ResponseEntity    │
│ + notFound(String): ResponseEntity      │
│ + error(String): ResponseEntity         │
│ + getStatus(): int                      │
│ + getMessage(): String                  │
│ + getData(): Object                     │
└─────────────────────────────────────────┘
```

### 4.2 PacienteResource
```
┌─────────────────────────────────────────┐
│         <<Resource>>                    │
│         @Path("/pacientes")             │
│         PacienteResource                │
├─────────────────────────────────────────┤
│ - pacienteService: PacienteService      │
├─────────────────────────────────────────┤
│ + PacienteResource()                    │
│ @GET                                    │
│ + listarTodos(): Response               │
│ @GET @Path("/{id}")                     │
│ + buscarPorId(Long): Response           │
│ @POST                                   │
│ + criar(Paciente): Response             │
│ @PUT @Path("/{id}")                     │
│ + atualizar(Long, Paciente): Response   │
│ @DELETE @Path("/{id}")                  │
│ + deletar(Long): Response               │
│ @GET @Path("/buscar")                   │
│ + buscarPorNome(String): Response       │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌──────────────────┐
     │ PacienteService  │
     └──────────────────┘
```

**Endpoints**:
- `GET /pacientes` - Lista todos
- `GET /pacientes/{id}` - Busca por ID
- `POST /pacientes` - Cria novo
- `PUT /pacientes/{id}` - Atualiza
- `DELETE /pacientes/{id}` - Deleta
- `GET /pacientes/buscar?nome=X` - Busca por nome

### 4.3 MedicoResource
```
┌─────────────────────────────────────────┐
│         <<Resource>>                    │
│         @Path("/medicos")               │
│         MedicoResource                  │
├─────────────────────────────────────────┤
│ - medicoService: MedicoService          │
├─────────────────────────────────────────┤
│ + MedicoResource()                      │
│ @GET                                    │
│ + listarTodos(): Response               │
│ @GET @Path("/{id}")                     │
│ + buscarPorId(Long): Response           │
│ @POST                                   │
│ + criar(Medico): Response               │
│ @PUT @Path("/{id}")                     │
│ + atualizar(Long, Medico): Response     │
│ @DELETE @Path("/{id}")                  │
│ + deletar(Long): Response               │
│ @GET @Path("/crm/{crm}")                │
│ + buscarPorCrm(String): Response        │
│ @GET @Path("/especialidade/{id}")       │
│ + listarPorEspecialidade(Long):         │
│     Response                            │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌──────────────────┐
     │ MedicoService    │
     └──────────────────┘
```

**Endpoints**:
- `GET /medicos` - Lista todos
- `GET /medicos/{id}` - Busca por ID
- `POST /medicos` - Cria novo
- `PUT /medicos/{id}` - Atualiza
- `DELETE /medicos/{id}` - Deleta
- `GET /medicos/crm/{crm}` - Busca por CRM
- `GET /medicos/especialidade/{id}` - Lista por especialidade

### 4.4 ConsultaResource
```
┌─────────────────────────────────────────┐
│         <<Resource>>                    │
│         @Path("/consultas")             │
│         ConsultaResource                │
├─────────────────────────────────────────┤
│ - consultaService: ConsultaService      │
├─────────────────────────────────────────┤
│ + ConsultaResource()                    │
│ @GET                                    │
│ + listarTodos(): Response               │
│ @GET @Path("/{id}")                     │
│ + buscarPorId(Long): Response           │
│ @POST                                   │
│ + criar(Consulta): Response             │
│ @PUT @Path("/{id}")                     │
│ + atualizar(Long, Consulta): Response   │
│ @DELETE @Path("/{id}")                  │
│ + deletar(Long): Response               │
│ @GET @Path("/paciente/{id}")            │
│ + listarPorPaciente(Long): Response     │
│ @GET @Path("/medico/{id}")              │
│ + listarPorMedico(Long): Response       │
│ @GET @Path("/status/{status}")          │
│ + listarPorStatus(String): Response     │
│ @PUT @Path("/{id}/cancelar")            │
│ + cancelar(Long, String): Response      │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌──────────────────┐
     │ ConsultaService  │
     └──────────────────┘
```

**Endpoints**:
- `GET /consultas` - Lista todas
- `GET /consultas/{id}` - Busca por ID
- `POST /consultas` - Cria nova
- `PUT /consultas/{id}` - Atualiza
- `DELETE /consultas/{id}` - Deleta
- `GET /consultas/paciente/{id}` - Lista por paciente
- `GET /consultas/medico/{id}` - Lista por médico
- `GET /consultas/status/{status}` - Lista por status
- `PUT /consultas/{id}/cancelar` - Cancela consulta

### 4.5 EspecialidadeResource
```
┌─────────────────────────────────────────┐
│         <<Resource>>                    │
│         @Path("/especialidades")        │
│         EspecialidadeResource           │
├─────────────────────────────────────────┤
│ - especialidadeService:                 │
│     EspecialidadeService                │
├─────────────────────────────────────────┤
│ + EspecialidadeResource()               │
│ @GET                                    │
│ + listarTodos(): Response               │
│ @GET @Path("/{id}")                     │
│ + buscarPorId(Long): Response           │
│ @POST                                   │
│ + criar(Especialidade): Response        │
│ @PUT @Path("/{id}")                     │
│ + atualizar(Long, Especialidade):       │
│     Response                            │
│ @DELETE @Path("/{id}")                  │
│ + deletar(Long): Response               │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌──────────────────────┐
     │ EspecialidadeService │
     └──────────────────────┘
```

### 4.6 LocalizacaoResource
```
┌─────────────────────────────────────────┐
│         <<Resource>>                    │
│         @Path("/localizacoes")          │
│         LocalizacaoResource             │
├─────────────────────────────────────────┤
│ - localizacaoService:                   │
│     LocalizacaoService                  │
├─────────────────────────────────────────┤
│ + LocalizacaoResource()                 │
│ @GET                                    │
│ + listarTodos(): Response               │
│ @GET @Path("/{id}")                     │
│ + buscarPorId(Long): Response           │
│ @POST                                   │
│ + criar(Localizacao): Response          │
│ @PUT @Path("/{id}")                     │
│ + atualizar(Long, Localizacao):         │
│     Response                            │
│ @DELETE @Path("/{id}")                  │
│ + deletar(Long): Response               │
│ @GET @Path("/cidade/{cidade}")          │
│ + listarPorCidade(String): Response     │
└─────────────────────────────────────────┘
            │
            │ usa
            ▼
     ┌────────────────────┐
     │ LocalizacaoService │
     └────────────────────┘
```

---

## ⚠️ 5. CAMADA EXCEPTION

### 5.1 Hierarquia de Exceções
```
                 RuntimeException
                        │
         ┌──────────────┼──────────────┬──────────────────┐
         │              │              │                  │
  DatabaseException  ValidationException  ResourceNotFoundException  BusinessRuleException
```

### 5.2 DatabaseException
```
┌─────────────────────────────────────────┐
│       <<Exception>>                     │
│       DatabaseException                 │
│       extends RuntimeException          │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + DatabaseException(String)             │
│ + DatabaseException(String, Throwable)  │
└─────────────────────────────────────────┘
```
**Uso**: Erros relacionados ao banco de dados (SQL, conexão, transações)

### 5.3 ValidationException
```
┌─────────────────────────────────────────┐
│       <<Exception>>                     │
│       ValidationException               │
│       extends RuntimeException          │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + ValidationException(String)           │
│ + ValidationException(String, Throwable)│
└─────────────────────────────────────────┘
```
**Uso**: Erros de validação de dados (campos obrigatórios, formatos inválidos)

### 5.4 ResourceNotFoundException
```
┌─────────────────────────────────────────┐
│       <<Exception>>                     │
│       ResourceNotFoundException         │
│       extends RuntimeException          │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + ResourceNotFoundException(String)     │
│ + ResourceNotFoundException(String,     │
│     Throwable)                          │
└─────────────────────────────────────────┘
```
**Uso**: Quando um recurso solicitado não é encontrado (ID inexistente)

### 5.5 BusinessRuleException
```
┌─────────────────────────────────────────┐
│       <<Exception>>                     │
│       BusinessRuleException             │
│       extends RuntimeException          │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + BusinessRuleException(String)         │
│ + BusinessRuleException(String,         │
│     Throwable)                          │
└─────────────────────────────────────────┘
```
**Uso**: Violação de regras de negócio (conflito de horários, consulta já cancelada)

---

## 🎮 6. CAMADA MAIN (EXECUTÁVEIS)

### 6.1 SistemaAgendamentoConsultas
```
┌─────────────────────────────────────────┐
│       <<Main>>                          │
│       SistemaAgendamentoConsultas       │
├─────────────────────────────────────────┤
│ - scanner: Scanner {static}             │
│ - consultaDAO: ConsultaDAO {static}     │
│ - medicoDAO: MedicoDAO {static}         │
│ - pacienteDAO: PacienteDAO {static}     │
│ - especialidadeDAO:                     │
│     EspecialidadeDAO {static}           │
│ - localizacaoDAO:                       │
│     LocalizacaoDAO {static}             │
│ - dateTimeFormatter:                    │
│     DateTimeFormatter {static}          │
│ - dateFormatter:                        │
│     DateTimeFormatter {static}          │
├─────────────────────────────────────────┤
│ + main(String[]): void {static}         │
│ - exibirMenuPrincipal(): boolean        │
│ - menuConsultas(): void                 │
│ - criarConsulta(): void                 │
│ - listarConsultas(): void               │
│ - atualizarConsulta(): void             │
│ - deletarConsulta(): void               │
│ - listarMedicos(): void                 │
│ - listarPacientes(): void               │
│ - cadastrarPaciente(): void             │
│ - listarEspecialidades(): void          │
│ - listarLocalizacoes(): void            │
│ - lerInteiro(): int                     │
│ - lerLong(): Long                       │
└─────────────────────────────────────────┘
```

### 6.2 TesteSimples
```
┌─────────────────────────────────────────┐
│       <<Main>>                          │
│       TesteSimples                      │
├─────────────────────────────────────────┤
│                                         │
├─────────────────────────────────────────┤
│ + main(String[]): void {static}         │
└─────────────────────────────────────────┘
```

---

## 🔗 RELACIONAMENTOS ENTRE CAMADAS

### Fluxo de Dados
```
┌─────────────────┐
│   Resource      │  1. Recebe requisição HTTP
└────────┬────────┘
         │ chama
         ▼
┌─────────────────┐
│    Service      │  2. Valida dados e aplica regras de negócio
└────────┬────────┘
         │ chama
         ▼
┌─────────────────┐
│      DAO        │  3. Executa operações SQL
└────────┬────────┘
         │ manipula
         ▼
┌─────────────────┐
│    Model (DTO)  │  4. Representa os dados
└─────────────────┘
         │
         │ persiste em
         ▼
┌─────────────────┐
│  Oracle Database│  5. Armazena os dados
└─────────────────┘
```

### Dependências por Camada
```
Resource
   ├── usa Service
   └── retorna Model (DTO)

Service
   ├── usa DAO
   ├── valida Model (DTO)
   └── lança Exception

DAO
   ├── usa ConexaoBD
   ├── retorna Model (DTO)
   └── lança DatabaseException

Model (DTO)
   └── (sem dependências)

Exception
   └── extends RuntimeException
```

---

## 📊 ESTATÍSTICAS DO PROJETO

### Resumo Quantitativo
- **Total de Classes**: 30+
- **Classes Model (DTOs)**: 8
- **Classes DAO**: 9 (8 DAOs + ConexaoBD)
- **Classes Service**: 5
- **Classes Resource**: 6 (5 Resources + ResponseEntity)
- **Classes Exception**: 4
- **Classes Main**: 2
- **Total de Métodos**: 150+
- **Total de Endpoints REST**: 40+

### Padrões de Projeto Aplicados
1. **DAO (Data Access Object)**: Separação da lógica de persistência
2. **Service Layer**: Centralização de regras de negócio
3. **MVC (Model-View-Controller)**: Arquitetura em camadas
4. **Singleton**: ConexaoBD com instância única
5. **Dependency Injection**: Services injetam DAOs

### Princípios SOLID
- ✅ **S**ingle Responsibility Principle
- ✅ **O**pen/Closed Principle
- ✅ **L**iskov Substitution Principle
- ✅ **I**nterface Segregation Principle
- ✅ **D**ependency Inversion Principle

---

## 📝 NOTAS TÉCNICAS

### Convenções de Nomenclatura
- **Classes**: PascalCase (ex: `PacienteService`)
- **Métodos**: camelCase (ex: `buscarPorId()`)
- **Constantes**: UPPER_SNAKE_CASE (ex: `URL_DATABASE`)
- **Variáveis**: camelCase (ex: `idPaciente`)

### Tipos de Dados Java
- **IDs**: `Long`
- **Datas**: `LocalDate`
- **Data/Hora**: `LocalDateTime`
- **Textos**: `String`
- **Números inteiros**: `Integer`

### Anotações JAX-RS
- `@Path`: Define caminho do recurso
- `@GET`: Método HTTP GET
- `@POST`: Método HTTP POST
- `@PUT`: Método HTTP PUT
- `@DELETE`: Método HTTP DELETE
- `@PathParam`: Parâmetro da URL
- `@QueryParam`: Parâmetro query string

---

**Versão**: 1.0  
**Última Atualização**: Novembro 2025  
**Autores**:
- Samara Vilela de Oliveira - RM 566133
- Felipe Conte Ferreira - RM 562248
- Altamir Lima - RM 562906

