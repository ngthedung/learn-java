ScriptRunner.require('lib/lib.js') ;

var request = {
  requestAtTime: 0,
  module:  'core' ,
  service: 'PingService',
  method:  'hello',
  params: {
    name: "Tuan"
  }
} ;

Console.h1('GET Method') ;
var response = Server.GET(request) ;
Assert.equals("Expect 'Hello Tuan!!!' for the data", 'Hello Tuan!!!', response.data) ;
println(JSON.stringify(response, null, 2)) ;

Console.h1('POST Method') ;
response = Server.POST(request) ;
Assert.equals("Expect 'Hello Tuan!!!' for the data", 'Hello Tuan!!!', response.data) ;
println(JSON.stringify(response, null, 2)) ;
