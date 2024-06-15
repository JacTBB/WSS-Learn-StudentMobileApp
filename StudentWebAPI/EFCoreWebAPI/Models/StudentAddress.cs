using System;
using System.Collections.Generic;

namespace EFCoreWebAPI.Models
{
    public partial class StudentAddress
    {
        public int StudentId { get; set; }
        public string Address1 { get; set; } = null!;
        public string? Address2 { get; set; }
        public string City { get; set; } = null!;
        public string State { get; set; } = null!;

        public virtual Student Student { get; set; } = null!;
    }
}
