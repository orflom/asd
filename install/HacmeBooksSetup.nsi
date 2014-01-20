Name "Hacme Books"
# Defines
!define REGKEY "SOFTWARE\Foundstone Free Tools\$(^Name)"
!define VERSION 2.0.3
!define COMPANY "Foundstone Professional Services"
!define URL http://www.foundstone.com
!define GET_JAVA_URL "http://java.sun.com/j2se/downloads/"
!define ALL_USERS

# Included files
!include Sections.nsh
!include WriteEnvStr.nsh

# Reserved Files
ReserveFile "${NSISDIR}\Plugins\AdvSplash.dll"

# Variables
Var StartMenuGroup
Var JAVA_HOME
Var JAVA_HOME_FULL
Var JAVA_VER
Var JAVA_INSTALLATION_MSG
Var JAVA_HOME_ENV
Var JAVA_HOME_MISMATCH_MSG

# Installer pages
Page license
Page custom SecurityWarning
Page directory
Page instfiles

# Installer attributes
OutFile HacmeBooksSetup.exe
InstallDir "$PROGRAMFILES\Foundstone Free Tools\Hacme Books 2.0"
CRCCheck on
XPStyle on
Icon images\fs_icon_32.ico
WindowIcon on
ShowInstDetails hide
AutoCloseWindow false
LicenseData license\license.txt
VIProductVersion 2.0.2.0
VIAddVersionKey ProductName "Hacme Books"
VIAddVersionKey ProductVersion "${VERSION}"
VIAddVersionKey CompanyName "${COMPANY}"
VIAddVersionKey CompanyWebsite "${URL}"
VIAddVersionKey FileVersion ""
VIAddVersionKey FileDescription ""
VIAddVersionKey LegalCopyright ""
InstallDirRegKey HKLM "${REGKEY}" Path
ShowUninstDetails hide

;--------------------------------
;Locate Java Development Kit
;If not found, offer option to download or install anyway
;Ensure version 1.4 or higher
;
;Modified from original script by Ashwin Jayaprakash
;--------------------------------
Section "JDK Locate" JDKCheck
    Call LocateJVM
    StrCmp "" $JAVA_INSTALLATION_MSG Done NeedJava
 
    NeedJava:
        MessageBox MB_YESNO $JAVA_INSTALLATION_MSG IDYES OpenBrowserToGetJava IDNO InstallAnyway

    InstallAnyway:
        MessageBox MB_YESNO "Do you want to install $(^Name) anyway?  To run, you must install JDK 1.4+ and set the JAVA_HOME environment variable." IDYES Done IDNO CancelInstall

    OpenBrowserToGetJava:
        Exec '"explorer.exe" ${GET_JAVA_URL}'
        Quit

    CancelInstall:
        Quit
        
    Done:
SectionEnd

# Installer sections

    # TODO read Tomcat location from a properties file
    # Tomcat installation should be
    # "virgin": Fresh installation of 5.0.30 that has never been started 
    # "minimized": admin, manager, and balancer apps removed
    # Location on your hardrive may vary

Section -Main SEC0000
    SetOutPath $INSTDIR
    File userguide\HacmeBooks_UserGuide.pdf
    File known_bugs.txt
    SetOutPath $INSTDIR\tomcat
    SetOverwrite on
    ; next line should be modified to the location of your tomcat installation.
    ; base directory is C:\JavaDev\HacmeBooks\install
    ; so default location is C:\JavaDev\jakarta-tomcat-5.0.30
    File /r ..\..\jakarta-tomcat-5.0.30\*
    SetOutPath $INSTDIR\tomcat\bin\data
    File ..\data\hacmedb.script
    File ..\data\hacmedb.properties
    SetOutPath $INSTDIR\tomcat\common\endorsed
    File ..\lib\xalan-2.6.0\xalan.jar
    SetOutPath $INSTDIR\tomcat\common\lib
    File ..\lib\hsqldb\hsqldb.jar
    File ..\lib\hibernate-2.1.7\lib\jta.jar
    SetOutPath $INSTDIR\tomcat\conf\Catalina\localhost
    File ..\dist\webapps\context.xml
    SetOutPath $INSTDIR\tomcat\webapps
    File ..\dist\webapps\HacmeBooks.war
    WriteRegStr HKLM "${REGKEY}\Components" Main 1
    SetOutPath $INSTDIR\images
    File images\fs_icon_32.ico
    File images\PDF.ico
