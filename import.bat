@Echo off
set FROM=C:\Users\Ernest\workspace\UMLToCode\src
set TO=C:\Users\Ernest\Documents\ActiveJavaProjects\umlet\UML-to-Code\src
rd /S /Q %TO%
xcopy /E /Y %FROM% %TO%\
pause