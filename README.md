Project Title: OnCampus Timesheet and Payroll Management System

The directory structure used in this project is.

1. oncampuspayroll - This directory is the root directory of the project which contains sub folders like

	1.1 menus - This directory is stored with homemenu.php which contains menu nav code that can be included in the main 
						web pages such as index.php, about.php, contact.php.

	1.2 includes - This diretory is stored with head_includes.php which contain cdn links for bootstrap and jquery etc.  This file
						is included in all web pages of this project.

	1.3 images - This directory is used for storing image which are used in the main pages like about, contact, home.
	
	1.4 dbaseutils - This directory is stored with dbconnect.php where database connection string is stored.  This file is included
							in all php program files where database programming is required.
							
	1.5 logins - This directory is stored with login pages of admin, manager and student.
	
	1.6 admin - This directory contains admin web pages source files using which the administrative operations are performed.
	
		1.6.1 menu - This directory contains admin_home.php menu file used in admin portal.
		
		1.6.2 images - This directory is stored with image files that are displayed in admin portal pages.	
		
		1.6.3 uploads - This directory is stored with any document, images uploaded in the admin portal web pages.
		
	1.7 manager - This directory contains manager web pages source files using which the manager operations are performed.
	
		1.7.1 menu - This directory contains manager_menu.php menu file and also other functionalities of the manager such as add_time_sheet.php file used in manager portal web pages.
		
		1.7.2 images - This directory is stored with image files that are displayed in manager portal pages.	
	
	1.8 student - This directory contains student web pages source files using which the student operations are performed.
	
		1.8.1 menu - This directory contains student_home.php menu file used in student portal web pages.
		
		1.8.2 images - This directory is stored with image files that are displayed in student portal pages.	
      
1.9 Meeting minutes - this file contains the brief textual description about the meetings conducted which includes details like duration,attendees,topics discussed and dates.

1.10 Planning Documents - This Folder contains files which are required for the project planning purpose such as Gantt Chart.
