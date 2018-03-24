public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Worker.connect();
//		Worker.addWorker(308007749, "Roy", "Ash", 20000);
//		System.out.println(Worker.getWorker(308007749));
//		Worker.getWorker(308007749).updateWorker("Roy", "Ash", true, 20000);
		System.out.println(Worker.getWorker(308007749));
	}
}