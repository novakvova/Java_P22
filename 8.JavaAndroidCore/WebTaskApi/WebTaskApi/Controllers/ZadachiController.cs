using Microsoft.AspNetCore.Mvc;
using WebTaskApi.Models.Zadacha;

namespace WebTaskApi.Controllers;

[Route("api/[controller]")]
[ApiController]
public class ZadachiController : ControllerBase
{
    [HttpGet]
    public IActionResult GetList()
    {
        var items = new List<ZadachaItemModel>
        {
            new() { Id =  1, Name = "Зробити домашку"},
            new() { Id =  2, Name = "Прибарти в кімнаті"},
            new() { Id =  3, Name = "Піти гуляти з дівчиной"},
        };

        return Ok(items);
    }
}
