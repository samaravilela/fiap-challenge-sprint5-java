package br.com.fiap.main;

import br.com.fiap.config.JaxRsApplication;
import br.com.fiap.model.dao.ConexaoBD;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Servidor embutido para executar a API REST
 * Utiliza Jetty como servidor web e Jersey para JAX-RS
 * 
 * @author Samara Vilela de Oliveira - RM 566133
 * @author Felipe Conte Ferreira - RM 562248
 * @author Altamir Lima - RM 562906
 */
public class ApiServer {

    private static final int PORT = resolvePort();

    private static int resolvePort() {
        String port = System.getenv("PORT");
        if (port != null) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException ignored) {
                System.err.println("⚠️  Valor inválido para variável de ambiente PORT (" + port + "). Usando porta padrão 8080.");
            }
        }
        return 8080;
    }

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║   API REST - SISTEMA DE AGENDAMENTO DE CONSULTAS     ║");
        System.out.println("║              Sprint 5 - FIAP                          ║");
        System.out.println("║                                                       ║");
        System.out.println("║   Grupo:                                              ║");
        System.out.println("║   • Samara Vilela de Oliveira - RM 566133            ║");
        System.out.println("║   • Felipe Conte Ferreira - RM 562248                ║");
        System.out.println("║   • Altamir Lima - RM 562906                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Testar conexão com banco de dados
        System.out.println("Testando conexão com banco de dados...");
        if (!ConexaoBD.testarConexao()) {
            System.err.println("\n✗ Não foi possível conectar ao banco de dados!");
            System.err.println("Verifique suas credenciais e conexão com a internet.");
            System.exit(1);
        }
        System.out.println("✓ Conexão com banco de dados estabelecida!");
        System.out.println();
        
        // Configurar servidor Jetty
        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        // Configurar Jersey Servlet
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("javax.ws.rs.Application",
                JaxRsApplication.class.getCanonicalName());
        
        try {
            // Iniciar servidor
            server.start();
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("✓ Servidor iniciado com sucesso!");
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println();
            System.out.println("📍 Porta de escuta: " + PORT);
            System.out.println("📍 URL Base local: http://localhost:" + PORT + "/api");
            System.out.println();
            System.out.println("📋 Endpoints disponíveis:");
            System.out.println("   GET    http://localhost:" + PORT + "/api/consultas");
            System.out.println("   GET    http://localhost:" + PORT + "/api/pacientes");
            System.out.println("   GET    http://localhost:" + PORT + "/api/medicos");
            System.out.println("   GET    http://localhost:" + PORT + "/api/especialidades");
            System.out.println("   GET    http://localhost:" + PORT + "/api/localizacoes");
            System.out.println();
            System.out.println("🔧 Teste a API no Postman:");
            System.out.println("   1. Abra o Postman");
            System.out.println("   2. Crie uma requisição GET para: http://localhost:" + PORT + "/api/pacientes");
            System.out.println("   3. Execute a requisição");
            System.out.println();
            System.out.println("⚠️  Pressione Ctrl+C para parar o servidor");
            System.out.println("═══════════════════════════════════════════════════════");
            
            // Manter servidor rodando
            server.join();
        } catch (Exception e) {
            System.err.println("✗ Erro ao iniciar servidor: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            // Fechar conexão com banco de dados
            ConexaoBD.fecharConexao();
            try {
                server.stop();
            } catch (Exception e) {
                System.err.println("Erro ao parar servidor: " + e.getMessage());
            }
        }
    }
}

