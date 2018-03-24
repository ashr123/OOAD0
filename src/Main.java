public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Employee.addEmployee(308007749, "Roy", "Ash", 20000);
		System.out.println(Employee.getEmployee(308007749));
		Employee.getEmployee(308007749).updateEmployee("Roy", "Ash", true, 20000);
		System.out.println(Employee.getEmployee(308007749));
	}
}