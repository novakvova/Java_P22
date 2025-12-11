using JustDoItApi.Entities;
using Microsoft.EntityFrameworkCore;

namespace JustDoItApi.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    public DbSet<ZadachaEntity> Zadachi { get; set; }
}