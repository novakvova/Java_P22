using AutoMapper;
using JustDoItApi.Entities.Identity;
using JustDoItApi.Models.Auth;

namespace JustDoItApi.Mapper;

public class AuthMapper : Profile
{
    public AuthMapper()
    {
        CreateMap<RegisterModel, UserEntity>()
                .ForMember(x => x.UserName, opt => opt.MapFrom(x => x.Email));
    }
}
