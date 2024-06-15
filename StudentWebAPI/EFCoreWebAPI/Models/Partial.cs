namespace EFCoreWebAPI.Models
{
    public partial class Student
    {
        public string ModifiedStudentName
        {
            get { return "Student " + this.StudentName; }
        }
    }
}
