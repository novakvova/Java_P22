using JustDoItApi.Models.Auth;

namespace JustDoItApi.Interfaces;

public interface IAuthService
{
    public Task<string> LoginAsync(LoginModel model);
    public Task<string> RegisterAsync(RegisterModel model);
}
