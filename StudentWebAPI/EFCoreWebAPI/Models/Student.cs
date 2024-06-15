using System;
using System.Collections.Generic;

namespace EFCoreWebAPI.Models
{
    public partial class Student
    {
        public Student()
        {
            Courses = new HashSet<Course>();
        }

        public int StudentId { get; set; }
        public string? StudentName { get; set; }
        public int? StandardId { get; set; }

        public virtual Standard? Standard { get; set; }
        public virtual StudentAddress? StudentAddress { get; set; }

        public virtual ICollection<Course> Courses { get; set; }
    }
}
