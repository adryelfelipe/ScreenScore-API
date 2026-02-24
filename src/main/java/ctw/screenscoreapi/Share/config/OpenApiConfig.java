package ctw.screenscoreapi.Share.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customApiConfig() {

        return new OpenAPI().info(new Info()
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
                .contact(new Contact()
                        .name("Adryel Felipe")
                        .email("adryxd003@gmail.com")
                        .url("https://github.com/adryelfelipe/ScreenScore-API")));

    }
}
