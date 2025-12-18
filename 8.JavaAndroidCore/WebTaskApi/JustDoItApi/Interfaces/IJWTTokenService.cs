using JustDoItApi.Entities.Identity;

namespace JustDoItApi.Interfaces;

public interface IJWTTokenService
{
    Task<string> CreateTokenAsync(UserEntity user);
}
