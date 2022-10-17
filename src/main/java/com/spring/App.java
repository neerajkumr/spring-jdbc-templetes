package com.spring;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springDao.EmployeeDao;

public class App {
	public static final Logger log = Logger.getLogger("App");

	public static void main(String[] args) {
		// LogManager.getLogManager().reset();
		ApplicationContext con = new ClassPathXmlApplicationContext("AppCon.xml");
		EmployeeDao e = (EmployeeDao) con.getBean("edao");
		log.setLevel(Level.ALL);
		Scanner sc = new Scanner(System.in);
		int type = 1;
		String name = "";
		int id;
		String DOB;
		while (type > 0 && type < 6) {
			System.out.println(
					"1 -> insert \n 2->update \n 3->delete by Name \n 4->insert By PS \n 5->All EmPloyees Data\n 6->exit");
			type = sc.nextInt();
			switch (type) {
			case 1: {
				System.out.println("Enter the name ,id, dob");
				name = sc.next();
				id = sc.nextInt();
				DOB = sc.next();
				e.insertE(new Employee(name, id, DOB));
				log.info("Employee inserted successful");
				break;
			}
			case 2: {
				System.out.println("Enter name to update");
				name = sc.next();
				try {
					System.out.println("Enter ID for which name to be updated");
					id = sc.nextInt();
					e.updateE(id, name);
					log.info("Employee name was updated based on his ID");
				} catch (NumberFormatException a) {
					log.warning("=====Enter correct ID=====");
				} catch (Exception a) {
					log.warning("ID not found");
				}
				break;
			}
			case 3: {
				try {
					System.out.println("Enter name to delete");
					name = sc.next();
					e.deleteE(name);
					log.info("The Row Deletion was Successful");
				} catch (NumberFormatException a) {
					log.warning("=====Enter the correct format=====");
				} catch (Exception a) {
					if (name.startsWith("1"))
						log.warning("Name was not found");
				}
				break;
			}
			case 4: {
				System.out.println("Enter the name ,id, dob");
				name = sc.next();
				id = sc.nextInt();
				DOB = sc.next();
				e.insertWithPS(new Employee(name, id, DOB));
				log.info("==Data inserted using Prepared statement template==");
				break;
			}
			case 5: {

				e.getByID(4).forEach((l) -> System.out.println(l));
				log.info("==Fetching Employees Data Using ReseltSet Extracter==");
				break;
			}
			default:
				if (type > 6) {
					System.exit(0);
				}
			}
		}
		sc.close();
	}
}
