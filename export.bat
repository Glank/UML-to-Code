@Echo off
set FROM=C:\Users\Ernest\Documents\ActiveJavaProjects\umlet\UML-to-Code\src
set TO=C:\Users\Ernest\workspace\UMLToCode\src
rd /S /Q %TO%
xcopy /E /Y %FROM% %TO%\
pause