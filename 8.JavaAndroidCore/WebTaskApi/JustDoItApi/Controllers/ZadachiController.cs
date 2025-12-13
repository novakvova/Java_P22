using JustDoItApi.Interfaces;
using JustDoItApi.Models.Zadachi;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;

namespace JustDoItApi.Controllers;

[ApiController]
[Route("[controller]")]
public class ZadachiController(IZadachiService zadachiService) : ControllerBase
{

    [HttpGet()]
    public async Task<IActionResult> Get()
    {
        var items = await zadachiService.GetAllAsync();

        return Ok(items);
    }

    [HttpPost()]
    public async Task<IActionResult> Post([FromForm] ZadachaCreateModel model)
    {
        var res = await zadachiService.CreateZadachyAsync(model);
        return Ok(res);
    }
}
