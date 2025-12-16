namespace JustDoItApi.Models.Zadachi;

public class ZadachaUpdateModel
{
    public long Id { get; set; }
    public string Name { get; set; } = String.Empty;
    public IFormFile? Image { get; set; }
}
