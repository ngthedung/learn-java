define([
  'jquery',
  'service/service',
  'test/Test',
  'test/Site',
  'test/Assert'
], function($, service, test, Site, Assert) {
  
  var CleanDB = new test.UnitTask({
    name: "CleanSchoolModule",
    description: "Drop all the data in the school module",
    units: [
      function() { service.SchoolService.cleanSchoolDB() ; }
    ]
  });
  
  var TeacherApiCRUDTask = new test.UnitTask({
    name: "TeacherApiCRUDTask",
    description: "test call crud a teacher",
    units: [
      function() {
        console.log("TeacherApiCRUDTask start!!") ;
        var teacher = { 
          loginId: "dung.teacher", firstName: "HKT", lastName: "HKT"
        } ;
        service.SchoolService.saveTeacher(teacher) ;
        
        res = service.SchoolService.getAllTeacher() ;
        Assert.assertEquals(4, res.data.length);
        
        teacher = service.SchoolService.getTeacherByLoginId("dung.teacher").data ;
        Assert.assertEquals("HKT", teacher.firstName) ;
        
        teacher.firstName = "update" ;
        teacher = service.SchoolService.saveTeacher(teacher).data ;
        Assert.assertEquals("update", teacher.firstName) ;
        
        res = service.SchoolService.deleteTeacherByLoginId("dung.teacher") ;
        Assert.assertTrue(res.data) ;
        
        res = service.SchoolService.getTeacherByLoginId("dung.teacher") ;
        Assert.assertNull(res.data) ;
        console.log("TeacherApiCRUDTask: Finish!!!") ;
      }
    ]
  });
  
  var StudentApiCRUDTask = new test.UnitTask({
    name: "StudentApiCRUDTask",
    description: "test call crud a student",
    units: [
      function() {
        console.log("StudentApiCRUDTask start!!") ;
        var student = { 
          loginId: "hkt123", firstName: "HKT", lastName: "HKT"
        } ;
        service.SchoolService.saveStudent(student) ;
        
        var result = service.SchoolService.searchStudent(null).data ;
        var students = result.data ;
        Assert.assertEquals(6, students.length) ;
        
        student = service.SchoolService.getStudentByLoginId("hkt123").data ;
        Assert.assertEquals("HKT", student.firstName) ;
        
        student.firstName = "update" ;
        student = service.SchoolService.saveStudent(student).data ;
        Assert.assertEquals("update", student.firstName) ;
        
        res = service.SchoolService.deleteStudentByLoginId("hkt123") ;
        Assert.assertTrue(res.data) ;
        
        res = service.SchoolService.getStudentByLoginId("hkt123") ;
        Assert.assertNull(res.data) ;
        console.log("StudentApiCRUDTask: Finish!!!") ;
      }
    ]
  });

  var CourseApiCRUDTask = new test.UnitTask({
    name: "CourseApiCRUDTask",
    description: "test call crud a course",
    units: [
      function() {
        console.log("CourseApiCRUDTask start!!") ;
        var course = { 
          code: "history101", name: "History"
        } ;
        service.SchoolService.saveCourse(course) ;
        
        res = service.SchoolService.getAllCourse() ;
        Assert.assertEquals(4, res.data.length) ;
        
        course = service.SchoolService.getCourseByCode("history101").data ;
        Assert.assertEquals("History", course.name) ;
        
        course.name = "update" ;
        course = service.SchoolService.saveCourse(course).data ;
        Assert.assertEquals("update", course.name) ;
        
        res = service.SchoolService.deleteCourseByCode("history101") ;
        Assert.assertTrue(res.data) ;
        
        res = service.SchoolService.getCourseByCode("history101") ;
        Assert.assertNull(res.data) ;
        console.log("CourseApiCRUDTask: Finish!!!") ;
      }
    ]
  });
  
  var SessionApiCRUDTask = new test.UnitTask({
    name: "SessionApiCRUDTask",
    description: "test call crud a session",
    units: [
      function() {
        console.log("SessionApiCRUDTask start!!") ;
        var session = {
          sessionId: "math101-012014", courseCode: "math101", teacherLoginId: "thuy.teacher"
        } ;
        session = service.SchoolService.saveSession(session).data ;
        Assert.assertNotNull(session) ;
        
        session = service.SchoolService.getSessionBySessionId("math101-012014").data ;
        Assert.assertEquals("math101", session.courseCode) ;
        
        session.teacherLoginId = "minh.teacher" ;
        session = service.SchoolService.saveSession(session).data ;
        Assert.assertEquals("minh.teacher", session.teacherLoginId) ;
        
        res = service.SchoolService.deleteSessionBySessionId("math101-012014") ;
        Assert.assertTrue(res.data) ;
        
        res = service.SchoolService.getSessionBySessionId("math101-012014") ;
        Assert.assertNull(res.data) ;
        console.log("SessionApiCRUDTask: Finish!!!") ;
      }
    ]
  });
  
  var RegistrationApiCRUDTask = new test.UnitTask({
    name: "RegistrationApiCRUDTask",
    description: "test call crud a registration",
    units: [
      function() {
        console.log("RegistrationApiCRUDTask start!!") ;
        var registration = {
          courseSessionId: "math101-092013", studentLoginId: "ngoc.student", status: "REGISTERED"
        } ;
        registration = service.SchoolService.saveRegistration(registration).data ;
        
        res = service.SchoolService.findRegistrationBySessionId("math101-092013") ;
        Assert.assertEquals(1, res.data.length) ;
        
        registration.status = "ACCEPTED" ;
        registration = service.SchoolService.saveRegistration(registration).data ;
        Assert.assertEquals("ACCEPTED", registration.status) ;
        
        res = service.SchoolService.deleteSessionRegistration(registration) ;
        Assert.assertTrue(res.data) ;
        
        res = service.SchoolService.findRegistrationBySessionId("math101-092013") ;
        Assert.assertEquals(0, res.data.length) ;
        console.log("RegistrationApiCRUDTask: Finish!!!") ;
      }
    ]
  });
  
  var GotoSchoolStudentsScreen = new test.UnitTask({
    name: "GotoSchoolStudentsScreen",
    description: "Go to School Student Screen",
    units: [
      function() { Site.Navigation.clickMenuItem("school", "Students") ; }
    ]
  });
  
  var CreateStudent = new test.UnitTask({
    name: "CreateStudent",
    description: "Create Student",
    units: [
      function() { Site.Workspace.tableToolbar("Login Id").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Login Id:") ;
        form.inputVal("loginId", "thang.student") ;
        form.inputVal("firstName", "Minh") ;
        form.inputVal("lastName",  "Nguyen Dinh") ;
      },
      function() { Site.PopupPanel.formWithText("Login Id:").clickButton("Save") ; }
    ]
  });
  
  var SearchStudent = new test.UnitTask({
    name: "SearchStudent",
    description: "Search student",
    units: [
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('loginId', "minh123*") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("Filter"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('loginId', "") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Filter Option"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("First Name") ;
        controlGroup.inputVal('firstName', "Minh*") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("Filter"); } ,
    ]
  });
  
  var EditStudent = new test.UnitTask({
    name: "EditStudent",
    description: "Edit Student",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang.student") ;
        row.clickLink("thang.student") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Login Id") ;
        form.inputVal("firstName", "Minh (update)") ;
        form.inputVal("lastName",  "Nguyen Dinh (update)") ;
      },
      function() {
        Site.PopupPanel.formWithText("Login Id").clickButton("Save") ;
      },
      function() {
        Site.Workspace.tableToolbar('Login Id').clickButton("Refresh");
      }
    ]
  });
  
  var DeleteStudent = new test.UnitTask({
    name: "DeleteStudent",
    description: "Delete Student",
    units: [
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Filter Option"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Last Name") ;
        controlGroup.inputVal('lastName', "Nguyen*") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("Filter"); } ,
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row = table.tableRowWithText("thang.student") ;
        row.clickButton("Delete") ;
      } ,
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbar('Login Id').clickButton("Refresh"); } ,
    ]
  });
  
  var CreateRegistrationManhStudent = new test.UnitTask({
    name: "CreateRegistrationManhStudent",
    description: "Create Registration Manh Student",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row = table.tableRowWithText("manh.student") ;
        row.clickButton("Registration Session") ;
      },
      function() { Site.Workspace.tableToolbar("Session Id").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Student LoginId") ;
        form.inputVal("courseSessionId", "chemistry101-092013") ;
        form.selectVal("status", "REGISTERED") ;
      },
      function() { Site.PopupPanel.formWithText("Student LoginId").clickButton("Save") ; }
    ]
  });
  
  var SearchRegistrationStudent = new test.UnitTask({
    name: "SearchRegistrationStudent",
    description: "Search Registration Student",
    units: [
      function() { Site.Workspace.tableToolbars('Session Id').clickButton("More Toolbars") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Student Id") ;
        controlGroup.inputVal('studentLoginId', "manh.student*") ;
      },
      function() { Site.Workspace.tableToolbars('Student Id').clickButton("Filter") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Student Id") ;
        controlGroup.inputVal('studentLoginId', "") ;
      },
      function() { Site.Workspace.tableToolbars('Session Id').clickButton("More Filter Option") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Session Id") ;
        controlGroup.inputVal('courseSessionId', "che*") ;
      },
      function() { Site.Workspace.tableToolbars('Student Id').clickButton("Filter"); } ,
    ]
  });
  
  var EditRegistrationManhStudent = new test.UnitTask({
    name: "EditRegistrationManhStudent",
    description: "Edit Registration Manh Student",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row = table.tableRowWithText("chemistry101-092013") ;
        row.clickButton("Edit Registration") ;
      },
      
      function() {
        var form = Site.PopupPanel.formWithText("Student LoginId") ;
        form.selectVal("status", "ACCEPTED") ;
      },
      
      function() { Site.PopupPanel.formWithText("Student LoginId").clickButton("Save") ; },
      function() { Site.Workspace.tableToolbar("Session Id").clickButton("Refresh") ; },
    ]
  });
  
  var DeleteRegistrationManhStudent = new test.UnitTask({
    name: "DeleteRegistrationManhStudent",
    description: "Delete Registration Manh Student",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row = table.tableRowWithText("chemistry101-092013") ;
        row.clickButton("Delete Registration") ;
      },
    ]
  });
  
  var BackStudentsScreen = new test.UnitTask({
    name: "Back Students Screen",
    description: "Back Students Screen",
    units: [
      function() { Site.Workspace.toolbarWith("Students").clickButton("Students") ; },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row = table.tableRowWithText("manh.student") ;
        row.clickLink("manh.student") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Login Id") ;
        form.inputVal('firstName', "Manh") ;
        form.inputVal('lastName', "Nguyen Van") ;
      },
      function() {
        Site.PopupPanel.formWithText("Login Id").clickButton("Save") ;
      },
    ]
  });
  
  var GotoSchoolTeachersScreen = new test.UnitTask({
    name: "GotoSchoolTeachersScreen",
    description: "Go to School Teacher Screen",
    units: [
      function() { Site.Navigation.clickMenuItem("school", "Teachers") ; }
    ]
  });
  
  var CreateLongTeacher = new test.UnitTask({
    name: "CreateLongTeacher",
    description: "Create Long Teacher",
    units: [
      function() { Site.Workspace.tableToolbar("Login Id").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("First Name") ;
        form.inputVal("loginId", "thuy.teacher") ;
        form.inputVal("firstName", "Long") ;
        form.inputVal("lastName",  "Nguyen") ;
      },
      function() { Site.PopupPanel.formWithText("First Name:").clickButton("Save") ; }
    ]
  });
  
  var SearchTeacher = new test.UnitTask({
    name: "SearchTeacher",
    description: "Search Long Teacher",
    units: [
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('beanFilterField', "thuy") ;
      },
    ]
  });
  
  var EditLongTeacher = new test.UnitTask({
    name: "EditLongTeacher",
    description: "Edit Long Teacher",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thuy.teacher") ;
        row.clickLink("thuy.teacher") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Login Id") ;
        form.inputVal("firstName", "Long (update)") ;
        form.inputVal("lastName",  "Nguyen (update)") ;
      },
      function() {
        Site.PopupPanel.formWithText("Login Id").clickButton("Save") ;
      },
    ]
  });
  
  var DeleteLongTeacher = new test.UnitTask({
    name: "DeleteLongTeacher",
    description: "Delete Long Teacher",
    units: [
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('beanFilterField', "thuy") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row = table.tableRowWithText("thuy.teacher") ;
        row.clickButton("Delete") ;
      },
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('beanFilterField', "") ;
      },
    ]
  });
  
  var CreateSessionTeacher = new test.UnitTask({
    name: "CreateSessionTeacher",
    description: "Create Session Teacher",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row = table.tableRowWithText("huonganh.teacher") ;
        row.clickButton("Course Session") ;
      },
      
      function() { Site.Workspace.tableToolbar("Session Id").clickButton("New") ; },
      
      function() {
        var form = Site.PopupPanel.formWithText("Teacher LoginId") ;
        form.inputVal("sessionId", "math101-012014") ;
        form.inputVal("courseCode", "math101") ;
      },
      
      function() {
        Site.PopupPanel.formWithText("Teacher LoginId").clickButton("Save") ;
      }
    ]
  });
  
  var SearchCourseSession = new test.UnitTask({
    name: "SearchCourseSession",
    description: "Search Course Session",
    units: [
      function() { Site.Workspace.tableToolbars('Session Id').clickButton("More Toolbars") ; } ,
      function() { Site.Workspace.tableToolbars('Teacher Id').clickButton("More Filter Option") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Session Id") ;
        controlGroup.inputVal('sessionId', "math101*") ;
      },
      function() { Site.Workspace.tableToolbars('Teacher Id').clickButton("Filter") ; } ,
    ]
  });
  
  var EditSessionTeacher = new test.UnitTask({
    name: "EditSessionTeacher",
    description: "Edit Session Teacher",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row = table.tableRowWithText("math101-012014") ;
        row.clickLink("math101-012014") ;
      },
      
      function() {
        var form = Site.PopupPanel.formWithText("Teacher LoginId") ;
        form.inputVal("courseCode", "math2014") ;
      },
      
      function() {
        Site.PopupPanel.formWithText("Teacher LoginId").clickButton("Save") ;
      }
    ]
  });
  
  var DeleteSessionTeacher = new test.UnitTask({
    name: "DeleteSessionTeacher",
    description: "Delete Session Teacher",
    units: [
      function() { Site.Workspace.tableToolbars('Session Id').clickButton("More Toolbars") ; } ,
      function() { Site.Workspace.tableToolbars('Teacher Id').clickButton("More Filter Option") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Course Code") ;
        controlGroup.inputVal('courseCode', "*2014*") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row = table.tableRowWithText("math101-012014") ;
        row.clickButton("Delete Session") ;
      },
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Course Code") ;
        controlGroup.inputVal('courseCode', "") ;
      },
      function() {
        Site.Workspace.tableToolbars('Teacher Id').clickButton("More Toolbars") ;
        Site.Workspace.tableToolbars('Session Id').clickButton("Refresh") ;
      }
    ]
  });
  
  var CreateRegistrationTeacher = new test.UnitTask({
    name: "CreateRegistrationTeacher",
    description: "Create Registration Teacher",
    units: [
      function () {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row = table.tableRowWithText("physic101-012013") ;
        row.clickButton("Registrations") ;
      },
      function() { Site.Workspace.tableToolbar("Session Id").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Course SessionId") ;
        form.inputVal("studentLoginId", "long.student") ;
        form.selectVal("status",  "REGISTERED") ;
      },
      function() { Site.PopupPanel.formWithText("Course SessionId").clickButton("Save") ; }
    ]
  });
  
  var EditRegistrationTeacher = new test.UnitTask({
    name: "EditRegistration Teacher",
    description: "Edit Registration Teacher",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Student Id") ;
        var row = table.tableRowWithText("long.student") ;
        row.clickButton("Edit Registration") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Course SessionId") ;
        form.selectVal("status", "ACCEPTED") ;
      },
      function() { Site.PopupPanel.formWithText("Course SessionId").clickButton("Save") ; }
    ]
  });
  
  var DeleteRegistrationTeacher = new test.UnitTask({
    name: "DeleteRegistrationTeacher",
    description: "Delete Registration Teacher",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Student Id") ;
        var row = table.tableRowWithText("long.student") ;
        row.clickButton("Delete Registration") ;
      }
    ]
  });
  
  var BackTeachersScreen = new test.UnitTask({
    name: "BackTeachersScreen",
    description: "Back Teachers Screen",
    units: [
      function() {
        Site.Workspace.toolbarWith("Teachers").clickButton("Course Sessions") ;
      },
      function() {
        Site.Workspace.toolbarWith("Teachers").clickButton("Teachers") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row = table.tableRowWithText("huonganh.teacher") ;
        row.clickLink("huonganh.teacher") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Login Id") ;
        form.inputVal('firstName', "Huong Anh") ;
        form.inputVal('lastName', "Nguyen") ;
      },
      function() {
        Site.PopupPanel.formWithText("Login Id").clickButton("Save") ;
      }
    ]
  });
  
  var GotoSchoolCoursesScreen = new test.UnitTask({
    name: "GotoSchoolCoursesScreen",
    description: "Go to School Course Screen",
    units: [
      function() { Site.Navigation.clickMenuItem("school", "Courses") ; }
    ]
  });
  
  var CreateHistoryCourse = new test.UnitTask({
    name: "CreatehHistoryCourse",
    description: "Create History Teacher",
    units: [
      function() { Site.Workspace.tableToolbar("Code").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Code") ;
        form.inputVal("code", "history101") ;
        form.inputVal("name",  "History") ;
      },
      function() { Site.PopupPanel.formWithText("Code").clickButton("Save") ; }
    ]
  });
  
  var EditHistoryCourser = new test.UnitTask({
    name: "EditHistoryCourser",
    description: "Edit History Courser",
    units: [
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Code") ;
        controlGroup.inputVal('beanFilterField', "history") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Code") ;
        var row   = table.tableRowWithText("history101") ;
        row.clickLink("history101") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Code") ;
        form.inputVal("name", "History (update)") ;
      },
      function() {
        Site.PopupPanel.formWithText("Code").clickButton("Save") ;
      }
    ]
  });
  
  var DeleteHistoryCourser = new test.UnitTask({
    name: "DeleteHistoryCourser",
    description: "Delete History Courser",
    units: [
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Code") ;
        controlGroup.inputVal('beanFilterField', "history") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Code") ;
        var row = table.tableRowWithText("history101") ;
        row.clickButton("Delete") ;
      },
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Code") ;
        controlGroup.inputVal('beanFilterField', "") ;
        Site.Workspace.toolbarWith("Code").clickButton("Refresh") ;
      }
    ]
  });
  
  var CreateSession = new test.UnitTask({
    name: "CreateSession",
    description: "Create Session",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Code") ;
        var row   = table.tableRowWithText("math101") ;
        row.clickButton("Course Sessions") ;
      },
      function() { Site.Workspace.tableToolbar("Session Id").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Session Id") ;
        form.inputVal("sessionId", "math101-012014") ;
        form.inputVal("teacherLoginId",  "thuy.teacher") ;
      },
      function() { Site.PopupPanel.formWithText("Session Id").clickButton("Save") ; }
    ]
  });
  
  var EditSession = new test.UnitTask({
    name: "EditSession",
    description: "Edit Session",
    units: [
      function() { Site.Workspace.tableToolbars('Session Id').clickButton("More Toolbars") ; } ,
      function() { Site.Workspace.tableToolbars('Teacher Id').clickButton("More Filter Option") ; } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('courseCode', "math101*") ;
      },
      function() { Site.Workspace.tableToolbars('Teacher Id').clickButton("Filter") ; } ,
      function() {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row   = table.tableRowWithText("math101-012014") ;
        row.clickLink("math101-012014") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Session Id") ;
        form.inputVal("teacherLoginId",  "minh.teacher") ;
      },
      function() {
        Site.PopupPanel.formWithText("Session Id").clickButton("Save") ;
      }
    ]
  });
  
  var DeleteSession = new test.UnitTask({
    name: "DeleteSession",
    description: "Delete Session",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row = table.tableRowWithText("math101-012014") ;
        row.clickButton("Delete") ;
      },
      function() { Site.Workspace.tableToolbars('Teacher Id').clickButton("More Toolbars") ; } ,
      function() { Site.Workspace.tableToolbar('Session Id').clickButton("Refresh") ; }
    ]
  });
  
  var CreateRegistration = new test.UnitTask({
    name: "CreateRegistration",
    description: "Create Registration",
    units: [
      function () {
        var table = Site.Workspace.tableWithHeader("Session Id") ;
        var row   = table.tableRowWithText("math101-012013") ;
        row.clickButton("Registrations") ;
      },
      function() { Site.Workspace.tableToolbar("Session Id").clickButton("New") ; },
      function() {
        var form = Site.PopupPanel.formWithText("Course SessionId") ;
        form.inputVal("studentLoginId", "minh.student") ;
        form.selectVal("status",  "REGISTERED") ;
      },
      function() { Site.PopupPanel.formWithText("Course SessionId").clickButton("Save") ; }
    ]
  });
  
  var EditRegistration = new test.UnitTask({
    name: "EditRegistration",
    description: "Edit Registration",
    units: [
      function() { Site.Workspace.tableToolbars("Session Id").clickButton("More Toolbars") ; },
      function() { Site.Workspace.tableToolbars("Student Id").clickButton("More Filter Option") ; },
      function() {
        var searchSession = Site.Workspace.tableToolbar("Session Id") ;
        searchSession.inputVal('courseSessionId', "math*") ;
        var searchStatus = Site.Workspace.tableToolbar("Status") ;
        searchStatus.inputVal('status', "REGISTERED") ;
        Site.Workspace.tableToolbars("Student Id").clickButton("Filter") ; 
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Student Id") ;
        var row   = table.tableRowWithText("manh.student") ;
        row.clickButton("Edit Registration") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Course SessionId") ;
        form.selectVal("status", "ACCEPTED") ;
      },
      function() { Site.PopupPanel.formWithText("Course SessionId").clickButton("Save") ; }
    ]
  });
  
  var DeleteRegistration = new test.UnitTask({
    name: "DeleteRegistration",
    description: "Delete Registration",
    units: [
      function() { Site.Workspace.tableToolbars("Session Id").clickButton("More Toolbars") ; },
      function() { Site.Workspace.tableToolbars("Student Id").clickButton("More Filter Option") ; },
      function() {
        var searchSession = Site.Workspace.tableToolbar("Session Id") ;
        searchSession.inputVal('courseSessionId', "math*") ;
        var searchStatus = Site.Workspace.tableToolbar("Status") ;
        searchStatus.inputVal('status', "ACCEPTED") ;
        Site.Workspace.tableToolbars("Student Id").clickButton("Filter") ; 
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Student Id") ;
        var row = table.tableRowWithText("manh.student") ;
        row.clickButton("Delete Registration") ;
      },
    ]
  });
  
  var BackCoursesScreen = new test.UnitTask({
    name: "BackCoursesScreen",
    description: "Back Courses Screen",
    units: [
      function() {
        Site.Workspace.toolbarWith("Courses").clickButton("Course Sessions") ;
      },
      function() {
        Site.Workspace.toolbarWith("Courses").clickButton("Courses") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Code") ;
        var row = table.tableRowWithText("math101") ;
        row.clickLink("math101") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Code") ;
        form.inputVal("name", "Math") ;
      },
      function() {
        Site.PopupPanel.formWithText("Code").clickButton("Save") ;
      }
    ]
  });
  
  var school = {
    
    module: 'school',
    
    CleanDB: CleanDB,
    
    createScenario: function(name) {
      var Scenario = new test.UnitTask({
        name: "SchoolScenario " + name,
        description: "Create a minimum set of data for the module",
        units: [
          function() { 
            var jsonRes = 'scenario/' + name + '/school.json' ;
            var data = service.Server.syncGETResource(jsonRes, 'json') ;
            if(data != null) { 
              service.SchoolService.createScenario(data) ;
            } else {
              console.log("No data for " + jsonRes) ;
            }
          }
        ]
      }); 
      return Scenario ;
    },
    
    Service: {
      api: [ TeacherApiCRUDTask, StudentApiCRUDTask, CourseApiCRUDTask, SessionApiCRUDTask, RegistrationApiCRUDTask ]
    },
    
    UI: {
      Student: test.Suite.extend({
        name: 'Student' ,
        description: "create/update/delete/find student" ,
        unitTasks: [ 
//          GotoSchoolStudentsScreen, CreateStudent, SearchStudent, EditStudent, DeleteStudent,
//          CreateRegistrationManhStudent, SearchRegistrationStudent, EditRegistrationManhStudent, 
//          DeleteRegistrationManhStudent,
//          BackStudentsScreen
        ],
      }),
      
      Teacher: test.Suite.extend({
        name: 'Teacher' ,
        description: "create/update/delete/find teacher" ,
        unitTasks: [ 
//          GotoSchoolTeachersScreen,
//          CreateLongTeacher, 
//          SearchTeacher, 
//          EditLongTeacher, DeleteLongTeacher,
//          CreateSessionTeacher, SearchCourseSession, EditSessionTeacher, DeleteSessionTeacher,
//          CreateRegistrationTeacher, EditRegistrationTeacher, DeleteRegistrationTeacher,
//          BackTeachersScreen
        ],
      }),
      
      Course: test.Suite.extend({
        name: 'Course',
        description: "create/update/delete/find course" ,
        unitTasks: [ 
//          GotoSchoolCoursesScreen, CreateHistoryCourse, EditHistoryCourser, DeleteHistoryCourser,
//          CreateSession, EditSession, DeleteSession,
//          CreateRegistration, EditRegistration, DeleteRegistration,
//          BackCoursesScreen
        ],
      }),
    }
  };
  return school ;
});
