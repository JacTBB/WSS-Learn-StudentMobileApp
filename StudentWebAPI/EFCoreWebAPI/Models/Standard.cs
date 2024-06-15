using System;
using System.Collections.Generic;

namespace EFCoreWebAPI.Models
{
    public partial class Standard
    {
        public Standard()
        {
            Students = new HashSet<Student>();
            Teachers = new HashSet<Teacher>();
        }

        public int StandardId { get; set; }
        public string? StandardName { get; set; }
        public string? Description { get; set; }

        public virtual ICollection<Student> Students { get; set; }
        public virtual ICollection<Teacher> Teachers { get; set; }
    }
}
