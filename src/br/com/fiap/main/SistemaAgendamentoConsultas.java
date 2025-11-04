package br.com.fiap.main;

import br.com.fiap.model.dao.*;
import br.com.fiap.model.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Agendamento de Consultas
 * Demonstra operações CRUD (Create, Read, Update, Delete) com banco de dados Oracle
 * 
 * @author Samara Vilela de Oliveira - RM 566133
 * @author Felipe Conte Ferreira - RM 562248
 * @author Altamir Lima - RM 562906
 */
public class SistemaAgendamentoConsultas {
    
    private static Scanner scanner = new Scanner(System.in);
    private static ConsultaDAO consultaDAO;
    private static MedicoDAO medicoDAO;
    private static PacienteDAO pacienteDAO;
    private static EspecialidadeDAO especialidadeDAO;
    private static LocalizacaoDAO localizacaoDAO;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE AGENDAMENTO DE CONSULTAS MÉDICAS        ║");
        System.out.println("║              Sprint 4 - FIAP                          ║");
        System.out.println("║                                                       ║");
        System.out.println("║   Grupo:                                              ║");
        System.out.println("║   • Samara Vilela de Oliveira - RM 566133            ║");
        System.out.println("║   • Felipe Conte Ferreira - RM 562248                ║");
        System.out.println("║   • Altamir Lima - RM 562906                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println();

        // Testar conexão com banco de dados
        if (!ConexaoBD.testarConexao()) {
            System.err.println("\n✗ Não foi possível conectar ao banco de dados!");
            System.err.println("Verifique suas credenciais e conexão com a internet.");
            return;
        }

        // Inicializar DAOs
        consultaDAO = new ConsultaDAO();
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
        especialidadeDAO = new EspecialidadeDAO();
        localizacaoDAO = new LocalizacaoDAO();

        System.out.println("\n✓ Sistema iniciado com sucesso!");
        System.out.println("═══════════════════════════════════════════════════════");

        // Menu principal
        boolean continuar = true;
        while (continuar) {
            continuar = exibirMenuPrincipal();
        }

        // Fechar conexão e scanner
        ConexaoBD.fecharConexao();
        scanner.close();
        System.out.println("\n✓ Sistema encerrado. Até logo!");
    }

    private static boolean exibirMenuPrincipal() {
        System.out.println("\n╔═══════════════════ MENU PRINCIPAL ═══════════════════╗");
        System.out.println("║  1. Gerenciar Consultas             ║");
        System.out.println("║  2. Listar Médicos Disponíveis                       ║");
        System.out.println("║  3. Listar Pacientes                                 ║");
        System.out.println("║  4. Cadastrar Novo Paciente                          ║");
        System.out.println("║  5. Listar Especialidades                            ║");
        System.out.println("║  6. Listar Localizações                              ║");
        System.out.println("║  0. Sair                                             ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                menuConsultas();
                break;
            case 2:
                listarMedicos();
                break;
            case 3:
                listarPacientes();
                break;
            case 4:
                cadastrarPaciente();
                break;
            case 5:
                listarEspecialidades();
                break;
            case 6:
                listarLocalizacoes();
                break;
            case 0:
                return false;
            default:
                System.out.println("✗ Opção inválida! Tente novamente.");
        }
        return true;
    }

    // ==================== MENU DE CONSULTAS (CRUD COMPLETO) ====================
    
    private static void menuConsultas() {
        System.out.println("\n╔═══════════════ GERENCIAMENTO DE CONSULTAS ═══════════╗");
        System.out.println("║  1. CREATE - Criar Nova Consulta                     ║");
        System.out.println("║  2. READ   - Listar Todas as Consultas               ║");
        System.out.println("║  3. READ   - Buscar Consulta por ID                  ║");
        System.out.println("║  4. UPDATE - Atualizar Consulta                      ║");
        System.out.println("║  5. DELETE - Cancelar/Deletar Consulta               ║");
        System.out.println("║  0. Voltar ao Menu Principal                         ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                criarConsulta(); // CREATE
                break;
            case 2:
                listarConsultas(); // READ
                break;
            case 3:
                buscarConsultaPorId(); // READ
                break;
            case 4:
                atualizarConsulta(); // UPDATE
                break;
            case 5:
                deletarConsulta(); // DELETE
                break;
            case 0:
                return;
            default:
                System.out.println("✗ Opção inválida!");
        }
    }

