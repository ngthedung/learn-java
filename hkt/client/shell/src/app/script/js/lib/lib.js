Server = {
  POST: function(request) {
    var response = client.POST(JSON.stringify(request)) ;
    return JSON.parse(response) ;
  },

  GET: function(request) {
    var response = client.GET(JSON.stringify(request)) ;
    return JSON.parse(response) ;
  }
};

Console = {
  h1: function(header) {
    println('\n\n') ;
    println(header) ;
    println('===================================================================') ;
  },

  h2: function(header) {
    println('\n') ;
    println(header) ;
    println('-------------------------------------------------------------------');
  },

  h3: function(header) {
    println('###' + header + '###')  ;
  },

  println: function(msg) {
    println(msg)  ;
  }
} ;

Assert = {
  notNull: function(msg, obj) {
    if(obj == null) {
      throw new Exception(msg) ;
    }
  },

  isNull: function(msg, obj) {
    if(obj != null) {
      throw new Exception(msg) ;
    }
  },

  equals: function(msg, o1, o2) {
    if(o1 != o2) {
      throw new Exception(msg) ;
    }
  }
} ;
