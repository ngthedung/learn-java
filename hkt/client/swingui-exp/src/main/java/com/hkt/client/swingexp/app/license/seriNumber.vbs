
'Return Serial Numbers of USB Drives connected
'Bill Reid, 2011

Set objWMIService = GetObject("winmgmts:" & "{impersonationLevel=impersonate}!\\" & "." & "\root\cimv2")
Set colDiskDrives = objWMIService.ExecQuery ("Select * from Win32_DiskDrive where interfacetype like 'USB'")
For each objDiskDrive in colDiskDrives
   'Wscript.Echo "Signature: " & vbTab &  objDiskDrive.Signature        
  sernum = objDiskDrive.pnpdeviceid
    sernum = Mid(sernum, InStrRev(sernum, "\") + 1)
    sernum = Left(sernum, InStr(sernum, "&") - 1)
    wscript.echo sernum
Next