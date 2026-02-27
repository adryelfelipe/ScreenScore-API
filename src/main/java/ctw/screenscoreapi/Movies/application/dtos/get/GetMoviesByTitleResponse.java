package ctw.screenscoreapi.Movies.application.dtos.get;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetMoviesByTitleResponse(
        @Schema(example =
                """     
                    [                                           
                        {
                              "title": "Minions",
                              "originalLanguage": "en",
                              "originalTitle": "Minions",
                              "adult": false,
                              "releaseDate": "2015-06-17",
                              "posterImage": "/caq9Xi6b1sZNREfzFBO2tRIBzWn.jpg",
                                 "overview": "Seres amarelos milenares, os minions têm uma missão: servir os maiores vilões.", 
                               "genres": [
                                   "FAMILIA",
                                   "ANIMACAO",
                                   "AVENTURA",
                                   "COMEDIA"
                               ]
                          },
                          {
                               "title": "Minions 2: A Origem de Gru",
                               "originalLanguage": "en",
                               "originalTitle": "Minions: The Rise of Gru",
                               "adult": false,
                               "releaseDate": "2022-06-29",
                               "posterImage": "/iTP3mMw0AoqmScYzDoMmYeKxYe.jpg",
                               "overview": "A continuação das aventuras dos Minions, sempre em busca de um líder tirânico. Dessa vez, eles ajudam um Gru ainda criança, descobrindo como ser vilão.",
                               "genres": [
                                   "FAMILIA",
                                   "COMEDIA",
                                   "AVENTURA",
                                   "ANIMACAO",
                                   "FICCAO_CIENTIFICA",
                                   "FANTASIA"
                               ]
                          }                                               
                    ]        
                """
        )
        List<GetMovieResponse> movies
) {}
