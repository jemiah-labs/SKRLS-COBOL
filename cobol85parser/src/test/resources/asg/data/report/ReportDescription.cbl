 IDENTIFICATION DIVISION.
 PROGRAM-ID. PROGLIBIMPORT.
 DATA DIVISION.
    REPORT SECTION.
    RD REPORT1
       IS GLOBAL
       PAGE LIMITS ARE 5 LINES
       HEADING 1
       FIRST DETAIL 2
       LAST DETAIL 3
       FOOTING 4.
       01 SOMEDATANAME
       .
    RD REPORT2
       IS GLOBAL
       PAGE LIMITS ARE 10 LINES
       HEADING 11
       FIRST DETAIL 12
       LAST DETAIL 13
       FOOTING 14.
       01 SOMEDATANAME
       .