import java.util.Scanner;

public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		boolean exit=false;
		while (!exit)
		{
			int id;
			double salary;
			String fname, lname;
			boolean isLeaving=false;
			System.out.println("Enter command, or 'exit' to end program:");
			String comm=sc.nextLine();
			switch (comm)
			{
				case "Add":
				{
					System.out.print("Id: ");
					id=Integer.parseInt(sc.nextLine());
					System.out.print("First name: ");
					fname=sc.nextLine();
					System.out.print("Last name: ");
					lname=sc.nextLine();
					System.out.print("Salary: ");
					salary=Double.parseDouble(sc.nextLine());
					Employee.addEmployee(id, fname, lname, salary);
					break;
				}
				case "Update":
				{
					System.out.print("Id: ");
					id=Integer.parseInt(args[1]);
					System.out.print("Fist name: ");
					fname=sc.nextLine();
					System.out.print("Last name: ");
					lname=sc.nextLine();
					System.out.print("Salary: ");
					salary=Double.parseDouble(sc.nextLine());
					System.out.println("Is leaving(Y/N): ");
					if(sc.nextLine().equals("Y"))
						isLeaving=true;
					Employee emp=Employee.getEmployee(id);
					if (emp==null)
					{
						System.out.println("No such employee exists");
						break;
					}
					emp.updateEmployee(fname, lname, isLeaving, salary);
					break;
				}
				case "Get":
				{
					System.out.print("Id: ");
					id=Integer.parseInt(sc.nextLine());
					System.out.println(Employee.getEmployee(id));
				}
				case "exit":
				{
					exit=true;
					break;
				}
				default:
				{
					System.out.println("Illegal command");
					break;
				}
			}
		}

		//Employee.addEmployee(308007749, "Roy", "Ash", 20000,null);
		//System.out.println(Employee.getEmployee(308007749));
		//Employee.getEmployee(308007749).updateEmployee("Roy", "Ash", true, 20000);
		//System.out.println(Employee.getEmployee(308007749));
	}
}