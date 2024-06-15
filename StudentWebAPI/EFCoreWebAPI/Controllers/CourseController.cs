using EFCoreWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace EFCoreWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CourseController : ControllerBase
    {
        // GET: api/<CourseController>
        [HttpGet]
        public IActionResult Get()
        {
            using (SchoolDBContext context = new SchoolDBContext())
            {
                try
                {
                    Course[] courses = context.Courses.Include(s => s.Students).ToArray();
                    CourseView[] coursesView = new CourseView[courses.Length];
                    int i = 0;
                    foreach (Course c in courses)
                    {
                        CourseView cv = new CourseView();
                        cv.CourseId = c.CourseId;
                        cv.CourseName = c.CourseName;
                        cv.TeacherId = c.TeacherId;
                        cv.Students = c.Students.Select(s => s.StudentId).ToList();
                        coursesView[i] = cv;
                        i = i + 1;
                    }
                    return Ok(coursesView);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    return NotFound("An error has occured. Unable to load courses");
                }
            }
        }
    }
}