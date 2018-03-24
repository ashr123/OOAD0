public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Employee.connect();
		Employee.addWorker(308007749, "Roy", "Ash", 20000);
		System.out.println(Employee.getWorker(308007749));
		Employee.getWorker(308007749).updateWorker("Roy", "Ash", true, 20000);
		System.out.println(Employee.getWorker(308007749));
	}
}