SectionEnd

Section -post SEC0001
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    WriteRegStr HKLM "${REGKEY}" StartMenuGroup $StartMenuGroup
    WriteUninstaller $INSTDIR\uninstall.exe
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    CreateShortCut "$SMPROGRAMS\$StartMenuGroup\Hacme Books User and Solution Guide v2.0.lnk" "$INSTDIR\HacmeBooks_UserGuide.pdf" "" "$INSTDIR\images\PDF.ico"
    CreateShortCut "$SMPROGRAMS\$StartMenuGroup\$(^Name) v2.0.lnk" "http://localhost:8989/HacmeBooks" "" "$INSTDIR\images\fs_icon_32.ico"
    CreateShortCut "$SMPROGRAMS\$StartMenuGroup\Uninstall $(^Name).lnk" $INSTDIR\uninstall.exe
    SetOutPath $INSTDIR\tomcat\bin
    CreateShortCut "$SMPROGRAMS\$StartMenuGroup\$(^Name) Server START.lnk" $INSTDIR\tomcat\bin\startup.bat
    CreateShortCut "$SMPROGRAMS\$StartMenuGroup\$(^Name) Server STOP.lnk" $INSTDIR\tomcat\bin\shutdown.bat
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${VERSION}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" Publisher "${COMPANY}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" URLInfoAbout "${URL}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 HKLM "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    Goto done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o un.Main UNSEC0000
    MessageBox MB_YESNO "Are you sure you want to uninstall $(^Name)?" IDYES Proceed IDNO CancelUninstall

    Proceed:
      Delete /REBOOTOK $INSTDIR\tomcat\webapps\HacmeBooks.war
      Delete /REBOOTOK $INSTDIR\tomcat\conf\Catalina\localhost\context.xml
      Delete /REBOOTOK $INSTDIR\tomcat\common\lib\jta.jar
      Delete /REBOOTOK $INSTDIR\tomcat\common\lib\hsqldb.jar
      Delete /REBOOTOK $INSTDIR\tomcat\common\endorsed\xalan.jar
      Delete /REBOOTOK $INSTDIR\images\fs_icon_32.ico
      Delete /REBOOTOK $INSTDIR\images\PDF.ico
      Delete /REBOOTOK $INSTDIR\known_bugs.txt
      RMDir /r /REBOOTOK $INSTDIR\tomcat\bin\data
      RMDir /r /REBOOTOK $INSTDIR\tomcat\bin
      RMDir /r /REBOOTOK $INSTDIR\tomcat
      RMDir /r /REBOOTOK $INSTDIR\images
      Delete /REBOOTOK $INSTDIR\HacmeBooks_UserGuide.pdf
      DeleteRegValue HKLM "${REGKEY}\Components" Main
      Goto Done

    CancelUninstall:
      Quit

    Done:
SectionEnd

Section un.post UNSEC0001
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^Name) Server START.lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^Name) Server STOP.lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^Name) v2.0.lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Hacme Books User and Solution Guide v2.0.lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Uninstall $(^Name).lnk"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue HKLM "${REGKEY}" StartMenuGroup
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /ifempty HKLM "${REGKEY}\Components"
    DeleteRegKey /ifempty HKLM "${REGKEY}"
    RMDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RMDir /REBOOTOK $INSTDIR
SectionEnd

# Installer functions
Function .onInit
    InitPluginsDir
    File /oname=$PLUGINSDIR\warning.ini .\warning.ini
    Push $R1
    File /oname=$PLUGINSDIR\spltmp.bmp images\FoundstoneLogo.bmp
    advsplash::show 1500 500 300 -1 $PLUGINSDIR\spltmp
    Pop $R1
    Pop $R1
    
    StrCpy $StartMenuGroup "Foundstone Free Tools\Hacme Books 2.0"
FunctionEnd

