using System;
using System.Collections.Generic;

namespace EFCoreWebAPI.Models
{
    public partial class Teacher
    {
        public Teacher()
        {
            Courses = new HashSet<Course>();
        }

        public int TeacherId { get; set; }
        public string? TeacherName { get; set; }
        public int? StandardId { get; set; }
        public int? TeacherType { get; set; }

        public virtual Standard? Standard { get; set; }
        public virtual ICollection<Course> Courses { get; set; }
    }
}
