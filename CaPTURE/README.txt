SYSTEM REQUIREMENTS
-------------------
CaPTURE runs on any system equipped with the Java Virtual Machine (1.7 or newer), which can be downloaded at no cost from Oracle.

INSTALLING AND RUNNING
----------------------
Download the CaPTURE folder and unzip it. The simplest way to run the program is by double clicking on the CaPTURE.jar file, however, it might not work on some systems. Alternatively, execute the program from the command line using the following command:
java -jar CaPTURE.jar

To run the application from the JAR file that is in another directory, you must specify the path of that directory:
java -jar path/CaPTURE.jar

Note that on Windows the forward slash as the directory separator should be replaced with a backslash.

BUILDING FROM SOURCE
--------------------
To build the application from source, follow the given steps:

    Download CaPTURE and unzip it.
    Change the working directory to unzipped CaPTURE folder.
    Then compile .java source files to executable .class files.
    Copy bioformats_package.jar and icon.png resource file from the src directory in the scanPackage directory that was created during the compilation.
    Create a single java archive file from multiple .class files.
    Then you can start the program.

Linux:
cd CaPTURE
javac -d . -cp src/bioformats_package.jar src/*.java
cp src/icon.png src/bioformats_package.jar scanPackage
jar cfm CaPTURE.jar MANIFEST.MF scanPackage
java -jar CaPTURE.jar

Windows:
cd CaPTURE
javac -d . -cp src\bioformats_package.jar src\*.java
robocopy src scanPackage icon.png bioformats_package.jar
jar cfm CaPTURE.jar MANIFEST.MF scanPackage
java -jar CaPTURE.jar 