    // ==================== OPERAÇÕES CRUD DE CONSULTAS ====================

    // CREATE - Criar nova consulta
    private static void criarConsulta() {
        System.out.println("\n───────────── CREATE: NOVA CONSULTA ─────────────");
        
        // Listar pacientes disponíveis
        System.out.println("\nPacientes cadastrados:");
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        if (pacientes.isEmpty()) {
            System.out.println("✗ Nenhum paciente cadastrado! Cadastre um paciente primeiro.");
            return;
        }
        for (Paciente p : pacientes) {
            System.out.println("  ID: " + p.getIdPaciente() + " - " + p.getNomeCompleto());
        }

        System.out.print("\nDigite o ID do paciente: ");
        long idPaciente = lerLong();

        // Listar médicos disponíveis
        System.out.println("\nMédicos disponíveis:");
        List<Medico> medicos = medicoDAO.listarTodos();
        if (medicos.isEmpty()) {
            System.out.println("✗ Nenhum médico cadastrado!");
            return;
        }
        for (Medico m : medicos) {
            System.out.println("  ID: " + m.getIdMedico() + " - " + m.getNomeCompleto() + " (CRM: " + m.getCrm() + ")");
        }

        System.out.print("\nDigite o ID do médico: ");
        long idMedico = lerLong();

        // Listar especialidades
        System.out.println("\nEspecialidades:");
        List<Especialidade> especialidades = especialidadeDAO.listarTodos();
        if (especialidades.isEmpty()) {
            System.out.println("✗ Nenhuma especialidade cadastrada!");
            return;
        }
        for (Especialidade e : especialidades) {
            System.out.println("  ID: " + e.getIdEspecialidade() + " - " + e.getNomeEspecialidade());
        }

        System.out.print("\nDigite o ID da especialidade: ");
        long idEspecialidade = lerLong();

        // Listar localizações
        System.out.println("\nLocalizações:");
        List<Localizacao> localizacoes = localizacaoDAO.listarTodos();
        if (localizacoes.isEmpty()) {
            System.out.println("✗ Nenhuma localização cadastrada!");
            return;
        }
        for (Localizacao l : localizacoes) {
            System.out.println("  ID: " + l.getIdLocalizacao() + " - " + l.getNomeUnidade());
        }

        System.out.print("\nDigite o ID da localização: ");
        long idLocalizacao = lerLong();

        System.out.print("Digite a data e hora (formato: dd/MM/yyyy HH:mm): ");
        scanner.nextLine(); // Limpar buffer
        String dataStr = scanner.nextLine();
        
        LocalDateTime dataHora;
        try {
            dataHora = LocalDateTime.parse(dataStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("✗ Formato de data inválido!");
            return;
        }

        System.out.print("Digite a duração em minutos: ");
        int duracao = lerInteiro();

        System.out.print("Digite o status (Agendada/Cancelada/Realizada): ");
        String status = scanner.nextLine();

        System.out.print("Digite observações (ou pressione Enter): ");
        String observacoes = scanner.nextLine();

        System.out.print("Digite a prioridade (Alta/Normal/Baixa): ");
        String prioridade = scanner.nextLine();

        Consulta consulta = new Consulta(
            null, idPaciente, idMedico, idLocalizacao, idEspecialidade,
            dataHora, duracao, status, observacoes, prioridade
        );
        
        try {
            consultaDAO.criar(consulta);
            System.out.println("\n✓ Consulta criada com sucesso!");
        } catch (Exception e) {
            System.out.println("\n✗ Erro ao criar consulta: " + e.getMessage());
        }
    }

    // READ - Listar todas as consultas
    private static void listarConsultas() {
        System.out.println("\n───────────── READ: LISTAR CONSULTAS ─────────────");
        List<Consulta> consultas = consultaDAO.listarTodos();
        
        if (consultas.isEmpty()) {
            System.out.println("✗ Nenhuma consulta encontrada.");
        } else {
            System.out.println("\nTotal de consultas: " + consultas.size());
            System.out.println("────────────────────────────────────────────────");
            for (Consulta c : consultas) {
                System.out.println("ID: " + c.getIdConsulta() + 
                    " | Paciente: " + c.getIdPaciente() + 
                    " | Médico: " + c.getIdMedico() +
                    " | Data: " + c.getDataHora().format(dateTimeFormatter) +
                    " | Status: " + c.getStatus());
            }
        }
    }

    // READ - Buscar consulta por ID
    private static void buscarConsultaPorId() {
        System.out.println("\n───────────── READ: BUSCAR CONSULTA ─────────────");
        System.out.print("Digite o ID da consulta: ");
        long id = lerLong();

        Consulta consulta = consultaDAO.buscarPorId(id);
        
        if (consulta != null) {
            System.out.println("\n✓ Consulta encontrada:");
            System.out.println(consulta);
        } else {
            System.out.println("✗ Consulta não encontrada!");
        }
    }

    // UPDATE - Atualizar consulta
    private static void atualizarConsulta() {
        System.out.println("\n───────────── UPDATE: ATUALIZAR CONSULTA ─────────────");
        System.out.print("Digite o ID da consulta que deseja atualizar: ");
        long id = lerLong();

        Consulta consultaExistente = consultaDAO.buscarPorId(id);
        if (consultaExistente == null) {
            System.out.println("✗ Consulta não encontrada!");
            return;
        }

        System.out.println("\nConsulta atual:");
        System.out.println(consultaExistente);

        System.out.println("\n--- Digite os novos dados (ou pressione Enter para manter o atual) ---");
        scanner.nextLine(); // Limpar buffer

        System.out.print("Nova data e hora (formato: dd/MM/yyyy HH:mm) [" + 
            consultaExistente.getDataHora().format(dateTimeFormatter) + "]: ");
        String dataStr = scanner.nextLine();
        
        LocalDateTime novaDataHora = consultaExistente.getDataHora();
        if (!dataStr.trim().isEmpty()) {
            try {
                novaDataHora = LocalDateTime.parse(dataStr, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("✗ Formato de data inválido! Mantendo data anterior.");
            }
        }

        System.out.print("Novo status [" + consultaExistente.getStatus() + "]: ");
        String novoStatus = scanner.nextLine();
        if (novoStatus.trim().isEmpty()) {
            novoStatus = consultaExistente.getStatus();
        }

        System.out.print("Novas observações [" + consultaExistente.getObservacoes() + "]: ");
        String novasObs = scanner.nextLine();
        if (novasObs.trim().isEmpty()) {
            novasObs = consultaExistente.getObservacoes();
        }

        Consulta consultaAtualizada = new Consulta(
            id,
            consultaExistente.getIdPaciente(),
            consultaExistente.getIdMedico(),
            consultaExistente.getIdLocalizacao(),
            consultaExistente.getIdEspecialidade(),
            novaDataHora,
            consultaExistente.getDuracaoMinutos(),
            novoStatus,
            novasObs,
            consultaExistente.getPrioridade()
        );

        try {
            if (consultaDAO.atualizar(consultaAtualizada)) {
                System.out.println("\n✓ Consulta atualizada com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("\n✗ Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    // DELETE - Deletar consulta
    private static void deletarConsulta() {
        System.out.println("\n───────────── DELETE: CANCELAR CONSULTA ─────────────");
        System.out.print("Digite o ID da consulta que deseja deletar: ");
        long id = lerLong();

        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            System.out.println("✗ Consulta não encontrada!");
            return;
        }

        System.out.println("\nConsulta a ser deletada:");
        System.out.println(consulta);
        System.out.print("\nTem certeza que deseja deletar? (S/N): ");
        scanner.nextLine(); // Limpar buffer
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                if (consultaDAO.deletar(id)) {
                    System.out.println("\n✓ Consulta deletada com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("\n✗ Erro ao deletar consulta: " + e.getMessage());
            }
        } else {
            System.out.println("✗ Operação cancelada.");
        }
    }

    // ==================== OUTRAS OPERAÇÕES ====================

    private static void listarMedicos() {
        System.out.println("\n───────────── MÉDICOS DISPONÍVEIS ─────────────");
        List<Medico> medicos = medicoDAO.listarTodos();
        
        if (medicos.isEmpty()) {
            System.out.println("✗ Nenhum médico cadastrado.");
        } else {
            System.out.println("\nTotal de médicos: " + medicos.size());
            System.out.println("────────────────────────────────────────────────");
            for (Medico m : medicos) {
                System.out.println("ID: " + m.getIdMedico() + 
                    " | " + m.getNomeCompleto() + 
                    " | CRM: " + m.getCrm() +
                    " | Tel: " + m.getTelefone());
            }
        }
    }

    private static void listarPacientes() {
        System.out.println("\n───────────── PACIENTES CADASTRADOS ─────────────");
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        
        if (pacientes.isEmpty()) {
            System.out.println("✗ Nenhum paciente cadastrado.");
        } else {
            System.out.println("\nTotal de pacientes: " + pacientes.size());
            System.out.println("────────────────────────────────────────────────");
            for (Paciente p : pacientes) {
                System.out.println("ID: " + p.getIdPaciente() + 
                    " | " + p.getNomeCompleto() + 
                    " | Tel: " + p.getTelefone() +
                    " | Tipo: " + p.getTipoSanguineo());
            }
        }
    }

    private static void listarEspecialidades() {
        System.out.println("\n───────────── ESPECIALIDADES ─────────────");
        List<Especialidade> especialidades = especialidadeDAO.listarTodos();
        
        if (especialidades.isEmpty()) {
            System.out.println("✗ Nenhuma especialidade cadastrada.");
        } else {
            System.out.println("\nTotal de especialidades: " + especialidades.size());
            System.out.println("────────────────────────────────────────────────");
            for (Especialidade e : especialidades) {
                System.out.println("ID: " + e.getIdEspecialidade() + 
                    " | " + e.getNomeEspecialidade() + 
                    " | Área: " + e.getAreaMedica() +
                    " | Tempo médio: " + e.getTempoMedioConsulta() + " min");
            }
        }
    }

    private static void listarLocalizacoes() {
        System.out.println("\n───────────── LOCALIZAÇÕES ─────────────");
        List<Localizacao> localizacoes = localizacaoDAO.listarTodos();
        
        if (localizacoes.isEmpty()) {
            System.out.println("✗ Nenhuma localização cadastrada.");
        } else {
            System.out.println("\nTotal de localizações: " + localizacoes.size());
            System.out.println("────────────────────────────────────────────────");
            for (Localizacao l : localizacoes) {
                System.out.println("ID: " + l.getIdLocalizacao() + 
                    " | " + l.getNomeUnidade() + 
                    " | " + l.getCidade() + " - " + l.getEstado() +
                    " | Tel: " + l.getTelefone());
            }
        }
    }

    private static void cadastrarPaciente() {
        System.out.println("\n───────────── CADASTRAR NOVO PACIENTE ─────────────");
        scanner.nextLine(); // Limpar buffer
        
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();

        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        String dataNascStr = scanner.nextLine();
        LocalDate dataNasc;
        try {
            dataNasc = LocalDate.parse(dataNascStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("✗ Formato de data inválido!");
            return;
        }

        System.out.print("Gênero (F/M/O): ");
        String genero = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Tipo sanguíneo (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
        String tipoSanguineo = scanner.nextLine();

        System.out.print("Alergias: ");
        String alergias = scanner.nextLine();

        Paciente paciente = new Paciente(
            null, nome, dataNasc, genero, telefone, tipoSanguineo, alergias
        );
        
        try {
            pacienteDAO.criar(paciente);
            System.out.println("\n✓ Paciente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n✗ Erro ao cadastrar paciente: " + e.getMessage());
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("✗ Valor inválido! Digite um número: ");
            }
        }
    }

    private static long lerLong() {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("✗ Valor inválido! Digite um número: ");
            }
        }
    }
}
