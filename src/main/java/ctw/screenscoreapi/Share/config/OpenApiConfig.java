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

        ResolvedSchema problemDetail = ModelConverters.getInstance().resolveAsResolvedSchema(new AnnotatedType(ProblemDetail.class));
        problemDetail.schema
                .description("Representa um erro ocorrido durante o processamento da requisição, seguindo o padrão RFC 7807");

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
                                .addSchemas("ProblemDetail", problemDetail.schema)

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
                                                                                                .$ref("#/components/schemas/ProblemDetail")
                                                                                )
                                                                                .example(
                                                                                        """
                                                                                        {
                                                                                            "instance": "path-instance",
                                                                                            "status": "500",
                                                                                            "title": "Erro interno do servidor",
                                                                                            "type": "/erros/internal-server",
                                                                                            "detail": "Ocorreu um erro interno no servidor. Tente novamente mais tarde."
                                                                                        }
                                                                                        """
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
                                                                                                .$ref("#/components/schemas/ProblemDetail")
                                                                                )
                                                                                .example(
                                                                                        """
                                                                                        {
                                                                                            "instance": "path-instance",
                                                                                            "status": 400,
                                                                                            "title": "A requisição contém campos inválidos",
                                                                                            "type": "/erros/invalid-argument",
                                                                                            "detail": "Consulte a documentação do endpoint para visualizar os formatos esperados",
                                                                                            "erros": []                                                     \s
                                                                                        }
                                                                                       """
                                                                                )
                                                                )
                                                )
                                )

                                .addResponses(
                                        "404",
                                        new ApiResponse()
                                                .description("Recurso não encontrado")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(
                                                                                        new Schema<>()
                                                                                                .$ref("#/components/schemas/ProblemDetail")
                                                                                )
                                                                                .example(
                                                                                        """
                                                                                        {
                                                                                            "instance": "path-instance",
                                                                                            "status": 404,
                                                                                            "title": "Recurso não encontrado",
                                                                                            "type": "/erros/resource-not-found",
                                                                                            "detail": "details of the problem"
                                                                                        }
                                                                                        """
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
                                                                                                .$ref("#/components/schemas/ProblemDetail")
                                                                                )
                                                                                .example(
                                                                                        """
                                                                                        {
                                                                                            "instance": "path-instance",
                                                                                            "status": 409,
                                                                                            "title": "Dados já registrados no servidor",
                                                                                            "type": "/erros/data-already-used",
                                                                                            "detail": "details of the problem"
                                                                                        }
                                                                                        """
                                                                                )
                                                                )
                                                )
                                )

                                .addResponses(
                                        "422",
                                        new ApiResponse()
                                                .description("Não há nenhum conteúdo para atualizar")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(problemDetail.schema)
                                                                                .example(
                                                                                        """
                                                                                       {
                                                                                           "instance": "path-instance",
                                                                                           "status": 422,
                                                                                           "title": "Falha durante a execução da aplicação",
                                                                                           "type": "/erros/no-content-to-update",
                                                                                           "detail": "details of the problem"
                                                                                       }
                                                                                       """
                                                                                )
                                                                )
                                                )
                                )

                                .addResponses(
                                        "502",
                                        new ApiResponse()
                                                .description("Não foi possível se comunicar com uma api externa")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .schema(problemDetail.schema)
                                                                                .example(
                                                                                        """
                                                                                       {
                                                                                           "instance": "path-instance",
                                                                                           "status": 502,
                                                                                           "title": "Erro ao se comunicar com sistema externo",
                                                                                           "type": "/erros/external-server",
                                                                                           "detail": "Falha ao comunicar com serviço externo. Tente novamente mais tarde."
                                                                                       }
                                                                                       """
                                                                                )
                                                                )
                                                )
                                )

                );
    }
}