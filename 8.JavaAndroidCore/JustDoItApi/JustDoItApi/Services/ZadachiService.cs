using AutoMapper;
using JustDoItApi.Data;
using JustDoItApi.Entities;
using JustDoItApi.Interfaces;
using JustDoItApi.Models.Zadachi;
using Microsoft.EntityFrameworkCore;

namespace JustDoItApi.Services;

public class ZadachiService(AppDbContext context, 
    IMapper mapper, IImageService imageService,
    IIdentityService identityService) : IZadachiService
{
    public async Task<ZadachaItemModel> CreateZadachyAsync(ZadachaCreateModel model)
    {
        var userId = await identityService.GetUserIdAsync();
        var zadachaEntity = mapper.Map<ZadachaEntity>(model);

        zadachaEntity.Image = await imageService.SaveImageAsync(model.Image!);
        zadachaEntity.UserId = userId;

        context.Zadachi.Add(zadachaEntity);
        await context.SaveChangesAsync();

        var zadachaModel = mapper.Map<ZadachaItemModel>(zadachaEntity);
        return zadachaModel;
    }

    public async Task<bool> DeleteRangeZadachiAsync(List<long> ids)
    {
        var userId = await identityService.GetUserIdAsync();
        var zadachiEntities = context.Zadachi
            .Where(x => x.UserId == userId)
            .Where(x => ids.Contains(x.Id)).ToList();
        if (zadachiEntities.Count == 0)
        {
            return false;
        }

        foreach (var zadachaEntity in zadachiEntities)
        {
            await imageService.DeleteImageAsync(zadachaEntity.Image);
        }

        context.Zadachi.RemoveRange(zadachiEntities);
        await context.SaveChangesAsync();
        return true;
    }

    public async Task<bool> DeleteZadachyAsync(long id)
    {
        var userId = await identityService.GetUserIdAsync();
        var zadachaEntity = await context.Zadachi
            .Where(x => x.UserId == userId)
            .FirstOrDefaultAsync(x => x.Id == id);
        if (zadachaEntity == null)
        {
            return false;
        }

        await imageService.DeleteImageAsync(zadachaEntity.Image);

        context.Zadachi.Remove(zadachaEntity);
        await context.SaveChangesAsync();
        return true;
    }

    public async Task<IEnumerable<ZadachaItemModel>> GetAllAsync()
    {
        var userId = await identityService.GetUserIdAsync();
        var zadachy = await context.Zadachi
            .Where(x => x.UserId == userId)
            .ToListAsync();
        var zadachyModels = mapper.Map<IEnumerable<ZadachaItemModel>>(zadachy);
        return zadachyModels;
    }

    public async Task<bool> UpdateZadachyAsync(ZadachaUpdateModel model)
    {
        var userId = await identityService.GetUserIdAsync();
        var zadachaEntity = context.Zadachi
            .Where(x => x.UserId == userId)
            .FirstOrDefault(x => x.Id == model.Id);
        if (zadachaEntity == null)
        {
            return false;
        }
        zadachaEntity = mapper.Map(model, zadachaEntity);
        if (model.Image != null)
        {
            await imageService.DeleteImageAsync(zadachaEntity.Image);
            zadachaEntity.Image = await imageService.SaveImageAsync(model.Image);
        }
        context.Zadachi.Update(zadachaEntity);
        await context.SaveChangesAsync();
        return true;
    }
}