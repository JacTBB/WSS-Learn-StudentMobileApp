namespace EFCoreWebAPI.Models
{
    public class CourseView
    {
        public int CourseId { get; set; }
        public string? CourseName { get; set; }
        public int? TeacherId { get; set; }
        public List<int> Students { get; set; }
    }

}