# Uninstaller functions
Function un.onInit
    ReadRegStr $INSTDIR HKLM "${REGKEY}" Path
    ReadRegStr $StartMenuGroup HKLM "${REGKEY}" StartMenuGroup
    !insertmacro SELECT_UNSECTION Main ${UNSEC0000}
FunctionEnd

Function LocateJVM
    ;Performs a series of checks to verify JDK is installed, version is 1.4+, and JAVA_HOME is defined
    Push $0
    Push $1
    
    ReadRegStr $JAVA_VER HKLM "SOFTWARE\JavaSoft\Java Development Kit" CurrentVersion
    StrCmp "" "$JAVA_VER" JavaNotPresent CheckJavaVer
 
    JavaNotPresent:
        StrCpy $JAVA_INSTALLATION_MSG "The Java Development Kit (JDK) is not installed on your computer.  You need version 1.4 or newer to run this program.  Do you want to download the JDK now (requires restart after installation)?"
        Goto Done
 
    CheckJavaVer:
        ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Development Kit\$JAVA_VER" JavaHome
        GetFullPathName /SHORT $JAVA_HOME "$0"
        GetFullPathName $JAVA_HOME_FULL "$0"
        StrCpy $0 $JAVA_VER 1 0
        StrCpy $1 $JAVA_VER 1 2
        StrCpy $JAVA_VER "$0$1"
        IntCmp 14 $JAVA_VER FoundCorrectJavaVer FoundCorrectJavaVer JavaVerNotCorrect
        
    FoundCorrectJavaVer:
        IfFileExists "$JAVA_HOME\bin\javaw.exe" CheckJavaHome JavaNotPresent

    CheckJavaHome:
        ; If JAVA_HOME env var is empty, create it and set to value from registry
        ReadEnvStr $JAVA_HOME_ENV JAVA_HOME
        StrCmp "" "$JAVA_HOME_ENV" CreateJavaHome
        ; If JAVA_HOME env var is set, but does not match JavaHome in registry, prompt user
        StrCmp "$JAVA_HOME_FULL" "$JAVA_HOME_ENV" Done JavaHomeMismatch

    JavaHomeMismatch:
        StrCpy $JAVA_HOME_MISMATCH_MSG "The location of Java on your machine is ambiguous.$\n$\n $JAVA_HOME_ENV (JAVA_HOME Environment Variable)$\n $JAVA_HOME_FULL (JavaHome Registry Value)$\n$\n Would you like the installer to update your JAVA_HOME environment variable with the current Java version? $\n Selecting YES is recommended or Hacme Books may not function."
        MessageBox MB_YESNO $JAVA_HOME_MISMATCH_MSG IDYES CreateJavaHome IDNO Done
        Goto Done

    CreateJavaHome:
        Push "JAVA_HOME"
        Push $JAVA_HOME_FULL
        Call WriteEnvStr
        Goto Done

    JavaVerNotCorrect:
        StrCpy $JAVA_INSTALLATION_MSG "The version of Java Development Kit (JDK) installed on your computer is $JAVA_VER.  Version 1.4 or newer is required to run this program.  Do you want to download the latest JDK now (requires restart after installation)?"
        Goto Done
        
    Done:
        Pop $1
        Pop $0
FunctionEnd

Function SecurityWarning
  ; Return value $R0 needs to be push/popped off the stack whether it is used or not
  ; this custom installer page is probably a little more complicated 
  ; than necessary because it needs to use a red font.  For no font, the code is simple:
  ;Push $R0
  ;InstallOptions::dialog $PLUGINSDIR\warning.ini
  ;Pop $R0

  Push $R0
  Push $R1
  Push $R2

    InstallOptions::initDialog /NOUNLOAD $PLUGINSDIR\warning.ini
    Pop $R0

    ReadINIStr $R1 $PLUGINSDIR\warning.ini "Field 1" "HWND"

    ;$R1 contains the HWND of the first field
    CreateFont $R2 "Tahoma" 10 700
    SetCtlColors $R1 CC0000 FFFFFF
    SendMessage $R1 ${WM_SETFONT} $R2 0

    InstallOptions::show
    Pop $R0

  Pop $R2
  Pop $R1
  Pop $R0
FunctionEnd
