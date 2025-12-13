using AutoMapper;
using JustDoItApi.Data;
using JustDoItApi.Entities;
using JustDoItApi.Interfaces;
using JustDoItApi.Models.Zadachi;
using Microsoft.EntityFrameworkCore;

namespace JustDoItApi.Services;

public class ZadachiService(AppDbContext context, IMapper mapper, IImageService imageService) : IZadachiService
{
    public async Task<ZadachaItemModel> CreateZadachyAsync(ZadachaCreateModel model)
    {
        var zadachaEntity = mapper.Map<ZadachaEntity>(model);

        zadachaEntity.Image = await imageService.SaveImageAsync(model.Image);

        context.Zadachi.Add(zadachaEntity);
        await context.SaveChangesAsync();

        var zadachaModel = mapper.Map<ZadachaItemModel>(zadachaEntity);
        return zadachaModel;
    }

    public async Task<IEnumerable<ZadachaItemModel>> GetAllAsync()
    {
        var zadachy = await context.Zadachi.ToListAsync();
        var zadachyModels = mapper.Map<IEnumerable<ZadachaItemModel>>(zadachy);
        return zadachyModels;
    }
}
