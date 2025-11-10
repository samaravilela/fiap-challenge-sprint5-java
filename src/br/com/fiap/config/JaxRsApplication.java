package br.com.fiap.config;

import br.com.fiap.resource.*;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configuração da aplicação JAX-RS
 * Registra todos os recursos REST da aplicação
 * 
 * O suporte a JSON é fornecido automaticamente pelo Jersey
 * através da dependência jersey-media-json-jackson
 */
public class JaxRsApplication extends ResourceConfig {
    
    public JaxRsApplication() {
        // Registrar recursos REST
        register(ConsultaResource.class);
        register(PacienteResource.class);
        register(MedicoResource.class);
        register(EspecialidadeResource.class);
        register(LocalizacaoResource.class);

        // Registrar filtros globais
        register(CorsFilter.class);
    }
}

