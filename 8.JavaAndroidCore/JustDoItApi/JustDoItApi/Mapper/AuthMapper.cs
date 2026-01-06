using AutoMapper;
using JustDoItApi.Entities.Identity;
using JustDoItApi.Models.Auth;
using JustDoItApi.Models.Seeder;

namespace JustDoItApi.Mapper;

public class AuthMapper : Profile
{
    public AuthMapper()
    {
        CreateMap<RegisterModel, UserEntity>()
                .ForMember(x => x.UserName, opt => opt.MapFrom(x => x.Email));
        CreateMap<SeederUserModel, UserEntity>()
            .ForMember(x => x.UserName, opt => opt.MapFrom(x => x.Email));
    }
}
