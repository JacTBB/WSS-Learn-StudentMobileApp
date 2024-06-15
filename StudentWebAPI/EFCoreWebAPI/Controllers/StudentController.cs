using EFCoreWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace EFCoreWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StudentController : ControllerBase
    {
        // GET: api/<StudentController>
        [HttpGet]
        public IActionResult Get()
        {
            using (SchoolDBContext context = new SchoolDBContext())
            {
                try
                {
                    Student[] studentList = context.Students.Include(c => c.Standard).Include(d => d.StudentAddress).Include(sc => sc.Courses).ToArray();
                    StudentView[] studentListView = new StudentView[studentList.Length];
                    int i = 0;
                    foreach (Student s in studentList)
                    {
                        StudentView sv = new StudentView();
                        sv.StudentId = s.StudentId;

                        sv.StudentName = s.StudentName;
                        sv.ModifiedStudentName = s.ModifiedStudentName;
                        sv.StandardId = s.StandardId;
                        sv.Address1 = s.StudentAddress.Address1;
                        sv.Address2 = s.StudentAddress.Address2;
                        sv.State = s.StudentAddress.State;
                        sv.City = s.StudentAddress.City;
                        sv.Courses = s.Courses.Select(c => c.CourseId).ToList();
                        studentListView[i] = sv;
                        i = i + 1;
                    }
                    return Ok(studentListView);
                    //return Ok(context.Students.Include(c => c.Standard).Include(d => d.StudentAddress).ToArray());
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    return NotFound("An error has occured. Unable to load list of students");
                }
            }
        }


        // GET api/<StudentController>/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            using (SchoolDBContext context = new SchoolDBContext())
            {
                try
                {
                    Student s = context.Students.Include(c => c.Standard).Include(d => d.StudentAddress).Include(sc => sc.Courses).SingleOrDefault(s => s.StudentId == id);

                    StudentView sv = new StudentView();
                    sv.StudentId = s.StudentId;

                    sv.StudentName = s.StudentName;
                    sv.ModifiedStudentName = s.ModifiedStudentName;
                    sv.StandardId = s.StandardId;
                    sv.Address1 = s.StudentAddress.Address1;
                    sv.Address2 = s.StudentAddress.Address2;
                    sv.State = s.StudentAddress.State;
                    sv.City = s.StudentAddress.City;
                    sv.Courses = s.Courses.Select(c => c.CourseId).ToList();

                    return Ok(sv);
                }
                catch (Exception)
                {
                    return NotFound("An error has occured. Unable to find student.");
                }
            }
        }

        // POST api/<StudentController>
        [HttpPost]
        public IActionResult Post([FromBody] PostStudentBody newStudentPost)
        {
            using (SchoolDBContext db = new SchoolDBContext())
            {
                try
                {
                    Student newStudent = new Student();
                    newStudent.StudentName = newStudentPost.StudentName;
                    newStudent.StandardId = newStudentPost.StandardId;
                    foreach (int c in newStudentPost.Courses)
                    {
                        newStudent.Courses.Add(db.Courses.FirstOrDefault(course => course.CourseId == c));
                    }
                    db.Students.Add(newStudent);
                    db.SaveChanges();
                    StudentAddress newStudentAddress = new StudentAddress();
                    newStudentAddress.StudentId = newStudent.StudentId;
                    newStudentAddress.Address1 = newStudentPost.Address1;
                    newStudentAddress.Address2 = newStudentPost.Address2;
                    newStudentAddress.City = newStudentPost.City;
                    newStudentAddress.State = newStudentPost.State;
                    newStudent.StudentAddress = newStudentAddress;
                    db.SaveChanges();
                    return Ok(newStudent.StudentId);
                }
                catch (Exception)
                {
                    return BadRequest("Unable to add student. Check request body is in correct format.");
                }
            }
        }

        // PUT api/<StudentController>/5
        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody] PostStudentBody updatedStudent)
        {
            using (SchoolDBContext context = new SchoolDBContext())
            {
                try
                {
                    Student student = context.Students.Include(c => c.Standard).Include(d => d.StudentAddress).Include(sc => sc.Courses).SingleOrDefault(s => s.StudentId == id);

                    student.StudentName = updatedStudent.StudentName;
                    student.StandardId = updatedStudent.StandardId;
                    student.StudentAddress.Address1 = updatedStudent.Address1;
                    student.StudentAddress.Address2 = updatedStudent.Address2;
                    student.StudentAddress.City = updatedStudent.City;
                    student.StudentAddress.State = updatedStudent.State;
                    student.Courses.Clear();
                    foreach (int c in updatedStudent.Courses)
                    {
                        student.Courses.Add(context.Courses.FirstOrDefault(course => course.CourseId == c));
                    }

                    context.Attach(student).State = EntityState.Modified;
                    try
                    {
                        context.SaveChanges();
                    }
                    catch (DbUpdateConcurrencyException)
                    {
                        throw;
                    }
                    return Ok();
                }
                catch (Exception)
                {
                    return NotFound("An error has occured. Unable to find student.");
                }
            }
        }

        // DELETE api/<StudentController>/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            using (SchoolDBContext context = new SchoolDBContext())
            {
                try
                {
                    Student s = context.Students.Include(c => c.Standard).Include(d => d.StudentAddress).SingleOrDefault(s => s.StudentId == id);

                    context.Students.Remove(s);
                    context.SaveChanges();

                    return Ok();
                }
                catch (Exception)
                {
                    return NotFound("An error has occured. Unable to find student.");
                }
            }
        }
    }
}

public class PostStudentBody
{
    public string? StudentName { get; set; }
    public int? StandardId { get; set; }
    public string Address1 { get; set; }
    public string? Address2 { get; set; }
    public string City { get; set; }
    public string State { get; set; }
    public List<int> Courses { get; set; }

    public PostStudentBody() { }
}