using JustDoItApi.Entities;
using JustDoItApi.Entities.Identity;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace JustDoItApi.Data;

public class AppDbContext : IdentityDbContext<
    UserEntity,
    RoleEntity,
    long,
    IdentityUserClaim<long>,
    UserRoleEntity,
    IdentityUserLogin<long>,
    IdentityRoleClaim<long>,
    IdentityUserToken<long>>
{
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    public DbSet<ZadachaEntity> Zadachi { get; set; }
}