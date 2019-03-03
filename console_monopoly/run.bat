@ECHO OFF
e:
cd E:\eclipse_workspace\s4pj1

mode 108,37
java -cp bin Monopoly
pause
exit
cd ../bin
cd src
javac -d ../bin Monopoly.java