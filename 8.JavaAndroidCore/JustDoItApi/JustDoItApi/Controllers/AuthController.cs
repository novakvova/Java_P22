using JustDoItApi.Interfaces;
using JustDoItApi.Models.Auth;
using Microsoft.AspNetCore.Mvc;

namespace JustDoItApi.Controllers;

[ApiController]
[Route("[controller]")]
public class AuthController(IAuthService authService) : ControllerBase
{
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] LoginModel model)
    {
        string result = await authService.LoginAsync(model);
        if (string.IsNullOrEmpty(result))
        {
            return BadRequest(new
            {
                Status = 400,
                IsValid = false,
                Errors = new { Email = "Невірний логін або пароль" }
            });
        }
        return Ok(new
        {
            Token = result
        });
    }

    [HttpPost("register")]
    public async Task<IActionResult> Register([FromForm] RegisterModel model)
    {
        string result = await authService.RegisterAsync(model);
        if (string.IsNullOrEmpty(result))
        {
            return BadRequest(new
            {
                Status = 400,
                IsValid = false,
                Errors = new { Email = "Щось пішло не так " }
            });
        }
        return Ok(new
        {
            Token = result
        });
    }
}
