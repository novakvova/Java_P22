namespace JustDoItApi.Interfaces;

public interface IIdentityService
{
    Task<long> GetUserIdAsync();
}
