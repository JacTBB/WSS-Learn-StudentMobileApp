using System;
using System.Collections.Generic;

namespace EFCoreWebAPI.Models
{
    public partial class Course
    {
        public Course()
        {
            Students = new HashSet<Student>();
        }

        public int CourseId { get; set; }
        public string? CourseName { get; set; }
        public int? TeacherId { get; set; }

        public virtual Teacher? Teacher { get; set; }

        public virtual ICollection<Student> Students { get; set; }
    }
}
