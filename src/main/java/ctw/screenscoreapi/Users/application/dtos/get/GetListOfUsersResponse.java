package ctw.screenscoreapi.Users.application.dtos.get;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetListOfUsersResponse(
        @Schema(example =
                """
                   [
                      {
                        "id": 1,
                        "name": "Robson Cavalo",
                        "email": "robsoncavalo@gmail.com"
                      },
                      {
                        "id": 2,
                        "name": "Lipe Formigari",
                        "email": "lipeformigari@gmail.com"
                      },
                      {
                        "id": 3,
                        "name": "Matheus Devenzzi",
                        "email": "matheusdevenzzi@gmail.com"
                      },
                      {
                        "id": 4,
                        "name": "Pedro Pereira",
                        "email": "pedropereira@gmail.com"
                      },
                      {
                        "id": 5,
                        "name": "Rayan Scaburri",
                        "email": "rayanscaburri@gmail.com"
                      }, 
                      {
                        "id": 6,
                        "name": "Willyan Franz Muller",
                        "email": "willyanfranzmuller@gmail.com"
                      }                            
                   ]         
                """)
        List<GetUserResponse> users
) {}
