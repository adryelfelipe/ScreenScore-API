package ctw.screenscoreapi.Share.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customApiConfig() {

        return new OpenAPI()
                .info(new Info()
                .title("ScreenScore Movies Management API")
                .description("""
                        API para gerenciamento de filmes e séries, permitindo que usuários avaliem, filtrem e consultem rankings.
                        
                        ## Visão Geral
                        Este serviço oferece operações para:
                        
                        - **Gerenciamento de Filmes e Séries**: Criar, consultar, atualizar e remover filmes e séries
                        - **Avaliações de Usuários**: Usuários podem enviar, editar e remover avaliações e comentários
                        - **Filtragem e Ordenação**: Filtrar por gênero, ano, diretor ou faixa etária; ordenar por avaliação média
                        - **Rankings e Estatísticas**: Top 10 filmes, média de avaliações por gênero e outras estatísticas agregadas
                        """)
                .version("v1.0.0")
                .contact(new Contact()
                        .name("Adryel Felipe")
                        .email("adryxd003@gmail.com")
                        .url("https://github.com/adryelfelipe/ScreenScore-API")))

                .components(new Components()
                .addResponses("InternalServerError", new ApiResponse()
                        .description("Erro interno do servidor")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>()
                                                .$ref("#/components/schemas/ProblemDetail")
                                                .example(
                                                        """
                                                        {
                                                            "instance": "/filmes/{id}",
                                                            "status": 500,
                                                            "title": "Erro interno do servidor",
                                                            "type": "http://localhost:8080/errors/internal-server",
                                                            "detail": "O servidor encontrou um erro inesperado"
                                                        }
                                                        """
                                                        )
                                        )
                                )
                        ))
        );
    }
}
