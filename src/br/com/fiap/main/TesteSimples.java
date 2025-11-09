package br.com.fiap.main;

import br.com.fiap.model.dao.ConexaoBD;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dto.Paciente;

import java.util.List;

/**
 * Teste simples para verificar se tudo está funcionando
 * 
 * @author Samara Vilela de Oliveira - RM 566133
 * @author Felipe Conte Ferreira - RM 562248
 * @author Altamir Lima - RM 562906
 */
public class TesteSimples {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   TESTE RÁPIDO - SISTEMA EASEHC           ║");
        System.out.println("║   Grupo: Samara, Felipe, Altamir          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // Teste 1: Conexão com banco
        System.out.println("1️⃣  Testando conexão com banco de dados...");
        try {
            if (ConexaoBD.testarConexao()) {
                System.out.println("    ✅ Conexão OK!\n");
            } else {
                System.out.println("    ❌ Falha na conexão!\n");
                return;
            }
        } catch (Exception e) {
            System.out.println("    ❌ Erro: " + e.getMessage() + "\n");
            return;
        }
        
        // Teste 2: Listar pacientes
        System.out.println("2️⃣  Listando pacientes do banco...");
        try {
            PacienteDAO pacienteDAO = new PacienteDAO();
            List<Paciente> pacientes = pacienteDAO.listarTodos();
            
            if (pacientes.isEmpty()) {
                System.out.println("    ⚠️  Nenhum paciente encontrado.\n");
            } else {
                System.out.println("    ✅ Encontrados " + pacientes.size() + " pacientes:");
                for (int i = 0; i < Math.min(5, pacientes.size()); i++) {
                    Paciente p = pacientes.get(i);
                    System.out.println("       • " + p.getNomeCompleto());
                }
                if (pacientes.size() > 5) {
                    System.out.println("       ... e mais " + (pacientes.size() - 5) + " pacientes");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("    ❌ Erro ao listar pacientes: " + e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        
        // Teste 3: Verificar Services
        System.out.println("3️⃣  Testando camada Service...");
        try {
            br.com.fiap.service.PacienteService service = new br.com.fiap.service.PacienteService();
            List<Paciente> pacientes = service.listarTodos();
            System.out.println("    ✅ Service funcionando! " + pacientes.size() + " pacientes\n");
        } catch (Exception e) {
            System.out.println("    ❌ Erro no Service: " + e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        
        // Teste 4: Verificar Resources (apenas verificar se a classe pode ser instanciada)
        System.out.println("4️⃣  Verificando camada Resource...");
        try {
            br.com.fiap.resource.PacienteResource resource = new br.com.fiap.resource.PacienteResource();
            System.out.println("    ✅ Resource carregado com sucesso! (Use ApiServer para testar a API REST)\n");
        } catch (Exception e) {
            System.out.println("    ❌ Erro ao carregar Resource: " + e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        
        // Fechar conexão
        ConexaoBD.fecharConexao();
        
        // Resultado Final
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   ✅ TODOS OS TESTES PASSARAM!            ║");
        System.out.println("║   Sistema está funcionando perfeitamente  ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}

