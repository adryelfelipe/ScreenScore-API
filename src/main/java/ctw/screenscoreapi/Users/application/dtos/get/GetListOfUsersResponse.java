package ctw.screenscoreapi.Users.application.dtos.get;

import java.util.List;

public record GetListOfUsersResponse(List<GetUserResponse> users) {
}
