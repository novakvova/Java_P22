using AutoMapper;
using JustDoItApi.Entities;
using JustDoItApi.Models.Zadachi;

namespace JustDoItApi.Mapper;

public class ZadachaMapper : Profile
{
    public ZadachaMapper()
    {
        CreateMap<ZadachaItemModel, ZadachaEntity>().ReverseMap();
        CreateMap<ZadachaCreateModel, ZadachaEntity>()
                .ForMember(dest => dest.Image, opt => opt.Ignore());
    }
}
