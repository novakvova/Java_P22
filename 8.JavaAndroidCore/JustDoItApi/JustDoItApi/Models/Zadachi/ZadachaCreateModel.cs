using Microsoft.AspNetCore.Mvc;

namespace JustDoItApi.Models.Zadachi;

public class ZadachaCreateModel
{
    public string Name { get; set; } = String.Empty;

    //[FromForm]
    public IFormFile? Image { get; set; }
}
