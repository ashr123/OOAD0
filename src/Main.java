public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		
		boolean exit=false;
		while (!exit)
		{
			int id;
			double salary;
			String fname, lname;
			boolean isLeaving=false;
			System.out.println("Enter command, or 'exit' to end program:");
			switch (args[0])
			{
				case "Add":
				{
					if (args.length!=5)
					{
						System.out.println("Illegal arguments for Add command");
						break;
					}
					id=Integer.parseInt(args[1]);
					fname=args[2];
					lname=args[3];
					salary=Double.parseDouble(args[4]);
					Employee.addEmployee(id, fname, lname, salary);
					break;
				}
				case "Update":
				{
					if (args.length!=6)
					{
						System.out.println("Illegal arguments for Update command");
						break;
					}
					id=Integer.parseInt(args[1]);
					fname=args[2];
					lname=args[3];
					salary=Double.parseDouble(args[4]);
					if (args[5].equals("Y"))
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
					if (args.length!=2)
					{
						System.out.println("Illegal arguments for Get command");
						break;
					}
					id=Integer.parseInt(args[1]);
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