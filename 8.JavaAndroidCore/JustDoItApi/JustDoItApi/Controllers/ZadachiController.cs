using JustDoItApi.Interfaces;
using JustDoItApi.Models.Zadachi;
using Microsoft.AspNetCore.Mvc;

namespace JustDoItApi.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ZadachiController(IZadachiService zadachiService) : ControllerBase
{

    [HttpGet()]
    public async Task<IActionResult> Get()
    {
        Thread.Sleep(2000);
        var items = await zadachiService.GetAllAsync();

        return Ok(items);
    }

    [HttpPost()]
    public async Task<IActionResult> Post([FromForm] ZadachaCreateModel model)
    {
        var res = await zadachiService.CreateZadachyAsync(model);
        return Ok(res);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(long id)
    {
        var res = await zadachiService.DeleteZadachyAsync(id);
        if (!res)
        {
            return NotFound();
        }
        return Ok();
    }

    [HttpDelete("range")]
    public async Task<IActionResult> DeleteRange([FromBody] List<long> ids)
    {
        var res = await zadachiService.DeleteRangeZadachiAsync(ids);
        if (!res)
        {
            return NotFound();
        }
        return Ok();
    }

    [HttpPut()]
    public async Task<IActionResult> Put([FromForm] ZadachaUpdateModel model)
    {
        var res = await zadachiService.UpdateZadachyAsync(model);
        if (!res)
        {
            return NotFound();
        }
        return Ok();
    }
}
