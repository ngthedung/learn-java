Dim strComputer, objWMIService, colSettings, objProcessor

strComputer = "."

Set objWMIService = GetObject("winmgmts:" _ 
  & "{impersonationLevel=impersonate,authenticationLevel=Pkt}!\\" _ 
  & strComputer & "\root\cimv2") 

Set colSettings = objWMIService.ExecQuery _
  ("SELECT * FROM Win32_Processor")

For Each objProcessor In colSettings
  WScript.Echo objProcessor.AddressWidth
Next