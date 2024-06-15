using Microsoft.OpenApi.Any;

namespace EFCoreWebAPI.Models
{
    public class StudentView
    {
        public int StudentId { get; set; }

        private string? studentName;
        public string? ModifiedStudentName { get; set; }
        private int? standardId;
        private string? address2;
        public string? StudentName
        {
            get
            {
                return studentName;
            }
            set
            {
                if (value == null)
                {
                    studentName = "";
                }
                else
                {
                    studentName = value;
                }
            }
        }
        public int? StandardId
        {
            get
            {
                return standardId;
            }
            set
            {
                if (value == null)
                {
                    standardId = 0;
                }
                else
                {
                    standardId = value;
                }
            }
        }
        public string Address1 { get; set; }
        public string? Address2
        {
            get
            {
                return address2;
            }
            set
            {
                if (value == null)
                {
                    address2 = "";
                }
                else
                {
                    address2 = value;
                }
            }
        }
        public string State { get; set; }
        public string City { get; set; }
        public List<int> Courses { get; set; }
    }

}
