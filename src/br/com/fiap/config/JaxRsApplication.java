package br.com.fiap.config;

import br.com.fiap.resource.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

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
        register(HealthResource.class);

        // Registrar filtros globais
        register(CorsFilter.class);

        // Registrar suporte a JSON via Jackson
        register(JacksonFeature.class);
        register(createJacksonProvider());
    }

    private JacksonJaxbJsonProvider createJacksonProvider() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);
        return provider;
    }
}

