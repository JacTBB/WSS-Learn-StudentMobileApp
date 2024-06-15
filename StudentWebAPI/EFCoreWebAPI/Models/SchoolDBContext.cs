using System;
using System.Collections.Generic;
using EFCoreWebAPI.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace EFCoreWebAPI.Models
{
    public partial class SchoolDBContext : DbContext
    {
        public SchoolDBContext()
        {
        }

        public SchoolDBContext(DbContextOptions<SchoolDBContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Course> Courses { get; set; } = null!;
        public virtual DbSet<Standard> Standards { get; set; } = null!;
        public virtual DbSet<Student> Students { get; set; } = null!;
        public virtual DbSet<StudentAddress> StudentAddresses { get; set; } = null!;
        public virtual DbSet<Teacher> Teachers { get; set; } = null!;
        public virtual DbSet<ViewStudentCourse> ViewStudentCourses { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                optionsBuilder.UseSqlServer("Server=JACKIE-LAPTOP; Database=SchoolDB; Trusted_Connection=true;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Course>(entity =>
            {
                entity.ToTable("Course");

                entity.Property(e => e.CourseName)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.HasOne(d => d.Teacher)
                    .WithMany(p => p.Courses)
                    .HasForeignKey(d => d.TeacherId)
                    .OnDelete(DeleteBehavior.Cascade)
                    .HasConstraintName("FK_Course_Teacher");
            });

            modelBuilder.Entity<Standard>(entity =>
            {
                entity.ToTable("Standard");

                entity.Property(e => e.Description)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.StandardName)
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Student>(entity =>
            {
                entity.ToTable("Student");

                entity.Property(e => e.StudentId).HasColumnName("StudentID");

                entity.Property(e => e.StudentName)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.HasOne(d => d.Standard)
                    .WithMany(p => p.Students)
                    .HasForeignKey(d => d.StandardId)
                    .OnDelete(DeleteBehavior.Cascade)
                    .HasConstraintName("FK_Student_Standard");

                entity.HasMany(d => d.Courses)
                    .WithMany(p => p.Students)
                    .UsingEntity<Dictionary<string, object>>(
                        "StudentCourse",
                        l => l.HasOne<Course>().WithMany().HasForeignKey("CourseId").OnDelete(DeleteBehavior.ClientSetNull).HasConstraintName("FK_StudentCourse_Course"),
                        r => r.HasOne<Student>().WithMany().HasForeignKey("StudentId").HasConstraintName("FK_StudentCourse_Student"),
                        j =>
                        {
                            j.HasKey("StudentId", "CourseId");

                            j.ToTable("StudentCourse");
                        });
            });

            modelBuilder.Entity<StudentAddress>(entity =>
            {
                entity.HasKey(e => e.StudentId);

                entity.ToTable("StudentAddress");

                entity.Property(e => e.StudentId)
                    .ValueGeneratedNever()
                    .HasColumnName("StudentID");

                entity.Property(e => e.Address1)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.Address2)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.City)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.State)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.HasOne(d => d.Student)
                    .WithOne(p => p.StudentAddress)
                    .HasForeignKey<StudentAddress>(d => d.StudentId)
                    .HasConstraintName("FK_StudentAddress_Student");
            });

            modelBuilder.Entity<Teacher>(entity =>
            {
                entity.ToTable("Teacher");

                entity.Property(e => e.StandardId).HasDefaultValueSql("((0))");

                entity.Property(e => e.TeacherName)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.HasOne(d => d.Standard)
                    .WithMany(p => p.Teachers)
                    .HasForeignKey(d => d.StandardId)
                    .OnDelete(DeleteBehavior.Cascade)
                    .HasConstraintName("FK_Teacher_Standard");
            });

            modelBuilder.Entity<ViewStudentCourse>(entity =>
            {
                entity.HasNoKey();

                entity.ToView("View_StudentCourse");

                entity.Property(e => e.CourseName)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.StudentId).HasColumnName("StudentID");

                entity.Property(e => e.StudentName)
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
