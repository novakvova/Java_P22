using JustDoItApi.Models.Zadachi;

namespace JustDoItApi.Interfaces;

public interface IZadachiService
{
    Task<IEnumerable<ZadachaItemModel>> GetAllAsync();
    Task CreateZadachyAsync(ZadachaCreateModel model);
}
