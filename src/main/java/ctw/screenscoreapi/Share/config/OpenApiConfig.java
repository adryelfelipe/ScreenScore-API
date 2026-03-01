package ctw.screenscoreapi.Share.config;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ProblemDetail;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customApiConfig() {

        ResolvedSchema problemDetailBadRequest = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ProblemDetail.class));
        problemDetailBadRequest.schema
                .example(
                            """
                            {
                                "instance": "/filmes/externo",
                                "status": 400,
                                "title": "A requisição contém campos inválidos",
                                "type": "http://localhost:8080/errors/invalid-argument",
                                "erros": {
                                    "title": "O título do filme é obrigatório"
                                }
                            }
                            """)
                .description("Representa um erro ocorrido devido a dados inálidos durante o processamento da requisição, seguindo o padrão RFC 7807");

        ResolvedSchema problemDetailNotFound = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ProblemDetail.class));
        problemDetailNotFound.schema
                .example(
                            """
                            {
                                "instance": "/filmes",
                                "status": 404,
                                "title": "Filme não encontrado",
                                "type": "http://localhost:8080/errors/movie-not-found",
                                "detail": "Não foi possível identificar um filme com este título"
                            }
                            """)
                .description("Representa um erro ocorrido devido a um recurso não encontrado durante o processamento da requisição, seguindo o padrão RFC 7807");

        ResolvedSchema problemDetailConflict = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ProblemDetail.class));
        problemDetailConflict.schema
                .example(
                            """
                            {
                                "instance": "/filmes",
                                "status": 409,
                                "title": "Dados já registrados no servidor",
                                "type": "http://localhost:8080/errors/data-already-used",
                                "detail": "O título do filme já foi utilizado"
                            }
                            """)
                .description("Representa um erro ocorrido devido a conflito de dados durante o processamento da requisição, seguindo o padrão RFC 7807");

        ResolvedSchema problemDetailInternalErrorServer = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ProblemDetail.class));
        problemDetailInternalErrorServer.schema
                .example(
                            """
                            {
                                "instance": "/filmes/{id}",
                                "status": 500,
                                "title": "Erro interno do servidor",
                                "type": "http://localhost:8080/errors/internal-server",
                                "detail": "O servidor encontrou um erro inesperado"
                            }
                            """)
                .description("Representa um erro ocorrido devido a um problema interno do servidor durante o processamento da requisição, seguindo o padrão RFC 7807");



        return new OpenAPI()
                .info(
                        new Info()
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
                                .contact(
                                        new Contact()
                                                .name("Adryel Felipe")
                                                .email("adryxd003@gmail.com")
                                                .url("https://github.com/adryelfelipe/ScreenScore-API")
                                )
                )
                .components(
                        new Components()
                                .addSchemas("ProblemDetail_BadRequest", problemDetailBadRequest.schema)
                                .addSchemas("ProblemDetail_NotFound", problemDetailNotFound.schema)
                                .addSchemas("ProblemDetail_Conflict", problemDetailConflict.schema)
                                .addSchemas("ProblemDetail_InternalErrorServer", problemDetailInternalErrorServer.schema)

                                .addResponses(
                                        "500",
                                        new ApiResponse()
                                                .description("Erro interno do servidor")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(
                                                                                        new Schema<>()
                                                                                                .$ref("#/components/schemas/ProblemDetail_InternalErrorServer")
                                                                                )
                                                                )
                                                )
                                )

                                .addResponses(
                                        "400",
                                        new ApiResponse()
                                                .description("Os dados fornecidos estão inválidos")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(
                                                                                        new Schema<>()
                                                                                                .$ref("#/components/schemas/ProblemDetail_BadRequest")
                                                                                )
                                                                )
                                                )
                                )

                                .addResponses(
                                        "404",
                                        new ApiResponse()
                                                .description("Filme não encontrado")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(
                                                                                        new Schema<>()
                                                                                                .$ref("#/components/schemas/ProblemDetail_NotFound")
                                                                                )
                                                                )
                                                )
                                )

                                .addResponses(
                                        "409",
                                        new ApiResponse()
                                                .description("Dados já registrados no servidor")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(
                                                                                        new Schema<>()
                                                                                                .$ref("#/components/schemas/ProblemDetail_Conflict")
                                                                                )
                                                                )
                                                )
                                )
                );
    }
}